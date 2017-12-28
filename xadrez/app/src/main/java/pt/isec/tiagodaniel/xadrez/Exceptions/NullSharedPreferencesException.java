package pt.isec.tiagodaniel.xadrez.Exceptions;

import android.app.Activity;

import pt.isec.tiagodaniel.xadrez.R;

/**
 * Created by drmoreira on 28-12-2017.
 */

public class NullSharedPreferencesException extends Exception {

    private String error;

    public NullSharedPreferencesException(Activity activity) {
        this.error = activity.getString(R.string.null_shared_preferences);

    }

    @Override
    public String toString() {
        return this.error;
    }
}
