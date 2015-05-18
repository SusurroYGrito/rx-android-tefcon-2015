package susurro.grito.eventthrottling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private CompositeSubscription subscription = new CompositeSubscription();
    private Observable<OnTextChangeEvent> textChangeObservable;
    private EditText editTextUnthrottled;
    private EditText editTextThrottled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
        subscription.add(
                textChangeObservable
                        .subscribe(
                                event -> editTextUnthrottled.setText(event.text())
                        )
        );
        subscription.add(
                textChangeObservable
                        .throttleLast(300, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                event -> {
                                    editTextThrottled.setText(event.text());
                                }
                        )
        );
    }

    @Override
    protected void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }

    private void initializeComponents() {
        editTextThrottled = (EditText) findViewById(R.id.edit_throttled);
        editTextUnthrottled = (EditText) findViewById(R.id.edit_unthrottled);
        textChangeObservable = WidgetObservable.text((TextView) findViewById(R.id.edit_text));
    }
}
