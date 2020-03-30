package au.edu.jcu.cp3406.headhunterhunter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final Runnable updateUIRunnable = new Runnable() {
        public void run() {
            updateDisplay();
        }
    };
    PriceFetcher priceFetcher = new PriceFetcher(this, "headhunter", updateUIRunnable);
    private String itemName = "Headhunter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        priceFetcher.fetchData(PriceFetcher.FetchType.ITEM);
    }

    public void updateDisplay(){
        final TextView chaosTextView = findViewById(R.id.priceDisplayChaos);
        final TextView exaltedTextView = findViewById(R.id.priceDisplayExalted);
        final ImageView itemImage = findViewById(R.id.itemImage);
        chaosTextView.setText(String.format(Locale.getDefault(), "%.2f", priceFetcher.getChaosValue()));
        exaltedTextView.setText(String.format(Locale.getDefault(), "%.2f", priceFetcher.getExaltedValue()));
        itemImage.setImageResource(R.drawable.the_doctor);
    }

    public void clickedSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, SettingsActivity.SETTINGS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SettingsActivity.SETTINGS_REQUEST){
            if (resultCode == RESULT_OK){
                if(data != null){
                    itemName = data.getStringExtra("itemName");
                }
            }
        }
    }
}