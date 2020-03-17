package au.edu.jcu.cp3406.headhunterhunter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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


public class MainActivity extends AppCompatActivity {
    public String currentItem = "";
    public String currentItemUrl = "https://poe.ninja/api/data/itemhistory?league=Delirium&type=UniqueAccessory&itemId=607";
    public String exaltedUrl = "https://poe.ninja/api/data/currencyhistory?league=Delirium&type=Currency&currencyId=2";
    public double exaltedPrice = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exaltedPrice = getExaltedPrice();
        updatePrice();
    }

    public void callUpdatePrice(View view){
        updatePrice();
    }

    public void updatePrice(){
        final TextView chaosTextView = (TextView) findViewById(R.id.priceDisplayChaos);
        final TextView exaltedTextView = (TextView) findViewById(R.id.priceDisplayExalted);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, currentItemUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(response.length() - 1);
                    double chaosValue = jsonObject.getDouble("value");
                    double exaltedValue = chaosValue/getExaltedPrice();
                    chaosTextView.setText(String.format("%.2f", chaosValue));
                    exaltedTextView.setText(String.format("%.2f", exaltedValue));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                chaosTextView.setText("error");
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public double getExaltedPrice(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, exaltedUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("receiveCurrencyGraphData");
                    double exaltedPrice = jsonArray.getJSONObject(jsonArray.length() - 1).getDouble("value");
                    setExaltedPrice(exaltedPrice);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
        return exaltedPrice;
    }

    public void setExaltedPrice(double currentPrice){
        exaltedPrice = currentPrice;
    }
}
