package org.auto.screenplay.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import org.auto.screenplay.utils.Endpoints;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DeletePet implements Task {

    private final long petId;

    public DeletePet(long petId) {
        this.petId = petId;
    }

    public static DeletePet withId(long petId) {
        return instrumented(DeletePet.class, petId);
    }

    @Override
    @Step("{0} elimina la mascota con id {1}")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Delete.from(Endpoints.PET_BY_ID).with(request -> request
                .pathParam("petId", petId))
        );
    }
}

