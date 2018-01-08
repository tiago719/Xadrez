package pt.isec.tiagodaniel.xadrez.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.R;

@SuppressLint("ValidFragment")
public class IpDialog extends DialogFragment {
    private String mTitle;
    private OnCompleteListener mListener;
    private String mTag = Constantes.TAG_EMPTY;


    public IpDialog(OnCompleteListener listener, String title) {
        this.mListener = listener;
        this.mTitle = title;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final EditText edtIP = new EditText(getActivity());

        edtIP.setText(Constantes.IP_DEFAULT);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle(this.mTitle)
                .setIcon(R.drawable.ic_help_black_24dp)
                .setView(edtIP)
                .setPositiveButton(getString(R.string.ip_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String serverIP = edtIP.getText().toString();
                        mListener.onComplete(Constantes.IP_OK, serverIP);
                    }
                })
                .setNegativeButton(getString(R.string.ip_negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // Create the IpDialog object and return it
        return builder.create();
    }
}
