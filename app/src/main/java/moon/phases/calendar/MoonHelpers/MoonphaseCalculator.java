package moon.phases.calendar.MoonHelpers;


public class MoonphaseCalculator {
    static {
        System.loadLibrary("moonphase");
    }

    public int[] lunations = new int[42];
    public double[] phases = new double[42];

    public static native int getSizeOfTimet();


    public native void calcNative(int i, int i2, double[] dArr, int[] iArr);

    public void calc(int i, int i2) {
        calcNative(i, i2, this.phases, this.lunations);
    }

}
