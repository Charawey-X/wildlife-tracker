package models;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RangersTest {
    @Rule
    public DatabaseRule databaseRule=new DatabaseRule();

    @AfterEach
    void tearDown(){
        Rangers.deleteAll();
        Locations.deleteAll();
        Sightings.deleteAll();
        Animals.deleteAll();
    }

    private Rangers setupNewRanger(){
        return new Rangers("Chui","143234","071234567");
    }

    @Test
    public void createInstanceOfRangersClass_true(){
        Rangers ranger= setupNewRanger();
        assertTrue(ranger instanceof Rangers);
    }

    @Test
    public void Ranger_getsName() {
        Rangers rangers = setupNewRanger();
        assertEquals("Chui",rangers.getNane());
    }
    @Test
    public void Ranger_getsBadgeNumber() {
        Rangers rangers = setupNewRanger();
        assertEquals("143234",rangers.getBadgeNumber());
    }
    @Test
    public void Ranger_getsPhoneNumber() {
        Rangers rangers = setupNewRanger();
        assertEquals("071234567",rangers.getPhoneNumber());
    }

    @Test
    public void ChecksRangerCorrectlySaves() {
        Rangers ranger = setupNewRanger();
        ranger.save();
        Rangers rangers = new Rangers("Mbogo","234","079812346");
        rangers.save();
        assertEquals(2, Rangers.all().size());
    }

    @Test
    public void null_fieldsAreNotSaved() {
        Rangers rangers = new Rangers("","","12345");
        try {
            rangers.save();
            assertEquals(1,Rangers.all().size());
        }catch (IllegalArgumentException ex){
            System.out.println(ex);
        }
    }
    @Test
    public void findById() {
        Rangers ranger= setupNewRanger();
        Rangers otherRanger=new Rangers("Fisi","2537841","0726108898");
        ranger.save();
        otherRanger.save();
        Rangers foundRanger=Rangers.find(ranger.getId());
        assertEquals(foundRanger.getBadgeNumber(), ranger.getBadgeNumber());
    }
    @Test
    public void entriesAreDeleted() {
        Rangers ranger= setupNewRanger();
        Rangers otherRanger=new Rangers("Simba","223456","077689583");
        ranger.save();
        otherRanger.save();
        ranger.delete();
        assertNull(Rangers.find(ranger.getId()));

    }
    @Test
    public void allSightingsAreReturnedForRanger() {
        Rangers ranger=setupNewRanger();
        try {
            Locations location=new Locations("Zone A");
            ranger.save();
            location.save();
            Sightings sighting=new Sightings(location.getId(),ranger.getId(),1);
            Sightings otherSighting = new Sightings(1,ranger.getId(),1);
            sighting.save();
            otherSighting.save();
            assertEquals(sighting,ranger.getRangerSightings().get(0));
            assertEquals(otherSighting,ranger.getRangerSightings().get(1));
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

    @Test
    public void returns_badgeNumber_badgeNumber() {
        Rangers rangers = setupNewRanger();
        rangers.save();
        assertEquals("143234",rangers.getBadgeNumber());
    }

    @Test
    public void returns_PhoneNumber_phoneNumber () {
        Rangers rangers = setupNewRanger();
        rangers.save();
        assertEquals("071234567",rangers.getPhoneNumber());
    }

    @Test
    public void rangerRerunsEntries() {
        Rangers rangers = setupNewRanger();
        rangers.save();
        Rangers mass = new Rangers("Twiga","2345","8483920224");
        mass.save();
        assertEquals(2,Rangers.all().size());
    }
    @Test
    public void printRangers(){
        System.out.println( Rangers.all());
    }
}