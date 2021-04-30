package molinov.notes.ui.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import molinov.notes.MainActivity;
import molinov.notes.R;
import molinov.notes.ui.data.Notes;
import molinov.notes.ui.observe.Publisher;

public class DialogNote extends DialogFragment {

    private EditText noteShowName, noteShowText;
    private TextView noteShowDate;
    private Notes note;
    private DatePickerDialog dialog;
    private MaterialButton cancel, update;
    private final CardFragment cardFragment;
    private final int pos;
    private Publisher publisher;

    public static DialogNote newInstance(CardFragment data, int pos) {
        return new DialogNote(data, pos);
    }

    private DialogNote(CardFragment cardFragment, int pos) {
        this.cardFragment = cardFragment;
        this.pos = pos;
        note = cardFragment.getData().getCardData(pos);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_note, null);
        initFields(view);
        initListeners();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    private void initFields(View view) {
        noteShowName = view.findViewById(R.id.noteShowName);
        noteShowDate = view.findViewById(R.id.noteShowDate);
        noteShowText = view.findViewById(R.id.noteShowText);
        cancel = view.findViewById(R.id.cancel);
        update = view.findViewById(R.id.update);
        cancel.setText(R.string.cancel);
        update.setText(R.string.update);
        dialog = new DatePickerDialog(getContext());
        dialog.setTitle("Choice date");
        dialog.setContentView(R.layout.dialog_note);
        if (note != null) {
            noteShowName.setText(note.getName());
            noteShowDate.setText(new SimpleDateFormat("dd-MM-yy").format(note.getDate()));
            noteShowText.setText(note.getText());
        }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initListeners() {
        cancel.setOnClickListener(v -> {
            dismiss();
        });
        update.setOnClickListener(v -> {
            dismiss();
            note = collectNote();
            cardFragment.update(note, pos);
            publisher.notifySingle(note);
        });
        noteShowDate.setOnClickListener(v -> {
            dialog.show();
        });
        dialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            Date date = getDateFromDialog(year, month, dayOfMonth);
            note.setDate(date);
            noteShowDate.setText(new SimpleDateFormat("dd-MM-yy").format(date));
        });
    }

    private Notes collectNote() {
        String name = noteShowName.getText().toString();
        String text = noteShowText.getText().toString();
        Date date = note.getDate();
        String id = note.getId();
        if (id != null) {
            return new Notes(id, name, date, text);
        } else {
            return new Notes(name, date, text);
        }
    }

    private Date getDateFromDialog(int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return cal.getTime();
    }
}
