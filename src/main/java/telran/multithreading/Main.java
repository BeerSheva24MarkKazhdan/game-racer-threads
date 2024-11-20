package telran.multithreading;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandardInputOutput;

import java.util.Arrays;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        //Runs CLI game's menu with allowing the user to enter number of racers and a distance (number of iterations)
        //Creates Race
        //Runs Racers (Threads)
        //Prints out the Racer-winner number
        InputOutput io = new StandardInputOutput();
        io.writeLine("Welcome to the Racer Game!");

        int numRacers = readValidatedInt(io, "Enter the number of racers (not less than 2): ",
                "Should be at least 2 racers!", value -> value >= 2);
        int distance = readValidatedInt(io, "Enter the race distance (number of iterations, not less than 1): ",
                "Should be at least 1 distance!", value -> value >= 1);
        int minSleepTimeout = readValidatedInt(io, "Enter the minimum sleep timeout (ms, at least 1): ",
                "Should be at least 1 ms!", value -> value >= 1);
        int maxSleepTimeout = readValidatedInt(io, "Enter the maximum sleep timeout (ms, greater than or equal to minimum): ",
                "Should be greater than or equal to the minimum sleep timeout!", value -> value >= minSleepTimeout);


        Race race = new Race(distance, minSleepTimeout, maxSleepTimeout);

        Item[] items = getMenuItems(race, numRacers);

        items = addExitItem(items);

        Menu menu = new Menu("Racer Game Menu", items);
        menu.perform(io);

        io.writeLine("Application is finished");
    }

    private static Item[] getMenuItems(Race race, int numRacers) {
        return new Item[]{
                Item.of("Start Race", io -> startRace(io, race, numRacers), false)
        };
    }

    private static void startRace(InputOutput io, Race race, int numRacers) {
        Racer.reset();
        Racer[] racers = new Racer[numRacers];
        for (int i = 0; i < numRacers; i++) {
            racers[i] = new Racer(race, i + 1);
            racers[i].start();
        }

        for (Racer racer : racers) {
            try {
                racer.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        io.writeLine("Race finished!");
        io.writeLine("Total iterations performed: " + Racer.getGlobalCounter());
        io.writeLine("Winner is Racer " + Racer.getWinnerCounter());
    }

    private static Item[] addExitItem(Item[] items) {
        Item[] res = Arrays.copyOf(items, items.length + 1);
        res[items.length] = Item.of("Exit", io -> io.writeLine("Exiting the application..."), true);
        return res;
    }
    private static int readValidatedInt(InputOutput io, String prompt, String errorPrompt, Predicate<Integer> validator) {
        int result;
        while (true) {
            result = io.readInt(prompt, errorPrompt);
            if (validator.test(result)) {
                break;
            }
            io.writeLine(errorPrompt);
        }
        return result;
    }
}