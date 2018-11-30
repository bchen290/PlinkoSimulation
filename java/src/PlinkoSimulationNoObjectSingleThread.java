import java.math.BigDecimal;
import java.util.SplittableRandom;

@SuppressWarnings({"Duplicates", "BigDecimalMethodWithoutRoundingCalled"})
public class PlinkoSimulationNoObjectSingleThread {

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

        SplittableRandom random = new SplittableRandom();

        int chipRow = 0;
        int chipColumn = 0;
        int randomNumber = 0;

        long startTime = System.nanoTime();

        for(int i = 0; i < NUMBER_OF_LOCATIONS; i++){
            for(int j = 0; j < NUMBER_OF_TRIALS; j++){
                chipRow = 0;
                chipColumn = i * 2;

                for(int k = 0; k < BOARD_HEIGHT; k++){
                    if(chipColumn == 0){
                        randomNumber = 1;
                    }else if(chipColumn == BOARD_WIDTH){
                        randomNumber = 0;
                    }else{
                        randomNumber = random.nextInt(2);
                    }

                    chipRow += 1;
                    chipColumn += randomNumber == 1 ? 1 : -1;
                }

                if(chipRow == WINNER_ROW && chipColumn == WINNER_COLUMN){
                    locationWins[i]++;
                }
            }
        }

        long endTime = System.nanoTime();

        for(int i = 0; i < NUMBER_OF_LOCATIONS; i++){
            System.out.println("Probability of " + locations[i] + " winning is " + new BigDecimal(locationWins[i]).divide(new BigDecimal(NUMBER_OF_TRIALS)));
        }

        System.out.println("Single thread took " + new BigDecimal(endTime - startTime).divide(new BigDecimal(1e+9)) + " seconds");
    }
}
