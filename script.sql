
-- -----------------------------------------------------
-- Schema NegotiationDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `NegotiationDB` DEFAULT CHARACTER SET utf8 ;
USE `NegotiationDB` ;

-- -----------------------------------------------------
-- Table `NegotiationDB`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NegotiationDB`.`category` (
  `category_code` INT NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`category_code`));


-- -----------------------------------------------------
-- Table `NegotiationDB`.`merchant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NegotiationDB`.`merchant` (
  `merchant_code` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `create_time` DATETIME NULL,
  `mmc` INT NOT NULL,
  UNIQUE INDEX `id_mechant_UNIQUE` (`merchant_code` ASC) VISIBLE,
  PRIMARY KEY (`merchant_code`, `mmc`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `fk_merchant_category1_idx` (`mmc` ASC) VISIBLE,
  CONSTRAINT `fk_merchant_category1`
    FOREIGN KEY (`mmc`)
    REFERENCES `NegotiationDB`.`category` (`category_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `NegotiationDB`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NegotiationDB`.`account` (
  `account_code` BIGINT NOT NULL AUTO_INCREMENT,
  `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`account_code`));


-- -----------------------------------------------------
-- Table `NegotiationDB`.`account_category_balance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NegotiationDB`.`account_category_balance` (
  `account_code` BIGINT NOT NULL,
  `category_code` INT NOT NULL,
  `balance` DECIMAL NULL,
  PRIMARY KEY (`account_code`, `category_code`),
  INDEX `fk_account_has_category_category1_idx` (`category_code` ASC) VISIBLE,
  INDEX `fk_account_has_category_account1_idx` (`account_code` ASC) VISIBLE,
  CONSTRAINT `fk_account_has_category_account1`
    FOREIGN KEY (`account_code`)
    REFERENCES `NegotiationDB`.`account` (`account_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_account_has_category_category1`
    FOREIGN KEY (`category_code`)
    REFERENCES `NegotiationDB`.`category` (`category_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `NegotiationDB`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NegotiationDB`.`transaction` (
  `transaction_code` BIGINT NOT NULL AUTO_INCREMENT,
  `total_amount` DECIMAL NULL,
  `response_code` VARCHAR(25) NOT NULL,
  `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `merchant_code` BIGINT NULL,
  `account_code` BIGINT NULL,
  `mmc` INT NULL,
  PRIMARY KEY (`transaction_code`),
  INDEX `fk_transaction_merchant1_idx` (`merchant_code` ASC) VISIBLE,
  INDEX `fk_transaction_account1_idx` (`account_code` ASC) VISIBLE,
  INDEX `fk_transaction_category1_idx` (`mmc` ASC) VISIBLE,
  CONSTRAINT `fk_transaction_merchant1`
    FOREIGN KEY (`merchant_code`)
    REFERENCES `NegotiationDB`.`merchant` (`merchant_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_account1`
    FOREIGN KEY (`account_code`)
    REFERENCES `NegotiationDB`.`account` (`account_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_category1`
    FOREIGN KEY (`mmc`)
    REFERENCES `NegotiationDB`.`category` (`category_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
