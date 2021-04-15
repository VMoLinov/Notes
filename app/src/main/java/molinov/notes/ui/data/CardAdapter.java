package molinov.notes.ui.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import molinov.notes.R;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private final DataNotes dataNote;
    private OnItemClickListener listener;

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
        holder.setData(DataNotes.getNoteFromList(position));
    }

    @Override
    public int getItemCount() {
        return dataNote.getSize();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, date, text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            text = itemView.findViewById(R.id.text);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        public void setData(Notes note) {
            name.setText(note.getName());
            date.setText(note.getDate());
            text.setText(note.getText());
        }
    }
}
