import models.*;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        port(4200);

        //home page
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "welcome.hbs");
        }, new HandlebarsTemplateEngine());

        //animals

        //add animal form
        get("/create/animal", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());

        //process form
        post("/create/animal/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String type=request.queryParams("type");
            String health=request.queryParams("health");
            System.out.println(health);
            String age=request.queryParams("age");
            System.out.println(age);
            String name=request.queryParams("name");
            System.out.println(name);
            model.put("name",name);
            model.put("health",health);
            model.put("age",age);

            if(type.equals(EndangeredAnimals.CATEGORY1)){
                EndangeredAnimals endangered=new EndangeredAnimals(name,EndangeredAnimals.CATEGORY1,health,age);
                endangered.save();
            }
            else {
                Animals animal=new Animals(name,age,health);
                animal.save();
            }

            return new ModelAndView(model,"animal-form.hbs");
        },new HandlebarsTemplateEngine());

        //endangered animal form
        get("/create/animal/endangered", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<String> health = new ArrayList<>();
            health.add(EndangeredAnimals.HEALTH_HEALTHY);
            health.add(EndangeredAnimals.HEALTH_ILL);
            health.add(EndangeredAnimals.HEALTH_OKAY);
            List<String> age = new ArrayList<>();
            age.add(EndangeredAnimals.AGE_ADULT);
            age.add(EndangeredAnimals.AGE_NEWBORN);
            age.add(EndangeredAnimals.AGE_YOUNG);
            model.put("health", health);
            model.put("age", age);
            String typeChosen = "endangered";
            model.put("endangered", typeChosen);
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());

        //view animal details
        get("/view/animals", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("animals", Animals.all());
            return new ModelAndView(model, "animal-view.hbs");
        }, new HandlebarsTemplateEngine());

        //rangers

        //add ranger form
        get("/create/ranger", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "ranger-form.hbs");
        }, new HandlebarsTemplateEngine());

        //process form
        post("/create/ranger/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            String badge_number = request.queryParams("phone_number");
            String phone_number = request.queryParams("phone_number");
            Rangers ranger = new Rangers(name, badge_number, phone_number);
            ranger.save();
            return new ModelAndView(model, "ranger-form.hbs");
        }, new HandlebarsTemplateEngine());

        //view ranger details
        get("/view/rangers", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("rangers", Rangers.all());
            return new ModelAndView(model, "ranger-view.hbs");
        }, new HandlebarsTemplateEngine());

        get("/view/ranger/sightings/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfRanger = Integer.parseInt(request.params(":id"));
            Rangers foundRanger = Rangers.find(idOfRanger);
            try {
                List<Sightings> sightings = foundRanger.getRangerSightings();
                List<String> animals = new ArrayList<>();
                List<String> types = new ArrayList<>();
                for (Sightings sighting : sightings) {
                    String animal_name = Animals.find(sighting.getAnimal_Id()).getName();
                    String animal_type = Animals.find(sighting.getAnimal_Id()).getType();
                    animals.add(animal_name);
                    types.add(animal_type);
                }
                model.put("sightings", sightings);
                model.put("animals", animals);
                model.put("types", types);
                model.put("rangers", Rangers.all());
            }catch (IllegalArgumentException e){
                response.redirect("/create/sighting");
            }


            return new ModelAndView(model, "ranger-view.hbs");
        }, new HandlebarsTemplateEngine());

        //location

        //location form
        get("/create/location", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "location-form.hbs");
        }, new HandlebarsTemplateEngine());

        //process location
        post("/create/location/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            Locations location = new Locations(name);
            try {
                location.save();
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
            return new ModelAndView(model, "location-form.hbs");
        }, new HandlebarsTemplateEngine());

        //view location details
        get("/view/locations", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("locations", Locations.all());
            return new ModelAndView(model, "location-view.hbs");
        }, new HandlebarsTemplateEngine());

        get("/view/location/sightings/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfLocation = Integer.parseInt(request.params(":id"));
            Locations foundLocation = Locations.find(idOfLocation);

            try {
                List<Sightings> sightings = foundLocation.getLocationSightings();
                List<String> animals = new ArrayList<>();
                List<String> types = new ArrayList<>();
                for (Sightings sighting : sightings) {
                    String animal_name = Animals.find(sighting.getAnimal_Id()).getName();
                    String animal_type = Animals.find(sighting.getAnimal_Id()).getType();
                    animals.add(animal_name);
                    types.add(animal_type);
                }
                model.put("sightings", sightings);
                model.put("animals", animals);
                model.put("types", types);
                model.put("locations", Locations.all());
            }catch (IllegalArgumentException e){
                response.redirect("/create/sighting");
            }

            return new ModelAndView(model, "location-view.hbs");
        }, new HandlebarsTemplateEngine());

        //sighting

        //sighting form
        get("/create/sighting", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("rangers", Rangers.all());
            model.put("locations", Locations.all());
            model.put("animals", Animals.all());
            return new ModelAndView(model, "sighting-form.hbs");
        }, new HandlebarsTemplateEngine());

        //process form
        post("/create/sighting/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int location_id = Integer.parseInt(request.queryParams("location"));
            int ranger_id = Integer.parseInt(request.queryParams("ranger"));
            int animal_id = Integer.parseInt(request.queryParams("animal"));

            Sightings sighting = new Sightings(location_id, ranger_id, animal_id);
            sighting.save();
            return new ModelAndView(model, "sighting-form.hbs");
        }, new HandlebarsTemplateEngine());

        //view sighting details
        get("/view/sightings", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Sightings> sightings = Sightings.all();
            List<String> animals = new ArrayList<>();
            List<String> types = new ArrayList<>();
            for (Sightings sighting : sightings) {
                String animal_name = Animals.find(sighting.getAnimal_Id()).getName();
                String animal_type = Animals.find(sighting.getAnimal_Id()).getType();
                animals.add(animal_name);
                types.add(animal_type);
            }
            model.put("sightings", sightings);
            model.put("animals", animals);
            model.put("types", types);
            return new ModelAndView(model, "sighting-view.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
