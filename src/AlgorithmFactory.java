import Algorithm.*;

public class AlgorithmFactory {
    AbstractAlgorithm algorithm;
    public AlgorithmFactory (String chosenAlgorithm){
        createNewAlgorithm(chosenAlgorithm);
    }

    private AbstractAlgorithm createNewAlgorithm(String chosenAlgorithm){
        switch(chosenAlgorithm) {
            case "-huff":
                algorithm = new Huffman();
                break;
            case "-lzw":
                algorithm = new LZW();
                break;
            case "-opt":
                algorithm = new Optimized();
                break;
            default:
                System.out.println("That wasn't an algorithm!");
        }
        return null;
    }

    public AbstractAlgorithm getAlgorithm(){
        return this.algorithm;
    }
}
