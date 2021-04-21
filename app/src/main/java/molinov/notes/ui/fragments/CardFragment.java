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
import molinov.notes.ui.data.CardsSource;
import molinov.notes.ui.data.CardsSourceFirebase;
import molinov.notes.ui.data.CardsSourceResponse;
import molinov.notes.ui.data.DataNotes;
import molinov.notes.ui.data.Notes;
import molinov.notes.ui.observe.Observer;
import molinov.notes.ui.observe.Publisher;

public class CardFragment extends Fragment {

    //    private DataNotes data;
    private CardsSource data;
    private CardAdapter adapter;
    private RecyclerView recyclerView;
    private Publisher publisher;
    private boolean moveToFirstPosition;

    public static CardFragment newInstance() {
        return new CardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        recyclerView = view.findViewById(R.id.recycle_view);
        initRecycleView();
        setHasOptionsMenu(true);
        data = new CardsSourceFirebase().init(cardsData -> {
            adapter.notifyDataSetChanged();
        });
        adapter.setDataSource(data);
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

    private void initRecycleView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CardAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (moveToFirstPosition && data.getSize() > 0) {
            recyclerView.smoothScrollToPosition(0);
            moveToFirstPosition = false;
        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) ||
                super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) ||
                super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    private boolean onItemSelected(int itemId) {
        switch (itemId) {
            case R.id.action_add:
                NoteShowFragment noteShowFragmentAdd = NoteShowFragment.newInstance();
                openNote(noteShowFragmentAdd);
                publisher.subscribe(note -> {
                    data.addCardData(note);
                    adapter.notifyItemInserted(data.getSize() - 1);
                    moveToFirstPosition = true;
                });
                return true;
            case R.id.action_update:
                final int updatePosition = adapter.getMenuPosition();
                NoteShowFragment noteShowFragmentUpdate = NoteShowFragment.newInstance(new Notes(updatePosition));
                openNote(noteShowFragmentUpdate);
                publisher.subscribe(note -> {
                    data.updateCardData(updatePosition, note);
                    adapter.notifyItemChanged(updatePosition);
                });
                return true;
            case R.id.action_delete:
                final int deletePosition = adapter.getMenuPosition();
                data.deleteCardData(deletePosition);
                adapter.notifyItemRemoved(deletePosition);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return false;

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
