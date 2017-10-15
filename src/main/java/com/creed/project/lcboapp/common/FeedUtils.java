package com.creed.project.lcboapp.common;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class contains all general file actions, ones that aren't bound to a specific implementation. This class could
 * be used in any application, and may be deleted if there isn't much seperation fo functionality going on.
 */
public final class FeedUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedUtils.class);
    private static final int BUFFER_SIZE = 1024;

    /**
     * Default Construction
     */
    private FeedUtils() {
        throw new IllegalAccessError("Feed Util Class");
    }

    /**
     * @param inputDir the input directory
     * @return the file map
     */
    public static Map<String, File> getFeedFiles(String inputDir) {

        Map<String, File> fileMap = new HashMap<>();

        // Get description feed input location path
        Path path = Paths.get(inputDir);
        File dir = path.toFile();
        File[] files;

        // Validate description feed input location path
        if (dir.exists() && dir.isDirectory() && dir.canRead() && dir.canWrite()) {
            files = dir.listFiles();

            if (files != null) {
                // Store description feeds in the repository
                for (File file : files) {
                    String feedId = FilenameUtils.getBaseName(file.getName());
                    fileMap.put(feedId, file);
                }
            }
        }

        return fileMap;
    }

    /**
     * @param status the ETL status
     * @return the flag base on ETL Status
     */
    public static String getReadyForPickupFlag(ETLStatus status) {
        Flag flag;

        if (ETLStatus.COMPLETED == status) {
            flag = Flag.YES;
        } else if (ETLStatus.FAILED == status) {
            flag = Flag.ERROR;
        } else {
            flag = Flag.PROCESSING;
        }

        return flag.getValue();
    }

    /**
     * @param status the ETL status
     * @return the flag base on ETL Status
     */
    public static String getDPTReadyFlag(ETLStatus status) {
        Flag flag;

        if (ETLStatus.COMPLETED == status) {
            flag = Flag.YES;
        } else if (ETLStatus.FAILED == status) {
            flag = Flag.ERROR;
        } else {
            flag = Flag.PROCESSING;
        }

        return flag.getValue();
    }

    /**
     * move feed to archive location
     *
     * @param destinationDir the path to store file
     * @param sourceFile     the source file to be archived
     * @return archive file if archive a file successfully, null otherwise
     */
    public static File moveFile(String destinationDir, File sourceFile) throws IOException {
        // Validate the file
        if (sourceFile == null || !sourceFile.exists() || !sourceFile.canRead() || sourceFile.isDirectory()) {
            return null;
        }

        String fileName = sourceFile.getName();
        File destinationFile = new File(destinationDir, fileName);

        if(destinationFile.exists()){
            FileUtils.deleteQuietly(destinationFile);
        }

        FileUtils.moveFile(sourceFile, destinationFile);

        return destinationFile;
    }

    /**
     * @param destinationDir the path to store archive file
     * @param fileMap        the File Map
     * @throws IOException if error occurred while archiving a file
     */
    public static void moveFiles(String destinationDir, Map<String, File> fileMap) throws IOException {
        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
            File file = entry.getValue();
            File destinationFile = FeedUtils.moveFile(destinationDir, file);
            fileMap.put(entry.getKey(), destinationFile);
        }
    }

    /**
     * move feed to archive location
     *
     * @param archiveDir the path to store archive file
     * @param source     the source file to be archived
     * @return archive file if archive a file successfully, null otherwise
     */
    public static File archive(String archiveDir, File source) throws IOException {
        // Validate the file
        if (source == null || !source.exists() || !source.canRead() || source.isDirectory()) {
            return null;
        }

        String basename = source.getName();
        String extension = "";

        File archiveFile = generateArchiveFile(archiveDir, basename, extension);
        FileUtils.moveFile(source, archiveFile);

        return archiveFile;
    }

    /**
     * move feed to archive location
     *
     * @param archiveDir the path to store archive file
     * @param source     the source file to be archived
     * @param extension  the known file extension
     * @return archive file if archive a file successfully, null otherwise
     */
    public static File archive(String archiveDir, File source, String extension) throws IOException {
        // Validate the file
        if (source == null || !source.exists() || !source.canRead() || source.isDirectory()) {
            return null;
        }

        String filename = source.getName();
        String basename = FilenameUtils.getBaseName(filename);
        File archiveFile = generateArchiveFile(archiveDir, basename, extension);
        FileUtils.moveFile(source, archiveFile);

        return archiveFile;
    }

    /**
     * @param archiveDir the path to store archive file
     * @param fileMap    the File Map
     * @throws IOException if error occurred while archiving a file
     */
    public static void archiveFiles(String archiveDir, Map<String, File> fileMap) throws IOException {
        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
            File file = entry.getValue();
            File archiveFile = FeedUtils.archive(archiveDir, file);
            fileMap.put(entry.getKey(), archiveFile);
        }
    }

    /**
     * @param archiveDir the path to store archive file
     * @param fileMap    the File Map
     * @param extension  the known file extension
     * @throws IOException if error occurred while archiving a file
     */
    public static void archiveFiles(String archiveDir, Map<String, File> fileMap, String extension)
            throws IOException {

        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
            File file = entry.getValue();
            File archiveFile = FeedUtils.archive(archiveDir, file, extension);
            fileMap.put(entry.getKey(), archiveFile);
        }
    }

    /**
     * Generate unique name in the location
     *
     * @param archiveDir the path to store archive file
     * @param basename   the file name without extension
     * @param extension  the known file extension
     * @return a new file that does not exist in the location
     */
    private static File generateArchiveFile(String archiveDir, String basename, String extension) {
        // Get current time
        Calendar now = Calendar.getInstance();

        // Extract year, month, date from Calendar
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Get description feed input location path
        Path path = Paths.get(archiveDir);
        File directory = path.toFile();

        // Generate initial filename
        String suffix = String.format(".A%d%02d%02d", year, month, day);
        int count = 0;
        String filename = basename + suffix;
        File file;

        if (StringUtils.isNotBlank(extension)) {
            filename = basename + suffix + FilenameUtils.EXTENSION_SEPARATOR_STR + extension;
        }

        // Validate filename
        while (true) {
            file = FileUtils.getFile(directory, FilenameUtils.getName(filename));

            // If the file does not exist
            if (!file.exists()) {
                break;
            }

            // Otherwise
            count++;
            suffix = String.format(".A%d%02d%02d-%02d", year, month, day, count);
            filename = basename + suffix;

            if (StringUtils.isNotBlank(extension)) {
                filename = basename + suffix + FilenameUtils.EXTENSION_SEPARATOR_STR + extension;
            }
        }

        return file;
    }

    /**
     * Compress description feed
     *
     * @param archiveDir the path to store archive file
     * @param source     the source file to be compressed
     * @return compress file if compressed a file successfully, null otherwise
     * @throws IOException if error occurred while compressing a file
     */
    public static File compress(String archiveDir, File source) throws IOException {
        // Generate new description file name for archiving
        String filename = source.getName();
        String basename = FilenameUtils.getBaseName(filename);
        String extension = Constants.FILE_EXTENSION_ZIP;
        File zipFile = generateArchiveFile(archiveDir, basename, extension);

        // Archive description feed
        byte[] buffer = new byte[BUFFER_SIZE];
        try (FileInputStream inputStream = new FileInputStream(source);
             FileOutputStream outputStream = new FileOutputStream(zipFile);
             ZipOutputStream archiveStream = new ZipOutputStream(outputStream)) {

            // Add zip entry
            archiveStream.putNextEntry(new ZipEntry(filename));

            // Compress
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

            // Close input/output file streams
            archiveStream.closeEntry();
            archiveStream.close();
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            FileUtils.moveFile(source, zipFile);

            LOGGER.debug("The file \"{}\" archived to \"{}\"", source.getPath(), zipFile.getPath());
        } catch (IOException e) {
            zipFile = null;
            LOGGER.error("Could not compress the file \"{}\" :", source.getPath(), e);
        }


        return zipFile;
    }
}
