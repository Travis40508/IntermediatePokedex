package com.elkcreek.rodneytressler.intermediatepokedex.ui.PokedexView;

import com.elkcreek.rodneytressler.intermediatepokedex.common.utils.MVPUtil;

import io.reactivex.disposables.CompositeDisposable;

public class PokedexPresenter implements MVPUtil.BasePresenter<PokedexView> {

    private CompositeDisposable disposable;
    private PokedexView view;

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    @Override
    public void setView(PokedexView view) {
        this.view = view;
    }
}
