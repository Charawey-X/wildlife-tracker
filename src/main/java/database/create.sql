
\c wildlife_tracker
CREATE TABLE animals(id  serial PRIMARY KEY,name varchar, age varchar,health varchar );
ALTER TABLE  animals ADD COLUMN type varchar;
CREATE TABLE rangers (id serial PRIMARY KEY,name varchar,badge_number varchar,phone_number varchar);
CREATE TABLE locations (id serial PRIMARY KEY, name varchar);
CREATE TABLE locations_sightings (id serial PRIMARY KEY,location_id INT,sighting_id INT);
CREATE TABLE rangers_sightings (id serial PRIMARY KEY,ranger_id INT,sighting_id INT);
CREATE TABLE sightings (id serial PRIMARY KEY,animal_id INT,ranger_id INT,location_id INT,time TIMESTAMP);
CREATE DATABASE wildlife_tracker_test OWNER x WITH TEMPLATE wildlife_tracker;