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

        //create animal form
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

        get("/view/animals", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("animals", Animals.all());
            return new ModelAndView(model, "animal-view.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
