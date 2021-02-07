package Algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class Huffman extends AbstractAlgorithm {
    private ArrayList<Node> frequencyTable;
    private String testString = "accacbcc";


    public Huffman(){
        System.out.println("You have chosen Huffman!");
        //Node poop = new Node();
        frequencyTable = new ArrayList<>();
        frequencyTable = createFrequencyTable();
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

    private void createHuffmanTable(){
        //TODO
    };

    private void encode(){
        //TODO
    };

    public static class Node implements Comparable<Node>{
        private int value;
        private int frequency;
        private static Node instance;

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

        public static Node getInstance() {
            if (instance == null) {
                instance = new Node();
            }
            return instance;
        }
    }
}
