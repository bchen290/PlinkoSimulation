class PlinkoChip {
    private int column;
    private int row;

    PlinkoChip(){
        column = 0;
        row = 0;
    }

    int getColumn() {
        return column;
    }

    int getRow() {
        return row;
    }

    void setColumn(DroppingLocation droppingLocation){
        this.column = droppingLocation.getColumn();
    }

    void setRow(int row){
        this.row = row;
    }

    void moveChipDownOneRow() throws Exception {
        if(this.row + 1 > PlinkoBoard.BOARD_HEIGHT){
            throw new Exception("Row out of bound");
        }else {
            this.row++;
        }
    }

    void moveColumn(int columnOffset) throws Exception {
        if(this.column + columnOffset > PlinkoBoard.BOARD_WIDTH){
            throw new Exception("Column out of bound");
        }else {
            this.column += columnOffset;
        }
    }

    @Override
    public String toString() {
        return "Row: " + row + "\nColumn: " + column;
    }
}
