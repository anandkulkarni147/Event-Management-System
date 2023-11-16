package chord;

import event.Event;
import org.springframework.web.bind.annotation.*;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping("/nodes")
public class ChordController {
    private TreeMap<Long, ChordNode> nodes = new TreeMap<>();
    List<Event> eventList = new ArrayList<>();

    /**
     * get field
     *
     * @return nodes
     */
    public TreeMap<Long, ChordNode> getNodes() {
        return this.nodes;
    }

    @GetMapping("/{key}")
    public Long findSuccessor(@PathVariable Long key) {
        Map.Entry<Long, ChordNode> entry = nodes.ceilingEntry(key);
        return entry != null ? entry.getKey() : null;
    }

    public void addNewNode(Event event) {
        ChordNode newNode = new ChordNode(hashKey(event.getId()));
        nodes.put(newNode.getNodeId(), newNode);
    }

    @DeleteMapping("/{nodeId}")
    public void removeNode(@PathVariable String nodeId) {
        redistributeKeys(nodes.get(nodeId));
        nodes.remove(nodeId);
    }

    @GetMapping("/events")
    public List<Event> getEvents() {
        return eventList;
    }

    @PostMapping("/events")
    public void storeEventAtNode(@RequestBody Event event) {
        Long nodeId = hashKey(event.getId());
        Long nodePosition = nodes.ceilingKey(nodeId);
        ChordNode node = null;
        if (nodePosition != null) {
            node = nodes.get(nodePosition);
        } else {
            node = nodes.get(nodes.firstKey());
        }
        node.storeEvent(event);
        eventList.add(event);
    }

    private void redistributeKeys(ChordNode departingNode) {
        for (ChordNode node : nodes.values()) {
            if (!node.equals(departingNode)) {
                node.transferKeys(departingNode);
            }
        }
    }

    private String generateNodeId() {
        return UUID.randomUUID().toString();
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