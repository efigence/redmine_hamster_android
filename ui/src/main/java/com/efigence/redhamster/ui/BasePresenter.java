package com.efigence.redhamster.ui;

import com.annimon.stream.function.Supplier;
import com.efigence.redhamster.domain.usecase.UseCase;
import com.efigence.redhamster.domain.usecase.UseCaseArgumentless;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BasePresenter<T extends UI> implements Presenter<T> {

    public static final boolean UN_SUBSCRIBE_WHEN_NO_UI = true;
    public static final boolean IGNORE_UI = false;

    private final List<Subscription> subscriptions = new ArrayList<>();
    protected final BehaviorSubject<T> uiSubject;
    protected T ui;


    public BasePresenter(){
        uiSubject = BehaviorSubject.create();
    }

    @Override
    public void onAttach(T ui) {
        this.ui = ui;
        uiSubject.onNext(ui);
    }

    @Override
    public void onDetach() {
        final Iterator<Subscription> iterator = subscriptions.iterator();
        while (iterator.hasNext()){
            final Subscription subscription = iterator.next();
            if (!subscription.isUnsubscribed()){
                subscription.unsubscribe();
            }
            iterator.remove();
        }
        this.ui = null;
        uiSubject.onNext(null);
    }

    public <Result> Observable<Result> createObservable(final UseCaseArgumentless<Result> useCase){
        return Observable
                .create(new UseCaseExecutor<>(UN_SUBSCRIBE_WHEN_NO_UI, () -> useCase.execute()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <Result, Argument> Observable<Result> createObservable(final UseCase<Result, Argument> useCase, final Argument arg) {
        return Observable
                .create(new UseCaseExecutor<>(UN_SUBSCRIBE_WHEN_NO_UI, () -> useCase.execute(arg)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <Result> Observable<Result> createObservableOnUi(final UseCaseArgumentless<Result> useCase) {
        return subscribeOnUi(Observable.create(new UseCaseExecutor<>(IGNORE_UI, () -> useCase.execute())));
    }

    public <Result, Argument> Observable<Result> createObservableOnUi(final UseCase<Result, Argument> useCase, Argument argument) {
        return subscribeOnUi(Observable.create(new UseCaseExecutor<>(IGNORE_UI, () -> useCase.execute(argument))));
    }

    protected  <Result> Observable<Result> subscribeOnUi(Observable<Result> observable) {
        return Observable.combineLatest(observable, uiSubject, (o, u) -> new Pair<>(o, u))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(p -> p.second != null)
                .map(p -> p.first);
    }

    protected Action1<Throwable> catchException() {
        return (e) -> {
            new Thread(() -> {
                throw new RuntimeException(e);
            }).start();
        };
    }

    private class Pair<Result, T> {

        final Result first;
        final T second;

        public Pair(Result first, T second) {
            this.first = first;
            this.second = second;
        }
    }

    private class UseCaseExecutor<Result> implements Observable.OnSubscribe<Result> {

        private final boolean unSubscribeWhenNoUi;
        private final Supplier<Result> supplier;

        public UseCaseExecutor(boolean unSubscribeWhenNoUi, Supplier<Result> supplier) {
            this.unSubscribeWhenNoUi = unSubscribeWhenNoUi;
            this.supplier = supplier;
        }

        @Override
        public void call(Subscriber<? super Result> subscriber) {
            if (unSubscribeIfNeeded(subscriber)) {
                return;
            }
            subscriptions.add(subscriber);
            try {
                final Result result = supplier.get();
                if (unSubscribeIfNeeded(subscriber)) {
                    return;
                }
                subscriber.onNext(result);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        }

        private <Result> boolean unSubscribeIfNeeded(Subscriber<? super Result> subscriber) {
            if (shouldUnSubscribe(subscriber)) {
                subscriber.unsubscribe();
                return true;
            }
            return false;
        }

        private <Result> boolean shouldUnSubscribe(Subscriber<? super Result> subscriber) {
            return unSubscribeWhenNoUi && ui == null && !subscriber.isUnsubscribed();
        }
    }
}
