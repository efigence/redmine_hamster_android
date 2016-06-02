package com.efigence.redhamster.ui;

public class BasePresenter<T extends UI> implements Presenter<T> {

    protected T ui;

    @Override
    public void onAttach(T ui) {
        this.ui = ui;
    }

    @Override
    public void onDetach() {
        this.ui = null;
    }
}
