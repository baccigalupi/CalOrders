-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `calordersdb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `calordersdb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema tempusdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `calordersdb` ;

-- -----------------------------------------------------
-- Schema tempusdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `calordersdb` DEFAULT CHARACTER SET utf8 ;
USE `calordersdb` ;

-- -----------------------------------------------------
-- Table `calordersdb`.`DEPARTMENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`DEPARTMENT` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`DEPARTMENT` (
  `DEP_UID` INT(11) NOT NULL AUTO_INCREMENT,
  `DEP_NAME` VARCHAR(128) NOT NULL,
  `DEP_ACT_REP` VARCHAR(45) NULL,
  `DEP_IMG_ID` VARCHAR(128) NULL,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`DEP_UID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `DEP_UID_UNIQUE` ON `calordersdb`.`DEPARTMENT` (`DEP_UID` ASC);


-- -----------------------------------------------------
-- Table `calordersdb`.`GRP_TYPE_CD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`GRP_TYPE_CD` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`GRP_TYPE_CD` (
  `CODE` CHAR(4) NOT NULL,
  `SHORT_DESC` VARCHAR(64) NOT NULL,
  `LONG_DESC` VARCHAR(128) NOT NULL,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `calordersdb`.`GROUPS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`GROUPS` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`GROUPS` (
  `GRP_UID` INT(11) NOT NULL AUTO_INCREMENT,
  `DEP_UID_FK` INT(11) NOT NULL,
  `GRP_TYPE_CD` CHAR(4) NOT NULL,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`GRP_UID`),
  CONSTRAINT `fk_GROUPS_Department1`
    FOREIGN KEY (`DEP_UID_FK`)
    REFERENCES `calordersdb`.`DEPARTMENT` (`DEP_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_GROUPS_GRP_TYPE_CD1`
    FOREIGN KEY (`GRP_TYPE_CD`)
    REFERENCES `calordersdb`.`GRP_TYPE_CD` (`CODE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `GRP_UID_UNIQUE` ON `calordersdb`.`GROUPS` (`GRP_UID` ASC);

CREATE INDEX `fk_GROUPS_DEPARTMENT1_idx` ON `calordersdb`.`GROUPS` (`DEP_UID_FK` ASC);

CREATE INDEX `fk_GROUPS_GRP_TYPE_CD1_idx` ON `calordersdb`.`GROUPS` (`GRP_TYPE_CD` ASC);


-- -----------------------------------------------------
-- Table `calordersdb`.`PRIVILEGE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`PRIVILEGE` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`PRIVILEGE` (
  `PRV_UID` INT NOT NULL AUTO_INCREMENT,
  `GRP_UID_FK` INT(11) NOT NULL,
  `PRV_PAGE_ID` VARCHAR(45) NOT NULL,
  `PRV_COMPONENT_ID` VARCHAR(45) NULL,
  `PRV_READ_IND` INT NOT NULL DEFAULT 0,
  `PRV_WRITE_IND` INT NOT NULL DEFAULT 0,
  `PRV_MISC` VARCHAR(45) NULL,
  `PRV_ORDER` INT NOT NULL DEFAULT 0,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`PRV_UID`),
  CONSTRAINT `fk_PRIVILEGES_GROUPS`
    FOREIGN KEY (`GRP_UID_FK`)
    REFERENCES `calordersdb`.`GROUPS` (`GRP_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `PRV_UID_UNIQUE` ON `calordersdb`.`PRIVILEGE` (`PRV_UID` ASC);

CREATE INDEX `fk_PRIVILEGES_GROUPS_idx` ON `calordersdb`.`PRIVILEGE` (`GRP_UID_FK` ASC);


-- -----------------------------------------------------
-- Table `calordersdb`.`VENDOR`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`VENDOR` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`VENDOR` (
  `VND_UID` INT NOT NULL AUTO_INCREMENT,
  `VND_NAME` VARCHAR(45) NOT NULL,
  `VND_DESC` VARCHAR(256) NULL,
  `VND_MANAGER` VARCHAR(128) NULL,
  `VND_ACTIVE_IND` INT NOT NULL,
  `CREATE_USER_ID` VARCHAR(45) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(45) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`VND_UID`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `VND_UID_UNIQUE` ON `calordersdb`.`VENDOR` (`VND_UID` ASC);


-- -----------------------------------------------------
-- Table `calordersdb`.`PRD_CATEGORY_CD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`PRD_CATEGORY_CD` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`PRD_CATEGORY_CD` (
  `CODE` CHAR(4) NOT NULL,
  `SHORT_DESC` VARCHAR(64) NOT NULL,
  `LONG_DESC` VARCHAR(128) NOT NULL,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `calordersdb`.`PRD_IMG_TYPE_CD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`PRD_IMG_TYPE_CD` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`PRD_IMG_TYPE_CD` (
  `CODE` CHAR(4) NOT NULL,
  `SHORT_DESC` VARCHAR(64) NOT NULL,
  `LONG_DESC` VARCHAR(128) NOT NULL,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `calordersdb`.`PRD_UNIT_CD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`PRD_UNIT_CD` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`PRD_UNIT_CD` (
  `CODE` CHAR(4) NOT NULL,
  `SHORT_DESC` VARCHAR(64) NOT NULL,
  `LONG_DESC` VARCHAR(128) NOT NULL,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `calordersdb`.`PRODUCT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`PRODUCT` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`PRODUCT` (
  `PRD_UID` INT NOT NULL AUTO_INCREMENT,
  `VND_UID_FK` INT NOT NULL,
  `PRD_SKU` VARCHAR(45) NOT NULL,
  `PRD_NAME` VARCHAR(45) NOT NULL,
  `PRD_CATEGORY_CD` CHAR(4) NOT NULL,
  `PRD_SHORT_DESC` VARCHAR(128) NOT NULL,
  `PRD_LONG_DESC` VARCHAR(2048) NOT NULL,
  `PRD_PRICE` DECIMAL(12,2) NOT NULL,
  `PRD_ACTIVE_IND` INT NOT NULL DEFAULT 1,
  `PRD_IMG_KEY` VARCHAR(256) NULL,
  `PRD_IMG_NAME` VARCHAR(256) NULL,
  `PRD_IMG_SIZE` INT NULL,
  `PRD_IMG_ORIGIN` VARCHAR(128) NULL,
  `PRD_IMG_IMAGE` LONGBLOB NULL,
  `PRD_IMG_TYPE_CD` CHAR(4) NOT NULL,
  `PRD_CNTR_LN_ITM` VARCHAR(45) NULL,
  `PRD_OEM_PART_NUM` VARCHAR(45) NULL,
  `PRD_OEM_NAME` VARCHAR(45) NULL,
  `PRD_CNTR_UNIT_PRICE` DECIMAL(12,2) NULL,
  `PRD_CNTR_DISCOUNT` INT NULL,
  `PRD_UNIT_CD` CHAR(4) NOT NULL,
  `CREATE_USER_ID` VARCHAR(45) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(45) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`PRD_UID`),
  CONSTRAINT `fk_PRODUCT_VENDOR1`
    FOREIGN KEY (`VND_UID_FK`)
    REFERENCES `calordersdb`.`VENDOR` (`VND_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PRODUCT_PRD_CATEGORY_CD1`
    FOREIGN KEY (`PRD_CATEGORY_CD`)
    REFERENCES `calordersdb`.`PRD_CATEGORY_CD` (`CODE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PRODUCT_PRD_IMG_TYPE_CD1`
    FOREIGN KEY (`PRD_IMG_TYPE_CD`)
    REFERENCES `calordersdb`.`PRD_IMG_TYPE_CD` (`CODE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PRODUCT_PRD_UNIT_CD1`
    FOREIGN KEY (`PRD_UNIT_CD`)
    REFERENCES `calordersdb`.`PRD_UNIT_CD` (`CODE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `PRD_UID_UNIQUE` ON `calordersdb`.`PRODUCT` (`PRD_UID` ASC);

CREATE INDEX `fk_PRODUCT_VENDOR1_idx` ON `calordersdb`.`PRODUCT` (`VND_UID_FK` ASC);

CREATE INDEX `fk_PRODUCT_PRD_CATEGORY_CD1_idx` ON `calordersdb`.`PRODUCT` (`PRD_CATEGORY_CD` ASC);

CREATE INDEX `fk_PRODUCT_PRD_IMG_TYPE_CD1_idx` ON `calordersdb`.`PRODUCT` (`PRD_IMG_TYPE_CD` ASC);

CREATE INDEX `fk_PRODUCT_PRD_UNIT_CD1_idx` ON `calordersdb`.`PRODUCT` (`PRD_UNIT_CD` ASC);


-- -----------------------------------------------------
-- Table `calordersdb`.`ORD_STATUS_CD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`ORD_STATUS_CD` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`ORD_STATUS_CD` (
  `CODE` CHAR(4) NOT NULL,
  `SHORT_DESC` VARCHAR(64) NOT NULL,
  `LONG_DESC` VARCHAR(128) NOT NULL,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `calordersdb`.`PARTY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`PARTY` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`PARTY` (
  `PTY_UID` INT(11) NOT NULL AUTO_INCREMENT,
  `PTY_USER_ID` VARCHAR(32) NOT NULL,
  `PTY_PASSWORD` VARCHAR(256) NOT NULL,
  `PTY_FIRST_NM` VARCHAR(45) NOT NULL,
  `PTY_MID_NM` VARCHAR(45) NULL,
  `PTY_LAST_NM` VARCHAR(45) NOT NULL,
  `PTY_TITLE` VARCHAR(45) NULL,
  `PTY_HIRE_DT` DATETIME NOT NULL,
  `PTY_ID` VARCHAR(128) NOT NULL,
  `PTY_IMG_ID` VARCHAR(512) NULL,
  `PTY_ACTIVE_IND` INT NOT NULL DEFAULT 1,
  `PTY_DOB_DT` DATETIME NULL,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`PTY_UID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `EMP_UID_UNIQUE` ON `calordersdb`.`PARTY` (`PTY_UID` ASC);

CREATE UNIQUE INDEX `EMP_USER_ID_UNIQUE` ON `calordersdb`.`PARTY` (`PTY_USER_ID` ASC);


-- -----------------------------------------------------
-- Table `calordersdb`.`ORDER_HISTORY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`ORDER_HISTORY` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`ORDER_HISTORY` (
  `ORD_UID` INT NOT NULL AUTO_INCREMENT,
  `PTY_UID_FK` INT(11) NOT NULL,
  `ORD_STATUS_CD` CHAR(4) NOT NULL,
  `CREATE_USER_ID` VARCHAR(45) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(45) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`ORD_UID`),
  CONSTRAINT `fk_ORDER_ORD_STATUS_CD1`
    FOREIGN KEY (`ORD_STATUS_CD`)
    REFERENCES `calordersdb`.`ORD_STATUS_CD` (`CODE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ORDER_USER1`
    FOREIGN KEY (`PTY_UID_FK`)
    REFERENCES `calordersdb`.`PARTY` (`PTY_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `ORD_UID_UNIQUE` ON `calordersdb`.`ORDER_HISTORY` (`ORD_UID` ASC);

CREATE INDEX `fk_ORDER_ORD_STATUS_CD1_idx` ON `calordersdb`.`ORDER_HISTORY` (`ORD_STATUS_CD` ASC);

CREATE INDEX `fk_ORDER_USER1_idx` ON `calordersdb`.`ORDER_HISTORY` (`PTY_UID_FK` ASC);


-- -----------------------------------------------------
-- Table `calordersdb`.`PRD_REL_SERVICE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`PRD_REL_SERVICE` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`PRD_REL_SERVICE` (
  `PRS_UID` INT NOT NULL AUTO_INCREMENT,
  `PRS_NAME` VARCHAR(64) NOT NULL,
  `PRS_DESC` VARCHAR(128) NOT NULL,
  `PRS_PRICE` DECIMAL(12,2) NOT NULL,
  `PRS_ACTIVE_IND` INT NOT NULL DEFAULT 1,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`PRS_UID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `CODE_UNIQUE` ON `calordersdb`.`PRD_REL_SERVICE` (`PRS_UID` ASC);


-- -----------------------------------------------------
-- Table `calordersdb`.`ORDER_PRODUCT_ASSOC`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`ORDER_PRODUCT_ASSOC` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`ORDER_PRODUCT_ASSOC` (
  `OPA_UID` INT NOT NULL AUTO_INCREMENT,
  `ORD_UID_FK` INT NOT NULL,
  `PRD_UID_FK` INT NOT NULL,
  `OPA_QUANTITY` INT NULL,
  `OPA_PRICE` DECIMAL(12,2) NULL,
  `PRS_UID_FK` INT NULL,
  `CREATE_USER_ID` VARCHAR(45) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(45) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`OPA_UID`),
  CONSTRAINT `fk_ORDER_PRODUCT_ASSOC_ORDER1`
    FOREIGN KEY (`ORD_UID_FK`)
    REFERENCES `calordersdb`.`ORDER_HISTORY` (`ORD_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ORDER_PRODUCT_ASSOC_PRODUCT1`
    FOREIGN KEY (`PRD_UID_FK`)
    REFERENCES `calordersdb`.`PRODUCT` (`PRD_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ORDER_PRODUCT_ASSOC_PRD_REL_SERVICE_CD1`
    FOREIGN KEY (`PRS_UID_FK`)
    REFERENCES `calordersdb`.`PRD_REL_SERVICE` (`PRS_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `OPA_UID_UNIQUE` ON `calordersdb`.`ORDER_PRODUCT_ASSOC` (`OPA_UID` ASC);

CREATE INDEX `fk_ORDER_PRODUCT_ASSOC_ORDER1_idx` ON `calordersdb`.`ORDER_PRODUCT_ASSOC` (`ORD_UID_FK` ASC);

CREATE INDEX `fk_ORDER_PRODUCT_ASSOC_PRODUCT1_idx` ON `calordersdb`.`ORDER_PRODUCT_ASSOC` (`PRD_UID_FK` ASC);

CREATE INDEX `fk_ORDER_PRODUCT_ASSOC_PRD_REL_SERVICE_CD1_idx` ON `calordersdb`.`ORDER_PRODUCT_ASSOC` (`PRS_UID_FK` ASC);


-- -----------------------------------------------------
-- Table `calordersdb`.`RELATED_SERVICE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`RELATED_SERVICE` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`RELATED_SERVICE` (
  `RLS_UID` INT NOT NULL AUTO_INCREMENT,
  `PRD_UID_FK` INT NOT NULL,
  `PRS_UID_FK` INT NOT NULL,
  `CREATE_USER_ID` VARCHAR(45) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(45) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`RLS_UID`),
  CONSTRAINT `fk_RELATED_SERVICE_PRODUCT1`
    FOREIGN KEY (`PRD_UID_FK`)
    REFERENCES `calordersdb`.`PRODUCT` (`PRD_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RELATED_SERVICE_PRD_REL_SERVICE_CD1`
    FOREIGN KEY (`PRS_UID_FK`)
    REFERENCES `calordersdb`.`PRD_REL_SERVICE` (`PRS_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `RLS_UID_UNIQUE` ON `calordersdb`.`RELATED_SERVICE` (`RLS_UID` ASC);

CREATE INDEX `fk_RELATED_SERVICE_PRODUCT1_idx` ON `calordersdb`.`RELATED_SERVICE` (`PRD_UID_FK` ASC);

CREATE INDEX `fk_RELATED_SERVICE_PRD_REL_SERVICE_CD1_idx` ON `calordersdb`.`RELATED_SERVICE` (`PRS_UID_FK` ASC);


-- -----------------------------------------------------
-- Table `calordersdb`.`GROUP_PRIVILEGE_ASSOC`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`GROUP_PRIVILEGE_ASSOC` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`GROUP_PRIVILEGE_ASSOC` (
  `GPA_UID` INT NOT NULL AUTO_INCREMENT,
  `GRP_UID_FK` INT(11) NOT NULL,
  `PRV_UID_FK` INT NOT NULL,
  `CREATE_USER_ID` VARCHAR(45) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(45) NOT NULL,
  `UPDATE_TS` VARCHAR(45) NULL,
  PRIMARY KEY (`GPA_UID`),
  CONSTRAINT `fk_GROUP_PRIVILEGE_ASSOC_GROUP1`
    FOREIGN KEY (`GRP_UID_FK`)
    REFERENCES `calordersdb`.`GROUPS` (`GRP_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_GROUP_PRIVILEGE_ASSOC_PRIVILEGE1`
    FOREIGN KEY (`PRV_UID_FK`)
    REFERENCES `calordersdb`.`PRIVILEGE` (`PRV_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `GPA_UID_UNIQUE` ON `calordersdb`.`GROUP_PRIVILEGE_ASSOC` (`GPA_UID` ASC);

CREATE INDEX `fk_GROUP_PRIVILEGE_ASSOC_GROUP1_idx` ON `calordersdb`.`GROUP_PRIVILEGE_ASSOC` (`GRP_UID_FK` ASC);

CREATE INDEX `fk_GROUP_PRIVILEGE_ASSOC_PRIVILEGE1_idx` ON `calordersdb`.`GROUP_PRIVILEGE_ASSOC` (`PRV_UID_FK` ASC);

USE `calordersdb` ;

-- -----------------------------------------------------
-- Table `calordersdb`.`ADR_COUNTRY_CD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`ADR_COUNTRY_CD` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`ADR_COUNTRY_CD` (
  `CODE` CHAR(4) NOT NULL,
  `SHORT_DESC` VARCHAR(64) NOT NULL,
  `LONG_DESC` VARCHAR(128) NOT NULL,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `calordersdb`.`ADR_STATE_CD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`ADR_STATE_CD` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`ADR_STATE_CD` (
  `CODE` CHAR(2) NOT NULL,
  `SHORT_DESC` VARCHAR(64) NOT NULL,
  `LONG_DESC` VARCHAR(128) NOT NULL,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `calordersdb`.`ADDRESS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`ADDRESS` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`ADDRESS` (
  `ADR_UID` INT(11) NOT NULL AUTO_INCREMENT,
  `PTY_UID_FK` INT(11) NULL,
  `DEP_UID_FK` INT(11) NULL,
  `VND_UID_FK` INT NULL,
  `ADR_LINE1` VARCHAR(128) NULL DEFAULT NULL,
  `ADR_LINE2` VARCHAR(128) NULL DEFAULT NULL,
  `ADR_CITY` VARCHAR(64) NULL DEFAULT NULL,
  `ADR_STATE_CD` CHAR(2) NULL DEFAULT NULL,
  `ADR_COUNTRY_CD` CHAR(4) NULL DEFAULT NULL,
  `ADR_ZIP5` CHAR(5) NULL DEFAULT NULL,
  `ADR_ZIP4` CHAR(4) NULL DEFAULT NULL,
  `ADR_FOREIGN_ZIP` VARCHAR(32) NULL DEFAULT NULL,
  `ADR_RTN_IND` INT(11) NOT NULL DEFAULT '0' COMMENT '0 NO MAIL HAS BEEN BOUNCED FOR THIS ADDRESS, 1 MAIL RETURNED FOR THIS ADDRESS',
  `ADR_LOCATION_IND` INT(11) NOT NULL DEFAULT '0' COMMENT '0 DOMESTIC, 1 FOREIGN',
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`ADR_UID`),
  CONSTRAINT `fk_ADDRESS_Department2`
    FOREIGN KEY (`DEP_UID_FK`)
    REFERENCES `calordersdb`.`DEPARTMENT` (`DEP_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ADDRESS_Party1`
    FOREIGN KEY (`PTY_UID_FK`)
    REFERENCES `calordersdb`.`PARTY` (`PTY_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ADR_COUNTRY_CD`
    FOREIGN KEY (`ADR_COUNTRY_CD`)
    REFERENCES `calordersdb`.`ADR_COUNTRY_CD` (`CODE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ADR_STATE_CD`
    FOREIGN KEY (`ADR_STATE_CD`)
    REFERENCES `calordersdb`.`ADR_STATE_CD` (`CODE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ADDRESS_VENDOR1`
    FOREIGN KEY (`VND_UID_FK`)
    REFERENCES `calordersdb`.`VENDOR` (`VND_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `ADR_UID_UNIQUE` ON `calordersdb`.`ADDRESS` (`ADR_UID` ASC);

CREATE INDEX `fk_ADDRESS_PARTY1_idx` ON `calordersdb`.`ADDRESS` (`PTY_UID_FK` ASC);

CREATE INDEX `fk_ADDRESS_DEPARTMENT1_idx` ON `calordersdb`.`ADDRESS` (`DEP_UID_FK` ASC);

CREATE INDEX `fk_ADR_STATE_CD_idx` ON `calordersdb`.`ADDRESS` (`ADR_STATE_CD` ASC);

CREATE INDEX `fk_ADR_COUNTRY_CD_idx` ON `calordersdb`.`ADDRESS` (`ADR_COUNTRY_CD` ASC);

CREATE INDEX `fk_ADDRESS_VENDOR1_idx` ON `calordersdb`.`ADDRESS` (`VND_UID_FK` ASC);


-- -----------------------------------------------------
-- Table `calordersdb`.`EMC_TYPE_CD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`EMC_TYPE_CD` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`EMC_TYPE_CD` (
  `CODE` CHAR(4) NOT NULL,
  `SHORT_DESC` VARCHAR(64) NOT NULL,
  `LONG_DESC` VARCHAR(128) NOT NULL,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `calordersdb`.`CONTACT_INFO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`CONTACT_INFO` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`CONTACT_INFO` (
  `EMC_UID` INT(11) NOT NULL AUTO_INCREMENT,
  `PTY_UID_FK` INT(11) NULL,
  `DEP_UID_FK` INT(11) NULL,
  `EMC_TYPE_CD` CHAR(4) NOT NULL,
  `EMC_VALUE` VARCHAR(256) NOT NULL,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`EMC_UID`),
  CONSTRAINT `fk_EMC_TYPE_CD`
    FOREIGN KEY (`EMC_TYPE_CD`)
    REFERENCES `calordersdb`.`EMC_TYPE_CD` (`CODE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PARTY_CONTACT_PARTY`
    FOREIGN KEY (`PTY_UID_FK`)
    REFERENCES `calordersdb`.`PARTY` (`PTY_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CONTACT_PARTY`
    FOREIGN KEY (`DEP_UID_FK`)
    REFERENCES `calordersdb`.`DEPARTMENT` (`DEP_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_PARTY_CONTACT_PARTIES_idx` ON `calordersdb`.`CONTACT_INFO` (`PTY_UID_FK` ASC);

CREATE INDEX `fk_CONTACT_DEPARTMENT1_idx` ON `calordersdb`.`CONTACT_INFO` (`DEP_UID_FK` ASC);

CREATE INDEX `fk_EMC_TYPE_CD_idx` ON `calordersdb`.`CONTACT_INFO` (`EMC_TYPE_CD` ASC);


-- -----------------------------------------------------
-- Table `calordersdb`.`GROUP_PARTY_ASSOC`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calordersdb`.`GROUP_PARTY_ASSOC` ;

CREATE TABLE IF NOT EXISTS `calordersdb`.`GROUP_PARTY_ASSOC` (
  `GRE_UID` INT(11) NOT NULL AUTO_INCREMENT,
  `PTY_UID_FK` INT(11) NOT NULL,
  `GRP_UID_FK` INT(11) NOT NULL,
  `CREATE_USER_ID` VARCHAR(32) NOT NULL,
  `CREATE_TS` DATETIME NOT NULL,
  `UPDATE_USER_ID` VARCHAR(32) NOT NULL,
  `UPDATE_TS` DATETIME NOT NULL,
  PRIMARY KEY (`GRE_UID`),
  CONSTRAINT `fk_GROUP_PARTY_ASSOC_PARTY1`
    FOREIGN KEY (`PTY_UID_FK`)
    REFERENCES `calordersdb`.`PARTY` (`PTY_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_GROUP_PARTY_ASSOC_GROUPS1`
    FOREIGN KEY (`GRP_UID_FK`)
    REFERENCES `calordersdb`.`GROUPS` (`GRP_UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `GRE_UID_UNIQUE` ON `calordersdb`.`GROUP_PARTY_ASSOC` (`GRE_UID` ASC);

CREATE INDEX `fk_GROUP_PARTY_ASSOC_PARTYS1_idx` ON `calordersdb`.`GROUP_PARTY_ASSOC` (`PTY_UID_FK` ASC);

CREATE INDEX `fk_GROUP_PARTY_ASSOC_GROUPS1_idx` ON `calordersdb`.`GROUP_PARTY_ASSOC` (`GRP_UID_FK` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
