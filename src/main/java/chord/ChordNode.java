
package chord;

import event.Event;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ChordNode {
    private Long nodeId;
    private List<Long> keys = new ArrayList<>();

    private Map<String, Event> events = new HashMap<>();

    public ChordNode(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void storeEvent(Event event) {
        events.put(event.getId(), event);
    }

    public Event getEvent(String eventId) {
        return events.get(eventId);
    }

    public void transferKeys(ChordNode newPredecessor) {
        List<Long> keysToTransfer = new ArrayList<>();
        for (Long key : keys) {
            if (newPredecessor.getNodeId() < key && key <= nodeId) {
                keysToTransfer.add(key);
            }
        }
        newPredecessor.addKeys(keysToTransfer);
        keys.removeAll(keysToTransfer);
    }

    public void addKeys(List<Long> keysToAdd) {
        keys.addAll(keysToAdd);
        keys.sort(Long::compareTo);
    }

    private static Long hashKey(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] keyBytes = md.digest(key.getBytes());
            return ByteBuffer.wrap(keyBytes).getLong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing key");
        }
    }
}