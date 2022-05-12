package models;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocationsTest {
    @Rule
    public DatabaseRule databaseRule=new DatabaseRule();

    @AfterEach
    void tearDown(){
        Locations.deleteAll();
    }

    @Test
    public void createInstanceOfLocationsClass() {
        Locations location=setupLocations();
        assertTrue(location instanceof Locations);
    }

    @Test
    public void Locations_getsNameSuccessfully() {
        Locations locations = setupLocations();
        assertEquals("zoneA",locations.getName());
    }

    @Test
    public void saveLocationInstances() {
        Locations location = setupLocations();
        Locations locations = new Locations("Zone c");
        try{
            location.save();
            locations.save();
            assertEquals(2, Locations.all().size());
        }catch (IllegalArgumentException  ex){
            System.out.println(ex);
        }
    }
    @Test
    public void entryIsDeletedSuccessfully() {
        Locations location=setupLocations();
        Locations newLocation=new Locations("Zone B");
        location.save();
        newLocation.save();
        location.delete();
        newLocation.delete();
        assertNull(Locations.find(location.getId()));
    }
    @Test
    public void allSightingsAreReturnedForRanger() {
        Locations location = setupLocations();
        try {
            location.save();
            Sightings sighting=new Sightings(location.getId(),1,1);
            Sightings otherSighting=new Sightings(location.getId(),1,1);
            sighting.save();
            otherSighting.save();
            assertEquals(location.getLocationSightings().get(0),sighting);
            assertEquals(location.getLocationSightings().get(1),otherSighting);
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }
    private Locations  setupLocations(){
        return new Locations("zoneA");
    }
}