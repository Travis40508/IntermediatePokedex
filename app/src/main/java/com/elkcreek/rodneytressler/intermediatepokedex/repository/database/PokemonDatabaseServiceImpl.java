package com.elkcreek.rodneytressler.intermediatepokedex.repository.database;

import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonApi;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonService;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PokemonDatabaseServiceImpl implements PokemonDatabaseService {

    private final PokemonDatabase pokemonDatabase;

    public PokemonDatabaseServiceImpl(PokemonDatabase pokemonDatabase) {
        this.pokemonDatabase = pokemonDatabase;
    }

    @Override
    public void insertPokemon(PokemonApi.Pokemon pokemon) {
        pokemonDatabase.pokemonDao().insertPokemon(pokemon);
    }

    @Override
    public PokemonApi.Pokemon getPokemonByName(String name) {
        return pokemonDatabase.pokemonDao().findPokemonByName(name);
    }
}
