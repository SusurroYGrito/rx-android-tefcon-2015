package susurro.grito.tefcon.operators;

import com.pedrogomez.renderers.RendererAdapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import susurro.grito.tefcon.operators.renderers.TelephonyEventRendererAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private RendererAdapter<Sms> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeComponents();

        Observable<TelephonyEvent> realTimeSequence1 = Observable
                .interval(3, TimeUnit.SECONDS)
                .map(i ->
                                new Call(
                                        new Date(),
                                        "John",
                                        i,
                                        true
                                )
                );

        Observable<TelephonyEvent> realTimeSequence2 = Observable
                .interval(3, TimeUnit.SECONDS)
                .map(i ->
                                new Sms(
                                        new Date(),
                                        "John",
                                        String.format("Dude sent you %d sms", i),
                                        true
                                )
                );

        Observable<TelephonyEvent> realTimeSequence3 = Observable
                .interval(3, TimeUnit.SECONDS)
                .map(i ->
                                new Sms(
                                        new Date(),
                                        "Eric",
                                        String.format("Yo, Eric?  %d sms", i),
                                        false
                                )
                );

        Observable<TelephonyEvent> oldEvents = Observable.from(existingTelephonyEvents());
        oldEvents
                .mergeWith(realTimeSequence1)
                .mergeWith(realTimeSequence2)
                .mergeWith(realTimeSequence3)
                .ofType(Sms.class)
                .filter(sms -> sms.getPartner().equalsIgnoreCase("John")) //Change to Eric for demo
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addSms);
    }

    private Iterable<TelephonyEvent> existingTelephonyEvents() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -4);
        return Arrays.asList(
                new Call(
                        addDays(-5),
                        "John",
                        5,
                        true
                ),
                new Sms(
                        addDays(-4),
                        "John",
                        "Going to TefCon?",
                        true
                ),
                new Call(
                        addDays(-3),
                        "Eric",
                        5,
                        false
                ),
                new Sms(
                        addDays(-2),
                        "Eric",
                        "Eric, going to TefCon with John",
                        false
                ),
                new Sms(
                        addDays(-1),
                        "John",
                        "Sure, I'll meet you there",
                        false
                )
        );
    }

    private void addSms(Sms sms) {
        adapter.add(sms);
        adapter.notifyDataSetChanged();
    }

    private void initializeComponents() {
        setContentView(R.layout.activity_main);
        adapter = new TelephonyEventRendererAdapter(LayoutInflater.from(this));
        list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);
    }

    private Date addDays(long toAdd) {
        return new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(toAdd));
    }
}
