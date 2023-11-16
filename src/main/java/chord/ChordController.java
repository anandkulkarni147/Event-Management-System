package chord;

import event.Event;
import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@RestController
@RequestMapping("/nodes")
public class ChordController {
    private TreeMap<String, ChordNode> nodes = new TreeMap<>();

    /**
     * get field
     *
     * @return nodes
     */
    public TreeMap<String, ChordNode> getNodes() {
        return this.nodes;
    }

    @PostMapping
    public void addNode() {
        ChordNode newNode = new ChordNode(generateNodeId());
        nodes.put(newNode.getNodeId(), newNode);
    }

    @GetMapping("/{key}")
    public String findSuccessor(@PathVariable String key) {
        int keyHash = hashKey(key);
        Map.Entry<String, ChordNode> entry = nodes.ceilingEntry(key);
        return entry != null ? entry.getKey() : null;
    }

    @DeleteMapping("/{nodeId}")
    public void removeNode(@PathVariable String nodeId) {
        redistributeKeys(nodes.get(nodeId));
        nodes.remove(nodeId);
    }

    @PostMapping("/{nodeId}/events")
    public void storeEventAtNode(@PathVariable String nodeId, @RequestBody Event event) {
        ChordNode node = nodes.get(nodeId);
        if (node != null) {
            node.storeEvent(event);
        }
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

    private int hashKey(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] keyBytes = md.digest(key.getBytes());
            return ByteBuffer.wrap(keyBytes).getInt();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing key");
        }
    }

}