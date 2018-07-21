package com.elkcreek.rodneytressler.intermediatepokedex.ui.MainActivityView;

import com.elkcreek.rodneytressler.intermediatepokedex.common.utils.MVPUtil;

import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter implements MVPUtil.BasePresenter<MainView> {

    private CompositeDisposable disposable;
    private MainView view;

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    @Override
    public void setView(MainView view) {
        this.view = view;
        if(view != null) {
            view.setPokedexFragment();
        }
    }
}
