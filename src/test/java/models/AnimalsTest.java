package models;
import org.junit.Rule;

import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalsTest {

    @Rule
    public  DatabaseRule databaseRule = new DatabaseRule();

    @Test
    public void Animal_instantiatesCorrectly() {
        Animals animals = setupAnimals();
        assertTrue(animals instanceof Animals);
    }

    @Test
    public void Animal_getsNameOfSavedAnimal() {
        Animals animals = setupAnimals();
        assertEquals("Zebra", animals.getName());
    }

    @Test
    public void Animal_returnsCategoryOfSavedAnimal() {
        Animals animals = setupAnimals();
        assertEquals("not endangered",animals.getType());
    }

    @Test
    public void Animal_returnsAge() {
        Animals animals = setupAnimals();
        assertEquals("adult",animals.getAge());
    }

    @Test
    public void testAnimal_returnsHealth() {
        Animals testAnimal = setupAnimals();
        assertEquals("young",testAnimal.getHealth());
    }

    @Test
    public void AnimalRetrievesIdAndReturnsCorrectInfo() {
        Animals testAnimal=setupAnimals();
        testAnimal.save();
        Animals foundAnimal= Animals.find(testAnimal.getId());
        assertTrue(foundAnimal.getHealth().equals(testAnimal.getHealth()));
    }

    @Test
    public void Animal_allInstancesAreSaved() {
        Animals animals = setupAnimals();
        animals.save();
        Animals animals1 = setupAnimals();
        animals1.save();
        assertEquals(Animals.all().size(),2);
    }

    @Test
    public void deleteById() {
        Animals testAnimal=setupAnimals();
        testAnimal.save();
        testAnimal.delete();
        assertEquals(null,Animals.find(testAnimal.getId()));
    }
    @Test
    public void deleteAllEntries() {
        Animals testAnimal=setupAnimals();
        Animals otherAnimal= new Animals("Octopus","adult","young");
        testAnimal.save();
        otherAnimal.save();
        Animals.deleteAll();
        List<Animals> animals=Animals.all();
        assertEquals(0,animals.size());
    }

    @Test
    public void EnsureNaneNotNull() {
        Animals testAnimal=setupAnimals();
        try {
            testAnimal.save();
        }catch (IllegalArgumentException e){

        }
    }

    private Animals setupAnimals() {
        return new Animals("Zebra","adult","young");
    }
}