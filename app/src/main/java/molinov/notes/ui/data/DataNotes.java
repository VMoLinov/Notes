package molinov.notes.ui.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataNotes {

    private final List<Notes> notesList = new ArrayList<>(Arrays.asList(
            new Notes("Name 1", "Date 1", "Text 1"),
            new Notes("Name 2", "Date 2", "Text 2"),
            new Notes("Name 3", "Date 3", "Text 3"),
            new Notes("Name 4", "Date 4", "Text 4"),
            new Notes("Name 5", "Date 5", "Text 5")));
    private final int size;

    public DataNotes() {
        size = notesList.size();
    }

    public void setNotesList(Notes note, int index) {
        notesList.set(index, note);
    }

    public Notes getNoteFromList(int i) {
        return notesList.get(i);
    }

    public int getSize() {
        return size;
    }
}
