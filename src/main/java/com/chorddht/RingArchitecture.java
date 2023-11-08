import java.util.SortedMap;
import java.util.TreeMap;

public class RingArchitecture {
    private final SortedMap<Integer, Node> ring;

    public RingArchitecture() {
        this.ring = new TreeMap<>();
    }

    public void addNode(Node node) {
        int hash = hashFunction(node.getNodeId());
        ring.put(hash, node);
    }

    public Node getNodeForKey(String key) {
        int hash = hashFunction(key);
        if (ring.isEmpty()) {
            return null;
        }

        if (!ring.containsKey(hash)) {
            SortedMap<Integer, Node> tailMap = ring.tailMap(hash);
            hash = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        }

        return ring.get(hash);
    }

    private int hashFunction(String key) {
        return key.hashCode();
    }
}