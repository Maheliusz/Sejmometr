package sejmometr;

import org.json.JSONException;

/**
 * Created by Michał Zakrzewski on 2016-12-20.
 */
public class Main {
    public static void main(String args[]) throws JSONException {
        ArgumentParser argumentParser = new ArgumentParser(args);
        Sejmometr sejmometr = new Sejmometr(argumentParser.getKadencja());
        sejmometr.setPoslowie();
        FunctionExecutor functionExecutor = new FunctionExecutor(sejmometr, argumentParser);
        functionExecutor.execute();


        /*JSONObject jsonObject = sejmometr.getPosel(512);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonObject.toString());
        System.out.print(gson.toJson(je));
        */
    }
}
