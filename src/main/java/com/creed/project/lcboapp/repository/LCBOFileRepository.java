package com.creed.project.lcboapp.repository;

import com.creed.project.lcboapp.common.Constants;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class handles all details for the particular actions performed on files for this ETL
 *
 * The difference between this and FileUtils is that FileUtils is agnostic in its functionality, while this class
 * handles a specific implemenation.
 */
@Component
public class LCBOFileRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(LCBOFileRepository.class);
    private static final byte[] buffer = new byte[1024];

    private static final Map<String, File> currentLCBOFileRepository = new ConcurrentHashMap<>();

    private Map<String,String> jsonData = new HashMap<>();

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

//    public void loadFileRepository() {
//        // Clear repository before loading file list:
//        fileRepository.clear();
//
//        String ocsInputDir = environment.getRequiredProperty(Constants.PROP_COST_OCS_FEED_INPUT_DIR);
//        String mfgInputDir = environment.getRequiredProperty(Constants.PROP_COST_MFG_FEED_INPUT_DIR);
//
//        LOGGER.debug("OCS Input Path: {}", ocsInputDir);
//        LOGGER.debug("MFG Input Path: {}", mfgInputDir);
//
//        filterFeedFiles(CostType.OCS, FeedUtils.getFeedFiles(ocsInputDir));
//        filterFeedFiles(CostType.MANUFACTURING, FeedUtils.getFeedFiles(mfgInputDir));
//    }


    //Public methods - Sorted by order of operation in app

    /**
     * Download the latest file from the LCBO API source
     *
     * It does the following actions:
     * - Craft the URL to download the latest zip file
     * - Download the zip file and save it to the download directory
     */
    public void downloadLatestLCBODataFile() throws FileAlreadyExistsException {
        if (!doesFileExist()) {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();

            HttpGet getFile = new HttpGet("http://static.lcboapi.com/datasets/1.zip");

            try (CloseableHttpResponse response = httpClient.execute(getFile)) {
                writeFile(response);
            } catch (ClientProtocolException cpe) {
                LOGGER.error("A client protocal issue occured.", cpe.getMessage());
            } catch (IOException ioe) {
                LOGGER.error("Unable to access the file.", ioe.getMessage());
            }
        } else {
            throw new FileAlreadyExistsException("File already exists. File has not been moved to archive dir. Aborting.");
        }
    }

    //Helper Methods - Sort by letter

    private void writeFile(CloseableHttpResponse response) {
        try (OutputStream output = new FileOutputStream(environment.getRequiredProperty(
                Constants.PROP_LCBO_FEED_DOWNLOAD_DIR) +
                File.separator + "1.zip")) {
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

    private boolean doesFileExist() {
        File file = new File(environment.getRequiredProperty(Constants.PROP_LCBO_FEED_DOWNLOAD_DIR) +
                                File.separator + "1.zip");

        return file.exists();
    }
}