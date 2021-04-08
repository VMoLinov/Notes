package molinov.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

public class DataNotes implements Parcelable {

    private static ArrayList<String> arrayNames = new ArrayList<>(Arrays.asList("name 1", "name 2", "name 3", "name 4", "name 5"));
    private static ArrayList<String> arrayDates = new ArrayList<>(Arrays.asList("date 1", "date 2", "date 3", "date 4", "date 5"));
    private static ArrayList<String> arrayTexts = new ArrayList<>(Arrays.asList("text 1", "text 2", "text 3", "text 4", "text 5"));
    private final String NAME;
    private String DATE;
    private final String TEXT;
    private final int INDEX;

    protected DataNotes(Parcel in) {
        NAME = in.readString();
        DATE = in.readString();
        TEXT = in.readString();
        INDEX = in.readInt();
    }

    public DataNotes() {
        this(0);
    }

    public DataNotes(int index) {
        NAME = arrayNames.get(index);
        DATE = arrayDates.get(index);
        TEXT = arrayTexts.get(index);
        this.INDEX = index;
    }

    public static final Creator<DataNotes> CREATOR = new Creator<DataNotes>() {
        @Override
        public DataNotes createFromParcel(Parcel in) {
            return new DataNotes(in);
        }

        @Override
        public DataNotes[] newArray(int size) {
            return new DataNotes[size];
        }
    };

    public ArrayList<String> getArrayNames() {
        return arrayNames;
    }

//    public String getNAME() {
//        return NAME;
//    }

    public String getDATE() {
        return DATE;
    }

    public String getTEXT() {
        return TEXT;
    }

    public int getINDEX() {
        return INDEX;
    }

//    public void setName(ArrayList<String> name) {
//        DataNotes.name = name;
//    }

    public void setDate(String date, int index) {
        if (arrayDates.get(index) != null) {
            arrayDates.set(index, date);
            this.DATE = date;
        }
    }

//    public void setText(ArrayList<String> text) {
//        DataNotes.text = text;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(NAME);
        dest.writeString(DATE);
        dest.writeString(TEXT);
        dest.writeInt(INDEX);
    }
}
