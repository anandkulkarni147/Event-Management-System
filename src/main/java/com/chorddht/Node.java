import java.util.HashMap;
import java.util.Map;

public class Node {
    private final String nodeId;
    private final Map<String, String> dataMap;

    public Node(String nodeId) {
        this.nodeId = nodeId;
        this.dataMap = new HashMap<>();
    }
    public String get(String key) {
        return dataMap.get(key);
    }
    public void put(String key, String value) {
        dataMap.put(key, value);
    }
    public String getNodeId() {
        return nodeId;
    }
}



