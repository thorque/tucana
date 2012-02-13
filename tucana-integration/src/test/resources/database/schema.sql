DROP TABLE CONSTELLATIONS if exists;

CREATE TABLE CONSTELLATIONS(
  id bigint identity,
  name varchar(50) not null,
  code varchar(3) not null,
  genitiveName varchar(50) not null,
  hemisphere varchar(3) not null,
  author varchar(20) not null,
  authorYear INTEGER not null,
  area double not null,
  primary key (id)
);

