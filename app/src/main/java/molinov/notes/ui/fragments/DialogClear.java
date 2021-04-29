package molinov.notes.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import molinov.notes.R;

public class DialogClear extends DialogFragment {

    private final CardFragment cardFragment;

    public DialogClear(CardFragment cardFragment) {
        this.cardFragment = cardFragment;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final View contentView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_custom, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.are_you_sure)
                .setMessage(R.string.clear_all_notes)
                .setView(contentView)
                .setPositiveButton(R.string.yes, (DialogInterface.OnClickListener) (dialog, which) -> {
                    dismiss();
                    cardFragment.clear();
                }).setNegativeButton(R.string.no, (DialogInterface.OnClickListener) (dialog, which) -> {
                    dismiss();
                });
        return builder.create();
    }
}
