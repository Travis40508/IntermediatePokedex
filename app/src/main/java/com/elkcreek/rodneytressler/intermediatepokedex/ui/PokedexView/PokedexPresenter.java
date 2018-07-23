package com.elkcreek.rodneytressler.intermediatepokedex.ui.PokedexView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.elkcreek.rodneytressler.intermediatepokedex.common.utils.MVPUtil;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.database.PokemonDatabaseService;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonApi;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

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

        checkDatabase(pokemonName);

    }

    @SuppressLint("CheckResult")
    private void checkDatabase(String pokemonName) {
        pokemonDatabaseService.findPokemonByName(pokemonName)
                .subscribe(pokemon -> {
                    view.hideProgressBar();
                    view.clearPokemonInput();
                    view.showPokemonSprite(pokemon.getSpriteUrl());
                    view.showPokemonName(pokemon.getPokemonName());
                    view.showPokemonHeight("Height - " + pokemon.getPokemonHeight());
                    view.showPokemonWeight("Weight - " + pokemon.getPokemonWeight());
                }, throwable -> {
                    performNetworkCall(pokemonName);
                });


    }

    private void performNetworkCall(String pokemonName) {
        disposable.add(pokemonService.getPokemon(pokemonName)
                .subscribe(pokemon -> {
                    view.hideProgressBar();
                    view.clearPokemonInput();
                    pokemon.setSpriteUrl(pokemon.getPokemonSprites().getPokemonFrontSprite());
                    view.showPokemonSprite(pokemon.getSpriteUrl());
                    view.showPokemonName(pokemon.getPokemonName());
                    view.showPokemonHeight("Height - " + pokemon.getPokemonHeight());
                    view.showPokemonWeight("Weight - " + pokemon.getPokemonWeight());
                    pokemonDatabaseService.insertPokemon(pokemon);
                }, throwable -> {
                    view.hideProgressBar();
                    view.showErrorToast();
                }));
    }

}
