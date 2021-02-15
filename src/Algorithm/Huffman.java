package Algorithm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

//TODO
public class Huffman extends AbstractAlgorithm {
    private static final int FREQU_CONSTANT = 48;
    private static final int HEADER_LENGTH = 5;
    private ArrayList<Node> frequencyTable;
    private ArrayList<Node> huffmanTree;

    public Huffman(){
        System.out.println("You have chosen Huffman!");
        frequencyTable = new ArrayList<>();
        huffmanTree = new ArrayList<>();
    }

    @Override
    public byte[] compress(byte[] data) throws IOException {
        System.out.println("You have chosen to compress with Huffman!");
        createFrequencyTable(data);
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
        System.out.println("Encoded data: " + encodedData);
        return encodedData.toByteArray();
    }

    private void createFrequencyTable(byte[] data){
        frequencyTable.clear();
        Node tempNode = new Node();

        for (int i = 0; i < data.length; i++){
            int c = data[i];
            tempNode.setValue(data[i]);
            //if (frequencyTable.contains(node with specific value)
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
     * Removes header and returns the frequency table detected
     */
    private int headerEndPosition(byte[] data){
        int index = HEADER_LENGTH;
        int headerEnd = -1;

        while (!reachedEndHeader(index, data)){
            headerEnd++;
        }

        return headerEnd;
    }

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

    @Override
    public byte[] decompress(byte[] data){
        System.out.println("You have chosen to compress with Huffman!");
        ByteArrayOutputStream decodedData = new ByteArrayOutputStream();
        int dataIndex = headerEndPosition(data) + HEADER_LENGTH;

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

        public byte[] nodeAttributes(){
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            output.write(value);
            output.write(frequency + FREQU_CONSTANT);
            return output.toByteArray();
        }
    }
}
