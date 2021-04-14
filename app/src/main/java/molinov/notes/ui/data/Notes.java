package molinov.notes.ui.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Notes extends DataNotes implements Parcelable {

    public static String PARCELABLE_KEY;
    private final String name;
    private String date;
    private final String text;

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

    public Notes() {
        this(0);
    }

    public Notes(int index) {
        name = arrayNames.get(index);
        date = arrayDates.get(index);
        text = arrayTexts.get(index);
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

    public static ArrayList<String> getNames() {
        return arrayNames;
    }

    public void setDate(String date, int index) {
        if (arrayDates.get(index) != null) {
            arrayDates.set(index, date);
            this.date = date;
        }
    }

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
