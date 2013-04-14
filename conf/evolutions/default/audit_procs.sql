# Schema script generated on: Sat Apr 13 10:49:39 EDT 2013

# --- !Ups

CREATE OR REPLACE FUNCTION update_created_column()
RETURNS TRIGGER AS $$
BEGIN
	NEW.createdOn = now();
	RETURN NEW;
END;
$$ language 'plpgsql';

CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
	NEW.lastChanged = now();
	RETURN NEW;
END;
$$ language 'plpgsql';

# --- !Downs

DROP FUNCTION update_created_column;
DROP FUNCTION update_modified_column;

