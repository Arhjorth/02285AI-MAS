package searchclient;

public final class StaticLevel {
    private static StaticLevel instance = null;
    private int maxCol;
    private int maxRow;

    private StaticLevel(int maxRow, int maxCol) {
        this.maxCol=maxCol;
        this.maxRow=maxRow;
    }

    public static StaticLevel getInstance() {
        return instance;
    }

    public static StaticLevel createInstance(int maxRow, int maxCol){
        if (instance == null){
            instance = new StaticLevel(maxRow, maxCol);
        }
        return instance;
    }

    public int getMaxCol() {
        return maxCol;
    }

    public int getMaxRow() {
        return maxRow;
    }
}
