package molinov.notes.ui.data;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CardsSourceFirebase implements CardsSource {

    private final String CARDS_COLLECTION = "cards";
    private final String TAG = "[CardsSourceFirebase]";
    private final FirebaseFirestore store = FirebaseFirestore.getInstance();
    private final CollectionReference collection = store.collection(CARDS_COLLECTION);
    private List<Notes> notes;


    @Override
    public CardsSource init(CardsSourceResponse cardsSourceResponse) {
        collection.orderBy(CardDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        notes = new ArrayList<>();
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Map<String, Object> doc = document.getData();
                            String id = document.getId();
                            Notes note = CardDataMapping.toNotes(id, doc);
                            notes.add(note);
                        }
                        Log.d(TAG, "success " + notes.size() + " qnt");
                        cardsSourceResponse.initialized(CardsSourceFirebase.this);
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "get failed with " + e);
                });
        return this;
    }

    @Override
    public void deleteCardData(int position) {
        collection.document(notes.get(position).getId()).delete();
        notes.remove(position);
    }

    @Override
    public void updateCardData(int position, Notes note) {
        String id = note.getId();
        collection.document(id).set(CardDataMapping.toDocument(note));
    }

    @Override
    public void addCardData(Notes note) {
        collection.add(CardDataMapping.toDocument(note)).addOnSuccessListener(documentReference -> {
            note.setId(documentReference.getId());
        });
    }

    @Override
    public void clearCardData() {
        for (Notes note : notes) {
            collection.document(note.getId()).delete();
        }
        notes = new ArrayList<>();
    }

    @Override
    public int getSize() {
        return notes.size();
    }
}
