-- Select schema
USE appRiskRealUbu;

-- Delete Tables
DROP TABLE Profiles;
DROP TABLE Users;

-- Create users table
CREATE TABLE `Users` (
	`email` varchar(100) NOT NULL,
	`password` varchar(100) NOT NULL,
	`company` int NOT NULL,
	`rol` varchar(50) NOT NULL,
	`firstname` varchar(100) NOT NULL,
	`lastname` varchar(100) NOT NULL,
	`gender` varchar(50) NOT NULL,
    `age` varchar(50) NOT NULL,
    `status` tinyint NOT NULL,
	PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create profiles table
CREATE TABLE `Profiles` (
    `email` varchar(100) NOT NULL,
    `profile` varchar(70) NOT NULL,
    PRIMARY KEY (`email`),
    FOREIGN KEY (`email`) REFERENCES `Users` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

