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
    private HashMap<String, Integer> itemImageMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        //create a spinner and set its position to that previously selected
        final Spinner spinner = findViewById(R.id.itemSelector);
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        itemImageMap = (HashMap<String, Integer>)intent.getSerializableExtra("itemImageMap");
        //this warning cant really be avoided as the map being passed in is unknown to the cast
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.itemNameArray, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        int spinnerPosition = adapter.getPosition(itemName);
        spinner.setSelection(spinnerPosition);
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
    public void onBackPressed() {
        backClicked(findViewById(R.id.backButton));
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ImageView itemImage = findViewById(R.id.itemImage);
        itemImage.setImageResource(itemImageMap.get(parent.getSelectedItem().toString()));
        //this warning can be checked with a try catch or an if non-null however the warning remains
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}