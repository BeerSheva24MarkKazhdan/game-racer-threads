package telran.multithreading;

public class Race {
//Fields and methods for Race parameters
// min_sleep_timeout, max_sleep_time for getting some random sleep value in each iteration as random factor for racer-winner definition
//distance - number of iterations
//any others possible fields
private final int distance;
    private final int minSleepTimeout;
    private final int maxSleepTimeout;

    public Race(int distance, int minSleepTimeout, int maxSleepTimeout) {
        this.distance = distance;
        this.minSleepTimeout = minSleepTimeout;
        this.maxSleepTimeout = maxSleepTimeout;
    }

    public int getDistance() {
        return distance;
    }

    public int getMinSleepTimeout() {
        return minSleepTimeout;
    }

    public int getMaxSleepTimeout() {
        return maxSleepTimeout;
    }

}