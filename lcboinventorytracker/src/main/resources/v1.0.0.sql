CREATE TABLE `lcbo`.`inventory` (
  `id` INT(14) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `productID` INT NOT NULL,
  `storeID` INT NOT NULL,
  `quantity` INT NOT NULL,
  `reportedOn` DATE NOT NULL,
  `updatedAt` DATE NOT NULL
);
  
CREATE TABLE `lcbo`.`product` (
`id` INT(11) NOT NULL PRIMARY KEY,
`name` VARCHAR(200) NOT NULL ,
`priceInCents` DOUBLE NOT NULL ,
`regularPriceInCents` DOUBLE NOT NULL ,
`primaryCategory` VARCHAR(255) NOT NULL ,
`secondaryCategory` VARCHAR(255),
`origin` VARCHAR(255),
`producerName` VARCHAR(255),
`releasedOn` DATE,
`updatedAt` DATE NOT NULL,
`imageUrl` VARCHAR(255),
`varietal` VARCHAR(255),
`style` VARCHAR(255),
`tertiaryCategory` VARCHAR(255)
);

CREATE TABLE `lcbo`.`store` (
`id` INT(11) NOT NULL PRIMARY KEY,
`addressLineOne` VARCHAR(200) NOT NULL ,
`addressLineTwo` VARCHAR(200) NULL ,
`city` VARCHAR(200) NOT NULL ,
`postalCode` VARCHAR(200) NOT NULL ,
`latitude` VARCHAR(200) NOT NULL ,
`longitude` VARCHAR(200) NOT NULL ,
`updatedAt` DATE NOT NULL
); 

