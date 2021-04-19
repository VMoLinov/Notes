package molinov.notes.ui.data;

public interface CardsSource {

    void deleteCardData(int position);

    void updateCardData(int position, Notes note);

    void addCardData(Notes note);

    void clearCardData();
}
