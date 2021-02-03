package Algorithm;

public abstract class AbstractAlgorithm {
    public AbstractAlgorithm(){}

    public abstract byte[] compress(byte[] data);
    public abstract byte[] decompress(byte[] data);
}
