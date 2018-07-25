package com.elkcreek.rodneytressler.intermediatepokedex.ui.PokedexView;

import com.elkcreek.rodneytressler.intermediatepokedex.common.utils.MVPUtil;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.database.PokemonDatabaseService;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonApi;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class PokedexPresenter implements MVPUtil.BasePresenter<PokedexView> {

    private final PokemonService pokemonService;
    private final PokemonDatabaseService pokemonDatabaseService;
    private CompositeDisposable disposable;
    private PokedexView view;

    @Inject
    public PokedexPresenter(PokemonService pokemonService, PokemonDatabaseService pokemonDatabaseService) {
        this.pokemonService = pokemonService;
        this.pokemonDatabaseService = pokemonDatabaseService;
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

        findPokemon(pokemonName);
    }

    private Observable<PokemonApi.Pokemon> findInDatabase(String name) {
        return pokemonDatabaseService.findPokemonByName(name).toObservable();
    }

    private Observable<PokemonApi.Pokemon> fetchFromNetwork(String name) {
        return pokemonService.getPokemon(name)
                // save pokemon to database for next call
                .doOnNext(pokemonDatabaseService::insertPokemon);
    }

    private Observable<PokemonApi.Pokemon> findInDatabaseElseFetchFromNetwork(String name) {
        return findInDatabase(name)
                // if error, return empty observable
                .onErrorResumeNext(Observable.empty())
                // switch to network if empty
                .switchIfEmpty(fetchFromNetwork(name));
    }

    private Consumer<PokemonApi.Pokemon> updateUiForPokemon() {
        return pokemon -> {
            view.hideProgressBar();
            if (pokemon.getSpriteUrl() == null || pokemon.getSpriteUrl().isEmpty()) {
                pokemon.setSpriteUrl(pokemon.getPokemonSprites().getPokemonFrontSprite());
            }
            view.showPokemonSprite(pokemon.getSpriteUrl());
            view.showPokemonName(pokemon.getPokemonName());
            view.showPokemonHeight("Height - " + pokemon.getPokemonHeight());
            view.showPokemonWeight("Weight - " + pokemon.getPokemonWeight());
        };
    }

    private Consumer<Throwable> handlerError() {
        return throwable -> {
            view.hideProgressBar();
            view.showErrorToast();
        };
    }

    public void findPokemon(String name) {
        disposable.add(findInDatabaseElseFetchFromNetwork(name).subscribe(updateUiForPokemon(), handlerError()));
    }
}
