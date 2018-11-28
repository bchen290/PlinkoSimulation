import java.math.BigDecimal;

@SuppressWarnings({"BigDecimalMethodWithoutRoundingCalled", "SameParameterValue"})
public class PlinkoSimulation {
    private static PlinkoBoard plinkoBoard = new PlinkoBoard();
    private static PlinkoChip plinkoChip = new PlinkoChip();

    private static final int WINNER_COLUMN = 8;
    private static final int WINNER_ROW = 12;

    private static final int NUMBER_OF_TRIALS = 1000;

    public static void main(String[] args) throws Exception{
        for(DroppingLocation droppingLocation : DroppingLocation.values()){
            for(int i = 0; i < NUMBER_OF_TRIALS; i++) {
                plinkoChip.setColumn(droppingLocation);
                plinkoChip.setRow(0);
                plinkoBoard.putChipInSpot(plinkoChip);

                simulateDrop(false);

                if(isWinner()){
                    droppingLocation.incrementNumTimesDroppedIn10000();
                }
            }
        }

        printProbability();
    }

    private static void simulateDrop(boolean animate) throws Exception{
        for(int i = 0; i < PlinkoBoard.BOARD_HEIGHT - 1; i++){
            double randomNumber;

            if(plinkoChip.getColumn() == 0){
                randomNumber = 1;
            }else if(plinkoChip.getColumn() == PlinkoBoard.BOARD_WIDTH - 1){
                randomNumber = 0;
            }else{
                randomNumber = Math.random();
            }

            plinkoChip.moveChipDownOneRow();
            plinkoChip.moveColumn(randomNumber > 0.5 ? 1 : -1);
            plinkoBoard.putChipInSpot(plinkoChip);

            if(animate) {
                System.out.println(plinkoBoard);
            }
        }
    }

    private static boolean isWinner(){
        return plinkoChip.getColumn() == WINNER_COLUMN && plinkoChip.getRow() == WINNER_ROW;
    }

    private static void printProbability(){
        for(DroppingLocation droppingLocation : DroppingLocation.values()){
            BigDecimal bigDecimal = new BigDecimal(droppingLocation.getNumTimesDroppedIn10000());
            System.out.println("Probability of " + droppingLocation + " winning is: " + bigDecimal.divide(new BigDecimal(NUMBER_OF_TRIALS)));
        }
    }
}
