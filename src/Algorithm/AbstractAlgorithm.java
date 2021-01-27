package Algorithm;

public abstract class AbstractAlgorithm {
    public AbstractAlgorithm(){}

    public abstract byte[] compress(byte[] data);
    public abstract void decompress();
}
