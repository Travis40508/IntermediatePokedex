package com.elkcreek.rodneytressler.intermediatepokedex.repository.network;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PokemonServiceImpl implements  PokemonService {


    private final PokemonApi pokemonApi;

    public PokemonServiceImpl(PokemonApi pokemonApi) {
        this.pokemonApi = pokemonApi;
    }

    @Override
    public Observable<PokemonApi.Pokemon> getPokemon(String pokemonName) {
        return pokemonApi.getPokemon(pokemonName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
