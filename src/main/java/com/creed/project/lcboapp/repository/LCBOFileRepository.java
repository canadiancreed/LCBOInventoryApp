package com.creed.project.lcboapp.repository;

import com.creed.project.lcboapp.common.Constants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.creed.project.lcboapp.common.Constants.PROP_LCBO_FEED_DOWNLOAD_DIR;

/**
 * This class handles all details for the particular actions performed on files for this ETL
 *
 * The difference between this and FileUtils is that FileUtils is agnostic in its functionality, while this class
 * handles a specific implemenation.
 */
@Component
public class LCBOFileRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(LCBOFileRepository.class);
    private static final Map<String, File> lcboFileRepository = new ConcurrentHashMap<>();

    @Autowired
    private Environment environment;

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
                environment.getRequiredProperty(PROP_LCBO_FEED_DOWNLOAD_DIR));
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
     * Download the latest file from the LCBO API source
     */
    public void downloadLatestLCBODataFile() {
        // Clear repository of file data in repository map:
        lcboFileRepository.clear();

        //determine what file should be downloaded

        //open connection to LCBO API and download file

        try {
            final String fileName = "1.zip";

            URL url = new URL(environment.getRequiredProperty(Constants.PROP_LCBO_API_DATASET_URL) + fileName +
                    "?access_key=" + environment.getRequiredProperty(Constants.PROP_LCBO_FEED_ACCESS_KEY));

            try (BufferedInputStream bis = new BufferedInputStream(url.openStream())) {
                try (FileOutputStream fis = new FileOutputStream(environment.getRequiredProperty(Constants.PROP_LCBO_FEED_DOWNLOAD_DIR) + fileName)) {

                    byte[] buffer = new byte[1024];

                    int count = 0;

                    while ((count = bis.read(buffer, 0, 1024)) != -1) {
                        fis.write(buffer, 0, count);
                    }
                }
            } catch (IOException ioe) {
                LOGGER.debug("The file could not be created in the specified location. File location is ",
                        ioe.getMessage());
            }
        } catch (MalformedURLException mURLe) {
            LOGGER.debug("The URL is not valid. URL attempted is ", mURLe.getMessage());
        }
    }

    /**
     * Unpack lcbo data file to working directory
     */
    public void unpackLCBODataFIleToWorkingDirectory() throws IOException {
//        String workingDir = environment.getRequiredProperty(Constants.PROP_DESC_FEED_WORKING_DIR);
//        FeedUtils.moveFiles(workingDir, lcboFileRepository);
//
//        // Debug Message
//        for (Map.Entry<String, File> entry : lcboFileRepository.entrySet()) {
//            LOGGER.debug("{}, {}", entry.getKey(), entry.getValue().getPath());
//        }
    }

    /**
     * Insert all files in current LCBO data file collection into file repository
     */
    public void loadLCBOFileRepository() {
//        // Get description feed input location path
//        String inputDir = environment.getRequiredProperty(Constants.PROP_DESC_FEED_INPUT_DIR);
//        LOGGER.debug("Input Path: {}", inputDir);
//
//        Map<String, File> fileMap = FeedUtils.getFeedFiles(inputDir);
//
//        // DPT-RULE-3 Policy: Feed Eligibility and Retries
//        // A feed is considered eligible when
//        // a)it has not previously been processed
//        // b)it is not currently being processed
//
//        // Filtering Processed File
//        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
//            try {
//                String filename = entry.getValue().getName();
//                Path path = Paths.get(entry.getValue().getAbsoluteFile().toURI());
//                BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
//                Date creationTime = new Date(attr.creationTime().toMillis());
//
//                //Do we want to save the file data into db?
//                //long count = feedJpaRepository.countByFeedFileNameAndFeedFileCreationTime(filename, creationTime);
//
//                LOGGER.debug("DESC Feed: {}, {}", filename, creationTime);
//
//                if (count < 1) {
//                    lcboFileRepository.put(entry.getKey(), entry.getValue());
//                    LOGGER.debug("DESC Feed: {}, {}", entry.getKey(), entry.getValue().getPath());
//                }
//            } catch (IOException e) {
//                LOGGER.debug("{}", e.getMessage(), e);
//            }
//        }
    }

    /**
     * Move latest data file to archive directory.
     */
    public void archiveLCBODataFile() throws IOException {

    }

    /**
     * Delete all files in working directory.
     */
    public void clearWorkingDirectory() {

    }

    /**
     * Returns current size of the repository
     *
     * @return size of feed repository
     */
    public int size() {
        return lcboFileRepository.size();
    }

    /**
     * @param feedId the feed id to get feed file
     * @return file object if found, null otherwise
     */
    public File getFeedFile(final String feedId) {
        if (feedId == null || feedId.isEmpty()) {
            return null;
        }
        return lcboFileRepository.get(feedId);
    }

    /**
     * @return the feed Id to process if exists, null otherwise
     */
    public String getNextFeedId() {
        // check repository whether empty or not
        if (lcboFileRepository.isEmpty()) {
            return null;
        }

        String feedId = null;

        for (Map.Entry<String, File> entry : lcboFileRepository.entrySet()) {
            feedId = entry.getKey();
            break;
        }

        // return feed if exist, null otherwise
        return feedId;
    }

    /**
     * Remove lcbo file feed in the repository
     *
     * @param feedId feed Id
     * @return file if removed from feed repository, null otherwise
     */
    public File remove(String feedId) {
        // validate feedId
        if (feedId == null || feedId.isEmpty()) {
            return null;
        }

        // Search feed and remove if exist
        File feed = null;
        if (lcboFileRepository.containsKey(feedId)) {
            feed = lcboFileRepository.remove(feedId);
        }

        if (feed != null) {
            FileUtils.deleteQuietly(feed);
        }

        // return removed feed if exists, null otherwise
        return feed;
    }

    public void loadFileRepository() {
    }

    public void moveFeedFilesToWorkingDir() {
    }

    //Temp

    // Using Java IO
    public static void downloadFileFromUrlWithJavaIO(String fileName, String fileUrl)
            throws MalformedURLException, IOException {
        BufferedInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            URL fileUrlObj=new URL(fileUrl);
            inStream = new BufferedInputStream(fileUrlObj.openStream());
            outStream = new FileOutputStream(fileName);

            byte data[] = new byte[1024];
            int count;
            while ((count = inStream.read(data, 0, 1024)) != -1) {
                outStream.write(data, 0, count);
            }
        } finally {
            if (inStream != null)
                inStream.close();
            if (outStream != null)
                outStream.close();
        }
    }

    // Using common IO
    public static void downloadFileFromUrlWithCommonsIO(String fileName,
                                                        String fileUrl) throws MalformedURLException, IOException {
        FileUtils.copyURLToFile(new URL(fileUrl), new File(fileName));
    }

    // Using NIO
    private static void downloadFileFromURLUsingNIO(String fileName,String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fOutStream = new FileOutputStream(fileName);
        fOutStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fOutStream.close();
        rbc.close();
    }
}
