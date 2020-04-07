package au.edu.jcu.cp3406.headhunterhunter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Locale;
import static java.util.Objects.requireNonNull;

public class MainActivity extends AppCompatActivity {
    private final Runnable updateUIRunnable = new Runnable() {
        public void run() {
            updateDisplay();
        }
    };
    PriceFetcher priceFetcher;
    private String itemName = "Headhunter";
    /*The initialization of this hashmap can likely be done more efficiently but I am unsure if you
    can make an array of drawables. If you can implementation would be the same as that seen in
    the PriceFetcher class.*/
    private HashMap<String, Integer> itemImageMap = new HashMap<String, Integer>() {
        {
            put("Headhunter", R.drawable.headhunter);
            put("Unnatural Instinct", R.drawable.unnatural_instinct);
            put("Inspired Learning", R.drawable.inspired_learning);
            put("House of Mirrors", R.drawable.house_of_mirrors);
            put("The Doctor", R.drawable.the_doctor);
            put("The Halcyon", R.drawable.the_halcyon);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            itemName = savedInstanceState.getString("itemName");
        }
        priceFetcher = new PriceFetcher(this, itemName, updateUIRunnable);
        priceFetcher.fetchData(PriceFetcher.FetchType.FINAL);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("itemName", itemName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        priceFetcher.fetchData(PriceFetcher.FetchType.FINAL);
    }

    //Graphically Update all UI elements
    public void updateDisplay() {
        final TextView currentItemTextView = findViewById(R.id.currentItemText);
        final TextView chaosTextView = findViewById(R.id.priceDisplayChaos);
        final TextView exaltedTextView = findViewById(R.id.priceDisplayExalted);
        final ImageView itemImage = findViewById(R.id.itemImage);
        currentItemTextView.setText(itemName);
        chaosTextView.setText(String.format(Locale.getDefault(), "%.2f",
                priceFetcher.getChaosValue()));
        exaltedTextView.setText(String.format(Locale.getDefault(), "%.2f",
                priceFetcher.getExaltedValue()));
        itemImage.setImageResource(requireNonNull(itemImageMap.get(itemName)));
        Toast toast = Toast.makeText(this, "Item Fetch Complete", Toast.LENGTH_SHORT);
        toast.show();
    }

    //Send intent to SettingsActivity putting necessary data as extras on the intent
    public void clickedSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("itemName", itemName);
        intent.putExtra("itemImageMap", itemImageMap);
        startActivityForResult(intent, SettingsActivity.SETTINGS_REQUEST);
    }

    @Override
    //Manage chosen configuration from SettingsActivity
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SettingsActivity.SETTINGS_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    itemName = data.getStringExtra("itemName");
                }
            }
        }
        priceFetcher = new PriceFetcher(this, itemName, updateUIRunnable);
        priceFetcher.fetchData(PriceFetcher.FetchType.FINAL);
    }
}