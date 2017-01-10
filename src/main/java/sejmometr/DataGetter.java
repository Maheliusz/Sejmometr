package sejmometr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Michał Zakrzewski on 2017-01-06.
 */
public class DataGetter {
    public List<Integer> getIDsFromUrl(String url) throws JSONException {
        List<Integer> res = new LinkedList<>();
        JSONArray dataObject = new JSONArray();
        try {
            JSONObject json = JSONReader.readJsonFromUrl(url);
            //res = json.getJSONObject("data");
            dataObject = json.getJSONArray("Dataobject");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        for (int n = 0; n < dataObject.length(); n++) {
            JSONObject object = dataObject.getJSONObject(n);
            int id = object.getInt("id");
            res.add(id);
            System.out.print("\rID " + id);
        }
        return res;
    }

    public JSONObject getJSON(int id) {
        JSONObject res = new JSONObject();
        String url = "https://api-v3.mojepanstwo.pl/dane/poslowie/" + id + ".json?layers[]=wyjazdy&layers[]=wydatki";
        try {
            JSONObject json = JSONReader.readJsonFromUrl(url);
            res.put("data", json.getJSONObject("data"));
            res.put("layers", json.getJSONObject("layers"));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    public int getCount(String url) {
        int res = 0;
        try {
            JSONObject json = JSONReader.readJsonFromUrl(url);
            res = json.getInt("Count");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
}
