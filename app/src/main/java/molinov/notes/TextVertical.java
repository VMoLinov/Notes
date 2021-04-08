package molinov.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class TextVertical extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_vertical);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        if (savedInstanceState == null) {
            NotesTextFragment details = new NotesTextFragment();
            details.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.text_fragment, details).commit();
        }
    }
}
