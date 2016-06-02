package com.efigence.redhamster.ui;

public interface Presenter<T extends UI> {

    void onAttach(T ui);
    void onDetach();

}
