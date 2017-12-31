package com.creed.project.lcboapp.repository;

import com.creed.project.lcboapp.common.Constants;
import com.creed.project.lcboapp.common.FeedUtils;
import com.creed.project.lcboapp.domain.model.LCBOFileTypeModel;
import com.creed.project.lcboapp.persistence.repository.LCBOAppFeedJpaRepository;
import org.apache.commons.io.FileUtils;
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

    /**
     * List files in the feed input directory and store in feed repository
     */

    public void loadZipFileRepository() {
        // Clear repository before loading file list:
        currentLCBOFilesRepository.clear();

        final String currentLCBOZipFiles = environment.getProperty(Constants.PROP_LCBO_FEED_DOWNLOAD_DIR);

        LOGGER.debug("LCBO Data Zip Files Input Path: {}", currentLCBOZipFiles);

        filterFeedFiles(FeedUtils.getFeedFiles(currentLCBOZipFiles));
    }

    public void loadLCBODataFileRepository() {
        // Clear repository before loading file list:
        currentLCBOFilesRepository.clear();

        final String currentLCBODataFiles = environment.getProperty(Constants.PROP_LCBO_FEED_WORKING_DIR);

        LOGGER.debug("LCBO Data Files Input Path: {}", currentLCBODataFiles);

        filterFeedFiles(FeedUtils.getFeedFiles(currentLCBODataFiles));
    }

    /**
     * Filters and stores all files for current day data into currentLCBOFileRepository map
     *
     * @param fileMap
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

        String feedId = null;

        for (Map.Entry<String, File> entry : currentLCBOFilesRepository.entrySet()) {
            feedId = entry.getKey();
            break;
        }

        // return feed if exist, null otherwise
        return feedId;
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
    public void downloadLatestLCBODataFile() throws FileAlreadyExistsException {
//        if (!doesFileExist()) {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();

            HttpGet getFile = new HttpGet("http://static.lcboapi.com/datasets/1.zip");

            try (CloseableHttpResponse response = httpClient.execute(getFile)) {
                writeFile(response);
            } catch (ClientProtocolException cpe) {
                LOGGER.error("A client protocal issue occured.", cpe.getMessage());
            } catch (IOException ioe) {
                LOGGER.error("Unable to access the file.", ioe.getMessage());
            }
//        } else {
//            throw new FileAlreadyExistsException("File already exists. File has not been moved to archive dir. Aborting.");
//        }
    }

    /**
     * Unpack the latest file currently residing in the download folder
     *
     * It performs the following actions
     * - Unpacks the downloaded file
     * - Export all unpacked files to the working directory
     */
    public void unpackLatestLCBODataFile() throws FileNotFoundException {
        if (doesFileExist()) {
            unpackFile();
        } else {
            throw new FileNotFoundException("File to unpack does not exists. Attempting to redownload file.");
        }
    }

    //Helper Methods - Sort by letter

    /**
     * Does the file specified exist?
     *
     * todo - file is hardcoded. Fix this
     * @return
     */
    private boolean doesFileExist() {
        File file = new File(environment.getRequiredProperty(Constants.PROP_LCBO_FEED_DOWNLOAD_DIR) +
                File.separator + "1.zip");

        return file.exists();
    }

    private void writeFile(CloseableHttpResponse response) {
        try (OutputStream output = new FileOutputStream(environment.getRequiredProperty(
                Constants.PROP_LCBO_FEED_DOWNLOAD_DIR) + File.separator + "1.zip")) {
            final InputStream input = response.getEntity().getContent();

            for (int length; (length = input.read(buffer)) > 0; ) {
                output.write(buffer, 0, length);
            }

            output.close();
        } catch (FileNotFoundException fnfe) {
            LOGGER.error("Unable to find file to write too.", fnfe.getMessage());
        } catch (IOException ioe) {
            LOGGER.error("Unable to write to the file.", ioe.getMessage());
        }
    }

    /**
     * Unpacks the current zip file
     *
     * todo - Filename hardcoded. Fix
     */
    private void unpackFile() {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(environment.getRequiredProperty(
                Constants.PROP_LCBO_FEED_DOWNLOAD_DIR) + File.separator + "1.zip"))) {

            ZipEntry zipEntry = zis.getNextEntry();

            while(zipEntry != null){
                String fileName = zipEntry.getName();
                File newFile = new File(environment.getRequiredProperty(Constants.PROP_LCBO_FEED_WORKING_DIR)
                                            + fileName);
                FileOutputStream fos = new FileOutputStream(newFile);

                for (int len; (len = zis.read(buffer)) > 0; ) {
                    fos.write(buffer, 0, len);
                }

                fos.close();

                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
        } catch (FileNotFoundException fnfe) {
            LOGGER.error("Unable to find file to read from.", fnfe.getMessage());
        } catch (IOException ioe) {
            LOGGER.error("Unable to write to the file.", ioe.getMessage());
        }
    }
}