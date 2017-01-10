package sejmometr;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Michał Zakrzewski on 2017-01-06.
 */
public class Sejmometr {
    private DataGetter dataGetter;
    private List<Integer> ids;
    private List<JSONObject> poslowie;
    private int count;
    private int kadencja;
    public  Sejmometr(int kadencja){
        dataGetter = new DataGetter();
        poslowie = new LinkedList<>();
        ids = new LinkedList<>();
        this.kadencja=kadencja;
        count=dataGetter.getCount
                ("https://api-v3.mojepanstwo.pl/dane/poslowie.json?_type=objects&conditions[poslowie.kadencja]="+kadencja);
    }
    public void setIDs() throws JSONException {
        String url = "https://api-v3.mojepanstwo.pl/dane/poslowie.json?_type=objects&conditions[poslowie.kadencja]="+kadencja+"&page=";
        int max=0;
        if(kadencja==7) max=11;
        else max=10;
        for(int i=1; i<=max; i++){
            String newUrl = url+i;
            ids.addAll(dataGetter.getIDsFromUrl(newUrl));
        }
    }
    public int getCount(){ return count; }
    public void setPoslowie(int iterator){
        for(int i=iterator; i<=count || i<iterator+50; i++){
            poslowie.add(dataGetter.getJSON(ids.get(i)));
        }
    }
    public void setPosel(int index){
        poslowie.add(dataGetter.getJSON(ids.get(index)));
    }
    public void setPoslowie() throws JSONException {
        setIDs();
        for(int i=0; i<getCount(); i++){
            System.out.print("\rPosel "+(i+1)+"\\"+getCount());
            setPosel(i);
        }
        System.out.println();
        return;
    }
    public JSONObject getPosel(int index){
        return poslowie.get(index);
    }

    public JSONObject getPoselbyName(String name) throws JSONException {
        for(JSONObject jsonObject : poslowie){
            JSONObject data = jsonObject.getJSONObject("data");
            if(data.getString("ludzie.nazwa").equals(name)) return jsonObject;
        }
        //System.out.println("Brak posla o takim imieniu i/lub nazwisku");
        return null;
    }
}
