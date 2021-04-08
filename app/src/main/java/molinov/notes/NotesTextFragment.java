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

import molinov.notes.ui.data.Notes;

public class NotesTextFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private Notes notes;
    private TextView date, text;
    private DatePicker datePicker;
    private Button dateChange;

    public static NotesTextFragment newInstance(Notes notes) {
        NotesTextFragment fragment = new NotesTextFragment();
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_NOTE, notes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            notes = getArguments().getParcelable(CURRENT_NOTE);
        } else {
            notes = new Notes();
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
        outState.putParcelable(CURRENT_NOTE, notes);
        super.onSaveInstanceState(outState);
    }

    private void initFields(View view) {
        date = view.findViewById(R.id.date);
        text = view.findViewById(R.id.text);
        datePicker = view.findViewById(R.id.datePicker);
        dateChange = view.findViewById(R.id.dateChange);
//        date.setText(dataNotes.getDATE());
//        text.setText(dataNotes.getTEXT());
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
//            dataNotes.setDate(text, dataNotes.getINDEX());
            dateChange.setVisibility(View.INVISIBLE);
            datePicker.setVisibility(View.INVISIBLE);
        });
    }
}
