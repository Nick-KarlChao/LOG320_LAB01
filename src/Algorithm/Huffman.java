package Algorithm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class containing Huffman algorithm. Class extends from AbstractAlgorithm.
 * Written by Nick-Karl Chao and Ai-Vi Nguyen.
 */
public class Huffman extends AbstractAlgorithm {
    private static final int FREQU_CONSTANT = 48;
    private static final int HEADER_LENGTH = 5;
    private static final int BYTE_LENGTH = 8;
    private ArrayList<Node> frequencyTable;
    private ArrayList<Node> huffmanTree;

    public Huffman(){
        System.out.println("You have chosen Huffman!");
        frequencyTable = new ArrayList<>();
        huffmanTree = new ArrayList<>();
    }

    /**
     * This functions call upon all required subsequent functions and methods
     * necessary to compress a byte array using the Huffman algorithm. After
     * creating a frequency table based on the input data bytes, it creates a
     * tree of nodes that will be use to encode the different bytes.
     * @param data the byte array extracted from the file.
     * @return encodedData in the form of a byte array
     * @throws IOException
     */
    @Override
    public byte[] compress(byte[] data) throws IOException {
        System.out.println("You have chosen to compress with Huffman!");
        createFrequencyTable(data);
        //createHeader();
        createHuffmanTree();
        encodeNodes(huffmanTree.get(0), "");

        ByteArrayOutputStream encodedData = new ByteArrayOutputStream();
        encodedData.write(createHeader());
        for(byte d: data){
            for (int i = 0; i < frequencyTable.size(); i++){
                if (d == (byte) frequencyTable.get(i).getValue()){
                    encodedData.write(frequencyTable.get(i).getCode().getBytes());
                    continue;
                }
            }
        }
        //System.out.println("Encoded data: " + encodedData);

        return encodedData.toByteArray();
    }

