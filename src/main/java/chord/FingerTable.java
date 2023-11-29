package chord;

import java.util.ArrayList;
import java.util.List;

public class FingerTable {
    private static final int TABLE_SIZE = 5;  // Size of the finger table
    private ChordNode owner;
    private List<ChordNode> fingers;

    public FingerTable(ChordNode owner) {
        this.owner = owner;
        this.fingers = new ArrayList<>();
        initializeFingerTable();
    }

    private void initializeFingerTable() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            long fingerStart = (owner.getNodeId() + (1 << i)) % (1 << 32);
            fingers.add(owner);
        }
    }

    public void updateFingerTable(ChordNode existingNode) {
        for (int i = 0; i < TABLE_SIZE; i++) {
            long fingerStart = (owner.getNodeId() + (1 << i)) % (1 << 32);
            ChordNode successor = existingNode.getSuccessor();
            fingers.set(i, successor);
        }
    }

    public ChordNode get(int index) {
        if (index >= 0 && index < TABLE_SIZE) {
            return fingers.get(index);
        }
        return null;
    }

    public int size() {
        return fingers.size();
    }
}
