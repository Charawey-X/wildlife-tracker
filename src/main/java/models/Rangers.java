package models;
import database.*;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Rangers implements DataAccess {
    private String name;
    private String badge_number;
    private  String phone_number;
    private int id;
    public Rangers(String name, String badge_number, String phone_number) {
        this.name = name;
        this.badge_number=  badge_number;
        this.phone_number= phone_number;
    }
    public String getNane() {
        return name;
    }
    public String getBadgeNumber() {
        return badge_number;
    }

    public String getPhoneNumber() {
        return phone_number;
    }
    public int getId(){
        return id;
    }

    public static Rangers find(int id){
        try (Connection con=DB.sql2o.open()){
            String sql="SELECT * FROM rangers WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Rangers.class);
        }

    }

    public void save(){
        try (Connection con=DB.sql2o.open()){
            String sql="INSERT INTO rangers (name,badge_number,phone_number) VALUES (:name,:badge_number,:phone_number)";
            if(name.equals("")||badge_number.equals("")||phone_number.equals("")){
                throw new IllegalArgumentException("All fields must be filled");
            }
            this.id=(int) con.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("badge_number",this.badge_number)
                    .addParameter("phone_number",this.phone_number)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static List<Rangers> all(){
        try (Connection con=DB.sql2o.open()){
            String sql="SELECT * FROM rangers";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Rangers.class);

        }

    }

    public void delete(){
        try (Connection con=DB.sql2o.open()){
            String sql="DELETE FROM rangers WHERE id=:id";

            con.createQuery(sql)
                    .addParameter("id",this.id)
                    .executeUpdate();
        }
    }

    public static void deleteAll(){
        try (Connection con=DB.sql2o.open()){
            String sql = "DELETE FROM rangers";
            con.createQuery(sql)
                    .executeUpdate();
        }  catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    public List<Sightings> getRangerSightings(){
        try (Connection con=DB.sql2o.open()){
            String sql="SELECT sighting_id FROM rangers_sightings WHERE ranger_id=:ranger_id";
            List<Integer> sightings_ids=con.createQuery(sql)
                    .addParameter("ranger_id",this.getId())
                    .executeAndFetch(Integer.class);
            List<Sightings> sightings=new ArrayList<>();

            for(Integer sighting_id:sightings_ids){
                String sightingsQuery="SELECT * FROM sightings WHERE id=:sighting_id";
                Sightings sighting=con.createQuery(sightingsQuery)
                        .addParameter("sighting_id",sighting_id)
                        .executeAndFetchFirst(Sightings.class);
                sightings.add(sighting);
            }
            if(sightings.size()==0){
                throw new IllegalArgumentException("Ranger has no sighting");
            }
            else {return sightings;}
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rangers rangers = (Rangers) o;
        return id == rangers.id && name.equals(rangers.name) && badge_number.equals(rangers.badge_number) && phone_number.equals(rangers.phone_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, badge_number, phone_number, id);
    }
}