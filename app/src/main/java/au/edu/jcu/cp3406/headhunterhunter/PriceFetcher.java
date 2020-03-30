package au.edu.jcu.cp3406.headhunterhunter;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PriceFetcher {

    enum FetchType{CURRENCY, ITEM}
    private double exaltedPrice = 100;
    private double chaosValue = 0;
    private double exaltedValue = 0;
    private String itemUrl = "";
    private Context context;
    private final Handler requestHandler = new Handler();
    private final Handler updateUIHandler = new Handler();
    private final Runnable fetchItemRunnable = new Runnable() {
        public void run() {
            fetchItemPrice(context);
        }
    };
    private Runnable updateUIRunnable;

    private HashMap<String, String> currencyUrlMap = new HashMap<String, String>() {
        {
            put("Exalted Orb", "https://poe.ninja/api/data/currencyhistory?league=Delirium&type=Currency&currencyId=2");
        }
    };

    PriceFetcher(Context context, String itemName, Runnable updateUIRunnable) {
        HashMap<String, String> itemUrlMap = new HashMap<String, String>() {
            {
                put("headhunter", "https://poe.ninja/api/data/itemhistory?league=Delirium&type=UniqueAccessory&itemId=607");
                put("Unnatural Instinct", "https://poe.ninja/api/data/itemhistory?league=Delirium&type=UniqueJewel&itemId=7376");
                put("Inspired Learning", "https://poe.ninja/api/data/itemhistory?league=Delirium&type=UniqueJewel&itemId=676");
                put("House of Mirrors", "https://poe.ninja/api/data/itemhistory?league=Delirium&type=DivinationCard&itemId=636");
                put("The Doctor", "https://poe.ninja/api/data/itemhistory?league=Delirium&type=DivinationCard&itemId=1476");
                put("The Halcyon", "https://poe.ninja/api/data/itemhistory?league=Delirium&type=UniqueAccessory&itemId=1961");
            }
        };

        this.context = context;
        this.itemUrl = itemUrlMap.get(itemName);
        this.updateUIRunnable = updateUIRunnable;
    }

    double getChaosValue() {
        return chaosValue;
    }

    double getExaltedValue() {
        return exaltedValue;
    }

    void fetchData(final FetchType fetchType) {
        RequestQueue exaltedRequest = Volley.newRequestQueue(context);
        JsonObjectRequest exaltedDataRequest = new JsonObjectRequest(Request.Method.GET,
                currencyUrlMap.get("Exalted Orb"), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray exaltedData = response.getJSONArray("receiveCurrencyGraphData");
                    exaltedPrice = exaltedData.getJSONObject(exaltedData.length() - 1).getDouble("value");
                    if(fetchType == FetchType.ITEM){
                        requestHandler.post(fetchItemRunnable);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        exaltedRequest.add(exaltedDataRequest);
    }

    private void fetchItemPrice(Context context){
        RequestQueue itemRequest = Volley.newRequestQueue(context);
        JsonArrayRequest itemDataRequest = new JsonArrayRequest(Request.Method.GET,
                itemUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject latestItemData = response.getJSONObject(response.length() - 1);
                    chaosValue = latestItemData.getDouble("value");
                    exaltedValue = chaosValue/exaltedPrice;
                    updateUIHandler.post(updateUIRunnable);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        itemRequest.add(itemDataRequest);
    }

}

