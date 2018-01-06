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
public class DrawDialog extends DialogFragment {
    private String title;
    private OnCompleteListener mListener;
    private String mTag = Constantes.TAG_EMPTY;

    public DrawDialog(OnCompleteListener listener) {
        this.mListener = listener;
        title = Constantes.EMPATE;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setIcon(R.mipmap.ic_draw)
                .setPositiveButton(getString(R.string.error_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onComplete(Constantes.DRAW_OK, mTag);
                    }
                });
        // Create the IpDialog object and return it
        return builder.create();
    }
}

