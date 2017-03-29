package model;

public class Move {

    private int rowOffset;
    private int columnOffset;
    private boolean continuous;

    /**
     * @param rowOffset Sets how far the piece can be moved vertically
     * @param columnOffset Sets how far the piece can be moved horizontally
     * @param continuous true if it forms a line movement as a vector. false if it "jumps"
     */
    public Move(int rowOffset, int columnOffset, boolean continuous){
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
        this.continuous = continuous;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public int getColumnOffset() {
        return columnOffset;
    }

    public boolean isContinuous() {
        return continuous;
    }
}
