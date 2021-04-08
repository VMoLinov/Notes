package molinov.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

public class DataNotes implements Parcelable {

    private static ArrayList<String> name = new ArrayList<>(Arrays.asList("name 1", "name 2", "name 3", "name 4", "name 5"));
    private static ArrayList<String> date = new ArrayList<>(Arrays.asList("date 1", "date 2", "date 3", "date 4", "date 5"));
    private static ArrayList<String> text = new ArrayList<>(Arrays.asList("text 1", "text 2", "text 3", "text 4", "text 5"));
    private int index;
asfasg
    protected DataNotes(Parcel in) {
        name = in.readArrayList(null);
        date = in.readArrayList(null);
        text = in.readArrayList(null);
        index = in.readInt();
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

    public DataNotes() {
        index = 0;
    }

    public DataNotes(int index) {
        this.index = index;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public ArrayList<String> getDate() {
        return date;
    }

    public ArrayList<String> getText() {
        return text;
    }

    public int getIndex() {
        return index;
    }

    public void setName(ArrayList<String> name) {
        DataNotes.name = name;
    }

    public void setDate(String date, int index) {
        if (DataNotes.date.get(index) != null) {
            DataNotes.date.set(index, date);
        }
    }

    public void setText(ArrayList<String> text) {
        DataNotes.text = text;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(name);
        dest.writeList(date);
        dest.writeList(text);
        dest.writeInt(index);
    }
}
