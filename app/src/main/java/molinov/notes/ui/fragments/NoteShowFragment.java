package molinov.notes.ui.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import molinov.notes.MainActivity;
import molinov.notes.R;
import molinov.notes.ui.data.Notes;
import molinov.notes.ui.observe.Publisher;

public class NoteShowFragment extends Fragment {
    private EditText noteShowName, noteShowText;
    private TextView noteShowDate;
    private Publisher publisher;
    private Notes note;
    private DatePickerDialog dialog;

    public static NoteShowFragment newInstance(Notes note) {
        NoteShowFragment fragment = new NoteShowFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Notes.PARCELABLE_KEY, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static NoteShowFragment newInstance() {
        return new NoteShowFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(Notes.PARCELABLE_KEY);
        } else {
            note = new Notes();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_show, container, false);
        initFields(view);
        initListeners(view);
        if (note != null) {
            populateView();
        }
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        note = collectNote();
        publisher.notifySingle(note);
    }

    private Notes collectNote() {
        String name = noteShowName.getText().toString();
        String text = noteShowText.getText().toString();
        Date date = note.getDate();
        String id = note.getId();
        if (id != null) {
            return new Notes(id, name, date, text);
        } else {
            return new Notes(name, date, text);
        }
    }


    private Date getDateFromDialog(int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return cal.getTime();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initFields(View view) {
        noteShowName = view.findViewById(R.id.noteShowName);
        noteShowDate = view.findViewById(R.id.noteShowDate);
        noteShowText = view.findViewById(R.id.noteShowText);
        dialog = new DatePickerDialog(getContext());
        dialog.setTitle("Choice date");
        dialog.setContentView(R.layout.fragment_note_show);
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initListeners(View view) {
        noteShowDate.setOnClickListener(v -> {
            dialog.show();
        });
        dialog.setOnDateSetListener((view1, year, month, dayOfMonth) -> {
            Date date = getDateFromDialog(year, month, dayOfMonth);
            note.setDate(date);
            noteShowDate.setText(new SimpleDateFormat("dd-MM-yy").format(date));
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void populateView() {
        noteShowName.setText(note.getName());
        noteShowText.setText(note.getText());
        noteShowDate.setText(new SimpleDateFormat("dd-MM-yy").format(note.getDate()));
    }

//    private void initDatePicker(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        this.datePicker.init(calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH),
//                null);
}

//    @Override
//    public void onPause() {
//        super.onPause();
//        if (position != 0) {
//            Bundle bundle = new Bundle();
//            onSaveInstanceState(bundle);
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        note.setName(noteShowName.getText().toString());
//        note.setDate(noteShowDate.getText().toString());
//        note.setText(noteShowText.getText().toString());
//        DataNotes.setNotesList(note, position);
//        outState.putParcelable(Notes.PARCELABLE_KEY, note);
//    }
