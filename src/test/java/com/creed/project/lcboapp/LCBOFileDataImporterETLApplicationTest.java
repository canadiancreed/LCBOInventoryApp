package com.creed.project.lcboapp;

import com.jayway.awaitility.Awaitility;

import com.creed.project.lcboapp.common.Constants;
import com.creed.project.lcboapp.persistence.repository.LCBOAppTransactionJpaRepository;
import com.creed.project.lcboapp.persistence.repository.LCBOInventoryJpaRepository;
import com.creed.project.lcboapp.persistence.repository.LCBOProductJpaRepository;
import com.creed.project.lcboapp.persistence.repository.LCBOStoreJpaRepository;
import com.creed.project.lcboapp.repository.LCBOFileRepository;
import com.creed.project.lcboapp.repository.TransactionRepository;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.runner.RunWith;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LCBOFileDataImporterETLApplicationTest {

    private static Path testPath;
    private ExecutionContext executionContext;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private LCBOAppTransactionJpaRepository lcboAppTransactionJpaRepository;

    @Autowired
    private LCBOFileRepository lcboFileRepository;

    @Autowired
    private LCBOInventoryJpaRepository lcboInventoryJpaRepository;

    @Autowired
    private LCBOProductJpaRepository lcboProductJpaRepository;

    @Autowired
    private LCBOStoreJpaRepository lcboStoreJpaRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeClass
    public static void runOnceBeforeClass() throws IOException {
        // Create Test Directory
        testPath = Paths.get(FileUtils.getTempDirectoryPath() + File.separator + "lcboapp/data");
//        File inventoryFile = FileUtils.getFile(testPath.toFile(), "inventories");
//        File productFile = FileUtils.getFile(testPath.toFile(), "products");
//        File storeFile = FileUtils.getFile(testPath.toFile(), "stores");
        File archiveDir = FileUtils.getFile(testPath.toFile(), "archive");
        File downloadDir = FileUtils.getFile(testPath.toFile(), "download");
        File workingDir = FileUtils.getFile(testPath.toFile(), "working");

        FileUtils.forceMkdir(archiveDir);
        FileUtils.forceMkdir(downloadDir);
        FileUtils.forceMkdir(workingDir);

        System.out.println("Create Archive Directory: " + archiveDir.getPath());
        System.out.println("Create Download Directory: " + downloadDir.getPath());
        System.out.println("Create Working Directory: " + workingDir.getPath());

        //Set Test Enviroment
        System.setProperty(Constants.PROP_LCBO_FEED_ARCHIVE_DIR, archiveDir.getPath());
        System.setProperty(Constants.PROP_LCBO_FEED_DOWNLOAD_DIR, downloadDir.getPath());
        System.setProperty(Constants.PROP_LCBO_FEED_WORKING_DIR, workingDir.getPath());
    }

    @AfterClass
    public static void runOnceAfterClass() throws IOException {
        System.out.println("Cleanup Directory: " + testPath.toString());

        File archiveDir = FileUtils.getFile(testPath.toFile(), "archive");
        File downloadDir = FileUtils.getFile(testPath.toFile(), "download");
        File workingDir = FileUtils.getFile(testPath.toFile(), "working");

        Collection<File> archiveFiles =  FileUtils.listFiles(archiveDir, null, false);
        for(File file: archiveFiles){
            FileUtils.deleteQuietly(file);
        }

        Collection<File> downloadFiles = FileUtils.listFiles(downloadDir, null, false);
        for(File file: downloadFiles){
            FileUtils.deleteQuietly(file);
        }

        Collection<File> workingFiles = FileUtils.listFiles(workingDir, null, false);
        for(File file: workingFiles){
            FileUtils.deleteQuietly(file);
        }
    }

    @Before
    public void setUp() throws Exception {
        transactionRepository.beginTransaction();
        lcboFileRepository.loadFileRepository();
    }

    @After
    public void tearDown() throws Exception {
        Long transId = transactionRepository.getTransactionId();

//        lcboInventoryJpaRepository.deleteAllByTransId(transId);
//        lcboProductJpaRepository.deleteAllByTransId(transId);
//        lcboStoreJpaRepository.deleteAllByTransId(transId);

        lcboAppTransactionJpaRepository.deleteAllByTransId(transId);

        File archiveInputDir = FileUtils.getFile(testPath.toFile(), "archive");
        Collection<File> archiveFiles = FileUtils.listFiles(archiveInputDir, null, false);
        for (File file : archiveFiles) {
            FileUtils.deleteQuietly(file);
        }

        File downloadInputDir = FileUtils.getFile(testPath.toFile(), "download");
        Collection<File> downloadFiles = FileUtils.listFiles(downloadInputDir, null, false);
        for (File file : downloadFiles) {
            FileUtils.deleteQuietly(file);
        }

        File workingInputDir = FileUtils.getFile(testPath.toFile(), "working");
        Collection<File> workingFiles = FileUtils.listFiles(workingInputDir, null, false);
        for (File file : workingFiles) {
            FileUtils.deleteQuietly(file);
        }
    }

//    @Test
//    public void testUploadFeedJobExecution() throws Exception {
//        tearDown();
//
//        if (lcboFileRepository.size() != 3) {
//            copyTestFiles();
//            lcboFileRepository.loadFileRepository();
//        }
//
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addLong("timestamp", System.currentTimeMillis())
//                .toJobParameters();
//
//        JobExecution execution = jobLauncherTestUtils.launchJob(jobParameters);
//        Awaitility.await().atMost(10, TimeUnit.MINUTES).until(jobExecutionCompleted(execution));
//
//        String message = "Batch Status: ";
//        Object expected = BatchStatus.COMPLETED;
//        Object actual = execution.getStatus();
//
//        Assert.assertEquals(message, expected, actual);
//    }

    @Test
    public void testDownloadFeedJobExecution() throws Exception {

    }

    @Test
    public void testUnpackingFeedJobExecution() throws Exception {

    }

    @Test
    public void testRetrievingFeedJobExecution() throws Exception {

    }

    @Test
    public void testLoadingFeedJobExecution() throws Exception {

    }

    @Test
    public void testArchivingFeedJobExecution() throws Exception {

    }

    //////

    private void copyTestFiles() throws IOException {
        File inputDir = FileUtils.getFile(testPath.toFile(), "working");

        // Copy Test Description Feed
        ClassLoader classLoader = LCBOFileDataImporterETLApplicationTest.class.getClassLoader();
        List<URL> resources = new ArrayList<>();

        resources.add(classLoader.getResource("inventories.csv"));
        resources.add(classLoader.getResource("products.csv"));
        resources.add(classLoader.getResource("stores.csv"));

        for (URL resource : resources) {
            if (resource != null) {
                File inputFile = new File(resource.getFile());
                FileUtils.copyFileToDirectory(inputFile, inputDir);
            }
        }
    }

    private JobExecution launchStep(String stepName) {
        System.out.println("Step Execution Test: " + stepName);
        JobExecution execution = jobLauncherTestUtils.launchStep(stepName, executionContext);
        Awaitility.await().atMost(5, TimeUnit.SECONDS).until(jobExecutionCompleted(execution));
        return execution;
    }

    private Callable<Boolean> jobExecutionCompleted(JobExecution execution) {
        return new Callable<Boolean>() {
            public Boolean call() throws Exception {
                BatchStatus status = execution.getStatus();
                return BatchStatus.COMPLETED == status ||
                        BatchStatus.FAILED == status ||
                        BatchStatus.STOPPED == status;
            }
        };
    }
}