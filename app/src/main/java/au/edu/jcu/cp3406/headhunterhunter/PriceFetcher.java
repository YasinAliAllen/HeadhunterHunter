package au.edu.jcu.cp3406.headhunterhunter;

import android.content.Context;
import android.os.Handler;
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

class PriceFetcher {
    enum FetchType{TEST, FINAL}
    private double exaltedPrice = 1;
    private double chaosValue = 0;
    private double exaltedValue = 0;
    private String itemUrl;
    private Context context;
    private Runnable updateUIRunnable;
    private HashMap<String, String> currencyUrlMap = new HashMap<String, String>() {
        {
            put("Exalted Orb", "https://poe.ninja/api/data/currencyhistory?league=Delirium&type=Currency&currencyId=2");
        }
    };
    private HashMap<String, String> itemUrlMap = new HashMap<String, String>() {};

    PriceFetcher(Context context, String itemName, Runnable updateUIRunnable) {
        String[] itemUrlArray = context.getResources().getStringArray(R.array.itemUrlArray);
        String[] itemNameArray = context.getResources().getStringArray(R.array.itemNameArray);
        for (int i = 0; i < itemNameArray.length; i++){
            itemUrlMap.put(itemNameArray[i], itemUrlArray[i]);
        }
        this.context = context;
        this.itemUrl = itemUrlMap.get(itemName);
        this.updateUIRunnable = updateUIRunnable;
    }

    PriceFetcher(Context context, String itemName) {
        this.context = context;
        this.itemUrl = itemUrlMap.get(itemName);
    }

    double getChaosValue() {
        return chaosValue;
    }

    double getExaltedValue() {
        return exaltedValue;
    }

    double getExaltedPrice() {
        return exaltedPrice;
    }

    //pulls currency price data from poe.ninja, using it for future item pricing if fetchType is FINAL
    void fetchData(final FetchType fetchType) {
        RequestQueue exaltedRequest = Volley.newRequestQueue(context);
        JsonObjectRequest exaltedDataRequest = new JsonObjectRequest(Request.Method.GET,
                currencyUrlMap.get("Exalted Orb"), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //try pull the current price of exalted orbs from poe.ninja
                try {
                    JSONArray exaltedData = response.getJSONArray("receiveCurrencyGraphData");
                    exaltedPrice = exaltedData.getJSONObject(exaltedData.length() - 1)
                            .getDouble("value");
                    if(fetchType == FetchType.FINAL){
                        final Handler requestHandler = new Handler();
                        final Runnable fetchItemRunnable = new Runnable() {
                            public void run() {
                                fetchItemPrice();
                            }
                        };
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

    //pulls item data from poe.ninja, calling handle on completion to update UI
    private void fetchItemPrice(){
        final Handler updateUIHandler = new Handler();
        RequestQueue itemRequest = Volley.newRequestQueue(context);
        JsonArrayRequest itemDataRequest = new JsonArrayRequest(Request.Method.GET,
                itemUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //try get latest item price in chaos, convert to price in exalts and display both
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

