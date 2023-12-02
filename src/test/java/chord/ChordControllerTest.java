package chord;

import event.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class ChordControllerTest {

    private ChordController chordController;
    @BeforeEach
    void setUp() {
        chordController = new ChordController();
        chordController.initNodes();
    }

    @Test
    void addNewNode() {
        // Add a new node
        ChordNode newNode = new ChordNode(123L);
        chordController.addNewNode(newNode);

        // Ensure that the new node is added to the ring
        assertNotNull(chordController.getRing().get(newNode.getNodeId()));
    }
    @Test
    void removeNode() {
        // Add a node
        ChordNode newNode = new ChordNode(123L);
        chordController.getRing().put(newNode.getNodeId(), newNode);

        // Remove the node
        chordController.removeNode(newNode.getNodeId());

        // Ensure that the node is removed from the ring
        assertNull(chordController.getRing().get(newNode.getNodeId()));
    }

    @Test
    void storeEventAtNode() {
        Event event = new Event("Test Event", new Date(), "This is a test event", "");
        chordController.storeEventAtNode(event);

        // Ensure that the event is stored in the appropriate node
        ChordNode node = chordController.getRing().get(chordController.hashKey(event.getId()));
        assertNotNull(node.getEvent(event.getId()));
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
