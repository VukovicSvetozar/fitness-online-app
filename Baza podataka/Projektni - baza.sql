-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: fitnessonline
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrator` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(255) NOT NULL,
  `prezime` varchar(255) NOT NULL,
  `korisnicko_ime` varchar(50) NOT NULL,
  `lozinka` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `korisnicko_ime` (`korisnicko_ime`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (1,'Ana','Kostić','dmnstrtra','$2a$12$55HFaNdS0cZFvfdseeniuOBSP00C606yJms7oKGPr3G2FJyyTDQ4a');
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aktivnost`
--

DROP TABLE IF EXISTS `aktivnost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aktivnost` (
  `id` int NOT NULL AUTO_INCREMENT,
  `broj_ponavljanja` int NOT NULL,
  `datum` varchar(255) NOT NULL,
  `intezitet` varchar(255) NOT NULL,
  `naziv` varchar(255) NOT NULL,
  `trajanje` int NOT NULL,
  `zavrseno` bit(1) NOT NULL,
  `kategorija_id` int NOT NULL,
  `korisnik_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhgesccg1cfcsn6c29w75sv48f` (`kategorija_id`),
  KEY `FKjnhdljoufmoyuwib9wmvglnkp` (`korisnik_id`),
  CONSTRAINT `FKhgesccg1cfcsn6c29w75sv48f` FOREIGN KEY (`kategorija_id`) REFERENCES `kategorija` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKjnhdljoufmoyuwib9wmvglnkp` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnik` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aktivnost`
--

LOCK TABLES `aktivnost` WRITE;
/*!40000 ALTER TABLE `aktivnost` DISABLE KEYS */;
INSERT INTO `aktivnost` VALUES (32,1,'2024-03-20','umjereno','Rastezanje',20,_binary '',1,17),(33,10,'2024-03-20','umjereno','Sklekovi',10,_binary '\0',9,17),(34,2,'2024-03-20','lako','Sprint',10,_binary '',1,17),(35,3,'2024-03-21','umjereno','Plank',2,_binary '',12,17),(36,5,'2024-03-25','umjereno','Sklekovi',20,_binary '\0',9,17),(37,5,'2024-03-25','lako','Plank',1,_binary '',1,17);
/*!40000 ALTER TABLE `aktivnost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fitnes_program`
--

DROP TABLE IF EXISTS `fitnes_program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fitnes_program` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lokacija_adresa` varchar(255) DEFAULT NULL,
  `cijena` double NOT NULL,
  `naziv` varchar(255) NOT NULL,
  `nivo_tezine` varchar(255) NOT NULL,
  `opis` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `lokacija_tip` varchar(255) NOT NULL,
  `trajanje` int NOT NULL,
  `lokacija_url` varchar(255) DEFAULT NULL,
  `vrijeme_objave` varchar(255) NOT NULL,
  `instruktor_id` int DEFAULT NULL,
  `kategorija_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkb61tacoci2219u1kjhby51uh` (`instruktor_id`),
  KEY `FKrnnastnadsc5x0jeoxvoiu0a8` (`kategorija_id`),
  CONSTRAINT `FKkb61tacoci2219u1kjhby51uh` FOREIGN KEY (`instruktor_id`) REFERENCES `korisnik` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKrnnastnadsc5x0jeoxvoiu0a8` FOREIGN KEY (`kategorija_id`) REFERENCES `kategorija` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fitnes_program`
--

LOCK TABLES `fitnes_program` WRITE;
/*!40000 ALTER TABLE `fitnes_program` DISABLE KEYS */;
INSERT INTO `fitnes_program` VALUES (19,'Kralja Petra 18, Banja Luka',199.99,'Cardio blast','umjereno','Dizajniran kako bi maksimalno potaknuo vaš kardiovaskularni sistem.','AKTIVAN','teretana',30,NULL,'2024-03-20',17,1),(20,'Gradska 12, Doboj',220,'Powerful physique','tesko','Program treninga snage osmišljen kako bi oblikovao i ojačao vaše mišiće.','AKTIVAN','teretana',45,NULL,'2024-03-20',17,9),(22,'Gradski park, Banja Luka',300,'Pilates harmony','umjereno','Kombinuje pokrete i disanje kako bi se postigla harmonija između uma i tijela.','AKTIVAN','park',30,NULL,'2024-03-20',17,12),(32,NULL,120,'Flex flow','lako','Dizajniran za poboljšanje fleksibilnosti tijela kroz niz dinamičkih i statičkih vježbi istezanja.','AKTIVAN','online',20,'https://www.youtube.com/embed/K0gUgreChdY?si=7KkROcn1Jp_LzYQR','2024-03-21',17,10),(33,NULL,350.67,'Pilates Essential','umjereno','Dizajniran za jačanje tijela i poboljšanje fleksibilnosti.','ZAVRSEN','online',45,'https://www.youtube.com/embed/p1i4MV3-LZU?si=8MXzlMH_ojRonEpQ','2024-03-21',19,12),(35,'Majke Jugovica 34, Banja Luka',450,'Pilates Essential','tesko','Dizajniran za jačanje tijela i poboljšanje fleksibilnosti.','AKTIVAN','teretana',30,NULL,'2024-03-26',19,12),(36,'Visegradska 11, Trebinje',450,'Strong Foundations','tesko','Osmišljen kako bi poboljšao snagu i izdržljivost.','AKTIVAN','teretana',25,NULL,'2024-03-26',19,9),(37,'Koste Talica 12, Novi Grad',123.5,'Cardio Burn','lako','Intenzivno trčanje u optimalnoj puls zoni za sagorijevanje masti.','AKTIVAN','teretana',10,NULL,'2024-03-26',17,1);
/*!40000 ALTER TABLE `fitnes_program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fitnes_program_komentar`
--

DROP TABLE IF EXISTS `fitnes_program_komentar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fitnes_program_komentar` (
  `id` int NOT NULL AUTO_INCREMENT,
  `komentar` varchar(255) NOT NULL,
  `vrijeme` varchar(255) NOT NULL,
  `fitnes_program_id` int NOT NULL,
  `korisnik_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrwdajjj4tmykyr6yk1svc0y6o` (`fitnes_program_id`),
  KEY `FKmfwoe18amubtyyqt5t8w6nivs` (`korisnik_id`),
  CONSTRAINT `FKmfwoe18amubtyyqt5t8w6nivs` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnik` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKrwdajjj4tmykyr6yk1svc0y6o` FOREIGN KEY (`fitnes_program_id`) REFERENCES `fitnes_program` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fitnes_program_komentar`
--

LOCK TABLES `fitnes_program_komentar` WRITE;
/*!40000 ALTER TABLE `fitnes_program_komentar` DISABLE KEYS */;
INSERT INTO `fitnes_program_komentar` VALUES (7,'Cardio Blast je nevjerojatan!','2024-03-20T20:52:55',19,18),(8,'Najbolji','2024-03-20T20:59:31',20,17),(9,'Preporuka svima.','2024-03-20T21:02:48',19,17),(10,'Isplati se probati.','2024-03-20T21:03:11',19,17),(11,'Tako je.','2024-03-20T21:03:47',19,18),(12,'20 posto popusta u narednih 7 dana.','2024-03-20T21:05:54',19,17);
/*!40000 ALTER TABLE `fitnes_program_komentar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fitnes_program_specifikacija`
--

DROP TABLE IF EXISTS `fitnes_program_specifikacija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fitnes_program_specifikacija` (
  `vrijednost` varchar(255) DEFAULT NULL,
  `specifikacija_id` int NOT NULL,
  `fitnes_program_id` int NOT NULL,
  PRIMARY KEY (`fitnes_program_id`,`specifikacija_id`),
  KEY `FKsi18ltob877b2qxsle4v0yye1` (`specifikacija_id`),
  CONSTRAINT `FK2em7qjdubjmpqlkhfdje9mbva` FOREIGN KEY (`fitnes_program_id`) REFERENCES `fitnes_program` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKsi18ltob877b2qxsle4v0yye1` FOREIGN KEY (`specifikacija_id`) REFERENCES `specifikacija` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fitnes_program_specifikacija`
--

LOCK TABLES `fitnes_program_specifikacija` WRITE;
/*!40000 ALTER TABLE `fitnes_program_specifikacija` DISABLE KEYS */;
INSERT INTO `fitnes_program_specifikacija` VALUES ('trčanje, vježbe s otporom',9,19),('poboljšanje kondicije',10,19),('45-60 minuta',12,19),('300-400 kcal',13,19),('3-5 sesija nedeljno',14,19),('70-90% maksimalnog pulsa',15,19),('glavno fokusiranje na nogama',54,19),('minimalna upotreba težina ili sprava',58,19),('traka za trčanje',65,19),('grudi, leđa, ramena, noge',52,20),('kombinacija slobodnih tegova i sprava',56,20),('u zavisnosti od trenutne snage',61,20),('umjerena do kontrolirana brzina',62,20),('3-5 serija po vježbi',63,20),('30-90 sekundi',64,20),('ne treba posebna oprema',71,22),('stabilizacija tijela',72,22),('roll-up',73,22),('kontrolisano disanje ',74,22),('dinamičko istezanje',75,22),('dinamičko istezanje',66,32),('poboljšanje opsega pokreta',67,32),('nije potrebna posebna oprema',68,32),('30 sekundi do 1 minute',69,32),('pruža raznovrsne vježbe',70,32),('pilates lopte i trake',71,33),('stabilizacija tijela',72,33),('bridging, roll-up i druge pilates vježbe',73,33),('vježbe disanja na podu',74,33),('aktivno',75,33),('pilates lopte i trake ',71,35),('stabilizacija tijela',72,35),('bridging, roll-up i druge pilates vježbe',73,35),('vježbe disanja na podu',74,35),('aktivno',75,35),('grudi, ruke, noge',52,36),('kombinacija tegova i tjelesne težine',56,36),('da',61,36),('kontrolirana brzina',62,36),('3-5 serija po vježbi',63,36),('30-60 sekundi',64,36),('aerobik, trčanje',9,37),('poboljšanje kondicije, sagorijevanje kalorija',10,37),('10',12,37),('300-500 kcal po sesiji',13,37),('4-5 puta sedmično',14,37),('60-80% maksimalnog pulsa',15,37),('naglasak na noge i kardiovaskularni sustav',54,37),('nije potrebna dodatna oprema',58,37),('traka za trčanje',65,37);
/*!40000 ALTER TABLE `fitnes_program_specifikacija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorija` (
  `id` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naziv` (`naziv`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorija`
--

LOCK TABLES `kategorija` WRITE;
/*!40000 ALTER TABLE `kategorija` DISABLE KEYS */;
INSERT INTO `kategorija` VALUES (10,'Fleksibilnost'),(1,'Kardio'),(12,'Pilates'),(9,'Snaga');
/*!40000 ALTER TABLE `kategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(255) NOT NULL,
  `prezime` varchar(255) NOT NULL,
  `grad` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `broj_telefona` varchar(255) DEFAULT NULL,
  `korisnicko_ime` varchar(50) NOT NULL,
  `lozinka` varchar(255) NOT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `datum_pristupa` varchar(255) NOT NULL,
  `kljuc` varchar(255) NOT NULL,
  `uloga` varchar(255) NOT NULL,
  `pretplata` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `korisnicko_ime` (`korisnicko_ime`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (17,'Ela','Mašić','Novi Grad','ela@elatestmail.com','065123459','Ela','$2a$10$np3Mfsl1ovAkd1R3tJHlsu.Qkip78SyXxkICMpkzQED0seJPjB7Yq','assets/avatari/20.png','2024-03-16','g0zKBLs5FtNI','VERIFIKOVANI',0),(18,'Mia','Jelić','Banja Luka','mia@testmail.com','065123457','Mia','$2a$10$hQ0yTdzA0lTUMztkZEHgoeuoDEntU59KX1NpIx/efQrDQ1aoiA3u6','assets/avatari/7.png','2024-03-16','tkgOZarqcTUu','VERIFIKOVANI',0),(19,'Rade','Radović','Trebinje','rade@testmail.com','065123458','Rade','$2a$10$bLHxe5OaMrHKXmWS/7E6VeWs3.a7naw9D4sfLvKy2/gUUyCeQO5OK','assets/avatari/3.png','2024-03-16','JjLRBaSyNCEW','VERIFIKOVANI',0);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `masa_korisnika`
--

DROP TABLE IF EXISTS `masa_korisnika`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `masa_korisnika` (
  `id` int NOT NULL AUTO_INCREMENT,
  `datum` varchar(255) NOT NULL,
  `masa` double NOT NULL,
  `korisnik_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsctnfjip8nq74n0mhbv10swmf` (`korisnik_id`),
  CONSTRAINT `FKsctnfjip8nq74n0mhbv10swmf` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnik` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `masa_korisnika`
--

LOCK TABLES `masa_korisnika` WRITE;
/*!40000 ALTER TABLE `masa_korisnika` DISABLE KEYS */;
INSERT INTO `masa_korisnika` VALUES (5,'2024-02-15',66,17),(6,'2024-02-19',66,17),(7,'2024-03-12',65,17),(8,'2024-03-20',64,17),(9,'2024-03-21',63,17),(10,'2024-03-25',62,17);
/*!40000 ALTER TABLE `masa_korisnika` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `placanje`
--

DROP TABLE IF EXISTS `placanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `placanje` (
  `id` int NOT NULL AUTO_INCREMENT,
  `broj_kartice` varchar(255) DEFAULT NULL,
  `licno` bit(1) DEFAULT NULL,
  `pay_pal_racun` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `placanje`
--

LOCK TABLES `placanje` WRITE;
/*!40000 ALTER TABLE `placanje` DISABLE KEYS */;
INSERT INTO `placanje` VALUES (1,NULL,_binary '',NULL),(2,'qqqqqqqqqqqqqqqq',_binary '\0',NULL),(3,NULL,_binary '',NULL),(4,NULL,_binary '',NULL),(5,NULL,_binary '\0','Abaab345'),(6,'12345787',_binary '\0',NULL);
/*!40000 ALTER TABLE `placanje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poruka_korisnik`
--

DROP TABLE IF EXISTS `poruka_korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poruka_korisnik` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tekst_poruke` varchar(255) NOT NULL,
  `vrijeme_poruke` varchar(255) NOT NULL,
  `posiljalac_id` int NOT NULL,
  `primalac_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlk3etl8mo1q55q0qoabik2fa4` (`posiljalac_id`),
  KEY `FKfi3wxjg9h7rey9nxnd6fkc5p7` (`primalac_id`),
  CONSTRAINT `FKfi3wxjg9h7rey9nxnd6fkc5p7` FOREIGN KEY (`primalac_id`) REFERENCES `korisnik` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKlk3etl8mo1q55q0qoabik2fa4` FOREIGN KEY (`posiljalac_id`) REFERENCES `korisnik` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poruka_korisnik`
--

LOCK TABLES `poruka_korisnik` WRITE;
/*!40000 ALTER TABLE `poruka_korisnik` DISABLE KEYS */;
INSERT INTO `poruka_korisnik` VALUES (17,'Zdravo. Znaš li možda neki dobar fitnes program za poboljšanje kondicije?','2024-03-20T20:35:46',17,19),(18,'Imam nekoliko prijedloga. Da li više voliš vježbanje u teretani ili kod kuće?','2024-03-20T20:36:37',19,17),(19,'Voljela bih nešto što mogu raditi kod kuće.','2024-03-20T20:37:23',17,19),(20,'Razumijem. Preporučio bih ti HIIT (High-Intensity Interval Training) program.','2024-03-20T20:38:06',19,17),(21,'Hej, čula sam da si počela s novim fitnes programom. Kako ide?','2024-03-20T20:41:15',18,17),(22,'Da, počela sam prije nekoliko sedmica. Ide super!','2024-03-20T20:41:34',17,18),(23,'To je sjajno čuti! Kakav program radiš?','2024-03-20T20:42:03',18,17),(24,'Radim HIIT treninge tri puta sedmično.','2024-03-20T20:42:25',17,18),(25,'Zvuči jako intenzivno, ali učinkovito.','2024-03-20T20:42:54',18,17);
/*!40000 ALTER TABLE `poruka_korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poruka_savjetnik`
--

DROP TABLE IF EXISTS `poruka_savjetnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poruka_savjetnik` (
  `id` int NOT NULL AUTO_INCREMENT,
  `naslov_odgovor` varchar(255) DEFAULT NULL,
  `naslov_poruke` varchar(255) NOT NULL,
  `procitana` bit(1) NOT NULL,
  `tekst_odgovor` varchar(255) DEFAULT NULL,
  `tekst_poruke` varchar(255) NOT NULL,
  `vrijeme_odgovor` varchar(255) DEFAULT NULL,
  `vrijeme_poruke` varchar(255) NOT NULL,
  `korisnik_id` int NOT NULL,
  `savjetnik_id` int NOT NULL,
  `prilog` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKir8u3h8g3g4npbxdynkga0w7e` (`korisnik_id`),
  KEY `FKjjne595xpodtkqe2bj5f6k71k` (`savjetnik_id`),
  CONSTRAINT `FKir8u3h8g3g4npbxdynkga0w7e` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnik` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKjjne595xpodtkqe2bj5f6k71k` FOREIGN KEY (`savjetnik_id`) REFERENCES `savjetnik` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poruka_savjetnik`
--

LOCK TABLES `poruka_savjetnik` WRITE;
/*!40000 ALTER TABLE `poruka_savjetnik` DISABLE KEYS */;
INSERT INTO `poruka_savjetnik` VALUES (20,NULL,'Trbušni mišići',_binary '\0',NULL,'Koje su najbolje vježbe za sagorijevanje trbušne masti i toniranje trbušnih mišića?',NULL,'2024-03-20T19:08:19',17,10,NULL),(21,NULL,'Motivacija',_binary '\0',NULL,'Treba mi savjet o tome kako održavati motivaciju za redovno vježbanje.',NULL,'2024-03-20T19:10:01',17,10,NULL),(22,NULL,'Program za trudnice',_binary '\0',NULL,'Molim vas preporučite mi fitnes program koji je prilagođen trudnicama.',NULL,'2024-03-20T19:11:44',17,11,NULL),(23,NULL,'Leđa i ramena',_binary '\0',NULL,'Kako mogu prilagoditi svoj fitnes program da bih ciljano jačao određene mišićne skupine, poput leđa i ramena?\"',NULL,'2024-03-20T19:12:52',19,10,NULL),(24,NULL,'Minimalna oprema',_binary '',NULL,'Tražim fitnes program koji mogu raditi kod kuće s minimalnom opremom. Možete li mi preporučiti nešto?',NULL,'2024-03-20T19:13:36',19,10,NULL),(25,NULL,'Kardio vježbe',_binary '\0',NULL,'Kako mogu uključiti kardio vježbe u svoj postojeći fitnes program bez gubitka mišićne mase?',NULL,'2024-03-20T19:14:12',19,10,NULL);
/*!40000 ALTER TABLE `poruka_savjetnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savjetnik`
--

DROP TABLE IF EXISTS `savjetnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `savjetnik` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(255) NOT NULL,
  `prezime` varchar(255) NOT NULL,
  `korisnicko_ime` varchar(50) NOT NULL,
  `lozinka` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `korisnicko_ime` (`korisnicko_ime`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savjetnik`
--

LOCK TABLES `savjetnik` WRITE;
/*!40000 ALTER TABLE `savjetnik` DISABLE KEYS */;
INSERT INTO `savjetnik` VALUES (10,'Ana','Marić','Ana','$2a$10$w8lHYOedP0YEI0LF6z2Ice8DO9/Q0OXph0ww21Bd1ZWJleWJqRAIK'),(11,'Maja','Ilić','Maja','$2a$10$/OUI6s2XI68aPNUGUvMJ2uGZUJbLPTliK0dmrGzMJDYke9SpYFGRS'),(12,'Ema','Kosić','Ema','$2a$10$1mTLCI649mnTh0wh6wEmWeEbxHIXi.HL7TpiLdDqRLKCHe2e7Rh3S');
/*!40000 ALTER TABLE `savjetnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specifikacija`
--

DROP TABLE IF EXISTS `specifikacija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specifikacija` (
  `id` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) NOT NULL,
  `kategorija_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_specifikacija_naziv_kategorija` (`naziv`,`kategorija_id`),
  UNIQUE KEY `UKbtqv7d7x4fs8e20cagn5804pf` (`naziv`,`kategorija_id`),
  KEY `specifikacija_ibfk_1` (`kategorija_id`),
  CONSTRAINT `specifikacija_ibfk_1` FOREIGN KEY (`kategorija_id`) REFERENCES `kategorija` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specifikacija`
--

LOCK TABLES `specifikacija` WRITE;
/*!40000 ALTER TABLE `specifikacija` DISABLE KEYS */;
INSERT INTO `specifikacija` VALUES (13,'Broj potrošenih kalorija po sesiji (kcal)',1),(63,'Broj serija',9),(62,'Brzina izvođenja pokreta',9),(72,'Fokus (stabilizacija, držanja tijela)',12),(67,'Fokus vježbi',10),(54,'Mišićna skupina (grudi, ramena, noge)',1),(52,'Mišićna skupina (grudi, ramena, noge)',9),(10,'Očekivanja (gubitak kilograma, poboljšanje kondicije)',1),(69,'Odmor između vježbi',10),(64,'Pauze između serija',9),(15,'Puls zona (raspon maksimalnog pulsa)',1),(70,'Raznovrsnost vježbi',10),(74,'Tehnika disanja',12),(75,'Tip istezanja (statičko, dinamičko, aktivno)',12),(68,'Tip opreme',10),(73,'Tipovi vježbi (bridging, roll-up)',12),(61,'Tjelesna težina',9),(12,'Trajanje sesije (u minutama)',1),(14,'Učestalost treninga (broj dana u nedelji)',1),(9,'Vrsta aktivnosti (trčanje, vožnja bicikla)',1),(65,'Vrsta kardio opreme (traka, boksački džak)',1),(71,'Vrsta opreme (lopta, traka)',12),(66,'Vrsta vježbi',10),(58,'Vrste sprava (tegovi, mašine)',1),(56,'Vrste sprava (tegovi, mašine)',9);
/*!40000 ALTER TABLE `specifikacija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ucestvovanje`
--

DROP TABLE IF EXISTS `ucestvovanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ucestvovanje` (
  `placanje_id` int NOT NULL,
  `vrijeme` varchar(255) NOT NULL,
  `fitnes_program_id` int NOT NULL,
  `kupac_id` int NOT NULL,
  `prodavac_id` int NOT NULL,
  PRIMARY KEY (`placanje_id`),
  KEY `FK7dvslcvbh0vwssyhjmtc5dcy9` (`fitnes_program_id`),
  KEY `FK77eyjqgnh77xt8gs03wmxux0f` (`kupac_id`),
  KEY `FKggcl95u7euvem29urkp8k5ens` (`prodavac_id`),
  CONSTRAINT `FK77eyjqgnh77xt8gs03wmxux0f` FOREIGN KEY (`kupac_id`) REFERENCES `korisnik` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK7dvslcvbh0vwssyhjmtc5dcy9` FOREIGN KEY (`fitnes_program_id`) REFERENCES `fitnes_program` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKggcl95u7euvem29urkp8k5ens` FOREIGN KEY (`prodavac_id`) REFERENCES `korisnik` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKje6y7gwfw4silmtfbjic998vs` FOREIGN KEY (`placanje_id`) REFERENCES `placanje` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ucestvovanje`
--

LOCK TABLES `ucestvovanje` WRITE;
/*!40000 ALTER TABLE `ucestvovanje` DISABLE KEYS */;
INSERT INTO `ucestvovanje` VALUES (2,'2024-03-20',19,19,17),(5,'2024-03-21',32,19,17),(6,'2024-03-21',33,17,19);
/*!40000 ALTER TABLE `ucestvovanje` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-26 14:21:23
