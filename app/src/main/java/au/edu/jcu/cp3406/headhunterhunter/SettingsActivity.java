package au.edu.jcu.cp3406.headhunterhunter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;


public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public final static int SETTINGS_REQUEST = 0;
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
        setContentView(R.layout.settings_activity);
        final Spinner spinner = findViewById(R.id.itemSelector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.itemArray, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    public void backClicked(View view) {
        Spinner spinner = findViewById(R.id.itemSelector);
        String itemName = spinner.getSelectedItem().toString();
        Intent intent = new Intent();
        intent.putExtra("itemName", itemName);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ImageView itemImage = findViewById(R.id.itemImage);
        itemImage.setImageResource(itemImageMap.get(parent.getSelectedItem().toString()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}