package molinov.notes.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import molinov.notes.MainActivity;
import molinov.notes.R;
import molinov.notes.ui.data.CardAdapter;
import molinov.notes.ui.data.DataNotes;
import molinov.notes.ui.data.Notes;
import molinov.notes.ui.observe.Publisher;

public class CardFragment extends Fragment {

    private DataNotes data;
    private CardAdapter adapter;
    private RecyclerView recyclerView;
    private Publisher publisher;
    private boolean moveToLastPosition;

    public static CardFragment newInstance() {
        return new CardFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new DataNotes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        recyclerView = view.findViewById(R.id.recycle_view);
        initRecycleView();
        setHasOptionsMenu(true);
        setRetainInstance(true);
        return view;
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                NoteShowFragment noteShowFragment = NoteShowFragment.newInstance();
                openNote(noteShowFragment);
                publisher.subscribe(note -> {
                    data.addCardData(note);
                    adapter.notifyItemInserted(data.getSize() - 1);
                    moveToLastPosition = true;
                });
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecycleView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CardAdapter(data, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (moveToLastPosition) {
            recyclerView.smoothScrollToPosition(data.getSize() - 1);
            moveToLastPosition = false;
        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case (R.id.action_update):
                NoteShowFragment noteShowFragment = NoteShowFragment.newInstance(new Notes(position));
                openNote(noteShowFragment);
                publisher.subscribe(note -> {
                    data.updateCardData(position, note);
                    adapter.notifyItemChanged(position);
                });
                return true;
            case (R.id.action_delete):
                data.deleteCardData(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void openNote(Fragment fragment) {
        FragmentManager fm = getParentFragmentManager();
        fm.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }
}
