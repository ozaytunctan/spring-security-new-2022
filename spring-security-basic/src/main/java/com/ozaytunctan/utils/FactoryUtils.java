package com.ozaytunctan.utils;

public final class FactoryUtils {



    private final static double SECONDS=1000.0d;
    private final static double MILLI_SECONDS = 1000000.0d;

    /**
     * System zamanını göstermektedir.
     *
     * @return
     */
    public static long tic() {
        long currentTime = System.nanoTime();
        return currentTime;
    }

    /**
     * Parametre tic ve o anki zaman farkını milisaniye cinsinden
     * göstermektedir.
     *
     * @param startedTime
     * @return
     */
    public static double toc(long startedTime) {
        return tocMilliseconds(startedTime);
    }

    public static double tocMilliseconds(long startedTime) {
        return toc(startedTime, MILLI_SECONDS);
    }

    public static double tocSeconds(long startedTime) {
        return toc(startedTime)/SECONDS;
    }

    public static double toc(long startedTime, double targetTime) {
        long toc = System.nanoTime();
        double elapsed = (toc - startedTime) / targetTime;
        return elapsed;
    }

    public static String tocString(long tic) {
        return String.format("Elapsed Time: %f miliSecond", toc(tic));
    }
}
