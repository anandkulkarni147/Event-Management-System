
package chord;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ChordNode {
    private String nodeId;
    private int nodeIdHash;
    private List<String> keys = new ArrayList<>();

    public ChordNode(String nodeId) {
        this.nodeId = nodeId;
        this.nodeIdHash = hashNodeId(nodeId);
    }

    public String getNodeId() {
        return nodeId;
    }

    public int getNodeIdHash() {
        return nodeIdHash;
    }

    public void transferKeys(ChordNode newPredecessor) {
        List<String> keysToTransfer = new ArrayList<>();
        for (String key : keys) {
            int keyHash = hashKey(key);
            if (newPredecessor.getNodeIdHash() < keyHash && keyHash <= nodeIdHash) {
                keysToTransfer.add(key);
            }
        }
        newPredecessor.addKeys(keysToTransfer);
        keys.removeAll(keysToTransfer);
    }

    public void addKeys(List<String> keysToAdd) {
        keys.addAll(keysToAdd);
        keys.sort(String::compareTo);
    }

    private static int hashNodeId(String nodeId) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] nodeIdBytes = md.digest(nodeId.getBytes());
            return ByteBuffer.wrap(nodeIdBytes).getInt();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing node ID");
        }
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