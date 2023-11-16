package chord;

import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/nodes")
public class ChordController {
    private List<ChordNode> nodes = new ArrayList<>();

    @PostMapping
    public void addNode() {
        ChordNode newNode = new ChordNode(generateNodeId());
        nodes.add(newNode);
        nodes.sort(Comparator.comparing(ChordNode::getNodeId));
    }

    @GetMapping("/{key}")
    public String findSuccessor(@PathVariable String key) {
        int keyHash = hashKey(key);
        ChordNode successor = findSuccessorNode(keyHash);
        return successor.getNodeId();
    }

    @DeleteMapping("/{nodeId}")
    public void removeNode(@PathVariable String nodeId) {
        ChordNode nodeToRemove = findNodeById(nodeId);
        if (nodeToRemove != null) {
            nodes.remove(nodeToRemove);
            redistributeKeys(nodeToRemove);
        }
    }

    private ChordNode findNodeById(String nodeId) {
        return nodes.stream()
                .filter(node -> node.getNodeId().equals(nodeId))
                .findFirst()
                .orElse(null);
    }

    private ChordNode findSuccessorNode(int keyHash) {
        for (ChordNode node : nodes) {
            if (node.getNodeIdHash() >= keyHash) {
                return node;
            }
        }
        // If no successor found, return the first node (wrap around)
        return nodes.get(0);
    }

    private void redistributeKeys(ChordNode departingNode) {
        for (ChordNode node : nodes) {
            if (!node.equals(departingNode)) {
                node.transferKeys(departingNode);
            }
        }
    }

    private static String generateNodeId() {
        return UUID.randomUUID().toString();
    }

    private static int hashKey(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] keyBytes = md.digest(key.getBytes());
            return ByteBuffer.wrap(keyBytes).getInt();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing key");
        }
    }
}