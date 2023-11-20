package chord;

public class FingerTable {

    /*
    *
    * import java.util.TreeMap;

        public class ChordController {
            private TreeMap<String, ChordNode> nodes = new TreeMap<>();

            public void addNode() {
                ChordNode newNode = new ChordNode(generateNodeId());
                nodes.put(newNode.getNodeId(), newNode);

                if (nodes.size() > 1) {
                    ChordNode existingNode = nodes.floorEntry(newNode.getNodeId()).getValue();
                    existingNode.joinChordRing(newNode);
                }
            }

            public void removeNode(String nodeId) {
                ChordNode removedNode = nodes.remove(nodeId);

                if (removedNode != null) {
                    ChordNode predecessor = removedNode.getPredecessor();
                    ChordNode successor = removedNode.getSuccessor();

                    if (predecessor != null) {
                        predecessor.setSuccessor(successor);
                    }

                    if (successor != null) {
                        successor.setPredecessor(predecessor);
                    }

                }
            }

            public void runStabilizationProtocol() {
                for (ChordNode node : nodes.values()) {
                    node.stabilize();
                    node.fixFingers();
                }
            }

            private String generateNodeId() {
                return "Node_" + System.nanoTime();
            }

            public static void main(String[] args) {
                ChordController chordController = new ChordController();

                chordController.addNode();
                chordController.addNode();
                chordController.addNode();

                // Running stabilization protocol
                chordController.runStabilizationProtocol();

                // Removing a node
                String nodeIdToRemove = "Node_123456789";
                chordController.removeNode(nodeIdToRemove);
            }
        }

        class ChordNode {
            private String nodeId;
            private int nodeIdHash;
            private ChordNode predecessor;
            private ChordNode successor;
            private FingerTable fingerTable;

            public ChordNode(String nodeId) {
                this.nodeId = nodeId;
                this.nodeIdHash = hashNodeId(nodeId);
                this.fingerTable = new FingerTable(this);
            }

            public String getNodeId() {
                return nodeId;
            }

            public int getNodeIdHash() {
                return nodeIdHash;
            }

            public ChordNode getPredecessor() {
                return predecessor;
            }

            public ChordNode getSuccessor() {
                return successor;
            }

            public void setPredecessor(ChordNode predecessor) {
                this.predecessor = predecessor;
            }

            public void setSuccessor(ChordNode successor) {
                this.successor = successor;
            }

            public void joinChordRing(ChordNode newNode) {
                fingerTable.updateFingerTable(newNode);
                stabilize();
                newNode.notifySuccessor(this);
            }

            public void stabilize() {
                ChordNode potentialSuccessor = successor.getPredecessor();

                if (potentialSuccessor != null && isBetween(potentialSuccessor.getNodeIdHash(), nodeIdHash, successor.getNodeIdHash(), false, false)) {
                    successor = potentialSuccessor;
                }

                successor.notifyPredecessor(this);
            }

            public void notifyPredecessor(ChordNode potentialPredecessor) {
                if (predecessor == null || isBetween(potentialPredecessor.getNodeIdHash(), predecessor.getNodeIdHash(), nodeIdHash, false, false)) {
                    predecessor = potentialPredecessor;
                }
            }

            public void notifySuccessor(ChordNode potentialSuccessor) {
                if (successor == null || isBetween(potentialSuccessor.getNodeIdHash(), nodeIdHash, successor.getNodeIdHash(), false, false)) {
                    successor = potentialSuccessor;
                }
            }

            public void fixFingers() {
                for (int i = 0; i < fingerTable.size(); i++) {
                    int fingerStart = (nodeIdHash + (1 << i)) % (1 << 32);
                    fingerTable.get(i).notifySuccessor(findSuccessor(fingerStart));
                }
            }

            public ChordNode findSuccessor(int keyHash) {
                if (isBetween(keyHash, nodeIdHash, successor.getNodeIdHash(), true, false)) {
                    return successor;
                } else {
                    ChordNode predecessor = findPredecessor(keyHash);
                    return predecessor.getSuccessor();
                }
            }

            private ChordNode findPredecessor(int keyHash) {
                ChordNode current = this;
                while (!isBetween(keyHash, current.getNodeIdHash(), current.getSuccessor().getNodeIdHash(), false, true)) {
                    current = current.closestPrecedingFinger(keyHash);
                }
                return current;
            }

            private ChordNode closestPrecedingFinger(int keyHash) {
                for (int i = fingerTable.size() - 1; i >= 0; i--) {
                    ChordNode fingerNode = fingerTable.get(i);
                    if (isBetween(fingerNode.getNodeIdHash(), nodeIdHash, keyHash, false, true)) {
                        return fingerNode;
                    }
                }
                return this;
            }

            private boolean isBetween(int value, int lowerBound, int upperBound, boolean includeLower, boolean includeUpper) {
                if (includeLower && value == lowerBound) return true;
                if (includeUpper && value == upperBound) return true;
                return (value > lowerBound) && (value < upperBound);
            }

            private int hashNodeId(String nodeId) {
                // Simplified hash function for node identifiers
                return nodeId.hashCode();
            }
        }

        class FingerTable {
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
                    int fingerStart = (owner.getNodeIdHash() + (1 << i)) % (1 << 32);
                    fingers.add(owner.findSuccessor(fingerStart));
                }
            }

            public void updateFingerTable(ChordNode existingNode) {
                for (int i = 0; i < TABLE_SIZE; i++) {
                    int fingerStart = (owner.getNodeIdHash() + (1 << i)) % (1 << 32);
                    ChordNode successor = existingNode.findSuccessor(fingerStart);
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

    *
    * */

}
