package molinov.notes.ui.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DataNotes implements CardsSource {

    private static final List<Notes> notesList = new ArrayList<>(Arrays.asList(
            new Notes("Name 1", new Date(new Random().nextLong()), "Text 1"),
            new Notes("Name 2", new Date(new Random().nextLong()), "Text 2"),
            new Notes("Name 3", new Date(new Random().nextLong()), "Text 3"),
            new Notes("Name 4", new Date(new Random().nextLong()), "Text 4"),
            new Notes("Name 5", new Date(new Random().nextLong()), "Text 5")));

    public static void setNotesList(Notes note, int index) {
        notesList.set(index, note);
    }

    public static Notes getNoteFromList(int i) {
        return notesList.get(i);
    }

    public int getSize() {
        return notesList.size();
    }

    @Override
    public void deleteCardData(int position) {
        notesList.remove(position);
    }

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
