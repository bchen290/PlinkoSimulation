import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"BigDecimalMethodWithoutRoundingCalled", "SameParameterValue", "Duplicates"})
public class PlinkoSimulationMultiThread {
    private static final int NUMBER_OF_THREADS = DroppingLocation.values().length;

    public static void main(String[] args){
        for (DroppingLocation droppingLocation : DroppingLocation.values()) {
            droppingLocation.resetNumTimesDroppedIn10000();
        }

        System.out.println("\nRunning for " + Constants.NUMBER_OF_TRIALS + " trials");

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        long startTime = System.nanoTime();

        for(DroppingLocation droppingLocation : DroppingLocation.values()){
            executorService.execute(() -> {
                PlinkoChip plinkoChip = new PlinkoChip();
                PlinkoBoard plinkoBoard = new PlinkoBoard();

                for(int j = 0; j < Constants.NUMBER_OF_TRIALS; j++) {
                    plinkoChip.setColumn(droppingLocation);
                    plinkoChip.setRow(0);
                    plinkoBoard.putChipInSpot(plinkoChip);

                    for(int i = 0; i < PlinkoBoard.BOARD_HEIGHT - 1; i++){
                        double randomNumber;

                        if(plinkoChip.getColumn() == 0){
                            randomNumber = 1;
                        }else if(plinkoChip.getColumn() == PlinkoBoard.BOARD_WIDTH - 1){
                            randomNumber = 0;
                        }else{
                            randomNumber = ThreadLocalRandom.current().nextDouble();
                        }

                        try {
                            plinkoChip.moveChipDownOneRow();
                            plinkoChip.moveColumn(randomNumber > 0.5 ? 1 : -1);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        plinkoBoard.putChipInSpot(plinkoChip);
                    }

                    if(plinkoChip.getColumn() == Constants.WINNER_COLUMN && plinkoChip.getRow() == Constants.WINNER_ROW){
                        droppingLocation.incrementNumTimesDroppedIn10000();
                    }
                }
            });
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);

        System.out.println();
        printProbability();
        System.out.println("Multi thread took: " + duration/1e+9 + " seconds");
    }

    private static void printProbability(){
        for(DroppingLocation droppingLocation : DroppingLocation.values()){
            System.out.println("Probability of " + droppingLocation + " winning is: " + new BigDecimal(droppingLocation.getNumTimesDroppedIn10000()).divide(new BigDecimal(Constants.NUMBER_OF_TRIALS)));
        }
    }
}
