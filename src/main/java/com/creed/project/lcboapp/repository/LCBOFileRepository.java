package com.creed.project.lcboapp.repository;

import com.creed.project.lcboapp.common.Constants;
import com.creed.project.lcboapp.domain.model.LCBOFileTypeModel;
import com.creed.project.lcboapp.persistence.repository.LCBOAppFeedJpaRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * This class handles all details for the particular actions performed on files for this ETL
 *
 * The difference between this and FileUtils is that FileUtils is agnostic in its functionality, while this class
 * handles a specific implemenation.
 */
@Component
public class LCBOFileRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(LCBOFileRepository.class);
    private static final Map<String, File> currentLCBOFilesRepository = new ConcurrentHashMap<>();

    private static final byte[] buffer = new byte[1024];

    @Autowired
    private Environment environment;

    @Autowired
    private LCBOAppFeedJpaRepository lcboAppFeedJpaRepository;

    /**
     * Default Constructor
     */
    public LCBOFileRepository() {
        super();
    }

    /**
     * Display directories being used
     */
    @PostConstruct
    private void initRepository() {
        LOGGER.debug("Using PROP_LCBO_FEED_ARCHIVE_DIR: {}",
                environment.getRequiredProperty(Constants.PROP_LCBO_FEED_ARCHIVE_DIR));
        LOGGER.debug("Using PROP_LCBO_FEED_DOWNLOAD_DIR: {}",
                environment.getRequiredProperty(Constants.PROP_LCBO_FEED_DOWNLOAD_DIR));
        LOGGER.debug("Using PROP_LCBO_FEED_WORKING_DIR: {}",
                environment.getRequiredProperty(Constants.PROP_LCBO_FEED_WORKING_DIR));
        LOGGER.debug("Using PROP_LCBO_INVENTORY_FEED_LINE_TO_SKIP: {}",
                environment.getRequiredProperty(Constants.PROP_LCBO_INVENTORY_FEED_LINE_TO_SKIP));
        LOGGER.debug("Using PROP_LCBO_PRODUCT_FEED_LINE_TO_SKIP: {}",
                environment.getRequiredProperty(Constants.PROP_LCBO_PRODUCT_FEED_LINE_TO_SKIP));
        LOGGER.debug("Using PROP_LCBO_STORE_FEED_LINE_TO_SKIP: {}",
                environment.getRequiredProperty(Constants.PROP_LCBO_STORE_FEED_LINE_TO_SKIP));
    }

    public void loadFileRepository() {

    }

    /**
     * Filters and stores all files for current day data into currentLCBOFileRepository map
     *
     * @param fileMap
     *
     * todo is this still needed?
     */

    private void filterFeedFiles(final Map<String, File> fileMap) {

        // Filtering Processed File
        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
            try {
                String filename = entry.getValue().getName();
                Path path = Paths.get(entry.getValue().getAbsoluteFile().toURI());
                BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
                long creationTime = attr.creationTime().toMillis();
                long count = lcboAppFeedJpaRepository.countByFeedFileName(filename);

                LOGGER.debug("LCBO Data File Feed: {}, {}", filename, creationTime);

                if (count < 1) {
                    currentLCBOFilesRepository.put(entry.getKey(), entry.getValue());
                    LOGGER.debug("LCBO Data File  Feed: {}, {}", entry.getKey(), entry.getValue().getPath());
                }
            } catch (IOException e) {
                LOGGER.debug("{}", e.getMessage(), e);
            }
        }
    }

    /**
     * Returns current size of the repository
     *
     * @return size of feed repository
     */
    public int size() {
        return currentLCBOFilesRepository.size();
    }

    /**
     * @param feedId the feed id to get feed file
     * @return file object if found, null otherwise
     */
    public File getFeedFile(final String feedId) {
        if (feedId == null || feedId.isEmpty()) {
            return null;
        }

        return currentLCBOFilesRepository.get(feedId);
    }

    /**
     * @return the feed Id to process if exists, null otherwise
     */
    public String getNextFeedId() {
        // check repository whether empty or not
        if (currentLCBOFilesRepository.isEmpty()) {
            return null;
        }

        return currentLCBOFilesRepository.entrySet().iterator().next().getKey();
    }

    /**
     * @param feedId the feed Id
     * @return the LCBO File Feed type
     */
    public LCBOFileTypeModel getLCBOFileType(final String feedId) {
        LCBOFileTypeModel lcboFileType = null;

        if (StringUtils.isNotBlank(feedId)) {
            for (LCBOFileTypeModel item : LCBOFileTypeModel.values()) {
                if (feedId.startsWith(item.getName().toLowerCase().substring(0, item.toString().length()-1))) {
                    lcboFileType = item;
                }
            }
        }

        return lcboFileType;
    }

    /**
     * Remove cost import feed in the repository
     *
     * @param feedId feed Id
     * @return file if removed from feed repository, null otherwise
     */
    public File remove(final String feedId) {
        // validate feedId
        if (feedId == null || feedId.isEmpty()) {
            return null;
        }

        // Search feed and remove if exist
        File feed = null;
        if (currentLCBOFilesRepository.containsKey(feedId)) {
            feed = currentLCBOFilesRepository.remove(feedId);
        }

        if(feed != null){
            FileUtils.deleteQuietly(feed);
        }

        // return removed feed if exists, null otherwise
        return feed;
    }

    //Public methods - Sorted by order of operation in app

    /**
     * Download the latest file from the LCBO API source
     *
     * It performs the following actions:
     * - Craft the URL to download the latest zip file
     * - Download the zip file and save it to the download directory
     */
    public String downloadLatestLCBODataFile() {

        String newZipFileName;

        //If there's anything in jobContext, add +1. If there's nothing, check the archive directory and get the latest
        // file. If there is, get the most recent file. If there's none, populate with 1

        newZipFileName = listFilesForFolder(environment.getRequiredProperty(Constants.PROP_LCBO_FEED_ARCHIVE_DIR));

        if (!newZipFileName.isEmpty()) {
            //get current value and increment it by one
            Integer zipFileValue = Integer.parseInt(newZipFileName);

            zipFileValue++;

            newZipFileName = zipFileValue.toString();
        } else {
            newZipFileName = "1";
        }

//        if (!doesFileExist(newZipFileName)) {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();

            HttpGet getFile = new HttpGet("http://static.lcboapi.com/datasets/" + newZipFileName + ".zip");

            try (CloseableHttpResponse response = httpClient.execute(getFile)) {
                writeFile(response, newZipFileName);
            } catch (ClientProtocolException cpe) {
                LOGGER.error("A client protocal issue occured.", cpe.getMessage());
            } catch (IOException ioe) {
                LOGGER.error("Unable to access the file.", ioe.getMessage());
            }
//        } else {
//            throw new FileAlreadyExistsException("File already exists. File has not been moved to archive dir. Aborting.");
//        }

        return newZipFileName;
    }

    /**
     * Unpack the latest file currently residing in the download folder
     *
     * It performs the following actions
     * - Unpacks the downloaded file
     * - Export all unpacked files to the working directory
     */
    public void unpackLatestLCBODataFile(final String zipFileName) throws FileNotFoundException {
        if (doesFileExist(zipFileName)) {
            currentLCBOFilesRepository.clear();
            currentLCBOFilesRepository.put("inventories.csv", new File(
                                        environment.getRequiredProperty(Constants.PROP_LCBO_FEED_WORKING_DIR) +
                                                    "inventories.csv"));
            currentLCBOFilesRepository.put("products.csv", new File(
                    environment.getRequiredProperty(Constants.PROP_LCBO_FEED_WORKING_DIR) +
                            "products.csv"));
            currentLCBOFilesRepository.put("stores.csv", new File(
                    environment.getRequiredProperty(Constants.PROP_LCBO_FEED_WORKING_DIR) +
                            "stores.csv"));
            //unpackFile(zipFileName);
        } else {
            throw new FileNotFoundException("File to unpack does not exists. Aborting.");
        }
    }

    //Helper Methods - Sort by letter

    /**
     * Does the file specified exist?
     *
     * @return
     */
    private boolean doesFileExist(final String fileName) {
        File file = new File(environment.getRequiredProperty(Constants.PROP_LCBO_FEED_DOWNLOAD_DIR) +
                File.separator + fileName + ".zip");

        return file.exists();
    }

    /**
     * Unpacks the current zip file
     */
    private void unpackFile(final String zipFileName) {
        currentLCBOFilesRepository.clear();

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(environment.getRequiredProperty(
                Constants.PROP_LCBO_FEED_DOWNLOAD_DIR) + File.separator + zipFileName + ".zip"))) {

            ZipEntry zipEntry = zis.getNextEntry();

            while(zipEntry != null){
                final String fileName = zipEntry.getName();
                final String filePath = environment.getRequiredProperty(Constants.PROP_LCBO_FEED_WORKING_DIR) + fileName;

                File newFile = new File(filePath);

                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    for (int len; (len = zis.read(buffer)) > 0; ) {
                        fos.write(buffer, 0, len);
                    }
                }

                currentLCBOFilesRepository.put(fileName, newFile);

                Path path = Paths.get(filePath);
                BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
                long creationTime = attr.creationTime().toMillis();

                LOGGER.debug("LCBO Data File Feed: {}, {}", fileName, creationTime);

                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
        } catch (FileNotFoundException fnfe) {
            LOGGER.error("Unable to find file to read from.", fnfe.getMessage());
        } catch (IOException ioe) {
            LOGGER.error("Unable to write to the file.", ioe.getMessage());
        }
    }

    private void writeFile(CloseableHttpResponse response, String zipFileName) {
        try (OutputStream output = new FileOutputStream(environment.getRequiredProperty(
                Constants.PROP_LCBO_FEED_DOWNLOAD_DIR) + File.separator + zipFileName + ".zip")) {
            final InputStream input = response.getEntity().getContent();

            for (int length; (length = input.read(buffer)) > 0; ) {
                output.write(buffer, 0, length);
            }
        } catch (FileNotFoundException fnfe) {
            LOGGER.error("Unable to find file to write too.", fnfe.getMessage());
        } catch (IOException ioe) {
            LOGGER.error("Unable to write to the file.", ioe.getMessage());
        }
    }

    private String listFilesForFolder(final String folderName) {
        TreeSet<String> fileNameList = new TreeSet<>();
        String latestFileName;

        File folder = new File(folderName);

        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (!fileEntry.isDirectory()) {
                fileNameList.add(FilenameUtils.removeExtension(fileEntry.getName()));
            }
        }

        if (!fileNameList.isEmpty()) {
            latestFileName = fileNameList.last();
        } else {
            latestFileName = "";
        }

        return latestFileName;
    }
}