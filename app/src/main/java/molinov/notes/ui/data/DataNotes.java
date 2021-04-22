package molinov.notes.ui.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DataNotes implements CardsSource {

    protected static final List<Notes> notesList = new ArrayList<>();

//    public static Notes getNoteFromList(int i) {
//        return notesList.get(i);
//    }

    @Override
    public int getSize() {
        return notesList.size();
    }

    @Override
    public CardsSource init(CardsSourceResponse cardsSourceResponse) {
        if (cardsSourceResponse != null) {
            cardsSourceResponse.initialized(this);
        }
        return this;
    }

    @Override
    public Notes getCardData(int position) {
        return notesList.get(position);
    }

    @Override
    public void deleteCardData(int position) {
        notesList.remove(position);
    }

    @Override
    public void updateCardData(int position, Notes note) {
        notesList.set(position, note);
    }

    @Override
    public void addCardData(Notes note) {
        notesList.add(note);
    }

    @Override
    public void clearCardData() {
        notesList.clear();
    }
}
