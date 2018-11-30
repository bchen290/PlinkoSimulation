class PlinkoBoard {
    private char[][] board;

    static final int BOARD_HEIGHT = 13;
    static final int BOARD_WIDTH = 17;
    private static final int BOARD_SIDE = 1;
    private static final int PRINT_BOARD_WIDTH = BOARD_SIDE + BOARD_WIDTH + BOARD_SIDE;

    private StringBuilder boardBuilder;

    PlinkoBoard(){
        board = new char[BOARD_HEIGHT][BOARD_WIDTH];

        fillBoard();

        boardBuilder = new StringBuilder();
    }

    void putChipInSpot(PlinkoChip plinkoChip){
        fillBoard();

        board[plinkoChip.getRow()][plinkoChip.getColumn()] = 'X';
    }

    private void fillBoard(){
        emptyBoard();

        for(int i = 0; i < board.length; i++){
            fillRow(board[i], i % 2 == 0);
        }
    }

    private void emptyBoard(){
        for(int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private void fillRow(char[] row, boolean evenRow){
        int startingColumn = evenRow ? 1 : 0;
        for(int i = startingColumn; i < row.length; i += 2){
            row[i] = 'O';
        }
    }

    @Override
    public String toString(){
        boardBuilder.setLength(0);

        for(int i = 0; i < BOARD_SIDE; i++){
            boardBuilder.append('-');
        }

        for(int i = 0; i < DroppingLocation.values().length; i++){
            boardBuilder.append(DroppingLocation.values()[i]).append("-");
        }

        boardBuilder.deleteCharAt(boardBuilder.length() - 1);

        for(int i = 0; i < BOARD_SIDE; i++){
            boardBuilder.append('-');
        }

        boardBuilder.append('\n');

        for(char[] row : board) {
            boardBuilder.append("|");

            for (char spot : row) {
                boardBuilder.append(spot);
            }

            boardBuilder.append("|\n");
        }

        for(int i = 0; i < PRINT_BOARD_WIDTH; i++){
            boardBuilder.append('-');
        }

        return boardBuilder.toString();
    }
}
