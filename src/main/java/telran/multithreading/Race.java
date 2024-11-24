package telran.multithreading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Race {
    private int distance;
    private int minSleep;
    private int maxSleep;
    private final List<Racer> results = Collections.synchronizedList(new ArrayList<>());
    AtomicInteger winner = new AtomicInteger(-1);
    public Race(int distance, int minSleep, int maxSleep) {
        this.distance = distance;
        this.minSleep = minSleep;
        this.maxSleep = maxSleep;
    }
    public int getWinner() {
        return winner.get();
    }

    public int getDistance() {
        return distance;
    }
    public int getMinSleep() {
        return minSleep;
    }
    public int getMaxSleep() {
        return maxSleep;
    }
    public synchronized void recordResult(Racer racer) {
        if (!results.contains(racer)) {
            results.add(racer);
        }
    }

    public synchronized List<Racer> getResults() {
        return new ArrayList<>(results);
    }
}