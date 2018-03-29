package testex.jokefetching;

import testex.Joke;

import static com.jayway.restassured.RestAssured.given;

public class Moma implements IJokeFetcher {

    @Override
    public Joke getJoke() {
        try {
            String joke = given().get("http://api.yomomma.info/").andReturn().jsonPath().getString("joke");
            return new Joke(joke,"http://api.yomomma.info/");
        } catch (Exception e) {
            return null;
        }
    }
}