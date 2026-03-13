package org.auto.screenplay.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.auto.screenplay.models.Pet;
import org.auto.screenplay.utils.Endpoints;

import static io.restassured.http.ContentType.JSON;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CreatePet implements Task {

    private final Pet pet;

    public CreatePet(Pet pet) {
        this.pet = pet;
    }

    public static CreatePet withDetails(Pet pet) {
        return instrumented(CreatePet.class, pet);
    }

    @Override
    @Step("{0} crea una nueva mascota en el PetStore")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Post.to(Endpoints.PET).with(request -> request
                .contentType(JSON)
                .body(pet))
        );
    }
}

