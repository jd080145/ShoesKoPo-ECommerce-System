-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 09, 2023 at 01:45 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ShoesKoPoDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `productID` int(15) NOT NULL,
  `productName` varchar(50) NOT NULL,
  `price` float NOT NULL,
  `displayImage` varchar(20) NOT NULL,
  `stock` int(15) NOT NULL,
  `sold` int(20) NOT NULL,
  `discount` int(3) DEFAULT NULL,
  `description` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`productID`, `productName`, `price`, `displayImage`, `stock`, `sold`, `discount`, `description`) VALUES
(1, 'Product Name', 999.99, '0', 99999, 99999, NULL, NULL),
(2, 'Product Name1', 420.99, '0', 696969, 420420, 30, NULL),
(6, 'hahaha', 69.9, '0', 23, 25000, NULL, NULL),
(9, 'a', 999.99, '0', 99999, 99999, NULL, NULL),
(10, 'b', 999.99, '0', 99999, 99999, NULL, NULL),
(11, '', 999.99, '0', 99999, 99999, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `ID` int(20) NOT NULL,
  `username` varchar(15) NOT NULL,
  `firstName` varchar(20) NOT NULL,
  `lastName` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(13) DEFAULT NULL,
  `password` varchar(20) NOT NULL,
  `bday` date DEFAULT NULL,
  `gender` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`ID`, `username`, `firstName`, `lastName`, `email`, `phone`, `password`, `bday`, `gender`) VALUES
(1, 'jd_080145', 'Jandee', 'Granada', 'jd.granada.game@gmail.com', NULL, 'password1234', NULL, NULL),
(10, 'aaaaa', 'Jandee', 'Granada', 'aaaa@gmail.com', NULL, 'aaaa', NULL, NULL),
(14, 'binivia03', 'Vince Azzeneth', 'Bantique', 'azzeneth03@gmail.com', NULL, 'password1234', NULL, NULL),
(23, 'binivia13', 'Vince Azzeneth', 'Granada', 'azzeneth13@gmail.com', NULL, 'password1234', NULL, NULL),
(24, '', '', '', '', NULL, '', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `userAddress`
--

CREATE TABLE `userAddress` (
  `ID` int(11) NOT NULL,
  `street` varchar(20) NOT NULL,
  `brgy` varchar(20) NOT NULL,
  `city` varchar(20) NOT NULL,
  `province` varchar(20) NOT NULL,
  `region` varchar(20) NOT NULL,
  `zip` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`productID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `phone` (`phone`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `inventory`
--
ALTER TABLE `inventory`
  MODIFY `productID` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `ID` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
