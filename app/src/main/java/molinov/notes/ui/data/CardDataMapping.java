package molinov.notes.ui.data;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CardDataMapping {

    public static class Fields {
        public static String NAME = "name";
        public static String DATE = "date";
        public static String TEXT = "text";
    }

    public static Notes toNotes(String id, Map<String, Object> doc) {
        String name = (String) doc.get(Fields.NAME);
        Date date = ((Timestamp) Objects.requireNonNull(doc.get(Fields.DATE))).toDate();
        String text = (String) doc.get(Fields.TEXT);
        return new Notes(id, name, date, text);
    }

    public static Map<String, Object> toDocument(Notes note) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.NAME, note.getName());
        answer.put(Fields.DATE, note.getDate());
        answer.put(Fields.TEXT, note.getText());
        return answer;
    }
}
