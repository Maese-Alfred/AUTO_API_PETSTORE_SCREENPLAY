package org.auto.screenplay.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;
import org.auto.screenplay.models.Pet;
import org.auto.screenplay.utils.Endpoints;

import static io.restassured.http.ContentType.JSON;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class UpdatePet implements Task {

    private final Pet pet;

    public UpdatePet(Pet pet) {
        this.pet = pet;
    }

    public static UpdatePet withDetails(Pet pet) {
        return instrumented(UpdatePet.class, pet);
    }

    @Override
    @Step("{0} actualiza la mascota en el PetStore")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Put.to(Endpoints.PET).with(request -> request
                .contentType(JSON)
                .body(pet))
        );
    }
}

