package models;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SightingsTest {
    @Rule
    public DatabaseRule databaseRule=new DatabaseRule();

    private  Sightings setupSightings(){
        return  new Sightings(2,3,7);
    }

    @Test
    public void createInstanceOfSightingsClass_true() {
        Sightings sighting= setupSightings();
        assertTrue(sighting instanceof Sightings);

    }

    @Test
    public void returnsAnimal_Id() {
        Sightings sightings = setupSightings();
        assertEquals(7,sightings.getAnimal_Id());
    }

    @Test
    public void allInstancesAreSaved() {
        Sightings sightings = setupSightings();
        Sightings otherSighting = new Sightings(1, 1, 1);
        sightings.save();
        otherSighting.save();
        assertEquals(Sightings.find(sightings.getId()), sightings);
    }

    @Test
    public void returnsLocationId() {
        Sightings sightings = setupSightings();
        assertEquals(2,sightings.getLocation_id());
    }

    @Test
    public void returnsLRangerId() {
        Sightings sightings = setupSightings();
        assertEquals(3,sightings.getRanger_Id());
    }

    @Test
    public void findSightingByID() {
        Sightings sighting=setupSightings();
        sighting.save();
        Sightings foundSighting=Sightings.find(sighting.getId());
        assertEquals(foundSighting, sighting);

    }

    @Test
    public void deleteSightingByID() {
        Sightings sighting=setupSightings();
        sighting.save();
        sighting.delete();
        assertNull(Sightings.find(sighting.getId()));

    }
    @Test
    public void deleteAll() {
        Sightings sighting=setupSightings();
        Sightings otherSightings=new Sightings(2,3,4);
        sighting.save();
        otherSightings.save();
        Sightings.deleteAll();
        assertEquals(0,Sightings.all().size());

    }
}