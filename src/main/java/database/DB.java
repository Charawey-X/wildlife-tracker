package database;

import org.sql2o.Sql2o;

public class DB {
    //public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost/wildlife_tracker", "x", "230620");

    //heroku deploy
    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://ec2-54-164-40-66.compute-1.amazonaws.com:5432/d2f5datn4ol7hp", "tsdtgirckndbcv", "7ab0f9b7ebc8ad14e93c30202e6c104aae13562b268843b63481c6befc19db15");

}
