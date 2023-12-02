package chord;

import event.Event;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ChordNodeTest {

    @Test
    void getSuccessor() {
        ChordNode chordNode = new ChordNode(1L);
        ChordNode successor = new ChordNode(2L);

        chordNode.setSuccessor(successor);

        assertEquals(successor, chordNode.getSuccessor());
    }

    @Test
    void getPredecessor() {
        ChordNode chordNode = new ChordNode(1L);
        ChordNode predecessor = new ChordNode(0L);

        chordNode.setPredecessor(predecessor);

        assertEquals(predecessor, chordNode.getPredecessor());
    }

    @Test
    void getNodeId() {
        Long nodeId = 123L;
        ChordNode chordNode = new ChordNode(nodeId);

        assertEquals(nodeId, chordNode.getNodeId());
    }

    @Test
    void getNumberOfEventsInCurrentNode() {
        ChordNode chordNode = new ChordNode(1L);
        Event event = new Event("Test Event", new Date(), "Description", "Location");

        chordNode.storeEvent(event);

        assertEquals(1, chordNode.getNumberOfEventsInCurrentNode());
        assertEquals(event, chordNode.getEvent(event.getId()));
    }

    @Test
    void putAll() {
        ChordNode chordNode1 = new ChordNode(1L);
        ChordNode chordNode2 = new ChordNode(2L);
        Event event1 = new Event("Event 1", new Date(), "Desc 1", "Loc 1");
        Event event2 = new Event("Event 2", new Date(), "Desc 2", "Loc 2");

        chordNode1.storeEvent(event1);
        chordNode1.storeEvent(event2);

        chordNode2.putAll(chordNode1);

        assertEquals(2, chordNode2.getNumberOfEventsInCurrentNode());
        assertNotNull(chordNode2.getEvent(event1.getId()));
        assertNotNull(chordNode2.getEvent(event2.getId()));
    }

    @Test
    void getEvents() {
        ChordNode chordNode = new ChordNode(1L);
        Event event = new Event("Test Event", new Date(), "Description", "Location");

        chordNode.storeEvent(event);

        assertEquals(1, chordNode.getEvents().size());
        assertNotNull(chordNode.getEvents().get(event.getId()));
    }
}