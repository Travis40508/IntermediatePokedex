package com.elkcreek.rodneytressler.intermediatepokedex.repository.database;

import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonApi;

import io.reactivex.Flowable;

public interface PokemonDatabaseService {

    void insertPokemon(PokemonApi.Pokemon pokemon);
    PokemonApi.Pokemon getPokemonByName(String name);
}
