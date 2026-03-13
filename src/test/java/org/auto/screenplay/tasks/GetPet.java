package org.auto.screenplay.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.auto.screenplay.utils.Endpoints;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetPet implements Task {

    private final long petId;

    public GetPet(long petId) {
        this.petId = petId;
    }

    public static GetPet withId(long petId) {
        return instrumented(GetPet.class, petId);
    }

    @Override
    @Step("{0} consulta la mascota con id {1}")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Get.resource(Endpoints.PET_BY_ID).with(request -> request
                .pathParam("petId", petId))
        );
    }
}

