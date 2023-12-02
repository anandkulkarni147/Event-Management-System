package event;

import chord.ChordController;
import email.KafkaProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class EventControllerTest {

    private EventController eventController;
    private ChordController chordController;
    private KafkaProducerService emailScheduler;

    @BeforeEach
    void setUp() {
        emailScheduler = mock(KafkaProducerService.class);
        chordController.initNodes();
    }

    @Test
    void getAllEvents() {
        // Add some events to the chordController
        Event event1 = new Event("Event1", new Date(), "Desc1", "Location1");
        Event event2 = new Event("Event2", new Date(), "Desc2", "Location2");
        chordController.storeEventAtNode(event1);
        chordController.storeEventAtNode(event2);

        List<Event> allEvents = eventController.getAllEvents();

        assertEquals(2, allEvents.size());
        assertEquals(event1, allEvents.get(0));
        assertEquals(event2, allEvents.get(1));
    }

    @Test
    void addEvent() {
        Event event = new Event("NewEvent", new Date(), "NewDesc", "NewLocation");

        eventController.addEvent(event);

        // Ensure the event is added to the chordController
        assertEquals(event, chordController.fetchEventObject(event.getId()));
    }

    @Test
    void getEvent() {
        Event event = new Event("TestEvent", new Date(), "TestDesc", "TestLocation");
        chordController.storeEventAtNode(event);

        Event retrievedEvent = eventController.getEvent(event.getId());

        assertEquals(event, retrievedEvent);
    }

    @Test
    void index() {
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);

        Event event = new Event("NewEvent", new Date(), "NewDesc", "NewLocation");
        String result = eventController.createNewEvent(event, session, model);

        // Assuming the result redirects to /home
        assertEquals("redirect:/home", result);

        // Ensure the event is added to the chordController
        assertEquals(event, chordController.fetchEventObject(event.getId()));
    }

    @Test
    void subscribeToEvent() {
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);

        Event event = new Event("TestEvent", new Date(), "TestDesc", "TestLocation");
        chordController.storeEventAtNode(event);

        String email = "test@gmail.com";
        session.setAttribute("email", email);

        String result = eventController.subscribeToEvent(event.getName(), event.getId(), session, model);

        // Assuming the result redirects to /home
        assertEquals("redirect:/home", result);

        // Ensure the user is added as a subscriber to the event
        assertTrue(event.getSubscribers().contains(email));
    }

}