import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("Duplicates")
public class PlinkoSimulationNoObjectMultiThread {

    private static final int NUMBER_OF_TRIALS = Constants.NUMBER_OF_TRIALS;
    private static final int NUMBER_OF_LOCATIONS = 9;

    private static final int BOARD_HEIGHT = 12;
    private static final int BOARD_WIDTH = 16;

    private static final int WINNER_COLUMN = 8;
    private static final int WINNER_ROW = 12;

    public static void main(String[] args){
        System.out.println("\nRunning for " + NUMBER_OF_TRIALS + " trials");

        char[] locations = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        int[] locationWins = {0, 0, 0, 0, 0, 0, 0, 0, 0};

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_LOCATIONS);

        long startTime = System.nanoTime();

        for(int i = 0; i < NUMBER_OF_LOCATIONS; i++){
            int locationNumber = i;

            executorService.execute(() -> {
                int chipRow, chipColumn, randomNumber;

                for(int j = 0; j < NUMBER_OF_TRIALS; j++){
                    chipRow = 0;
                    chipColumn = locationNumber * 2;

                    for(int k = 0; k < BOARD_HEIGHT; k++){
                        if(chipColumn == 0){
                            randomNumber = 1;
                        }else if(chipColumn == BOARD_WIDTH){
                            randomNumber = 0;
                        }else{
                            randomNumber = ThreadLocalRandom.current().nextInt(2);
                        }

                        chipRow += 1;
                        chipColumn += randomNumber == 1 ? 1 : -1;
                    }

                    if(chipRow == WINNER_ROW && chipColumn == WINNER_COLUMN){
                        locationWins[locationNumber]++;
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

        for(int i = 0; i < NUMBER_OF_LOCATIONS; i++){
            System.out.println("Probability of " + locations[i] + " winning is " + new BigDecimal(locationWins[i]).divide(new BigDecimal(NUMBER_OF_TRIALS)));
        }

        System.out.println("Single thread took " + new BigDecimal(endTime - startTime).divide(new BigDecimal(1e+9)) + " seconds");
    }
}
