public class PlinkoSimulationComparison {
    public static void main(String[] args) throws Exception{
        PlinkoSimulationSingleThread.main(args);
        PlinkoSimulationMultiThread.main(args);
        PlinkoSimulationNoObjectSingleThread.main(args);
        PlinkoSimulationNoObjectMultiThread.main(args);
    }
}
