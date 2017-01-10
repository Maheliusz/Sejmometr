package sejmometr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michał Zakrzewski on 2017-01-09.
 */
public class FunctionExecutor {
    private Sejmometr sejmometr;
    private ArgumentParser argumentParser;

    public FunctionExecutor(Sejmometr sejmometr, ArgumentParser argumentParser) {
        this.sejmometr = sejmometr;
        this.argumentParser = argumentParser;
    }

    public void execute() throws JSONException {
        boolean[] runFunctions = argumentParser.getRunFunctions();
        if(runFunctions[0]) functionOne();
        if(runFunctions[1]) functionTwo();
        if(runFunctions[2]) functionThree();
        if(runFunctions[3]) functionFour();
        if(runFunctions[4]) functionFive();
        if(runFunctions[5]) functionSix();
        if(runFunctions[6]) functionSeven();
    }

    private double sumOfExpenses(JSONObject jsonObject) throws JSONException {
        double res = 0;
        JSONArray roczniki = (jsonObject.getJSONObject("layers").getJSONObject("wydatki")).getJSONArray("roczniki");
        for (int i = 0; i < roczniki.length(); i++) {
            JSONArray pola = roczniki.getJSONObject(i).getJSONArray("pola");
            for (int j = 0; j < pola.length(); j++) {
                res += pola.getDouble(j);
            }
        }
        return res;
    }

    public void functionOne() throws JSONException {
        double res = 0;
        String name = argumentParser.getFirstArgs();
        JSONObject jsonObject = sejmometr.getPoselbyName(name);
        if (jsonObject == null) return;
        res = sumOfExpenses(jsonObject);
        System.out.println("Suma wydatkow " + name + ": " + res);
    }

    public void functionTwo() {

    }

    public void functionThree() throws JSONException {
        double res = 0;
        for (int i = 0; i < sejmometr.getCount(); i++) {
            res += sumOfExpenses(sejmometr.getPosel(i));
        }
        res /= sejmometr.getCount();
        System.out.println("Srednia suma wydatkow wszystkich poslow: " + res);
    }

    public void functionFour() throws JSONException {
        int max = 0;
        JSONObject maxPosel = new JSONObject();
        for (int i = 0; i < sejmometr.getCount(); i++) {
            if(sejmometr.getPosel(i).getJSONObject("layers").get("wyjazdy") instanceof JSONObject) continue;
            if (sejmometr.getPosel(i).getJSONObject("layers").getJSONArray("wyjazdy").length() > max) {
                maxPosel = sejmometr.getPosel(i);
                max = sejmometr.getPosel(i).getJSONObject("layers").getJSONArray("wyjazdy").length();
            }
        }
        System.out.println("Najwiecej podrozy zagranicznych wykonal(a): "
                + maxPosel.getJSONObject("data").getString("ludzie.nazwa"));
    }

    public void functionFive() throws JSONException {
        int max = 0;
        JSONObject maxPosel = new JSONObject();
        for (int i = 0; i < sejmometr.getCount(); i++) {
            int count = 0;
            if(sejmometr.getPosel(i).getJSONObject("layers").get("wyjazdy") instanceof JSONObject) continue;
            JSONArray wyjazdy = sejmometr.getPosel(i).getJSONObject("layers").getJSONArray("wyjazdy");
            for (int j = 0; j < wyjazdy.length(); j++) {
                count += wyjazdy.getJSONObject(j).getInt("liczba_dni");
            }
            if (count > max) {
                max = count;
                maxPosel = sejmometr.getPosel(i);
            }
        }
        System.out.println("Najwiecej za granica przebywal(a): "
                + maxPosel.getJSONObject("data").getString("ludzie.nazwa"));
    }

    public void functionSix() throws JSONException {
        double max = 0;
        JSONObject maxPosel = new JSONObject();
        for (int i = 0; i < sejmometr.getCount(); i++) {
            double count = 0;
            if(sejmometr.getPosel(i).getJSONObject("layers").get("wyjazdy") instanceof JSONObject) continue;
            JSONArray wyjazdy = sejmometr.getPosel(i).getJSONObject("layers").getJSONArray("wyjazdy");
            for (int j = 0; j < wyjazdy.length(); j++) {
                count = wyjazdy.getJSONObject(j).getInt("koszt_suma");
                if (count > max) {
                    max = count;
                    maxPosel = sejmometr.getPosel(i);
                }
            }
        }
        System.out.println("Najdrozszy wyjazd odbyl(a): "
                + maxPosel.getJSONObject("data").getString("ludzie.nazwa"));
    }

    public void functionSeven() throws JSONException {
        JSONArray res = new JSONArray();
        for(int i=0; i<sejmometr.getCount(); i++){
            if(sejmometr.getPosel(i).getJSONObject("layers").get("wyjazdy") instanceof JSONObject) continue;
            JSONArray wyjazdy = sejmometr.getPosel(i).getJSONObject("layers").getJSONArray("wyjazdy");
            for(int j=0; j<wyjazdy.length(); j++){
                if(wyjazdy.getJSONObject(j).getString("kraj").equals("Włochy")){
                    res.put(sejmometr.getPosel(i));
                    break;
                }
            }
        }
    }

}