-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 15, 2017 at 05:33 PM
-- Server version: 5.7.17-0ubuntu0.16.04.1
-- PHP Version: 7.0.13-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `resultmgntsys`
--

-- --------------------------------------------------------

--
-- Table structure for table `batch`
--

CREATE TABLE `batch` (
  `id` int(11) NOT NULL,
  `name` varchar(80) NOT NULL,
  `description` varchar(225) DEFAULT NULL,
  `start_date` varchar(20) DEFAULT NULL,
  `end_date` varchar(20) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `batch`
--

INSERT INTO `batch` (`id`, `name`, `description`, `start_date`, `end_date`, `status`) VALUES
(1, 'Rest', 'test', '2069', '2070', NULL),
(2, 'silver', 'this is silver batch', '2072', '2073', NULL),
(4, 'Gold', 'this is Gold', '2072', '2073', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `grade`
--

CREATE TABLE `grade` (
  `id` int(11) NOT NULL,
  `name` varchar(80) NOT NULL,
  `teacher` varchar(80) DEFAULT NULL,
  `batchId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `grade`
--

INSERT INTO `grade` (`id`, `name`, `teacher`, `batchId`) VALUES
(1, 'one', 'sanjok', 2070),
(2, 'two', NULL, NULL),
(3, 'three', NULL, NULL),
(4, 'four', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `marks`
--

CREATE TABLE `marks` (
  `studentId` int(11) NOT NULL,
  `subject` varchar(80) NOT NULL,
  `mark` varchar(11) NOT NULL,
  `remarks` varchar(80) DEFAULT NULL,
  `gradeId` int(11) NOT NULL,
  `batchId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `marks`
--

INSERT INTO `marks` (`studentId`, `subject`, `mark`, `remarks`, `gradeId`, `batchId`) VALUES
(1, 'Environment', '11', NULL, 0, 0),
(1, 'Environment', '66', NULL, 0, 0),
(1, 'Environment', '0', NULL, 0, 0),
(1, 'Environment', '0', NULL, 0, 0),
(1, 'English', '23', NULL, 0, 0),
(1, 'math', '12', NULL, 0, 0),
(1, 'Science', '15', NULL, 0, 0),
(1, 'Social', '46', NULL, 0, 0),
(1, 'Environment', '0', NULL, 0, 0),
(1, 'English', '33', NULL, 0, 0),
(1, 'math', '22', NULL, 0, 0),
(1, 'Science', '33', NULL, 0, 0),
(1, 'Social', '45', NULL, 0, 0),
(1, 'Environment', '6', NULL, 0, 0),
(27, '1', '20', NULL, 1, 1),
(27, '2', '22', NULL, 1, 1),
(27, '3', '33', NULL, 1, 1),
(27, '4', '11', NULL, 1, 1),
(27, '5', '44', NULL, 1, 1),
(27, '6', '55', NULL, 1, 1),
(26, '1', '23', NULL, 1, 1),
(26, '2', '24', NULL, 1, 1),
(26, '3', '58', NULL, 1, 1),
(26, '4', '69', NULL, 1, 1),
(26, '5', '54', NULL, 1, 1),
(26, '6', '62', NULL, 1, 1),
(24, '1', '20', NULL, 1, 1),
(24, '2', '30', NULL, 1, 1),
(24, '3', '22', NULL, 1, 1),
(24, '4', '66', NULL, 1, 1),
(24, '5', '52', NULL, 1, 1),
(24, '6', '20', NULL, 1, 1),
(22, '1', '20', NULL, 1, 1),
(22, '2', '30', NULL, 1, 1),
(22, '3', '0', NULL, 1, 1),
(22, '4', '0', NULL, 1, 1),
(22, '5', '0', NULL, 1, 1),
(22, '6', '0', NULL, 1, 1),
(37, '1', '20', NULL, 3, 2),
(37, '2', '30', NULL, 3, 2),
(37, '3', '40', NULL, 3, 2),
(37, '4', '50', NULL, 3, 2),
(37, '5', '60', NULL, 3, 2),
(37, '6', '70', NULL, 3, 2),
(39, '1', '20', NULL, 1, 1),
(39, '2', '30', NULL, 1, 1),
(39, '3', '50', NULL, 1, 1),
(39, '4', '40', NULL, 1, 1),
(39, '5', '60', NULL, 1, 1),
(39, '6', '70', NULL, 1, 1),
(27, '1', '30', NULL, 0, 1),
(27, '2', '40', NULL, 0, 1),
(27, '3', '50', NULL, 0, 1),
(27, '4', '60', NULL, 0, 1),
(27, '5', '70', NULL, 0, 1),
(27, '6', '80', NULL, 0, 1),
(28, '1', '20', NULL, 0, 0),
(28, '2', '30', NULL, 0, 0),
(28, '3', '40', NULL, 0, 0),
(28, '4', '50', NULL, 0, 0),
(28, '5', '60', NULL, 0, 0),
(28, '6', '70', NULL, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `id` int(11) NOT NULL,
  `firstname` varchar(80) NOT NULL,
  `lastname` varchar(80) NOT NULL,
  `address` varchar(100) NOT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `roll` int(11) DEFAULT '0',
  `phone` varchar(14) DEFAULT '0',
  `image` varchar(250) DEFAULT NULL,
  `gradeId` int(11) DEFAULT NULL,
  `batchId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `firstname`, `lastname`, `address`, `gender`, `roll`, `phone`, `image`, `gradeId`, `batchId`, `status`) VALUES
(1, 'santosh', 'pathak', 'Kathmandu', 'gay', 0, '984652', '', 1, 1, 0),
(2, 'sanjok', 'dangol', 'ktm', '', 0, '0', '', 0, 0, 0),
(3, 'Rajesh', 'Hamal', 'Dang', '', 0, '0', '', 0, 0, 0),
(5, 'radhaika', 'dhamal', 'ktm', '', 0, '0', '', 0, 0, 0),
(6, 'tesrest', '', '', '', 0, '0', '', 0, 0, 0),
(7, 'tulasi', 'bhandari', 'tes', '', 0, '0', '', 0, 0, 0),
(8, 'best', 'test', 'chest', '', 0, '0', '', 0, 0, 0),
(9, 'diwas', 'rijal', '', '', 0, '985625865', '', 3, 3, 0),
(10, 'stsa', 'asd', 'ktm', '', 1, '984652365', '', 0, 0, 0),
(11, 'tes', '', '', '', 0, '0', '', 0, 0, 0),
(12, 'Rakchya', 'Chhettri', 'Bhaktapur', '', 0, '0', '', 0, 0, 0),
(13, 'sanju', '', '', '', 0, '0', '', 0, 0, 0),
(15, 've', '', '', '', 0, '0', '', 0, 0, 0),
(16, 'dsafa', 'asdfa', '', '', 0, '0', '', 0, 0, 0),
(18, 'Gajju', 'Yaadav', 'Birjunj', '', 10, '985685', '', 0, 0, 0),
(19, 'suroj', 'dangol', '', '', 0, '0', '', 0, 0, 0),
(20, 'sabi', 'maharjan', 'kathmandu', '', 0, '0', '', 0, 0, 0),
(22, 'nischal', 'khadka', 'ktm', NULL, 5, '9856', NULL, NULL, NULL, NULL),
(24, 'Ganesh', 'Shrestha', 'ktm', NULL, 20, '98568', NULL, NULL, NULL, NULL),
(25, 'sa', 'sa', 'sa', NULL, 222, '23', NULL, NULL, NULL, NULL),
(26, 'sa', '', '', NULL, 12, '0', NULL, 0, 1, NULL),
(27, 'sa', 'sa', 'k', 'gay', 0, '9843408106', NULL, 1, 1, NULL),
(28, 'Prithivi', 'Nakarmi', 'Ktitipur', NULL, 20, '956', NULL, 1, 1, NULL),
(29, 'test', '', '', NULL, 2, '2', NULL, 0, 0, NULL),
(30, 'test', 'tes', '', NULL, 0, '0', NULL, 0, 0, NULL),
(31, 'test', 'tes', '', NULL, 0, '0', NULL, 1, 0, NULL),
(32, 'test', 'tes', '', NULL, 0, '0', NULL, 1, 1, NULL),
(33, 'test', 'tes', '', NULL, 0, '0', NULL, 1, 1, NULL),
(34, 'test', 'tes', 'ts', NULL, 0, '0', NULL, 1, 1, NULL),
(35, 'test', 'tes', 'ts', NULL, 0, '33', NULL, 1, 1, NULL),
(36, 'test', 'tes', 'ts', NULL, 32, '33', NULL, 1, 1, NULL),
(37, 'test', 'tes', 'ts', NULL, 32, '33', NULL, 2, 1, NULL),
(38, 'sanjok', 'dangol', 'betrawati', NULL, 1, '984340810', NULL, 2, 4, NULL),
(39, 'm', '', '', 'Male', 0, '0', NULL, 1, 1, NULL),
(40, 'tes', '', '', 'gay', 0, '0', NULL, 1, 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `subjects`
--

CREATE TABLE `subjects` (
  `id` int(11) NOT NULL,
  `name` varchar(80) NOT NULL,
  `pm` int(2) NOT NULL,
  `fm` int(2) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subjects`
--

INSERT INTO `subjects` (`id`, `name`, `pm`, `fm`, `status`) VALUES
(1, 'English', 50, 100, 1),
(2, 'math', 40, 100, 1),
(3, 'Science', 50, 100, 1),
(4, 'Social', 50, 100, 1),
(5, 'Environment', 50, 100, 1),
(6, 'Java', 50, 100, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `batch`
--
ALTER TABLE `batch`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `grade`
--
ALTER TABLE `grade`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `subjects`
--
ALTER TABLE `subjects`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `batch`
--
ALTER TABLE `batch`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `grade`
--
ALTER TABLE `grade`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;
--
-- AUTO_INCREMENT for table `subjects`
--
ALTER TABLE `subjects`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
