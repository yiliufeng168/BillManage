-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: dxq_db
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `bill_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `event_id` int DEFAULT NULL,
  `money` float DEFAULT NULL,
  `bill_date` date DEFAULT NULL,
  `note` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (1,2,4,36.5,'2020-06-23',NULL),(2,2,4,36.5,'2016-02-23',NULL),(6,2,3,-175,'2016-02-23',NULL),(7,2,3,-175,'2018-04-15','中了500万'),(8,2,3,-175,'2018-04-15','中了500万'),(9,2,3,-175,'2018-12-02','运气真好'),(11,7,10,2500,'2020-06-01','工资太少了'),(14,7,5,15,'2020-06-01','煤气打八折'),(15,7,18,50,'2020-06-02','话费'),(17,7,19,3600,'2020-06-04','中彩票了'),(18,7,5,159,'2020-06-01','煤气打八折'),(19,7,8,159,'2020-06-01','煤气打八折'),(20,12,20,26000,'2020-06-01','加新500'),(21,12,21,200,'2020-06-02','帮人买水'),(22,12,22,120,'2020-06-03','努力挣钱'),(23,12,24,2500,'2020-06-05','有车了'),(24,12,25,50,'2020-06-06','今晚做红烧肉'),(25,12,26,50,'2020-06-06','两天打了一百个电话'),(26,12,27,60,'2020-06-08','煤气真难吸');
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `events` (
  `event_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `event_name` varchar(50) DEFAULT NULL,
  `event_type` int DEFAULT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (2,2,'买车',0),(3,2,'交电费',0),(4,2,'交水费',0),(5,7,'煤气费',0),(8,7,'交水费',0),(9,7,'买菜',0),(10,7,'工资',1),(11,7,'奖金',1),(12,7,'买电脑',0),(13,7,'买手机',0),(16,7,'买火箭',0),(17,7,'买导弹',0),(18,7,'话费充值',0),(19,7,'彩票',1),(20,12,'工资',1),(21,12,'跑腿',1),(22,12,'开滴滴',1),(23,12,'开飞机',1),(24,12,'买车',0),(25,12,'买菜',0),(26,12,'电话费',0),(27,12,'煤气费',0);
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) DEFAULT NULL,
  `user_password` varchar(20) DEFAULT NULL,
  `user_age` int DEFAULT NULL,
  `user_phone` varchar(20) DEFAULT NULL,
  `user_sex` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'张三','123',13,'17855558888','男'),(2,'lisi','��饇�&�R�=��F�E',15,'17848485959','男'),(3,'wangwu',' ,筨琘[朘-#Kp',15,'17889895656','男'),(4,'杨过',' ,筨琘[朘-#Kp',123,'17858565452','男'),(5,'小龙女',' ,筨琘[朘-#Kp',125,'16969698989','男'),(6,'武则天',' ,筨琘[朘-#Kp',256,'17858963232','女'),(7,'ylf',' ,筨琘[朘-#Kp',225,'18856595857','男'),(8,'zhansan',' ,筨琘[朘-#Kp',123,'15656562356','男'),(9,'刘六',' ,筨琘[朘-#Kp',45,'878788565','男'),(10,'赵四','�\n�9I篩V郬��>',26,'365659596','男'),(11,'tangsan','�\n�9I篩V郬��>',756,'175485623','男'),(12,'杨狄锋',' ,筨琘[朘-#Kp',22,'1787858624','男');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-26 11:51:31
