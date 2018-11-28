enum DroppingLocation {
    A(0), B(2), C(4), D(6), E(8), F(10), G(12), H(14), I(16);

    private int column;
    private int numTimesDroppedIn10000;

    DroppingLocation(int column){
        this.column = column;
        this.numTimesDroppedIn10000 = 0;
    }

    public int getColumn() {
        return column;
    }

    public void incrementNumTimesDroppedIn10000() {
        this.numTimesDroppedIn10000++;
    }

    public int getNumTimesDroppedIn10000(){
        return numTimesDroppedIn10000;
    }
}
