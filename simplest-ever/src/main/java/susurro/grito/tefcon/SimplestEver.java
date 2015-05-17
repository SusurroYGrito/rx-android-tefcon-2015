package susurro.grito.tefcon;

import java.util.Scanner;

import rx.Observable;

public class SimplestEver {
    public static void main(String[] args) {
        Observable<Integer> numbers = Observable.create(subscriber -> {
            subscriber.onNext(1);
            subscriber.onNext(2);
            subscriber.onNext(3);
            subscriber.onCompleted();
        });

        System.out.println("Press any key to subscribe");
        new Scanner(System.in).nextLine();

        numbers.subscribe(
                value -> System.out.println(String.format("Received value %d", value)),
                error -> System.out.println(String.format("Sequence faulted with %s", error)),
                () -> System.out.println("Sequence terminated")
        );
        System.out.println("Press any key to exit");
        new Scanner(System.in).nextLine();
    }
}
