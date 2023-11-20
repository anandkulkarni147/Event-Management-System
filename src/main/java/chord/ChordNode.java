
package chord;

import event.Event;

import java.util.*;

public class ChordNode {
    private final Long nodeId;
    private ChordNode successor;
    private ChordNode predecessor;
    private final List<Long> keys = new ArrayList<>();
    private final Map<String, Event> events = new HashMap<>();

    public ChordNode(Long nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * get field
     *
     * @return successor
     */
    public ChordNode getSuccessor() {
        return this.successor;
    }

    /**
     * set field
     *
     * @param successor
     */
    public void setSuccessor(ChordNode successor) {
        this.successor = successor;
    }

    /**
     * get field
     *
     * @return predecessor
     */
    public ChordNode getPredecessor() {
        return this.predecessor;
    }

    /**
     * set field
     *
     * @param predecessor
     */
    public void setPredecessor(ChordNode predecessor) {
        this.predecessor = predecessor;
    }

    /**
     * get node id
     *
     * @return {@link Long}
     * @see Long
     */
    public Long getNodeId() {
        return nodeId;
    }

    /**
     * store event
     *
     * @param event event
     */
    public void storeEvent(Event event) {
        events.put(event.getId(), event);
        System.out.println("\nNew event (ID-"+event.getId()+")\nStored at Node ID - "+this.getNodeId());
        System.out.println("\n----------------------------List of events at current node - ["+this.getNodeId()+"]----------------------------");
        Map<String, Event> eventsLocal = this.getEvents();
        for(String eventId : eventsLocal.keySet()){
            System.out.println("*"+eventsLocal.get(eventId).getName());
        }
        System.out.println("");
    }

    /**
     * get field
     *
     * @return number of events in a node
     */
    public int getNumberOfEventsInCurrentNode() {
        return this.events.size();
    }

    /**
     * get event
     *
     * @param eventId eventId
     * @return {@link Event}
     * @see Event
     */
    public Event getEvent(String eventId) {
        return events.get(eventId);
    }

    public void putAll(ChordNode node) {
        this.events.putAll(node.events);
    }

    /**
     * get field
     *
     * @return events
     */
    public Map<String, Event> getEvents() {
        return this.events;
    }
}