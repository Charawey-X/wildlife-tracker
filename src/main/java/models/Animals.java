package models;

import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.Objects;
import database.*;

public class Animals implements DataAccess {
    public String name;
    public String  type;
    public String age;
    public String health;
    public int id;
    public static final String CATEGORY= "not endangered";
    public Animals(String name, String age, String health) {
        this.name = name;
        this.type = CATEGORY;
        this.age = age;
        this.health =health;
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAge() {
        return age;
    }

    public String getHealth() {
        return health;
    }

    public int getId() {
        return id;
    }

    public static List<Animals> all() {
        try(Connection conn = DB.sql2o.open()){
            String sql ="SELECT * FROM animals";
            return conn.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Animals.class);
        }
    }

    public static void deleteAll(){
        try (Connection con=DB.sql2o.open()){
            String sql = "DELETE FROM animals";
            con.createQuery(sql)
                    .executeUpdate();
        }  catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    public void save() {
        if (this.name.equals(null)||this.age.equals(null)||this.health.equals(null)){
            throw new IllegalArgumentException("Fields are required");
        }
        try(Connection conn = DB.sql2o.open()){
            String sql = "INSERT INTO  animals(name,age,health)VALUES(:name ,:age,:health)";
            this.id =(int) conn.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("age",this.age)
                    .addParameter("health",this.health)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static Animals find(int id){
        try (Connection con=DB.sql2o.open()){
            String sql= "SELECT * FROM animals WHERE id=:id";
            Animals animal=  con.createQuery(sql)
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Animals.class);
            return animal;

        }

    }

    public void delete(){
        try (Connection con=DB.sql2o.open()){
            String sql = "DELETE FROM animals WHERE id=:id";
            con.createQuery(sql)
                    .addParameter("id",this.id)
                    .executeUpdate();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animals animals = (Animals) o;
        return id == animals.id && name.equals(animals.name) && type.equals(animals.type) && age.equals(animals.age) && health.equals(animals.health);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, age, health, id);
    }
}