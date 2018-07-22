package com.elkcreek.rodneytressler.intermediatepokedex.ui.PokedexView;

import com.elkcreek.rodneytressler.intermediatepokedex.common.utils.MVPUtil;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonService;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PokedexPresenter implements MVPUtil.BasePresenter<PokedexView> {

    private final PokemonService pokemonService;
    private CompositeDisposable disposable;
    private PokedexView view;

    @Inject
    public PokedexPresenter(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

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

    public void onButtonClicked() {
        view.turnOnPokedexScreen();
        view.turnOnStatsScreen();
        view.turnOnPokemonSearchScreen();
    }

    public void searchClicked(String pokemonName) {
        view.showProgressBar();
        view.clearPokemonInput();
        disposable.add(pokemonService.getPokemon(pokemonName)
        .subscribe(pokemon -> {
            view.hideProgressBar();
            view.showPokemonSprite(pokemon.getPokemonSprites().getPokemonFrontSprite());
            view.showPokemonName(pokemon.getPokemonName());
            view.showPokemonWeight("Weight - " + pokemon.getPokemonWeight());
            view.showPokemonHeight("Height - " + pokemon.getPokemonHeight());
        }, throwable -> {
            view.showErrorToast();
            view.hideProgressBar();
        }));
    }
}
