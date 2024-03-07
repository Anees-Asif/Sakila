import com.example.sakila.controllers.ActorController;
import com.example.sakila.dto.ActorPartial;
import com.example.sakila.entities.Actor;
import com.example.sakila.services.ActorService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_old.Ac;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class ActorControllerStepDefs {
    private ActorService actorService;
    private final short expectedId = 1;

    private final ActorPartial expectedActor = new ActorPartial(expectedId, "PENELOPE", "GUINESS");
    private Actor actualActor;
    @Before
    public void setUp(){
        actorService = mock(ActorService.class);

    }
    @Given("the actor with id {short} exists")
    public void GivenActor1Exists(Short id) {
        doReturn(expectedActor).when(actorService).getActorById(id); // Include the method argument here
    }

    @When("When get request is made to \\/actors\\/{short}")
    public void whenRequestWithId(short Id){
        final ActorController actorController = new ActorController(actorService);
        try{
            actualActor = actorController.getActorById(Id);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Then("an actor is returned")
    public  void thenActorIsReturned(){
        assertNotNull(actualActor);
        assertEquals("John", actualActor.getFirstName());
        assertEquals("Doe", actualActor.getLastName());
    }

}
