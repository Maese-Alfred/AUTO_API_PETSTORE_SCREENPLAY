package org.auto.screenplay.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ResponseBody implements Question<String> {

    private final String field;

    public ResponseBody(String field) {
        this.field = field;
    }

    public static ResponseBody field(String field) {
        return new ResponseBody(field);
    }

    @Override
    public String answeredBy(Actor actor) {
        return SerenityRest.lastResponse().jsonPath().getString(field);
    }
}

