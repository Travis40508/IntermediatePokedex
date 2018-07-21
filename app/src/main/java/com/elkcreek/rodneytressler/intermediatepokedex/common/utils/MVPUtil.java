package com.elkcreek.rodneytressler.intermediatepokedex.common.utils;

public class MVPUtil {

    public interface BasePresenter<T> {
        void subscribe();
        void unsubscribe();
        void setView(T view);
    }

    public interface BaseView {
        void attachView();
    }
}
