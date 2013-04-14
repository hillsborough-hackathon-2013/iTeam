# Schema script generated on: Wed Apr 10 16:26:10 EDT 2013

# --- !Ups

CREATE SEQUENCE subscriber_seq START 100;

CREATE TABLE subscriber (
	id				integer NOT NULL PRIMARY KEY,
	entityId		varchar(64) NOT NULL UNIQUE,
	account			varchar(128) NOT NULL UNIQUE,
	password		varchar(64) NOT NULL,
	phone			varchar(14),
	createdOn		timestamp NOT NULL,
	lastChanged		timestamp NOT NULL
	);

# --- !Downs

DROP TABLE subscriber;
DROP SEQUENCE subscriber_seq;

