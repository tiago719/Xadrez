package pt.isec.tiagodaniel.xadrez.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.R;

@SuppressLint("ValidFragment")
public class AlertDialog extends DialogFragment {
    private OnCompleteListener mListener;
    private String mTag = Constantes.TAG_EMPTY;

    public AlertDialog(OnCompleteListener listener) {
        this.mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.alert_title))
                .setIcon(R.mipmap.ic_error_black_24dp)
                .setMessage(getString(R.string.alert_message))
                .setPositiveButton(getString(R.string.error_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onComplete(Constantes.ERROR_OK, mTag);
                    }
                });
        // Create the IpDialog object and return it
        return builder.create();
    }
}
