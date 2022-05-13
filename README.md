# Wildlife Tracker.

##Author
Charawey-X

#### Application.
This is a java application that allows Rangers to track wildlife sightings in the area.

## Description
This application allows rangers to input and record details of animals.


## Setup and Installation requirements
* Go to the projects [repository]
* Clone the project
```
git clone
```
* Install gradle
```
sdk install gradle 7.4.2
```
* Install java
```
sdk install java
```
* Open the directory in terminal
* move to main
```
cd build/classes/java/main
```
* Run the following command to execute the Terminal-java application
```
java App
```
* In PSQL:
```
CREATE DATABASE wildlife;
CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker OWNER x;
\c wildlife_tracker
CREATE TABLE animals(id  serial PRIMARY KEY,name varchar, age varchar,health varchar );
ALTER TABLE  animals ADD COLUMN type varchar;
CREATE TABLE rangers (id serial PRIMARY KEY,name varchar,badge_number varchar,phone_number varchar);
CREATE TABLE locations (id serial PRIMARY KEY, name varchar);
CREATE TABLE locations_sightings (id serial PRIMARY KEY,location_id INT,sighting_id INT);
CREATE TABLE rangers_sightings (id serial PRIMARY KEY,ranger_id INT,sighting_id INT);
CREATE TABLE sightings (id serial PRIMARY KEY,animal_id INT,ranger_id INT,location_id INT,time TIMESTAMP);
```

## Technologies used
* Java.
* Gradle.
* Bootstrap.
* Spark.

## Contact details
For more information reach out vial [email](charawey@gmail.com)

## Licence
MIT License

Copyright (c) [2022] [Charawey-X]