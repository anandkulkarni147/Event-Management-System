package chord;

import event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class ChordControllerTest {

    private ChordController chordController = new ChordController();
    @BeforeEach
    void setUp() {
        chordController.initNodes();
    }

    @Test
    void addNewNode() {
        // Ensure that the new node is added to the ring
        assertNotNull(chordController.getRing());
    }
    @Test
    void removeNode() {
        Event event = new Event("Test Event", new Date(), "This is a test event", "");
        chordController.storeEventAtNode(event);

        Long nodeId = chordController.hashKey(event.getId());

        chordController.removeNode(chordController.getRing().firstEntry().getKey());

        // Ensure that the node is removed from the ring
        assertNull(chordController.getRing().get(nodeId));
    }

    @Test
    void storeEventAtNode() {
        Event event = new Event("Test Event", new Date(), "This is a test event", "");
        chordController.storeEventAtNode(event);

        // Ensure that the event is stored in the appropriate node
        String eventId = event.getId();
        assertNotNull(chordController.getRing().firstEntry().getValue().getEvent(eventId));
    }

    @Test
    void fetchEventObject() {
        Event event = new Event("Test Event", new Date(), "This is a test event", "");
        chordController.storeEventAtNode(event);

        // Fetch the event and ensure it matches the original event
        Event fetchedEvent = chordController.fetchEventObject(event.getId());
        assertEquals(event, fetchedEvent);
    }

}
