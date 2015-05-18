package susurro.grito.tefcon.operators.renderers;

import com.pedrogomez.renderers.RendererAdapter;

import android.view.LayoutInflater;

import susurro.grito.tefcon.operators.Sms;

/**
 * @author Fernando Franco Giráldez
 */
public class TelephonyEventRendererAdapter extends RendererAdapter<Sms> {
    public TelephonyEventRendererAdapter(LayoutInflater layoutInflater) {
        super(layoutInflater, TelephonyEventRendererBuilder.build(), new TelephonyEventCollection());
    }
}
