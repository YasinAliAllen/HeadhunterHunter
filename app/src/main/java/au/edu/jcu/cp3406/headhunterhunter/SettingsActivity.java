package au.edu.jcu.cp3406.headhunterhunter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;


public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public final static int SETTINGS_REQUEST = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        final Spinner spinner = findViewById(R.id.itemSelector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.itemArray, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
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

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}