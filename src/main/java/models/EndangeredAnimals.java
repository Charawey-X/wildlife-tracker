package models;
import database.*;
import org.sql2o.Connection;

public class EndangeredAnimals extends  Animals implements DataAccess {
    private String health;
    private String age;
    public static final String HEALTH_HEALTHY="healthy";
    public static final String HEALTH_ILL="ill";
    public static final String HEALTH_OKAY="okay";

    public static final String AGE_NEWBORN="newborn";
    public static final String AGE_YOUNG="young";
    public static final String AGE_ADULT="adult";
    public static final String CATEGORY1 ="endangered";

    public EndangeredAnimals(String name ,String type, String health, String age) {
        super(name, age, health);
        this.type=type;
        this.health=health;
        this.age=age;
    }

    @Override
    public void save() {
        if(this.name.equals("")||this.type.equals("")||this.health.equals("")||this.age.equals("")){
            throw new IllegalArgumentException("Fields cannot be left blank");
        }
        try(Connection conn = DB.sql2o.open()){
            String sql ="INSERT INTO animals (name,type,health,age) VALUES (:name,:type,:health,:age)";
            this.id=(int) conn.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("type",this.type)
                    .addParameter("health",this.health)
                    .addParameter("age",this.age)
                    .executeUpdate()
                    .getKey();
        }
    }
}
