package molinov.notes.ui.data;

public interface CardsSource {

    CardsSource init (CardsSourceResponse cardsSourceResponse);

    void deleteCardData(int position);

    void updateCardData(int position, Notes note);

    void addCardData(Notes note);

    void clearCardData();

    int getSize();
}
