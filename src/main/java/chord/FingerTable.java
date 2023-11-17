package chord;

import java.util.ArrayList;
import java.util.List;

public class FingerTable {
    private ChordNode owner;
    private List<ChordNode> fingers;

    public FingerTable(ChordNode owner) {
        this.owner = owner;
        this.fingers = new ArrayList<>();
        initializeFingerTable();
    }

    private void initializeFingerTable() {
        for (int i = 0; i < 32; i++) {
            Long fingerStart = (owner.getNodeId()%(1 << 32) + (1 << i) % (1 << 32));
            fingers.add(findSuccessor(fingerStart));
        }
    }

    private ChordNode findSuccessor(Long key) {
        ChordNode predecessor = owner.findPredecessor(key);
        return predecessor.getSuccessor();
    }

    public ChordNode get(int index) {
        if (index >= 0 && index < fingers.size()) {
            return fingers.get(index);
        }
        return null;
    }
}