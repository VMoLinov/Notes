/*
package molinov.notes.ui.fragments;

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

import molinov.notes.R;
import molinov.notes.ui.data.Notes;

public class MainFragment extends Fragment {

    private boolean isLandscape;
    private Notes note;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Notes.PARCELABLE_KEY, note);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            note = savedInstanceState.getParcelable(Notes.PARCELABLE_KEY);
        } else {
            note = new Notes();
        }
        initList(view);
    }

    private void initList(View view) {
        LinearLayout linearLayout;
        if (isLandscape) {
            linearLayout = (LinearLayout) view.findViewById(R.id.main_start);
        } else {
            linearLayout = (LinearLayout) view;
        }
        for (int i = 0; i < Notes.getNames().size(); i++) {
            String name = Notes.getNames().get(i);
            TextView tv = new TextView(getContext());
            tv.setText(name);
            tv.setTextSize(30);
            linearLayout.addView(tv);
            final int fi = i;
            tv.setOnClickListener(v -> {
                note = new Notes(fi);
                showNote(note);
            });
        }
    }

    private void showNote(Notes note) {
        if (isLandscape) {
            showLandNote(note);
        } else {
            showPortNote(note);
        }
    }

    private void showLandNote(Notes note) {
        NoteShowFragment noteShowFragment = NoteShowFragment.newInstance(note);
        FragmentManager fm = getParentFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_end, noteShowFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void showPortNote(Notes note) {
        NoteShowFragment noteShowFragment = NoteShowFragment.newInstance(note);
        FragmentManager fm = getParentFragmentManager();
        fm.beginTransaction()
                .replace(R.id.nav_host_fragment, noteShowFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }
}
*/
