package ServiceTest;

import model.Event;
import model.Person;
import org.junit.jupiter.api.Test;
import services.GeneratorService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratorServiceTest {


    @Test
    void JsonImportPass(){
        boolean didItWork = true;
        try {
            GeneratorService gService = new GeneratorService();
        } catch (IOException e) {
            System.out.println("JSON IMPORT FAIL");
            didItWork = false;
            e.printStackTrace();
        }
        assertTrue(didItWork);
    }

    @Test
    void PersonGenerationPass(){

        try {
            GeneratorService gService = new GeneratorService();
            Person person = gService.generateBasePerson("Sarah");

            Person person2 = gService.generatePerson("Alexandra", "f");

            assertNotNull(person);
            assertNotNull(person2);

            assertNotNull(person.getFirstName());
            assertNotNull(person.getLastName());
            System.out.println(person.getFirstName() + ", " + person.getLastName());

        } catch (IOException e) {
            System.out.println("JSON IMPORT FAIL");

            e.printStackTrace();
        }

    }

    @Test
    void EventGenerationPass(){
        try {
            GeneratorService gService = new GeneratorService();
            Event event = gService.generateEvent("WOWZA", "crazygirl343454", "Birth", 1988);
            assertNotNull(event);

            assertNotNull(event.getCity());
            System.out.println(event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " " + event.getYear());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
