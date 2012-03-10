DROP TABLE if exists CONSTELLATIONS ;

CREATE TABLE constellations (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(255) DEFAULT NULL,
	code varchar(255) DEFAULT NULL,
	genitiveName varchar(255) DEFAULT NULL,
	hemisphere varchar(255) DEFAULT NULL,
	author varchar(255) DEFAULT NULL,
  	authorYear int(11) NOT NULL,
  	area double NOT NULL,
	PRIMARY KEY (`id`)
)

