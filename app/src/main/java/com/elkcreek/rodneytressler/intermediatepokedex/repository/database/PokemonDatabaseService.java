package com.elkcreek.rodneytressler.intermediatepokedex.repository.database;

import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonApi;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface PokemonDatabaseService {

    void insertPokemon(PokemonApi.Pokemon pokemon);
    Single<PokemonApi.Pokemon> findPokemonByName(String pokemonName);
}
