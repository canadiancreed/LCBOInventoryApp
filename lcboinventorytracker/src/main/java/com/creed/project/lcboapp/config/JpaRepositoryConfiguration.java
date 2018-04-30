package com.creed.project.lcboapp.config;

import com.creed.project.lcboapp.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Persistent Data Source Configuration Class
 */
@Profile("standalone")
@Configuration
@EnableJpaRepositories(
        basePackages = "com.creed.project.lcboapp.persistence.repository",
        entityManagerFactoryRef = "stagingEntityManagerFactory",
        transactionManagerRef = "stagingTransactionManager"
)
@EnableTransactionManagement
@PropertySource({
        "classpath:importer-${ENVIRONMENT}.properties",
        "classpath:LCBOFileImporter.properties"
})
public class JpaRepositoryConfiguration {

    @Autowired
    private Environment environment;

    /**
     * Default Constructor
     */
    public JpaRepositoryConfiguration() {
        super();
    }

    /**
     * Create primary datasource bean
     *
     * @return primary datasource
     */
    @Bean
    @ConfigurationProperties(prefix = "importer.jdbc")
    public DataSource stagingDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(environment.getRequiredProperty(Constants.PROP_BATCH_JDBC_DRIVER))
                .username(environment.getRequiredProperty(Constants.PROP_BATCH_JDBC_USER))
                .password(environment.getRequiredProperty(Constants.PROP_BATCH_JDBC_PASSWORD))
                .build();
    }

    /**
     * Create hibernate JPA transaction manager bean
     *
     * @return LocalContainerEntityManagerFactoryBean
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean stagingEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(stagingDataSource());
        entityManagerFactoryBean.setJpaProperties(hibernateProperties());
        entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(Constants.LCBO_PACKAGE_TO_SCAN);
        entityManagerFactoryBean.setPersistenceUnitName(Constants.STAGING_PERSISTENCE_UNIT_NAME);

        return entityManagerFactoryBean;
    }

    /**
     * Create JPA transaction manager bean
     *
     * @return PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager stagingTransactionManager() {
        return new JpaTransactionManager(stagingEntityManagerFactory().getObject());
    }

    /**
     * Create Hibernate JPA vendor adaptor bean
     *
     * @return jpa vendor adaptor
     */
    private JpaVendorAdapter hibernateJpaVendorAdapter() {
        boolean showSql = environment.getRequiredProperty(Constants.PROP_HIBERNATE_SHOW_SQL, Boolean.class);

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(showSql);
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);

        return hibernateJpaVendorAdapter;
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
        properties.put(Constants.PROP_HIBERNATE_GENERATE_DDL,
                environment.getRequiredProperty(Constants.PROP_HIBERNATE_GENERATE_DDL));
        properties.put(Constants.PROP_HIBERNATE_ID_NEW_GENERATOR_MAPPING,
                environment.getRequiredProperty(Constants.PROP_HIBERNATE_ID_NEW_GENERATOR_MAPPING));

        return properties;
    }
}
