package molinov.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class NotesNamesFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    public DataNotes currentNote;
    private boolean isLandscape;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new DataNotes();
        }
        initList(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isLandscape) {
            showLandTextOfNotes(currentNote);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        for (int i = 0; i < currentNote.getArrayNames().size(); i++) {
            String name = currentNote.getArrayNames().get(i);
            TextView tv = new TextView(getContext());
            tv.setText(name);
            tv.setTextSize(30);
            layoutView.addView(tv);
            final int fi = i;
            tv.setOnClickListener(v -> {
                currentNote = new DataNotes(fi);
                showTextOfNotes(currentNote);
            });
        }
    }

    private void showTextOfNotes(DataNotes currentNote) {
        if (isLandscape) {
            showLandTextOfNotes(currentNote);
        } else {
            showPortTextOfNotes(currentNote);
        }
    }

    private void showLandTextOfNotes(DataNotes currentNote) {
        NotesTextFragment detail = NotesTextFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.text_fragment, detail)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void showPortTextOfNotes(DataNotes currentNote) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), TextVertical.class);
        intent.putExtra(CURRENT_NOTE, currentNote);
        startActivity(intent);
    }
}
