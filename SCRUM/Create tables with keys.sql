SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema SCRUMProject
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `SCRUMProject` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `SCRUMProject` ;

-- -----------------------------------------------------
-- Table `SCRUMProject`.`Roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SCRUMProject`.`Roles` ;

CREATE TABLE IF NOT EXISTS `SCRUMProject`.`Roles` (
  `RoleID` INT NOT NULL AUTO_INCREMENT,
  `Role` VARCHAR(50) NULL,
  PRIMARY KEY (`RoleID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SCRUMProject`.`Teams`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SCRUMProject`.`Teams` ;

CREATE TABLE IF NOT EXISTS `SCRUMProject`.`Teams` (
  `TeamID` INT NULL AUTO_INCREMENT,
  `TeamName` VARCHAR(50) NULL,
  `ProjectName` VARCHAR(50) NULL,
  PRIMARY KEY (`TeamID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SCRUMProject`.`Employees`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SCRUMProject`.`Employees` ;

CREATE TABLE IF NOT EXISTS `SCRUMProject`.`Employees` (
  `EmployeeID` INT NOT NULL AUTO_INCREMENT,
  `FName` VARCHAR(50) NULL,
  `LName` VARCHAR(50) NULL,
  `RoleID` INT NULL,
  `TeamID` INT NULL,
  PRIMARY KEY (`EmployeeID`),
  INDEX `RoleID_idx` (`RoleID` ASC),
  INDEX `TeamID_idx` (`TeamID` ASC),
  CONSTRAINT `RoleID`
    FOREIGN KEY (`RoleID`)
    REFERENCES `SCRUMProject`.`Roles` (`RoleID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `TeamID`
    FOREIGN KEY (`TeamID`)
    REFERENCES `SCRUMProject`.`Teams` (`TeamID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SCRUMProject`.`Backlogs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SCRUMProject`.`Backlogs` ;

CREATE TABLE IF NOT EXISTS `SCRUMProject`.`Backlogs` (
  `BacklogID` INT NOT NULL AUTO_INCREMENT,
  `EmployeeID` INT NULL,
  `Goal` VARCHAR(50) NULL,
  `Benefit` VARCHAR(50) NULL,
  `Date` DATE NULL,
  PRIMARY KEY (`BacklogID`),
  INDEX `EmployeeID_idx` (`EmployeeID` ASC),
  CONSTRAINT `EmployeeID`
    FOREIGN KEY (`EmployeeID`)
    REFERENCES `SCRUMProject`.`Employees` (`EmployeeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SCRUMProject`.`Sprints`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SCRUMProject`.`Sprints` ;

CREATE TABLE IF NOT EXISTS `SCRUMProject`.`Sprints` (
  `SprintID` INT NOT NULL AUTO_INCREMENT,
  `BacklogID` INT NULL,
  `Date` DATE NULL,
  `TeamID` INT NULL,
  PRIMARY KEY (`SprintID`),
  INDEX `BacklogID_idx` (`BacklogID` ASC),
  INDEX `TeamID_idx` (`TeamID` ASC),
  CONSTRAINT `BacklogID`
    FOREIGN KEY (`BacklogID`)
    REFERENCES `SCRUMProject`.`Backlogs` (`BacklogID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `TeamID`
    FOREIGN KEY (`TeamID`)
    REFERENCES `SCRUMProject`.`Teams` (`TeamID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SCRUMProject`.`StakeHolders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SCRUMProject`.`StakeHolders` ;

CREATE TABLE IF NOT EXISTS `SCRUMProject`.`StakeHolders` (
  `StakeHolderID` INT NOT NULL AUTO_INCREMENT,
  `FName` VARCHAR(50) NULL,
  `LName` VARCHAR(50) NULL,
  `RoleID` INT NULL,
  PRIMARY KEY (`StakeHolderID`),
  INDEX `RoleID_idx` (`RoleID` ASC),
  CONSTRAINT `RoleID`
    FOREIGN KEY (`RoleID`)
    REFERENCES `SCRUMProject`.`Roles` (`RoleID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
