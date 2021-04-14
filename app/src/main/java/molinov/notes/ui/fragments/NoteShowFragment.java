package molinov.notes.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import molinov.notes.R;
import molinov.notes.ui.data.Notes;

public class NoteShowFragment extends Fragment {

    private Notes note;

    public static NoteShowFragment newInstance(Notes note) {
        NoteShowFragment fragment = new NoteShowFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Notes.PARCELABLE_KEY, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_show, container, false);
        initFields(view);
//        initListeners();
        return view;
    }

    private void initFields(View view) {
        TextView noteShowName = view.findViewById(R.id.noteShowName);
        TextView noteShowDate = view.findViewById(R.id.noteShowDate);
        TextView noteShowText = view.findViewById(R.id.noteShowText);
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
}
