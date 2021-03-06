-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema BancoUNA
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema BancoUNA
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `BancoUNA` DEFAULT CHARACTER SET utf8 ;
USE `BancoUNA` ;

-- -----------------------------------------------------
-- Table `BancoUNA`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BancoUNA`.`Usuario` (
  `cedula` VARCHAR(9) NOT NULL,
  `nombre` VARCHAR(45) NULL,
  `apellido1` VARCHAR(45) NULL,
  `apellido2` VARCHAR(45) NULL,
  `contraseña` VARCHAR(45) NULL,
  `is` TINYINT(1) NULL DEFAULT 0,
  `telefono` VARCHAR(45) NULL,
  PRIMARY KEY (`cedula`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BancoUNA`.`Moneda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BancoUNA`.`Moneda` (
  `id` VARCHAR(3) NOT NULL,
  `tipo_cambio` DOUBLE NULL,
  `interes` DOUBLE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BancoUNA`.`Cuenta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BancoUNA`.`Cuenta` (
  `numero` INT NOT NULL AUTO_INCREMENT,
  `saldo` DOUBLE NULL DEFAULT '0.0',
  `estado` TINYINT(1) NULL DEFAULT 0,
  `descripcion` VARCHAR(45) NULL,
  `interesesG` DOUBLE NULL DEFAULT '0.0',
  `limite` DOUBLE NULL DEFAULT '0.0',
  `Usuario_cedula` VARCHAR(9) NOT NULL,
  `Moneda_id` VARCHAR(3) NOT NULL,
  PRIMARY KEY (`numero`),
  INDEX `fk_Cuenta_Usuario_idx` (`Usuario_cedula` ASC) VISIBLE,
  INDEX `fk_Cuenta_Moneda1_idx` (`Moneda_id` ASC) VISIBLE,
  CONSTRAINT `fk_Cuenta_Usuario`
    FOREIGN KEY (`Usuario_cedula`)
    REFERENCES `BancoUNA`.`Usuario` (`cedula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cuenta_Moneda1`
    FOREIGN KEY (`Moneda_id`)
    REFERENCES `BancoUNA`.`Moneda` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BancoUNA`.`Deposito`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BancoUNA`.`Deposito` (
  `id` INT NOT NULL DEFAULT 0,
  `monto` DOUBLE NULL DEFAULT '0.0',
  `motivo` VARCHAR(45) NULL,
  `fecha` DATETIME NULL,
  `nombreDepositante` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BancoUNA`.`Retiro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BancoUNA`.`Retiro` (
  `id` INT NOT NULL DEFAULT 0,
  `monto` DOUBLE NULL DEFAULT '0.0',
  `fecha` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BancoUNA`.`Movimiento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BancoUNA`.`Movimiento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATETIME NULL,
  `detalle` VARCHAR(45) NULL,
  `Deposito_id` INT NOT NULL,
  `Retiro_id` INT NOT NULL,
  `Cuenta_numero` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Movimiento_Deposito1_idx` (`Deposito_id` ASC) VISIBLE,
  INDEX `fk_Movimiento_Retiro1_idx` (`Retiro_id` ASC) VISIBLE,
  INDEX `fk_Movimiento_Cuenta1_idx` (`Cuenta_numero` ASC) VISIBLE,
  CONSTRAINT `fk_Movimiento_Deposito1`
    FOREIGN KEY (`Deposito_id`)
    REFERENCES `BancoUNA`.`Deposito` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Movimiento_Retiro1`
    FOREIGN KEY (`Retiro_id`)
    REFERENCES `BancoUNA`.`Retiro` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Movimiento_Cuenta1`
    FOREIGN KEY (`Cuenta_numero`)
    REFERENCES `BancoUNA`.`Cuenta` (`numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BancoUNA`.`CuentaFavorita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BancoUNA`.`CuentaFavorita` (
  `numero` INT NOT NULL AUTO_INCREMENT,
  `Cuenta_numero` INT NOT NULL,
  `Usuario_cedula` VARCHAR(9) NOT NULL,
  PRIMARY KEY (`numero`),
  INDEX `fk_CuentaFavorita_Cuenta1_idx` (`Cuenta_numero` ASC) VISIBLE,
  INDEX `fk_CuentaFavorita_Usuario1_idx` (`Usuario_cedula` ASC) VISIBLE,
  CONSTRAINT `fk_CuentaFavorita_Cuenta1`
    FOREIGN KEY (`Cuenta_numero`)
    REFERENCES `BancoUNA`.`Cuenta` (`numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CuentaFavorita_Usuario1`
    FOREIGN KEY (`Usuario_cedula`)
    REFERENCES `BancoUNA`.`Usuario` (`cedula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `BancoUNA`.`Usuario`
-- -----------------------------------------------------
START TRANSACTION;
USE `BancoUNA`;
INSERT INTO `BancoUNA`.`Usuario` (`cedula`, `nombre`, `apellido1`, `apellido2`, `contraseña`, `is`, `telefono`) VALUES ('admin', 'admin', 'admin', 'admin', 'admin', 1, '88888888');

COMMIT;


-- -----------------------------------------------------
-- Data for table `BancoUNA`.`Moneda`
-- -----------------------------------------------------
START TRANSACTION;
USE `BancoUNA`;
INSERT INTO `BancoUNA`.`Moneda` (`id`, `tipo_cambio`, `interes`) VALUES ('CRC', 566.14, 0.05);
INSERT INTO `BancoUNA`.`Moneda` (`id`, `tipo_cambio`, `interes`) VALUES ('EUR', 0.89, 0.08);
INSERT INTO `BancoUNA`.`Moneda` (`id`, `tipo_cambio`, `interes`) VALUES ('USD', 1, 0.10);

COMMIT;


-- -----------------------------------------------------
-- Data for table `BancoUNA`.`Deposito`
-- -----------------------------------------------------
START TRANSACTION;
USE `BancoUNA`;
INSERT INTO `BancoUNA`.`Deposito` (`id`, `monto`, `motivo`, `fecha`, `nombreDepositante`) VALUES (0, NULL, NULL, NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `BancoUNA`.`Retiro`
-- -----------------------------------------------------
START TRANSACTION;
USE `BancoUNA`;
INSERT INTO `BancoUNA`.`Retiro` (`id`, `monto`, `fecha`) VALUES (0, NULL, NULL);

COMMIT;

