CREATE DATABASE `minecraft-data` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `minecraft-data`;
CREATE TABLE `accounts` (
  `idaccounts` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `credit` int(11) NOT NULL DEFAULT '0',
  `balance` double(32,8) DEFAULT NULL,
  PRIMARY KEY (`idaccounts`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `players` (
  `idplayers` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `hashword` varchar(256) NOT NULL,
  `verificationcode` varchar(45) NOT NULL,
  `isverified` int(11) NOT NULL DEFAULT '0',
  `accountid` int(11) DEFAULT NULL,
  PRIMARY KEY (`idplayers`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `transactions` (
  `idtransactions` int(11) NOT NULL AUTO_INCREMENT,
  `sender` int(11) NOT NULL,
  `reciever` int(11) NOT NULL,
  `amount` double(32,8) NOT NULL,
  `senderLabel` varchar(45) NOT NULL,
  `recieverLabel` varchar(45) NOT NULL,
  `message` varchar(45) NOT NULL,
  `time` int(32) NOT NULL,
  PRIMARY KEY (`idtransactions`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `expenses` (
  `idexpenses` int(11) NOT NULL,
  `sender` int(11) NOT NULL,
  `reciever` int(11) NOT NULL,
  `amount` double NOT NULL,
  `message` varchar(45) NOT NULL,
  PRIMARY KEY (`idexpenses`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `about` (
  `currentPlayersOnline` int(11) NOT NULL,
  `maxPlayersOnline` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `towns` (
  `idtowns` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `dateFounded` int(11) NOT NULL,
  `ownerName` varchar(45) NOT NULL,
  `ownerAccountId` varchar(45) NOT NULL,
  `centerX` int(11) NOT NULL,
  `centerZ` int(11) NOT NULL,
  `radius` int(11) NOT NULL,
  `mobKillTaxPerc` double NOT NULL,
  `chestShopTaxPerc` double NOT NULL,
  `warpTaxPerc` double NOT NULL,
  `auctionTaxPerc` double NOT NULL,
  `shippingTaxPerc` double NOT NULL,
  `dailyMemberTaxAmount` double NOT NULL,
  PRIMARY KEY (`idtowns`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




