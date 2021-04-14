package molinov.notes.ui.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import molinov.notes.R;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private DataNotes dataNote;

    public CardAdapter(DataNotes dataNote) {
        this.dataNote = dataNote;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        holder.setData(dataNote.getNotesList(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, date, text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            text = itemView.findViewById(R.id.text);
        }

        public void setData(Notes note) {
            name.setText(note.getName());
            date.setText(note.getDate());
            text.setText(note.getText());
        }
    }
}
