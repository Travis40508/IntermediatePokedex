package com.elkcreek.rodneytressler.intermediatepokedex.repository.network;

import io.reactivex.Observable;

public interface PokemonService {

    Observable<PokemonApi.Pokemon> getPokemon(String pokemonName);
}
