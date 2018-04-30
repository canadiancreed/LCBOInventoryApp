package com.creed.project.lcboapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * THe LCBO Data files are provided as three files that have the following data for the particular day that they're
 * created on. they break down as follows:
 * - Inventory.csv Entries for each item by product and then by store
 * - product.csv Enteries for each product
 * - store.csv Enteries for each store that exists or did exist
 */

@SpringBootApplication
public class LCBOFileDataImporterETLApplication {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(LCBOFileDataImporterETLApplication.class);

        application.setWebEnvironment(false);
        application.run(args);
    }
}