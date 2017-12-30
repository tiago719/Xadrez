package pt.isec.tiagodaniel.xadrez.Dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.R;

/**
 * Created by drmoreira on 28-12-2017.
 */

@SuppressLint("ValidFragment")
public class WinDialog extends DialogFragment {
    private String title;
    private OnCompleteListener mListener;
    private String mTag = Constantes.TAG_EMPTY;

    public WinDialog(String message) {

        this.title = message;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder .setTitle(title)
                .setIcon(R.mipmap.ic_win)
                .setPositiveButton(getString(R.string.error_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onComplete(Constantes.WIN_OK, mTag);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnCompleteListener) context;
        } catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
        }
    }
}

