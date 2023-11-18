package chord;

import event.Event;
import org.springframework.stereotype.Controller;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Controller
public class ChordController {
    private final TreeMap<Long, ChordNode> ring = new TreeMap<>();

    private static int MAX_EVENTS_IN_SINGLE_NODE = 2;

    /**
     * get field
     *
     * @return nodes
     */
    public TreeMap<Long, ChordNode> getRing() {
        return this.ring;
    }

    private Long findSuccessor(Long key) {
        Long successorId = ring.ceilingKey(key);
        return successorId != null ? successorId : ring.firstKey();
    }

    private Long findPredecessor(Long key) {
        Long predecessorId = ring.floorKey(key);
        return predecessorId != null ? predecessorId : ring.lastKey();
    }

    public void initNodes() {
        ChordNode first = new ChordNode((1 + Long.MAX_VALUE / 2) / 2);
        ring.put(first.getNodeId(), first);
        ChordNode second = new ChordNode(Long.MAX_VALUE / 2 + (Long.MAX_VALUE - Long.MAX_VALUE / 2) / 2);
        ring.put(second.getNodeId(), second);
        first.setSuccessor(second);
        first.setPredecessor(second);
        second.setPredecessor(first);
        second.setSuccessor(first);
    }

    public void removeNode(Long nodeId) {
        ChordNode node = ring.get(nodeId);
        ChordNode predecessor = node.getPredecessor();
        ChordNode successor = node.getSuccessor();
        successor.putAll(node);
        predecessor.setSuccessor(successor);
        successor.setPredecessor(predecessor);
        ring.remove(nodeId);
        node = null;
    }

    public void storeEventAtNode(Event event) {
        Long nodeHash = hashKey(event.getId());
        Long successorId = findSuccessor(nodeHash);
        ChordNode node = ring.get(successorId);
        node.storeEvent(event);
        if (node.getNumberOfEventsInCurrentNode() > MAX_EVENTS_IN_SINGLE_NODE) {
            addNewNode(node);
        }
    }

    private void addNewNode(ChordNode node) {
        ChordNode predecessor = node.getPredecessor();
        Long newNodeId = eventualConsistentHash(node.getNodeId(), predecessor.getNodeId());
        ChordNode newNode = new ChordNode(newNodeId);
        newNode.setSuccessor(node);
        newNode.setPredecessor(predecessor);
        node.setPredecessor(newNode);
        predecessor.setSuccessor(newNode);
        ring.put(newNodeId, newNode);
        balanceKeys(newNode, node);
        MAX_EVENTS_IN_SINGLE_NODE *= 2;
    }

    private void balanceKeys(ChordNode newNode, ChordNode successor) {
        for (Map.Entry<String, Event> eventEntry : new HashMap<>(successor.getEvents()).entrySet()) {
            Long hashId = hashKey(eventEntry.getKey());
            if (hashId <= newNode.getNodeId()) {
                newNode.getEvents().put(eventEntry.getKey(), eventEntry.getValue());
                successor.getEvents().remove(eventEntry.getKey());
            }
        }
    }

    private Long eventualConsistentHash(Long curr, Long prev) {
        return prev + (curr - prev) / 2;
    }

    public Long hashKey(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] keyBytes = md.digest(key.getBytes());
            return Math.abs(ByteBuffer.wrap(keyBytes).getLong());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing key");
        }
    }

}