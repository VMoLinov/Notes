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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import molinov.notes.MainActivity;
import molinov.notes.R;
import molinov.notes.ui.data.CardAdapter;
import molinov.notes.ui.data.CardsSource;
import molinov.notes.ui.data.CardsSourceFirebase;
import molinov.notes.ui.observe.Publisher;

public class CardFragment extends Fragment {

    private CardsSource data;
    private CardAdapter adapter;
    private RecyclerView recyclerView;
    private Publisher publisher;
    private boolean moveToFirstPosition;

    public static CardFragment newInstance() {
        return new CardFragment();
    }

    public CardsSource getData() {
        return data;
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
            recyclerView.scrollToPosition(0);
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
                NoteShowFragment noteShowFragmentUpdate = NoteShowFragment.newInstance(data.getCardData(updatePosition));
                openNote(noteShowFragmentUpdate);
                publisher.subscribe(note -> {
                    data.updateCardData(updatePosition, note);
                    adapter.notifyItemChanged(updatePosition);
                });
                return true;
            case R.id.action_delete:
                DialogFragment dlgBuilder = new DialogDelete(adapter.getMenuPosition(), this);
                dlgBuilder.show(getParentFragmentManager(), "DELETE");
                return true;
            case R.id.action_clear:
                DialogFragment dlgClearBuilder = new DialogClear(this);
                dlgClearBuilder.show(getParentFragmentManager(), "CLEAR");
                return true;
        }
        return false;
    }

    public void deletePos(int pos) {
        data.deleteCardData(pos);
        adapter.notifyItemRemoved(pos);
    }

    public void clear() {
        data.clearCardData();
        adapter.notifyDataSetChanged();
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
