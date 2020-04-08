CREATE DATABASE  IF NOT EXISTS `utext` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `utext`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: utext
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `messaggio`
--

DROP TABLE IF EXISTS `messaggio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messaggio` (
  `idMessaggio` int(11) NOT NULL AUTO_INCREMENT,
  `timestampInvio` timestamp NULL DEFAULT NULL,
  `mittente` varchar(45) DEFAULT NULL,
  `destinatario` varchar(45) DEFAULT NULL,
  `testo` text,
  PRIMARY KEY (`idMessaggio`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messaggio`
--

LOCK TABLES `messaggio` WRITE;
/*!40000 ALTER TABLE `messaggio` DISABLE KEYS */;
INSERT INTO `messaggio` VALUES (1,'2018-03-03 17:03:15','Bella','Marco','Bella li'),(2,'2018-12-23 16:03:53','Marco','Paris','Ciao'),(3,'2018-12-23 16:03:57','Marco','Paris','Bella raga'),(4,'2018-12-23 16:04:04','Marco','Graziano','Ciaoneee'),(5,'2018-12-23 16:05:48','Marco','Graziano','Bella'),(6,'2018-12-23 16:05:55','Marco','Bella','Ciaonee'),(7,'2018-12-23 16:06:03','Marco','Beppe','Beppe vieni da me daiii'),(8,'2018-03-03 17:03:15','Beppe','Marco','NO!'),(9,'2018-12-23 16:06:28','Marco','Beppe','Daiiiiii'),(10,'2018-03-03 17:03:15','Beppe','Marco','NO!'),(11,'2018-03-03 17:03:15','Beppe','Marco','NO!'),(12,'2018-03-03 17:03:15','Beppe','Marco','NO!'),(13,'2018-03-03 17:03:15','Beppe','Marco','NO!'),(14,'2018-03-03 17:03:15','Beppe','Marco','NO!'),(15,'2018-03-03 17:03:15','Beppe','Marco','NO!'),(16,'2018-03-03 17:03:15','Beppe','Marco','NO!'),(17,'2018-03-03 17:03:15','Raga','Marco','oi!'),(18,'2018-03-03 17:03:15','Raga','Marco','oi!'),(19,'2018-12-30 15:41:02','Marco','Beppe','Ciao'),(20,'2018-12-30 15:45:19','Marco','Beppe','Bellaraga'),(21,'2018-12-30 15:45:19','Marco','Beppe','Bellaraga'),(22,'2018-12-30 15:45:28','Beppe','Marco','Ciaone'),(23,'2018-12-30 15:45:28','Beppe','Marco','Ciaone'),(24,'2018-12-30 15:52:57','Beppe','Marco','Bella raga'),(25,'2018-12-30 15:52:57','Beppe','Marco','Bella raga'),(26,'2018-12-30 15:53:13','Marco','Beppe','Ue nino'),(27,'2018-12-30 15:53:24','Marco','Beppe','Ue'),(28,'2018-12-30 15:54:01','Marco','Beppe','Ue'),(29,'2018-12-30 15:54:01','Marco','Beppe','Ue'),(30,'2018-12-30 15:55:27','Marco','Beppe','Ue'),(31,'2018-12-30 15:55:36','Marco','Beppe','Bella'),(32,'2018-12-30 15:55:43','Beppe','Marco','Tanta roba'),(33,'2018-12-30 15:55:43','Beppe','Marco','Tanta roba'),(34,'2018-12-30 15:55:50','Marco','Beppe','Ciao'),(35,'2018-12-30 15:56:06','Beppe','Marco','Ciao'),(36,'2018-12-30 15:56:06','Beppe','Marco','Ciao'),(37,'2018-12-30 16:01:14','Beppe','Marco','Bella'),(38,'2018-12-30 16:01:14','Beppe','Marco','Bella'),(39,'2018-12-30 16:01:33','Beppe','Marco','Ciaoone'),(40,'2018-12-30 16:01:33','Beppe','Marco','Ciaoone'),(41,'2018-12-30 16:05:55','Marco','Beppe','Bellaragah'),(42,'2018-12-30 16:05:55','Marco','Beppe','Bellaragah'),(43,'2018-12-30 16:06:26','Marco','Beppe','Ciaone'),(44,'2018-12-30 16:06:42','Marco','Beppe','ne'),(45,'2018-12-30 16:06:50','Marco','Beppe','sasss'),(46,'2018-12-30 16:07:00','Beppe','Marco','ciao'),(47,'2018-12-30 16:07:00','Beppe','Marco','ciao'),(48,'2018-12-30 16:07:03','Beppe','Marco','bll'),(49,'2018-12-30 16:07:03','Beppe','Marco','bll'),(50,'2018-12-30 16:20:05','Marco','Beppe','Ciao'),(51,'2018-12-30 16:20:05','Marco','Beppe','Ciao'),(52,'2018-12-30 16:20:16','Beppe','Marco','Bella'),(53,'2018-12-30 16:20:16','Beppe','Marco','Bella'),(54,'2018-12-30 16:20:26','Marco','Beppe','Ma ciao'),(55,'2018-12-30 16:20:26','Marco','Beppe','Ma ciao'),(56,'2018-12-30 16:20:45','Marco','Beppe','ehi'),(57,'2018-12-30 16:20:45','Marco','Beppe','ehi'),(58,'2018-12-30 16:20:59','Marco','Beppe','Bellaraga'),(59,'2018-12-30 16:20:59','Marco','Beppe','Bellaraga'),(60,'2018-12-30 16:21:18','Beppe','Marco','ciaone'),(61,'2018-12-30 16:21:18','Beppe','Marco','ciaone'),(62,'2018-12-30 16:21:28','Marco','Beppe','Senti nino'),(63,'2018-12-30 16:21:28','Marco','Beppe','Senti nino'),(64,'2018-12-30 18:00:32','Marco','Bella','Ciao'),(65,'2018-12-31 09:37:14','Marco','Beppe','Ciao'),(66,'2018-12-31 09:37:14','Marco','Beppe','Ciao'),(67,'2018-12-31 09:37:23','Beppe','Marco','Bella li'),(68,'2018-12-31 09:37:38','Beppe','Marco','OOO'),(69,'2018-12-31 09:37:54','Marco','Beppe','CIaone'),(70,'2018-12-31 09:37:54','Marco','Beppe','CIaone'),(71,'2018-12-31 09:38:20','Beppe','Raga','Bellaraga'),(72,'2018-12-31 09:38:33','Beppe','Marco','Bellaraga'),(73,'2018-12-31 09:38:45','Marco','Beppe','Cioane'),(74,'2018-12-31 09:38:45','Marco','Beppe','Cioane'),(75,'2018-12-31 09:38:56','Beppe','Marco','Saas'),(76,'2018-12-31 09:40:10','Beppe','Marco','Ciaone'),(77,'2018-12-31 09:40:10','Beppe','Marco','Ciaone'),(78,'2018-12-31 09:40:18','Marco','Beppe','Bella'),(79,'2018-12-31 09:40:18','Marco','Beppe','Bella'),(80,'2018-12-31 09:40:28','Beppe','Marco','AHH'),(81,'2018-12-31 09:40:28','Beppe','Marco','AHH'),(82,'2018-12-31 09:40:38','Marco','Beppe','SAAS'),(83,'2018-12-31 09:40:38','Marco','Beppe','SAAS'),(84,'2018-12-31 09:40:45','Beppe','Marco','Bella'),(85,'2018-12-31 09:40:45','Beppe','Marco','Bella'),(86,'2018-12-31 09:40:50','Marco','Beppe','Bella'),(87,'2018-12-31 09:40:50','Marco','Beppe','Bella'),(88,'2018-12-31 09:46:09','Beppe','Marco','ehila'),(89,'2018-12-31 09:46:09','Beppe','Marco','ehila'),(90,'2018-12-31 09:46:19','Marco','Beppe','Ciaone'),(91,'2018-12-31 09:46:19','Marco','Beppe','Ciaone'),(92,'2018-12-31 09:46:28','Beppe','Marco','Bellali'),(93,'2018-12-31 09:46:39','Marco','Beppe','Sis'),(94,'2018-12-31 09:46:39','Marco','Beppe','Sis'),(95,'2018-12-31 09:46:46','Beppe','Marco','Ciaone'),(96,'2018-12-31 09:50:05','Beppe','Marco','Maron'),(97,'2018-12-31 09:50:05','Beppe','Marco','Maron'),(98,'2018-12-31 09:50:14','Marco','Beppe','Ciao'),(99,'2018-12-31 09:50:14','Marco','Beppe','Ciao'),(100,'2018-12-31 09:52:51','Beppe','Marco','Bella'),(101,'2018-12-31 09:52:51','Beppe','Marco','Bella'),(102,'2018-12-31 09:53:50','Beppe','Marco','Ciaone'),(103,'2018-12-31 09:53:50','Beppe','Marco','Ciaone'),(104,'2018-12-31 09:54:35','Beppe','Marco','Marco'),(105,'2018-12-31 09:54:35','Beppe','Marco','Marco'),(106,'2018-12-31 09:54:46','Marco','Beppe','Ciao'),(107,'2018-12-31 09:54:46','Marco','Beppe','Ciao'),(108,'2018-12-31 09:54:55','Beppe','Marco','Bellali'),(109,'2018-12-31 09:55:08','Beppe','Marco','SAS'),(110,'2018-12-31 09:55:51','Beppe','Marco','SAAS'),(111,'2018-12-31 09:55:51','Beppe','Marco','SAAS'),(112,'2018-12-31 09:55:57','Marco','Beppe','Bella'),(113,'2018-12-31 09:55:57','Marco','Beppe','Bella'),(114,'2018-12-31 09:56:01','Beppe','Marco','BELLA'),(115,'2018-12-31 09:56:01','Beppe','Marco','BELLA'),(116,'2018-12-31 09:57:09','Beppe','Marco','Ciaone'),(117,'2018-12-31 09:57:09','Beppe','Marco','Ciaone'),(118,'2018-12-31 09:57:57','Beppe','Marco','Ba'),(119,'2018-12-31 09:57:57','Beppe','Marco','Ba'),(120,'2018-12-31 09:58:12','Beppe','Marco','Ciaone'),(121,'2018-12-31 09:58:12','Beppe','Marco','Ciaone'),(122,'2018-12-31 09:58:20','Marco','Beppe','Sis'),(123,'2018-12-31 09:58:20','Marco','Beppe','Sis'),(124,'2018-12-31 09:58:29','Beppe','Marco','Bellaraga'),(125,'2018-12-31 09:58:29','Beppe','Marco','Bellaraga'),(126,'2018-12-31 09:58:38','Beppe','Marco','Saas'),(127,'2018-12-31 09:58:38','Beppe','Marco','Saas'),(128,'2018-12-31 09:58:46','Beppe','Marco','Bischero'),(129,'2018-12-31 09:58:46','Beppe','Marco','Bischero'),(130,'2018-12-31 09:58:54','Marco','Beppe','SAOS'),(131,'2018-12-31 09:58:54','Marco','Beppe','SAOS'),(132,'2018-12-31 11:10:05','Beppe','Marco','Bellarag'),(133,'2018-12-31 11:10:05','Beppe','Marco','Bellarag'),(134,'2018-12-31 11:10:14','Marco','Beppe','Ciao'),(135,'2018-12-31 11:10:14','Marco','Beppe','Ciao'),(136,'2018-12-31 11:13:45','Marco','Beppe','Ciao'),(137,'2018-12-31 11:13:45','Marco','Beppe','Ciao'),(138,'2018-12-31 11:13:52','Beppe','Marco','Hola chico'),(139,'2018-12-31 11:13:52','Beppe','Marco','Hola chico'),(140,'2018-12-31 11:14:06','Marco','Beppe','Bellaraga'),(141,'2018-12-31 11:14:06','Marco','Beppe','Bellaraga'),(142,'2019-01-01 15:58:33','Marco','Beppe','SAASDSD'),(143,'2019-01-01 15:58:33','Marco','Beppe','SAASDSD'),(144,'2019-01-01 15:58:39','Beppe','Marco','BELLARAGA'),(145,'2019-01-01 15:58:39','Beppe','Marco','BELLARAGA'),(146,'2019-01-01 16:41:09','Marco','Beppe','Bellaraga'),(147,'2019-01-01 17:10:40','Marco','Beppe','Bellaraga'),(148,'2019-01-01 18:14:24','Marco','Raga','Bellaragadedewdw'),(149,'2019-01-02 16:32:32','Marco','Beppe','ciao'),(150,'2019-01-07 11:08:54','Marco','Beppe','MESSAGGIO IN BUFFER'),(151,'2019-01-07 11:11:43','Marco','Cio','iao'),(152,'2019-01-07 11:08:54','Marco','Beppe','MESSAGGIO IN BUFFER'),(153,'2019-01-07 11:12:58','Marco','Beppe','Ciaone'),(154,'2019-01-07 11:12:58','Marco','Beppe','Ciaone'),(155,'2019-01-07 11:13:10','Marco','Beppe','Ohui'),(156,'2019-01-07 11:13:10','Marco','Beppe','Ohui'),(157,'2019-01-07 11:23:24','Marco','Beppe','Ciao'),(158,'2019-01-07 12:22:05','Marco','Beppe','Ciaone'),(159,'2019-01-07 12:22:09','Marco','Beppe','OIIIII');
/*!40000 ALTER TABLE `messaggio` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-07 16:23:58
