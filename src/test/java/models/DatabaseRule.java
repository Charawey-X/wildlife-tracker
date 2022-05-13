package models;

import org.junit.rules.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import database.*;

public class DatabaseRule extends ExternalResource {

    @Override
    protected  void  before(){
        DB.sql2o= new Sql2o ("jdbc:postgresql://localhost/wildlife_tracker_test", "x", "230620");
    }
    @Override
    protected void after() {
        try(Connection conn =DB.sql2o.open()){
            String deleteAnimals ="DELETE FROM animals";
            String deleteRangers = "DELETE FROM rangers";
            String deleteLocations ="DELETE FROM locations";
            String deleteSightings ="DELETE FROM sightings ";
            conn.createQuery(deleteAnimals).executeUpdate();
            conn.createQuery(deleteRangers).executeUpdate();
            conn.createQuery(deleteLocations).executeUpdate();
            conn.createQuery(deleteSightings).executeUpdate();
        }
    }
}