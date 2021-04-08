package molinov.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class NotesTextFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private DataNotes dataNotes = new DataNotes();
    private TextView date, text;
    private DatePicker datePicker;
    private Button dateChange;

    public static NotesTextFragment newInstance(DataNotes dataNotes) {
        NotesTextFragment fragment = new NotesTextFragment();
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_NOTE, dataNotes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataNotes = getArguments().getParcelable(CURRENT_NOTE);
        }
//        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_text, container, false);
        initFields(view);
        initListeners();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, dataNotes);
        super.onSaveInstanceState(outState);
    }

    private void initFields(View view) {
        date = view.findViewById(R.id.date);
        text = view.findViewById(R.id.text);
        datePicker = view.findViewById(R.id.datePicker);
        dateChange = view.findViewById(R.id.dateChange);
        date.setText(dataNotes.getDate().get(dataNotes.getIndex()));
        text.setText(dataNotes.getText().get(dataNotes.getIndex()));
    }

    private void initListeners() {
        date.setOnClickListener(v -> {
            if (datePicker.getVisibility() == View.INVISIBLE) {
                datePicker.setVisibility(View.VISIBLE);
                dateChange.setVisibility(View.VISIBLE);
            } else {
                datePicker.setVisibility(View.INVISIBLE);
                dateChange.setVisibility(View.INVISIBLE);
            }
        });
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Toast.makeText(getContext(), "date changed", Toast.LENGTH_SHORT).show();
                }
            });
        }*/
        dateChange.setOnClickListener(v -> {
            String[] months = getResources().getStringArray(R.array.months);
            String text = datePicker.getDayOfMonth() + " " +
                    months[datePicker.getMonth()] + " " +
                    datePicker.getYear();
            date.setText(text);
            dataNotes.setDate(text, dataNotes.getIndex());
            dateChange.setVisibility(View.INVISIBLE);
            datePicker.setVisibility(View.INVISIBLE);
        });
    }
}
