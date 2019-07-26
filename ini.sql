-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.6.41


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema medalfa
--

CREATE DATABASE IF NOT EXISTS medalfa;
USE medalfa;

--
-- Definition of table `_sequences`
--

DROP TABLE IF EXISTS `_sequences`;
CREATE TABLE `_sequences` (
  `name` varchar(70) NOT NULL,
  `next` int(11) NOT NULL,
  `inc` int(11) NOT NULL,
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `_sequences`
--

/*!40000 ALTER TABLE `_sequences` DISABLE KEYS */;
INSERT INTO `_sequences` (`name`,`next`,`inc`) VALUES 
 ('entradasalida_id_seq',3,1),
 ('producto_id_seq',4,1),
 ('usuario_id_seq',4,1);
/*!40000 ALTER TABLE `_sequences` ENABLE KEYS */;


--
-- Definition of table `entradasalida`
--

DROP TABLE IF EXISTS `entradasalida`;
CREATE TABLE `entradasalida` (
  `id` int(10) unsigned NOT NULL,
  `productoId` int(10) unsigned NOT NULL,
  `tipo` int(10) unsigned NOT NULL,
  `cantidad` int(10) unsigned NOT NULL,
  `motivo` varchar(90) NOT NULL,
  `fecha` datetime NOT NULL,
  `sumatoria` int(10) unsigned DEFAULT NULL,
  `usuarioId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_entradasalida_producto` (`productoId`),
  KEY `FK_entradasalida_usuario` (`usuarioId`),
  CONSTRAINT `FK_entradasalida_producto` FOREIGN KEY (`productoId`) REFERENCES `producto` (`id`),
  CONSTRAINT `FK_entradasalida_usuario` FOREIGN KEY (`usuarioId`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `entradasalida`
--

/*!40000 ALTER TABLE `entradasalida` DISABLE KEYS */;
INSERT INTO `entradasalida` (`id`,`productoId`,`tipo`,`cantidad`,`motivo`,`fecha`,`sumatoria`,`usuarioId`) VALUES 
 (1,1,0,23,'MOtivo','2019-06-27 20:38:39',23,NULL),
 (2,1,0,30,'Algo','2019-06-27 20:48:16',53,NULL);
/*!40000 ALTER TABLE `entradasalida` ENABLE KEYS */;


--
-- Definition of trigger `movimiento_i`
--

DROP TRIGGER /*!50030 IF EXISTS */ `movimiento_i`;

DELIMITER $$

CREATE TRIGGER `movimiento_i` BEFORE INSERT ON `entradasalida` FOR EACH ROW BEGIN

    DECLARE suma integer;

    SELECT sumatoria INTO suma FROM entradasalida where productoId = NEW.productoId order by id desc limit 1;

    IF ISNULL(suma) THEN
      SET suma = 0;
    END IF;

    IF NEW.tipo = 0 THEN
      SET NEW.sumatoria = suma + NEW.cantidad;
    ELSE
      SET NEW.sumatoria = suma - NEW.cantidad;
    END IF;



  END $$

DELIMITER ;

--
-- Definition of table `producto`
--

DROP TABLE IF EXISTS `producto`;
CREATE TABLE `producto` (
  `id` int(10) unsigned NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  `lote` varchar(45) NOT NULL,
  `caducidad` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `producto`
--

/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` (`id`,`nombre`,`descripcion`,`lote`,`caducidad`) VALUES 
 (1,'Producto','Descripcion del producto','123-AFR','2019-06-19 09:14:55'),
 (2,'Prueba','Prueba descripcion','ABC-654X','2019-06-20 19:00:00'),
 (3,'Orefta','Prueba descripcion','ABC-654X','2019-06-26 19:00:00');
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;


--
-- Definition of table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id` int(10) unsigned NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `correo` varchar(45) NOT NULL,
  `pass` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuario`
--

/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id`,`nombre`,`correo`,`pass`) VALUES 
 (1,'Aldo Escalona','aldo@prueba.com','*43684660CFDF6DCF5DBFDCD4B3246E50A5F0D23A'),
 (2,'Prueba','prueba@prueba.com','*F39A85FC42383B9852E49678DF30B923A0A7CA0E'),
 (3,'Prueba','prueba@prueba.com','*E9FC3A221BA421ED5BE7B70BAD6139AC280FADC6');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;


--
-- Definition of function `NextVal`
--

DROP FUNCTION IF EXISTS `NextVal`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE FUNCTION `NextVal`(vname VARCHAR(30)) RETURNS int(11)
BEGIN
          UPDATE _sequences
       SET next = (@next := next) + 1
       WHERE name = vname;

     RETURN @next;
  END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
