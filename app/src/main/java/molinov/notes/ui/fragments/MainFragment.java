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

import molinov.notes.NotesTextFragment;
import molinov.notes.R;
import molinov.notes.ui.data.Notes;

public class MainFragment extends Fragment {

    private final String KEY = "key";
    private boolean isLandscape;
    private Notes note;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            note = savedInstanceState.getParcelable(KEY);
        } else {
            note = new Notes();
        }
        initList(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isLandscape) {
            showLand(note);
        }
    }

    private void showLand(Notes note) {
            NotesTextFragment detail = NotesTextFragment.newInstance(note);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.text_fragment, detail)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }

    private void initList(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        for (int i = 0; i < Notes.getNames().size(); i++) {
            String name = Notes.getNames().get(i);
            TextView tv = new TextView(getContext());
            tv.setText(name);
            tv.setTextSize(30);
            linearLayout.addView(tv);
        }
    }
}