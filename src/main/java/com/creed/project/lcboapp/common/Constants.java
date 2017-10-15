package com.creed.project.lcboapp.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Importer Common Constants Class
 */
public final class Constants {

    /**
     * Hibernate Configuration Property keys
     */
    public static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    public static final String PROP_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    public static final String PROP_HIBERNATE_COMMENT = "hibernate.comment";
    public static final String PROP_HIBERNATE_GENERATE_DDL = "hibernate.generate_ddl";
    public static final String PROP_HIBERNATE_ID_NEW_GENERATOR_MAPPING = "hibernate.id.new_generator_mapping";

    /**
     * Spring Batch JDBC Property keys
     */
    public static final String PROP_BATCH_JDBC_USER = "batch.jdbc.user";
    public static final String PROP_BATCH_JDBC_PASSWORD = "batch.jdbc.password";
    public static final String PROP_BATCH_JDBC_DRIVER = "batch.jdbc.driver";
    public static final String PROP_BATCH_JDBC_JNDI_URL = "batch.jdbc.jndi.url";

    /**
     * JDBC Property keys
     */
    public static final String PROP_JDBC_JNDI_URL = "importer.lcbo.jdbc.jndi.url";
    public static final String PROP_JDBC_INITIAL_SIZE = "importer.lcbo.jdbc.initial-size";
    public static final String PROP_JDBC_MAX_ACTIVE = "importer.lcbo.jdbc.max-active";
    public static final String PROP_JDBC_REMOVE_ABANDONED = "importer.lcbo.jdbc.remove-abandoned";

    /**
     * Configuration Property Keys
     */

    //LCBO Feed

    public static final String PROP_LCBO_FEED_ARCHIVE_DIR = "importer.lcbo.archive.dir";
    public static final String PROP_LCBO_FEED_DOWNLOAD_DIR = "importer.lcbo.download.dir";
    public static final String PROP_LCBO_FEED_WORKING_DIR = "importer.lcbo.working.dir";

    public static final String PROP_LCBO_INVENTORY_FEED_LINE_TO_SKIP = "importer.lcbo.inventory.line.to.skip";
    public static final String PROP_LCBO_PRODUCT_FEED_LINE_TO_SKIP = "importer.lcbo.product.line.to.skip";
    public static final String PROP_LCBO_STORE_FEED_LINE_TO_SKIP = "importer.lcbo.store.line.to.skip";

    public static final String PROP_LCBO_FEED_CRON_SCHEDULE = "importer.lcbo.cron.schedule";

    public static final String PROP_LCBO_FEED_ACCESS_KEY = "importer.lcbo.access.key";

    /**
     * LCBO API Settings
     */

    public static final String PROP_LCBO_API_DATASET_URL = "importer.lcbo.api.dataset.url";


    /**
     * Batch Job Thread Property Keys
     */
    public static final String PROP_DATA_CHUNK_SIZE = "importer.lcbo.data.chunk.size";
    public static final String PROP_THREAD_NAME_PREFIX = "importer.lcbo.thread.name.prefix";
    public static final String PROP_THREAD_MAX_POOL_SIZE = "importer.lcbo.thread.max.pool.size";
    public static final String PROP_THREAD_KEEP_ALIVE_SECS = "importer.lcbo.thread.keep.alive.secs";

    /**
     * Batch Job - Other Configuration Property Keys
     */
    public static final String PROP_FILE_COMPRESS = "importer.lcbo.file.compress";
    public static final String PROP_FILE_BUFFER_SIZE = "importer.lcbo.file.buffer.size";
    public static final String PROP_TIME_ZONE = "importer.lcbo.time.zone";

    /**
     * Common Constants
     */
    public static final int DEFAULT_CHUNK_SIZE = 1;
    public static final String RESOURCE_FILE = "file:";
    public static final String KEY_SEPARATOR = ":";
    public static final String CODE_SEPARATOR = "/";
    public static final String SEMI_COLON = ";";
    public static final String DASH = "-";
    public static final String UNDERSCORE = "_";
    public static final String ASTRISK = "*";
    public static final String BASE_CURRENCY = "EUR";
    public static final String STAGING_PERSISTENCE_UNIT_NAME = "StagingPersistenceUnit";
    public static final String ISOLATION_READ_COMMITTED = "ISOLATION_READ_COMMITTED";
    public static final DateTimeFormatter JSON_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter SIMPLE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final LocalDateTime FUTURE_DATE;

    static {
        LocalDate localDate = LocalDate.parse("20500101", SIMPLE_DATE_FORMATTER);
        FUTURE_DATE = LocalDateTime.from(localDate.atStartOfDay());
    }

    /**
     * Batch Job Status
     */
    public static final String STEP_EXIT_STATUS_COMPLETED = "COMPLETED";
    public static final String STEP_EXIT_STATUS_CONTINUE = "CONTINUE";
    public static final String STEP_EXIT_STATUS_FAILED = "FAILED";
    public static final String STEP_EXIT_STATUS_ALL = "*";

    /**
     * File Extensions
     */
    public static final String FILE_EXTENSION_ZIP = "zip";
    public static final String FILE_EXTENSION_JSON = "json";
    public static final String FILE_EXTENSION_CSV = "csv";

    /**
     * LCBO Constants
     */
    public static final String LCBO_PACKAGE_TO_SCAN = "com.creed.project.lcboapp.persistence.model";
    public static final String TOTAL_BASE_COST = "Total Base Cost";
    public static final String COST_NOT_AVAILABLE = "#N/A";
    public static final String COST_NO_COST = "NoCost";
    public static final String ADDITIONAL_COST = "Additional Cost";

    /**
     * Default Construction
     */
    private Constants() {
        throw new IllegalAccessError("Constants Class");
    }

}
