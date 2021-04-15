package molinov.notes.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import molinov.notes.R;
import molinov.notes.ui.data.CardAdapter;
import molinov.notes.ui.data.DataNotes;
import molinov.notes.ui.data.Notes;

public class CardFragment extends Fragment {

    public static CardFragment newInstance() {
        return new CardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        DataNotes data = new DataNotes();
        initRecycleView(recyclerView, data);
        setRetainInstance(true);
        return view;
    }

    private void initRecycleView(RecyclerView recyclerView, DataNotes data) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final CardAdapter adapter = new CardAdapter(data);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> {
            Notes note = new Notes(position);
            NoteShowFragment noteShowFragment = NoteShowFragment.newInstance(note, position);
            FragmentManager fm = getParentFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.nav_host_fragment, noteShowFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        });
    }
//                Toast.makeText(getContext(), String.format(Locale.getDefault(), "Позиция %d", position), Toast.LENGTH_SHORT).show());
}
