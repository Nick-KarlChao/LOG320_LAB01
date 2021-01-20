import Algorithm.AbstractAlgorithm;

public class AlgorithmFactory {
    AbstractAlgorithm algorithm;
    public AlgorithmFactory (String chosenAlgorithm){
        createNewAlgorithm(chosenAlgorithm);
    }

    private AbstractAlgorithm createNewAlgorithm(String chosenAlgorithm){
        //TODO : implement switch case
        return null;
    }

    public AbstractAlgorithm getAlgorithm(){
        return this.algorithm;
    }
}
