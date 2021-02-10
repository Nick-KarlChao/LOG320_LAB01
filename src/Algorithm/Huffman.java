package Algorithm;

import java.util.ArrayList;
import java.util.Collections;

public class Huffman extends AbstractAlgorithm {
    private ArrayList<Node> frequencyTable;
    //private String testString = "accacbcc";
    private String testString = "accacbccddaasdddsffgghmmwwwfals";


    public Huffman(){
        System.out.println("You have chosen Huffman!");
        //Node poop = new Node();
        frequencyTable = new ArrayList<>();
        frequencyTable = createFrequencyTable();
        ArrayList<Integer> testTable = new ArrayList<>();
        testTable.add(1);
        testTable.add(5);
        testTable.add(3);
        /*for (int i = 0; i < testTable.size(); i++){
            System.out.println(testTable.get(i));
        }*/

        System.out.println(testTable.get(0));
        testTable.remove(0);
        System.out.println(testTable.get(0));
        createHuffmanTree();
    }
    @Override
    public byte[] compress(byte[] data){
        System.out.println("You have chosen to compress with Huffman!");
        //TODO
        return null;
    }

    @Override
    public byte[] decompress(byte[] data){
        System.out.println("You have chosen to compress with Huffman!");
        //TODO
        return null;
    }

    private ArrayList<Node> createFrequencyTable(){
        //TODO
        frequencyTable.clear();
        Node tempNode = new Node();

        for (int i = 0; i < testString.length(); i++){
            int c = testString.charAt(i);
            tempNode.setValue(testString.charAt(i));
            //if (frequencyTable.contains(node with specific value){
            if (frequencyTable.stream().anyMatch(o -> o.getValue() == tempNode.getValue())){
                //increment frequency of the specific node
                frequencyTable.stream().filter(o -> o.getValue() == tempNode.getValue()).findFirst().get().incrementFrequency();
            } else{
                Node newNode = new Node();
                newNode.setValue(c);
                frequencyTable.add(newNode);
                newNode.setFrequency(1);
            }
        }
        //Order the table by frequencies
        for (int i = 1; i < frequencyTable.size(); i++){
            Node key = frequencyTable.get(i);
            int j = i - 1;
            while (j >= 0 && frequencyTable.get(j).getFrequency() > key.getFrequency()){
                Collections.swap(frequencyTable, j, j+1);
                j--;
            }
        }
        System.out.println("Node values and frequencies test:");
        for (int i = 0; i < frequencyTable.size(); i++){
            System.out.println(frequencyTable.get(i).getValue() + " - " + frequencyTable.get(i).getFrequency());
        }
        return frequencyTable;
    }

    private void createHuffmanTree(){
        while (frequencyTable.size() > 1){
            Node newNode = new Node();
            newNode.setFrequency(frequencyTable.get(0).getFrequency()+frequencyTable.get(1).getFrequency());
            newNode.setLeftChild(frequencyTable.get(0));
            newNode.setRightChild(frequencyTable.get(1));
            System.out.println("1st smallest frequency: " + frequencyTable.get(0).getFrequency());
            System.out.println("2nd smallest frequency: " + frequencyTable.get(1).getFrequency());
            frequencyTable.remove(0);
            frequencyTable.remove(0);
            frequencyTable.add(newNode);

            if(frequencyTable.indexOf(newNode)>0){
                while (newNode.getFrequency() < frequencyTable.get((frequencyTable.indexOf(newNode))-1).getFrequency()){
                    Collections.swap(frequencyTable, frequencyTable.indexOf(newNode), frequencyTable.indexOf(newNode)-1);
                }
            }
            System.out.println("table size: " + frequencyTable.size());
        }
        System.out.println("final node frequency: " + frequencyTable.get(0).getFrequency());
    }

    private void encode(){
        //TODO
    };

    public static class Node implements Comparable<Node>{
        private int value;
        private int frequency;
        private Node leftChild;
        private Node rightChild;

        Node(){
            this.frequency = 0;
        }


        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value){
            this.value = value;
        }

        public int compareTo(Node n){
            return 0;
        }

        public void incrementFrequency(){
            this.frequency++;
        }

        public Node getRightChild() {
            return rightChild;
        }

        public void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
        }

        public Node getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
        }
    }
}
