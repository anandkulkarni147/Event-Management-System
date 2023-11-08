public class Main {
    public static void main(String[] args) {

//        System.out.println("Hello world!");
        RingArchitecture ringArchitecture = new RingArchitecture();
        Node node1 = new Node("1");
        ringArchitecture.addNode(node1);
        Node node2 = new Node("2");
        ringArchitecture.addNode(node2);
        Node node10 = new Node("10");
        ringArchitecture.addNode(node10);
        Node node24 = new Node("24");
        ringArchitecture.addNode(node24);
        System.out.println(ringArchitecture.getNodeForKey("14").getNodeId());

    }
}