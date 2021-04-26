package molinov.notes.ui.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class Notes implements Parcelable {

    public static String PARCELABLE_KEY;
    private String id;
    private String name;
    private String text;
    private Date date;


    protected Notes(Parcel in) {
        id = in.readString();
        name = in.readString();
        text = in.readString();
        date = new Date(in.readLong());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Notes() {
        this("New Note", null, "Enter text here");
        date = Calendar.getInstance().getTime();
//            date = String.valueOf(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public Notes(String name, Date date, String text) {
        this.name = name;
        this.text = text;
        this.date = date;
    }

    public Notes(String id, String name, Date date, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.date = date;
    }

//    public Notes(int index) {
//        Notes note = DataNotes.getNoteFromList(index);
//        this.name = note.getName();
//        this.date = note.getDate();
//        this.text = note.getText();
//    }

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
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(text);
        dest.writeLong(date.getTime());
    }
}
