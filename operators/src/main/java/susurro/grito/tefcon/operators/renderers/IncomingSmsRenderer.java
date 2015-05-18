package susurro.grito.tefcon.operators.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import susurro.grito.tefcon.operators.R;

/**
 * @author Fernando Franco Giráldez
 */
public class IncomingSmsRenderer extends TelephonyEventRenderer {

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.view_incoming_sms, parent, false);
    }
}
