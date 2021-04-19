package molinov.notes.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Date;

import molinov.notes.MainActivity;
import molinov.notes.R;
import molinov.notes.ui.data.DataNotes;
import molinov.notes.ui.data.Notes;
import molinov.notes.ui.observe.Publisher;

public class NoteShowFragment extends Fragment {
    private EditText noteShowName, noteShowText;
    private TextView noteShowDate;
    private Publisher publisher;
    private DatePicker datePicker;
    private Notes note;
    private static int position;

    public static NoteShowFragment newInstance(Notes note, int position) {
        NoteShowFragment fragment = new NoteShowFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Notes.PARCELABLE_KEY, note);
        NoteShowFragment.position = position;
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_show, container, false);
        initFields(view);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(note);
    }

    private Notes collectNote() {
        String name = this.noteShowName.getText().toString();
        String text = this.noteShowText.getText().toString();
        Date date = getDateFromDatePicker();
        return new Notes(name, date, text);
    }

    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }

    private void initFields(View view) {
        noteShowName = view.findViewById(R.id.noteShowName);
        noteShowDate = view.findViewById(R.id.noteShowDate);
        noteShowText = view.findViewById(R.id.noteShowText);
        datePicker = view.findViewById(R.id.datePicker);
    }

    private void populateView() {
        noteShowName.setText(note.getName());
        noteShowText.setText(note.getText());
//        noteShowDate.setText(note.getDate());
        initDatePicker(note.getDate());
    }

    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
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
}
