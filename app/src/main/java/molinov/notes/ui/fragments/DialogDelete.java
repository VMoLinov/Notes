package molinov.notes.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import molinov.notes.R;

public class DialogDelete extends DialogFragment {

    private final int pos;
    private final CardFragment cardFragment;

    public DialogDelete(int pos, CardFragment cardFragment) {
        this.pos = pos;
        this.cardFragment = cardFragment;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final View contentView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_custom, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.are_you_sure)
                .setMessage(getString(R.string.delete_note) + System.lineSeparator() + cardFragment.getData().getCardData(pos).getName())
                .setView(contentView)
                .setPositiveButton(R.string.yes, (DialogInterface.OnClickListener) (dialog, which) -> {
                    dismiss();
                    cardFragment.deletePos(pos);
                }).setNegativeButton(R.string.no, (DialogInterface.OnClickListener) (dialog, which) -> {
                    dismiss();
                });
        return builder.create();
    }
}
