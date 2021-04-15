package molinov.notes.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import molinov.notes.R;
import molinov.notes.ui.data.DataNotes;
import molinov.notes.ui.data.Notes;

public class NoteShowFragment extends Fragment {
    private EditText noteShowName, noteShowText;
    private TextView noteShowDate;
    private Notes note;
    private static int position;

    public static NoteShowFragment newInstance(Notes note, int position) {
        NoteShowFragment fragment = new NoteShowFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Notes.PARCELABLE_KEY, note);
//        bundle.putInt(Notes.PARCELABLE_KEY, position);
//        crash after this
        NoteShowFragment.position = position;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_show, container, false);
        initFields(view);
//        initListeners();
        setRetainInstance(true);
        return view;
    }

    private void initFields(View view) {
        noteShowName = view.findViewById(R.id.noteShowName);
        noteShowDate = view.findViewById(R.id.noteShowDate);
        noteShowText = view.findViewById(R.id.noteShowText);
        noteShowName.setText(note.getName());
        noteShowDate.setText(note.getDate());
        noteShowText.setText(note.getText());
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
    public void onPause() {
        super.onPause();
        Bundle bundle = new Bundle();
        onSaveInstanceState(bundle);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        note.setName(noteShowName.getText().toString());
        note.setDate(noteShowDate.getText().toString());
        note.setText(noteShowText.getText().toString());
        DataNotes.setNotesList(note, position);
        outState.putParcelable(Notes.PARCELABLE_KEY, note);
    }
}
