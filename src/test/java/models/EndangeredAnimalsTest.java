package models;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EndangeredAnimalsTest {
    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();

    private EndangeredAnimals setUpNewAnimal() {
        return new EndangeredAnimals("White Rhino","endangered","healthy","young");
    }
    @Test
    public void testInstanceOfEndangeredAnimalsClass_true(){
        EndangeredAnimals testAnimal= setUpNewAnimal();
        assertTrue(testAnimal instanceof EndangeredAnimals);
    }
    @Test
    public void allInstancesAreSaved(){
        EndangeredAnimals testAnimal=setUpNewAnimal();
        testAnimal.save();
        assertEquals(EndangeredAnimals.all().get(0).getHealth(), testAnimal.getHealth());
    }
    @Test
    public void findByIdReturnsCorrectInfo(){
        EndangeredAnimals testAnimal=setUpNewAnimal();
        testAnimal.save();
        Animals foundAnimal= Animals.find(testAnimal.getId());
        assertEquals(foundAnimal.getHealth(), testAnimal.getHealth());

    }
    @Test
    public void deleteByID(){
        EndangeredAnimals testAnimal=setUpNewAnimal();
        testAnimal.save();
        testAnimal.delete();
        assertNull(Animals.find(testAnimal.getId()));

    }
    @Test
    public void deleteAllEntries(){
        EndangeredAnimals testAnimal=setUpNewAnimal();
        EndangeredAnimals otherAnimal=setUpNewAnimal();
        testAnimal.save();
        otherAnimal.save();
        Animals.deleteAll();
        List<Animals> animals=Animals.all();
        assertEquals(0,animals.size());
    }
    @Test
    public void ensureNameFieldCannotBeEmpty(){
        EndangeredAnimals testAnimal=new EndangeredAnimals("","endangered","","");
        try {
            testAnimal.save();
        }catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}