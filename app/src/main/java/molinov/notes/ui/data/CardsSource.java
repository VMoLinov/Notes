package molinov.notes.ui.data;

public interface CardsSource {

    CardsSource init (CardsSourceResponse cardsSourceResponse);

    Notes getCardData(int position);

    void deleteCardData(int position);

    void updateCardData(int position, Notes note);

    void addCardData(Notes note);

    void clearCardData();

    int getSize();
}
