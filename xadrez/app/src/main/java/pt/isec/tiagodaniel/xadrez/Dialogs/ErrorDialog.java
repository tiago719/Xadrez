package pt.isec.tiagodaniel.xadrez.Dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.R;

@SuppressLint("ValidFragment")
public class ErrorDialog extends DialogFragment {
    private String mMessage;
    private OnCompleteListener mListener;
    private String mTag = Constantes.TAG_EMPTY;

    public ErrorDialog(OnCompleteListener listener, String message) {
        this.mListener = listener;
        this.mMessage = message;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.error_title))
                .setIcon(R.drawable.ic_error_black_24dp)
                .setMessage(this.mMessage)
                .setPositiveButton(getString(R.string.error_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onComplete(Constantes.ERROR_OK, mTag);
                    }
                });
        // Create the IpDialog object and return it
        return builder.create();
    }
}
