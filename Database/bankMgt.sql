CREATE DATABASE  IF NOT EXISTS `onlinebankingportal` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `onlinebankingportal`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: onlinebankingportal
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `accid` int NOT NULL AUTO_INCREMENT,
  `accno` varchar(255) DEFAULT NULL,
  `acctype` varchar(255) DEFAULT NULL,
  `balance` double NOT NULL,
  `ifsccode` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `userid` int NOT NULL,
  PRIMARY KEY (`accid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'517812381042453','SAVINGS A/C',14400,'DEMO0015014','ACTIVE',2),(2,'447304755695245','SAVINGS A/C',35600,'DEMO0015014','ACTIVE',5);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditcard`
--

DROP TABLE IF EXISTS `creditcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `creditcard` (
  `creditcardid` int NOT NULL AUTO_INCREMENT,
  `balance` double NOT NULL,
  `creditcardcvv` varchar(255) DEFAULT NULL,
  `creditcardlimit` double NOT NULL,
  `creditcardname` varchar(255) DEFAULT NULL,
  `creditcardnumber` varchar(255) DEFAULT NULL,
  `expirydate` date DEFAULT NULL,
  `loanamount` double NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `accid` int DEFAULT NULL,
  PRIMARY KEY (`creditcardid`),
  KEY `FKdwjmamirudkhv69tgxtpiwgr7` (`accid`),
  CONSTRAINT `FKdwjmamirudkhv69tgxtpiwgr7` FOREIGN KEY (`accid`) REFERENCES `account` (`accid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditcard`
--

LOCK TABLES `creditcard` WRITE;
/*!40000 ALTER TABLE `creditcard` DISABLE KEYS */;
/*!40000 ALTER TABLE `creditcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `giftcard`
--

DROP TABLE IF EXISTS `giftcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `giftcard` (
  `giftcardid` int NOT NULL AUTO_INCREMENT,
  `giftcardamount` double NOT NULL,
  `giftcardname` varchar(255) DEFAULT NULL,
  `recipientemail` varchar(255) DEFAULT NULL,
  `recipientname` varchar(255) DEFAULT NULL,
  `accid` int DEFAULT NULL,
  PRIMARY KEY (`giftcardid`),
  KEY `FKmydveu48b8iy18byvbx8ke1m2` (`accid`),
  CONSTRAINT `FKmydveu48b8iy18byvbx8ke1m2` FOREIGN KEY (`accid`) REFERENCES `account` (`accid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `giftcard`
--

LOCK TABLES `giftcard` WRITE;
/*!40000 ALTER TABLE `giftcard` DISABLE KEYS */;
/*!40000 ALTER TABLE `giftcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `loan` (
  `loanid` int NOT NULL AUTO_INCREMENT,
  `duration` int NOT NULL,
  `enddate` date DEFAULT NULL,
  `loanamount` double NOT NULL,
  `loantype` varchar(255) DEFAULT NULL,
  `monthlyemi` double NOT NULL,
  `statedate` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `accid` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`loanid`),
  KEY `FKqk5bsauisqaokxw8mq9boicw0` (`accid`),
  KEY `FKk05v2kcp9baxpi6mmpblwgcsb` (`user_id`),
  CONSTRAINT `FKk05v2kcp9baxpi6mmpblwgcsb` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKqk5bsauisqaokxw8mq9boicw0` FOREIGN KEY (`accid`) REFERENCES `account` (`accid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locker`
--

DROP TABLE IF EXISTS `locker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `locker` (
  `lockerid` int NOT NULL AUTO_INCREMENT,
  `amount_paid` bit(1) DEFAULT NULL,
  `lockerlocation` varchar(255) DEFAULT NULL,
  `lockerprice` double NOT NULL,
  `lockersize` varchar(255) DEFAULT NULL,
  `lockerstatus` varchar(255) DEFAULT NULL,
  `lockertype` varchar(255) DEFAULT NULL,
  `accid` int DEFAULT NULL,
  PRIMARY KEY (`lockerid`),
  KEY `FKf78irj0v3x9gdf9yc5pc9phjr` (`accid`),
  CONSTRAINT `FKf78irj0v3x9gdf9yc5pc9phjr` FOREIGN KEY (`accid`) REFERENCES `account` (`accid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locker`
--

LOCK TABLES `locker` WRITE;
/*!40000 ALTER TABLE `locker` DISABLE KEYS */;
/*!40000 ALTER TABLE `locker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `roleid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `userid` int NOT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN',1),(2,'ROLE_CUSTOMER',2),(3,'ROLE_EMPLOYEE',3),(5,'ROLE_CUSTOMER',5);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `transactionid` varchar(255) NOT NULL,
  `amount` double NOT NULL,
  `datetime` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `transactiontype` varchar(255) DEFAULT NULL,
  `accid` int DEFAULT NULL,
  PRIMARY KEY (`transactionid`),
  KEY `FKqb2brgb67jdbg08kfeplpfr09` (`accid`),
  CONSTRAINT `FKqb2brgb67jdbg08kfeplpfr09` FOREIGN KEY (`accid`) REFERENCES `account` (`accid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES ('1jbKQp9SG0HhKaV',600,'2023-10-27 10:46:46.504341','Withdrawal By Bank Transfer','DEBIT',1),('5kOXYzT2M2bw3iZ',20000,'2023-10-27 10:37:42.082670','Deposited From Bank/ATM','CREDIT',2),('9OR32QABE3cyxZ1',500,'2023-10-27 10:37:52.771947','Withdrawal From Bank/ATM','DEBIT',2),('BeuiQr4B3zYEMw6',600,'2023-10-27 10:46:46.520350','Deposited By Bank Transfer','CREDIT',2),('KmoWYc9BzrWCwnP',5000,'2023-10-27 10:38:35.525028','Withdrawal By Bank Transfer','DEBIT',2),('quzj27hRNh8LpGZ',5000,'2023-10-27 10:46:18.306456','Deposited From Bank/ATM','CREDIT',1),('r49T0NPm0fcByKH',5000,'2023-10-27 10:38:35.542149','Deposited By Bank Transfer','CREDIT',2),('YLwAseXCBvNrFKQ',500,'2023-10-27 10:36:41.495296','Deposited From Bank/ATM','CREDIT',2);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `blocked` bit(1) NOT NULL,
  `loginAttempts` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Nowhere','admin@gmail.com','admin','**********','admin',_binary '\0',0),(2,'fgg','Customer@gmail.com','Demo@123','8585858585','Customer',_binary '\0',0),(3,'fdf','employee@gmail.com','Demo@123','8585858987','employee',_binary '\0',0),(5,'ffgfdg','user2@gmail.com','Demo@123','8585858585','user2',_binary '\0',0);
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

-- Dump completed on 2023-10-27 10:49:54
