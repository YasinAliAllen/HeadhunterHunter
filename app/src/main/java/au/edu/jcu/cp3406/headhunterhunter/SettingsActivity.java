package au.edu.jcu.cp3406.headhunterhunter;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;


public class SettingsActivity extends AppCompatActivity {

    public final static int SETTINGS_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public void updateDisplay(){
        final ImageView itemImage = findViewById(R.id.itemImage);
        final Spinner spinner = findViewById(R.id.itemSelector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.itemArray, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        itemImage.setImageResource(R.drawable.the_doctor);
    }

}