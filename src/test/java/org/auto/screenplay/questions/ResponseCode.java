package org.auto.screenplay.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ResponseCode implements Question<Integer> {

    @Override
    public Integer answeredBy(Actor actor) {
        return SerenityRest.lastResponse().getStatusCode();
    }

    public static ResponseCode status() {
        return new ResponseCode();
    }
}

