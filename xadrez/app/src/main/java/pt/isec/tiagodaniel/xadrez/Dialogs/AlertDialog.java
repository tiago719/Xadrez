package pt.isec.tiagodaniel.xadrez.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.R;

/**
 * Created by drmoreira on 03-01-2018.
 */

@SuppressLint("ValidFragment")
public class AlertDialog extends DialogFragment {
    private String mTitle;
    private OnCompleteListener mListener;
    private String mTag = Constantes.TAG_EMPTY;


    public AlertDialog(String title) {
        this.mTitle = title;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final EditText edtIP = new EditText(getActivity());
        edtIP.setText("10.0.2.2"); // emulator's default ip

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle(this.mTitle)
                .setIcon(R.mipmap.ic_help_black_24dp)
                //.setMessage(this.mMessage)
                .setView(edtIP)
                .setPositiveButton(getString(R.string.alert_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onComplete(Constantes.ALERT_OK, edtIP.getText().toString());
                    }
                })
                .setNegativeButton(getString(R.string.alert_negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