    /**
     * This method creates a frequency table based on the how many times a byte is repeated.
     * The table is then ordered in order of frequency before being transformed into a tree.
     * Each table contains Nodes (see internal class).
     * @param data the byte array extracted from the file.
     */
    private void createFrequencyTable(byte[] data){
        frequencyTable.clear();
        Node tempNode = new Node();

        for (int i = 0; i < data.length; i++){
            int c = data[i];
            tempNode.setValue(data[i]);
            //if (frequencyTable.contains(node with specific value){
            if (frequencyTable.stream().anyMatch(o -> o.getValue() == tempNode.getValue())){
                //increment frequency of the specific node
                frequencyTable.stream().filter(o -> o.getValue() == tempNode.getValue()).findFirst().get().incrementFrequency();
            } else {
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
    }

    /**
     * This function creates a header that encapsulates the frequency table.
     * It contains a specific separator that will be detected at a later time
     * so that the a new frequency table can be recreated in order to decompress
     * a byte array that was compressed with this specific algorithm.
     * @return header in the form of an Array.
     * @throws IOException
     */
    private byte[] createHeader() throws IOException {
        ByteArrayOutputStream header = new ByteArrayOutputStream();
        String separator = "\\||//";
        header.write(separator.getBytes());

        for (Node n: frequencyTable){
            header.write(n.nodeAttributes());
            System.out.println(header.toString());
        }
        header.write(separator.getBytes());
        System.out.println("HEADER: " + header.toString());

        return header.toByteArray();
    }

    /**
     * This method creates a tree using the duplicate of the frequency table.
     * It uses 2 nodes at a time creating a new node to join both, now his children.
     * The nodes will be sequentially removed from the table until there remains just
     * one new Node, which will be the root node.
     */
    private void createHuffmanTree(){
        huffmanTree = new ArrayList<>(frequencyTable);
        while (huffmanTree.size() > 1){
            Node newNode = new Node();
            newNode.setFrequency(huffmanTree.get(0).getFrequency()+huffmanTree.get(1).getFrequency());
            huffmanTree.get(0).setLeftChildStatus(true);
            newNode.setLeftChild(huffmanTree.get(0));
            newNode.setRightChild(huffmanTree.get(1));
            System.out.println("L child frequency: " + huffmanTree.get(0).getFrequency());
            System.out.println("R child frequency: " + huffmanTree.get(1).getFrequency());
            huffmanTree.remove(0);
            huffmanTree.remove(0);
            huffmanTree.add(newNode);

            if(huffmanTree.indexOf(newNode)>0){
                while (newNode.getFrequency() < huffmanTree.get((huffmanTree.indexOf(newNode))-1).getFrequency()){
                    Collections.swap(huffmanTree, huffmanTree.indexOf(newNode), huffmanTree.indexOf(newNode)-1);
                }
            }
            System.out.println("table size: " + huffmanTree.size());
        }
        System.out.println("final node frequency: " + huffmanTree.get(0).getFrequency());
    }

    /**
     * This recursive method will explore the trees in order to get a code for each leaf.
     * As it traverses the tree node by node, it adds either a 0 or a 1 to the
     * code. Once it reaches a leaf, it will append that code to that Node.
     * @param currentNode starts with the root node, and changes to travel the tree
     * @param code the code associated with each node that will be used to encode
     */
    private void encodeNodes(Node currentNode,  String code){
        if (currentNode == null){
            return;
        }

        if(currentNode.isLeftChild){
            code = code + "0";
        }

        if(!currentNode.isLeftChild){
            code = code + "1";
        }

        if (currentNode.getLeftChild() == null && currentNode.getRightChild() == null){
            currentNode.setCode(code);
            System.out.println(code);
        } else {
            encodeNodes(currentNode.getLeftChild(), code);
            encodeNodes(currentNode.getRightChild(), code);
        }
    }

    /**
     * This function explores the beginning of a the data array to detect
     * the position where the second separator starts. This is needed in order
     * to separate the actual compressed data from the frequency table that's
     * encoded at the beginning of the compressed bytes.
     * @param data the byte array extracted from the file.
     * @return headerEnd index where the second
     */
    private int headerEndPosition(byte[] data){
        int index = HEADER_LENGTH;
        int headerEnd = -1;

        while (!reachedEndHeader(index, data)){
            headerEnd++;
        }

        return headerEnd + index;
    }

    /**
     * This function detects when the second separator is reached.
     * @param index current position in the data array
     * @param data the byte array extracted from the file.
     * @return true when index reaches the end of the header
     */
    private boolean reachedEndHeader (int index, byte[] data){

        if (data[index] == '\\'){
            index++;
            if(data[index] == '|'){
                index++;
                if( data[index] == '|'){
                    index++;
                    if (data[index] == '/'){
                        index++;
                        if (data[index] == '/'){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method will rebuild the frequency table by extracting the data
     * in the header of the input data array. It will create new Nodes using
     * the first byte as the value and the second byte as the frequency.
     * @param data input data array
     */
    private void rebuildFrequencyTable(byte[] data){
        int index = HEADER_LENGTH;
        frequencyTable.clear();

        while (index != headerEndPosition(data)) {
            Node tableElement = new Node();
            tableElement.setValue(data[index]);
            tableElement.setFrequency(data[index + 1]);
            frequencyTable.add(tableElement);
            index += 2;
        }
    }

    /**
     * This function decompresses the input data array by calling all necessary methods.
     * It then reverses the compression method by comparing the input data bytes to the new
     * frequency table and extracting the value from the code.
     * @param data input data array extracted from file
     * @return decodedData in the form of an array
     */
    @Override
    public byte[] decompress(byte[] data){
        System.out.println("You have chosen to compress with Huffman!");
        ByteArrayOutputStream decodedData = new ByteArrayOutputStream();
        int dataIndex = headerEndPosition(data);

        rebuildFrequencyTable(data);
        createHuffmanTree();

        for(int i = dataIndex; i < data.length - dataIndex; i++){
            for (int j = 0; j < frequencyTable.size(); j++){
                if (String.valueOf(data[dataIndex]) == frequencyTable.get(j).getCode().getBytes().toString()){
                    decodedData.write(frequencyTable.get(j).getValue());
                }
            }
        }

        return decodedData.toByteArray();
    }

    /**
     * This internal class contains attribtues for the a Node. This Node type
     * is used to create 2 arrayLists.
     */
    public static class Node implements Comparable<Node>{
        private int value;
        private int frequency;
        private Node leftChild;
        private Node rightChild;
        private String code;
        private boolean isLeftChild;

        Node(){
            this.frequency = 0;
            leftChild = null;
            rightChild = null;
            isLeftChild = false;
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

        public void setCode(String code){this.code = code; }

        public String getCode(){return this.code;}

        public void setLeftChildStatus(boolean status){
            this.isLeftChild = status;
        }

        public boolean isLeftChild(){
            return  isLeftChild;
        }

        public byte[] nodeAttributes(){
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            output.write(value);
            output.write(frequency + FREQU_CONSTANT);
            return output.toByteArray();
        }
    }
}
