package com.creed.project.lcboapp.config;

import com.creed.project.lcboapp.common.Constants;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Persistent Data Source Configuration Class
 */
@Profile("standalone")
@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
@PropertySource({
        "classpath:batch-${ENVIRONMENT}.properties",
        "classpath:importer-${ENVIRONMENT}.properties",
        "classpath:LCBOFileImporter.properties"
})
public class BatchDataSourceConfiguration {

    @Autowired
    private Environment environment;

    /**
     * Default Constructor
     */
    public BatchDataSourceConfiguration() {
        super();
    }

    /**
     * Create datasource bean for batch
     *
     * @return datasource
     *
     *
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "batch.jdbc")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(environment.getRequiredProperty(Constants.PROP_BATCH_JDBC_DRIVER))
                .username(environment.getRequiredProperty(Constants.PROP_BATCH_JDBC_USER))
                .password(environment.getRequiredProperty(Constants.PROP_BATCH_JDBC_PASSWORD))
                .build();
    }

    /**
     * Create session factory bean for batch
     *
     * @return session factory bean
     */
    @Bean
    @Primary
    public LocalSessionFactoryBean sessionFactory() {

        // Instantiate LocalSessionFactoryBean
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());

        // return primary session factory bean
        return sessionFactory;
    }

    /**
     * Create transaction manager bean for batch
     *
     * @return PlatformTransactionManager
     */
    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    /**
     * Create JobRepository bean for Batch Infrastructure
     *
     * @return Job Repository
     * @throws Exception if error occurred while initializing JobRepository
     */
    @Bean
    public JobRepository jobRepository(final DataSource datasource,
                                       final PlatformTransactionManager transactionManager) throws Exception {

        JobRepositoryFactoryBean jobRepositoryFactory = new JobRepositoryFactoryBean();

        jobRepositoryFactory.setDataSource(datasource);
        jobRepositoryFactory.setTransactionManager(transactionManager);
        jobRepositoryFactory.setIsolationLevelForCreate(Constants.ISOLATION_READ_COMMITTED);

        return jobRepositoryFactory.getObject();
    }

    /**
     * Create jobLauncher bean for Batch Infrastructure
     *
     * @return Job Launcher
     */
    @Bean
    public SimpleJobLauncher jobLauncher(@Qualifier("jobRepository") JobRepository jobRepository) {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();

        jobLauncher.setJobRepository(jobRepository);

        return jobLauncher;
    }

    /**
     * Create JobBuilderFactory bean for Batch Infrastructure
     *
     * @return Job Builder Factory
     */
    @Bean
    public JobBuilderFactory jobBuilderFactory(@Qualifier("jobRepository") JobRepository jobRepository) {
        return new JobBuilderFactory(jobRepository);
    }

    /**
     * Create stepBuilderFactory bean for Batch Infrastructure
     *
     * @return Step Builder Factory
     */
    @Bean
    public StepBuilderFactory stepBuilderFactory(@Qualifier("jobRepository") JobRepository jobRepository,
                                                 @Qualifier("transactionManager") PlatformTransactionManager transactionManager) {
        return new StepBuilderFactory(jobRepository, transactionManager);
    }

    /**
     * Create Hibernate Properties
     *
     * @return hibernate properties
     */
    private Properties hibernateProperties() {
        // create a new Properties object
        Properties properties = new Properties();

        // assign hibernate properties
        properties.put(Constants.PROP_HIBERNATE_DIALECT,
                environment.getRequiredProperty(Constants.PROP_HIBERNATE_DIALECT));
        properties.put(Constants.PROP_HIBERNATE_SHOW_SQL,
                environment.getRequiredProperty(Constants.PROP_HIBERNATE_SHOW_SQL));
        properties.put(Constants.PROP_HIBERNATE_FORMAT_SQL,
                environment.getRequiredProperty(Constants.PROP_HIBERNATE_FORMAT_SQL));
        properties.put(Constants.PROP_HIBERNATE_COMMENT,
                environment.getRequiredProperty(Constants.PROP_HIBERNATE_COMMENT));
        properties.put(Constants.PROP_HIBERNATE_ID_NEW_GENERATOR_MAPPING,
                environment.getRequiredProperty(Constants.PROP_HIBERNATE_ID_NEW_GENERATOR_MAPPING));

        return properties;
    }
}
