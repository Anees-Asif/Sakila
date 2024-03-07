import com.example.sakila.controllers.ActorController;
import com.example.sakila.dto.ActorPartial;
import com.example.sakila.entities.Actor;
import com.example.sakila.services.ActorService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.Mockito.when;

public class ActorControllerStepDefs {

    private ActorService actorService;
    private ActorController actorController;
    private ResponseEntity<Actor> responseEntity;

    @Before
    public void setUp() {
        actorService = Mockito.mock(ActorService.class);
        actorController = new ActorController(actorService);
    }

    @Given("the actor with id {short} exists")
    public void theActorWithIdExists(short id) {
        Actor expectedActor = new Actor();
        expectedActor.setId(id);
        expectedActor.setFirstName("John");
        expectedActor.setLastName("Doe");

        when(actorService.getActorById(anyShort())).thenReturn(expectedActor);
    }

   /* @When("a get request is made to actors/{int}")
    public void getRequestIsMadeToActors(short id) {
        responseEntity = ResponseEntity.ok(actorController.getActorById(id));
    }*/
    @When("^a get request is made to actors/(\\d+)$")
    public void getRequestIsMadeToActorsWithRegex(short id) {
        responseEntity = ResponseEntity.ok(actorController.getActorById(id));
    }

    @Then("an actor is returned")
    public void anActorIsReturned() {
        assertNotNull(responseEntity.getBody());
        Actor actualActor = responseEntity.getBody();
        assertEquals("John", actualActor.getFirstName());
        assertEquals("Doe", actualActor.getLastName());
    }



}
