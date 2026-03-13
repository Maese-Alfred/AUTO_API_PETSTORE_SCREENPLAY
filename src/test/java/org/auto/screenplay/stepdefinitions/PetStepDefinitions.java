package org.auto.screenplay.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.auto.screenplay.models.Pet;
import org.auto.screenplay.questions.ResponseBody;
import org.auto.screenplay.questions.ResponseCode;
import org.auto.screenplay.tasks.CreatePet;
import org.auto.screenplay.tasks.DeletePet;
import org.auto.screenplay.tasks.GetPet;
import org.auto.screenplay.tasks.UpdatePet;

import java.util.Random;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;

public class PetStepDefinitions {

    private static final String ACTOR_NAME = "el usuario";

    @Before
    public void setUp() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("el usuario quiere gestionar mascotas en el PetStore")
    public void elUsuarioQuiereGestionarMascotas() {
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String baseUrl = environmentVariables.getProperty("serenity.rest.base.url", "https://petstore.swagger.io/v2");
        OnStage.theActorCalled(ACTOR_NAME).whoCan(CallAnApi.at(baseUrl));
    }

    @When("crea una nueva mascota con nombre {string} y estado {string}")
    public void creaNuevaMascota(String nombre, String estado) {
        Actor actor = OnStage.theActorInTheSpotlight();
        long petId = new Random().nextInt(100000) + 1L;
        Pet pet = new Pet(petId, nombre, estado);
        actor.remember("petId", petId);
        actor.attemptsTo(CreatePet.withDetails(pet));
    }

    @When("consulta la mascota creada por su identificador")
    public void consultaMascotaCreada() {
        consultarMascotaPorId();
    }

    @When("actualiza el nombre de la mascota a {string} y el estado a {string}")
    public void actualizaMascota(String nuevoNombre, String nuevoEstado) {
        Actor actor = OnStage.theActorInTheSpotlight();
        long petId = actor.recall("petId");
        Pet petActualizado = new Pet(petId, nuevoNombre, nuevoEstado);
        actor.attemptsTo(UpdatePet.withDetails(petActualizado));
    }

    @When("elimina la mascota por su identificador")
    public void eliminaMascota() {
        Actor actor = OnStage.theActorInTheSpotlight();
        long petId = actor.recall("petId");
        actor.attemptsTo(DeletePet.withId(petId));
    }

    @When("consulta la mascota eliminada por su identificador")
    public void consultaMascotaEliminada() {
        consultarMascotaPorId();
    }

    private void consultarMascotaPorId() {
        Actor actor = OnStage.theActorInTheSpotlight();
        long petId = actor.recall("petId");
        actor.attemptsTo(GetPet.withId(petId));
    }

    @Then("la respuesta debe tener codigo de estado {int}")
    public void laRespuestaDebeTenerCodigoDeEstado(int codigoEsperado) {
        OnStage.theActorInTheSpotlight().should(
            seeThat("el codigo de estado de la respuesta", ResponseCode.status(), equalTo(codigoEsperado))
        );
    }

    @And("el nombre de la mascota en la respuesta debe ser {string}")
    public void elNombreDeLaMascotaDebeSer(String nombreEsperado) {
        OnStage.theActorInTheSpotlight().should(
            seeThat("el nombre de la mascota en la respuesta", ResponseBody.field("name"), equalTo(nombreEsperado))
        );
    }
}

