package telran.multithreading;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class Racer extends Thread{
    private Race race;
    private int number;
    private static AtomicLong globalCounter = new AtomicLong();
    private static AtomicLong winnerCounter = new AtomicLong(-1);
    public Racer(Race race, int number) {
        this.race = race;
        this.number = number;
    }
    @Override
    public void run(){
        //Running cycle containing number of iterations from the Race reference as the distance
        //Each iteration is printing out the number of the thread for game tracing to see game dynamics
        for (int i = 0; i < race.getDistance(); i++) {
            globalCounter.incrementAndGet();
            System.out.printf("Racer %d is on iteration %d%n", number, i + 1);

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(race.getMinSleepTimeout(), race.getMaxSleepTimeout()));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (winnerCounter.compareAndSet(-1, number)) {
            System.out.printf("Racer %d - Winner!%n", number);
        }

    }
    public static long getGlobalCounter() {
        return globalCounter.get();
    }

    public static long getWinnerCounter() {
        return winnerCounter.get();
    }
    public static void reset() {
        globalCounter.set(0);
        winnerCounter.set(-1);
    }
}