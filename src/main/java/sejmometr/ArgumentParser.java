package sejmometr;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Michał Zakrzewski on 2017-01-06.
 */
public class ArgumentParser {
    private int kadencja;
    private boolean[] runFunctions;
    private List<String > firstArgs;
    private List<String >secondArgs;

    public ArgumentParser(String args[]) throws IndexOutOfBoundsException {
        runFunctions = new boolean[7];
        firstArgs = new LinkedList<>();
        secondArgs = new LinkedList<>();
        for (int i = 0; i < runFunctions.length; i++) runFunctions[i] = false;
        kadencja = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-1")) {
                String firstArgs = "";
                try {
                    firstArgs = args[i + 1] + " " + args[i + 2];
                } catch (IndexOutOfBoundsException e) {
                    throw new IndexOutOfBoundsException("Brak Imienia i/lub Nazwiska dla Funkcji 1!");
                } finally {
                    this.firstArgs.add(firstArgs);
                }
                if (!firstArgs.equals("")) runFunctions[0] = true;
            }
            if (args[i].equals("-2")) {
                String secondArgs = "";
                try {
                    secondArgs = args[i + 1] + " " + args[i + 2];
                } catch (IndexOutOfBoundsException e) {
                    throw new IndexOutOfBoundsException("Brak Imienia i/lub Nazwiska dla Funkcji 2!");
                } finally {
                    this.secondArgs.add(secondArgs);
                }
                if (!secondArgs.equals("")) runFunctions[1] = true;
            }
            if (args[i].equals("-3")) runFunctions[2] = true;
            if (args[i].equals("-4")) runFunctions[3] = true;
            if (args[i].equals("-5")) runFunctions[4] = true;
            if (args[i].equals("-6")) runFunctions[5] = true;
            if (args[i].equals("-7")) runFunctions[6] = true;
            if (args[i].equals("-kadencja") && kadencja == 0) {
                try {
                    kadencja = Character.getNumericValue(args[i + 1].charAt(0));
                } catch (IndexOutOfBoundsException e) {
                    throw new IndexOutOfBoundsException("Brak Numeru Kadencji!");
                }
            }
        }
        if (kadencja == 0) throw new InvalidParameterException("Podaj Kadencję!");
        else if (kadencja != 7 && kadencja != 8) throw new InvalidParameterException("Dostępne Kadencje: 7, 8");
    }

    public int getKadencja() {
        return kadencja;
    }

    public List<String> getFirstArgs() {
        return firstArgs;
    }

    public List<String> getSecondArgs() {
        return secondArgs;
    }

    public boolean[] getRunFunctions() {
        return runFunctions;
    }
}
