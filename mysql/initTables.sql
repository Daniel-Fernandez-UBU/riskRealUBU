-- Create users table
CREATE TABLE `Users` (
	`username` varchar(100) NOT NULL,
	`password` varchar(100) NOT NULL,
	`company` varchar(100) NOT NULL,
	`rol` varchar(50) NOT NULL,
	`firstname` varchar(100) NOT NULL,
	`lastname` varchar(100) NOT NULL,
	`email` varchar(100) NOT NULL UNIQUE,
	`gender` varchar(50) NOT NULL,
    `age` varchar(50) NOT NULL,
	PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create profiles table
CREATE TABLE `Profiles` (
	`username` varchar(100) NOT NULL,
	`profile` varchar(70) NOT NULL,
	UNIQUE KEY `authorities_idx_2` (`username`,`profile`),
	CONSTRAINT `authorities_ibfk_2`
	FOREIGN KEY (`username`)
	REFERENCES `Users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Insert some users
INSERT INTO `Users` VALUES ('rmartico','{noop}raul123','UBU','professor','Raul','Marticorena','rmartico@ubu.es','male','33');
INSERT INTO `Users` VALUES ('dfb1001','{noop}daniel123','UBU','student','Daniel','Fernandez','dfb1001@alu.ubu.es','male','33');
INSERT INTO `Users` VALUES ('test01','{noop}test0123','UBU','student','Test','Tester','dfb1001@alu.ubu.es','female','33');

-- Insert (assign roles) to ours users.
INSERT INTO `Perfiles` VALUES ('rmartico','ADMIN');
INSERT INTO `Perfiles` VALUES ('dfb1001','ADMIN');
INSERT INTO `Perfiles` VALUES ('test01','CUSTOMER');