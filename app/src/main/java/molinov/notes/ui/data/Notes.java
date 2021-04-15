package molinov.notes.ui.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Notes implements Parcelable {

    public static String PARCELABLE_KEY;
    private String name;
    private String date;
    private String text;


    protected Notes(Parcel in) {
        name = in.readString();
        date = in.readString();
        text = in.readString();
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Notes() {
        this("Name 1", "Date 1", "Text 1");
    }

    public Notes(int index) {
        Notes note = DataNotes.getNoteFromList(index);
        this.name = note.getName();
        this.date = note.getDate();
        this.text = note.getText();
//        this = note.getNotesList(index);
//        почему этот вариает не работает?
    }

    public Notes(String name, String date, String text) {
        this.name = name;
        this.date = date;
        this.text = text;
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(text);
    }
}
