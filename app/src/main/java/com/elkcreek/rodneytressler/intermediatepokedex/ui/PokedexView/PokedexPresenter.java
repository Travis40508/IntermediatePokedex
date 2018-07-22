package com.elkcreek.rodneytressler.intermediatepokedex.ui.PokedexView;

import android.os.AsyncTask;

import com.elkcreek.rodneytressler.intermediatepokedex.common.utils.MVPUtil;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.database.PokemonDatabaseService;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonApi;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonService;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

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

        //TODO figure out how to store pokemmon in database and check that first before performing network call
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                PokemonApi.Pokemon searchedPokemon = pokemonDatabaseService.getPokemonByName(pokemonName);
                if (searchedPokemon != null) {
                    view.hideProgressBar();
                    view.showPokemonSprite(searchedPokemon.getSpriteUrl());
                    view.showPokemonName(searchedPokemon.getPokemonName());
                    view.showPokemonWeight("Weight - " + searchedPokemon.getPokemonWeight());
                    view.showPokemonHeight("Height - " + searchedPokemon.getPokemonHeight());
                } else {
                    disposable.add(pokemonService.getPokemon(pokemonName)
                            .subscribe(pokemon -> {
                                pokemonDatabaseService.insertPokemon(pokemon);
                                searchClicked(pokemonName);
                            }, throwable -> {
                                view.showErrorToast();
                                view.hideProgressBar();
                            }));
                }
            }
        });


    }
}
