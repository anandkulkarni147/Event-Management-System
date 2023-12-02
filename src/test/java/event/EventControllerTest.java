package event;

import chord.ChordController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventControllerTest {

    private EventController eventController = new EventController();
    private ChordController chordController = new ChordController();

    @BeforeEach
    void setUp() {
        chordController.initNodes();
    }

    @Test
    void getAllEvents() {
        // Add some events to the chordController
        Event event1 = new Event("Event1", new Date(), "Desc1", "Location1");
        Event event2 = new Event("Event2", new Date(), "Desc2", "Location2");
        chordController.storeEventAtNode(event1);
        chordController.storeEventAtNode(event2);

        List<Event> allEvents = new ArrayList<>(chordController.getRing().firstEntry().getValue().getEvents().values());
        assertNotNull(allEvents);
        assertEquals(2, allEvents.size());
    }

    @Test
    void addEvent() {
        Event event = new Event("NewEvent", new Date(), "NewDesc", "NewLocation");
        chordController.storeEventAtNode(event);

        // Ensure the event is added to the chordController
        assertEquals(event, chordController.fetchEventObject(event.getId()));
    }

    @Test
    void getEvent() {
        Event event = new Event("TestEvent", new Date(), "TestDesc", "TestLocation");
        chordController.storeEventAtNode(event);

        Event retrievedEvent = chordController.getRing().firstEntry().getValue().getEvent(event.getId());

        assertEquals(event, retrievedEvent);
    }

}