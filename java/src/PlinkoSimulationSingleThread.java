import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings({"BigDecimalMethodWithoutRoundingCalled", "SameParameterValue", "Duplicates"})
public class PlinkoSimulationSingleThread {
    private static PlinkoBoard plinkoBoard = new PlinkoBoard();
    private static PlinkoChip plinkoChip = new PlinkoChip();

    public static void main(String[] args) throws Exception{
        for (DroppingLocation droppingLocation : DroppingLocation.values()) {
            droppingLocation.resetNumTimesDroppedIn10000();
        }

        System.out.println("\nRunning for " + Constants.NUMBER_OF_TRIALS + " trials");

        long startTime = System.nanoTime();

        for(DroppingLocation droppingLocation : DroppingLocation.values()){
            for(int i = 0; i < Constants.NUMBER_OF_TRIALS; i++) {
                plinkoChip.setColumn(droppingLocation);
                plinkoChip.setRow(0);
                plinkoBoard.putChipInSpot(plinkoChip);

                simulateDrop(false);

                if(isWinner()){
                    droppingLocation.incrementNumTimesDroppedIn10000();
                }
            }
        }

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);

        printProbability();
        System.out.println("Single thread took: " + duration/1e+9 + " seconds");

    }

    private static void simulateDrop(boolean animate) throws Exception{
        for(int i = 0; i < PlinkoBoard.BOARD_HEIGHT - 1; i++){
            int randomNumber;

            if(plinkoChip.getColumn() == 0){
                randomNumber = 1;
            }else if(plinkoChip.getColumn() == PlinkoBoard.BOARD_WIDTH - 1){
                randomNumber = 0;
            }else{
                randomNumber = ThreadLocalRandom.current().nextInt(2);
            }

            plinkoChip.moveChipDownOneRow();
            plinkoChip.moveColumn(randomNumber == 1 ? 1 : -1);
            plinkoBoard.putChipInSpot(plinkoChip);

            if(animate) {
                System.out.println(plinkoBoard);
            }
        }
    }

    private static boolean isWinner(){
        return plinkoChip.getColumn() == Constants.WINNER_COLUMN && plinkoChip.getRow() == Constants.WINNER_ROW;
    }

    private static void printProbability(){
        for(DroppingLocation droppingLocation : DroppingLocation.values()){
            System.out.println("Probability of " + droppingLocation + " winning is: " + new BigDecimal(droppingLocation.getNumTimesDroppedIn10000()).divide(new BigDecimal(Constants.NUMBER_OF_TRIALS)));
        }
    }
}
