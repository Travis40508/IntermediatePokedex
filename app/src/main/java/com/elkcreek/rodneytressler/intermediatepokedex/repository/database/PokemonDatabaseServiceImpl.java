package com.elkcreek.rodneytressler.intermediatepokedex.repository.database;

import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonApi;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonService;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PokemonDatabaseServiceImpl implements PokemonDatabaseService {

    private final PokemonDatabase pokemonDatabase;

    public PokemonDatabaseServiceImpl(PokemonDatabase pokemonDatabase) {
        this.pokemonDatabase = pokemonDatabase;
    }

    @Override
    public void insertPokemon(PokemonApi.Pokemon pokemon) {
        Observable.just(pokemonDatabase)
                .subscribeOn(Schedulers.io())
                .subscribe(pokemonDatabaseOffMainThread -> pokemonDatabaseOffMainThread.pokemonDao().insertPokemon(pokemon));
    }

    @Override
    public Single<PokemonApi.Pokemon> findPokemonByName(String pokemonName) {
        return pokemonDatabase.pokemonDao().findPokemonByName(pokemonName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
