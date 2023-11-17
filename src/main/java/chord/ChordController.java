package chord;

import event.Event;
import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
public class ChordController {
    private final TreeMap<Long, ChordNode> nodes = new TreeMap<>();

    private final int MAX_EVENTS_IN_SINGLE_NODE = 100;

    /**
     * get field
     *
     * @return nodes
     */
    public TreeMap<Long, ChordNode> getNodes() {
        return this.nodes;
    }

    private Long findSuccessor(Long key) {
        Map.Entry<Long, ChordNode> entry = nodes.ceilingEntry(key);
        return entry != null ? entry.getKey() : nodes.firstKey();
    }

    public void initNodes() {
        ChordNode newNode = new ChordNode(1L);
        nodes.put(newNode.getNodeId(), newNode);
        newNode = new ChordNode(Long.MAX_VALUE);
        nodes.put(newNode.getNodeId(), newNode);
        //Add logic to redistribute the keys.
    }

    public void removeNode(Long nodeId) {
        redistributeKeys(nodes.get(nodeId));
        nodes.remove(nodeId);
    }

    public void storeEventAtNode(Event event) {
        Long nodeId = hashKey(event.getId());
        Long successorId = findSuccessor(nodeId);
        ChordNode node = nodes.get(successorId);
        node.storeEvent(event);
        if (node.getNumberOfEventsInCurrentNode() > MAX_EVENTS_IN_SINGLE_NODE) {
            addNewNodeAndBalanceKeys(node);
        }
    }

    private void addNewNodeAndBalanceKeys(ChordNode node) {
        Long prevId = nodes.lowerKey(node.getNodeId()) != null ? nodes.lowerKey(node.getNodeId()) : node.getNodeId();
        Long nextId = nodes.higherKey(node.getNodeId()) != null ? nodes.higherKey(node.getNodeId()) : node.getNodeId();
        Long newNodeId = prevId + (nextId - prevId) / 2;
        ChordNode newNode = new ChordNode(newNodeId);
        nodes.put(newNodeId, newNode);

        ChordNode next = nodes.get(nextId);
        for (Map.Entry<String, Event> eventEntry : next.getEvents().entrySet()) {
            if (hashKey(eventEntry.getKey()) <= newNodeId) {
                newNode.getEvents().put(eventEntry.getKey(), eventEntry.getValue());
                next.getEvents().remove(eventEntry.getKey());
            }
        }
    }

    private void redistributeKeys(ChordNode departingNode) {
        for (ChordNode node : nodes.values()) {
            if (!node.equals(departingNode)) {
                node.transferKeys(departingNode);
            }
        }
    }

    private Long hashKey(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] keyBytes = md.digest(key.getBytes());
            return ByteBuffer.wrap(keyBytes).getLong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing key");
        }
    }

}