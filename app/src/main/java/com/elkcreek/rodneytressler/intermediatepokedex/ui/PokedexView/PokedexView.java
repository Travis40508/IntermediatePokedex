package com.elkcreek.rodneytressler.intermediatepokedex.ui.PokedexView;

import com.elkcreek.rodneytressler.intermediatepokedex.common.utils.MVPUtil;

public interface PokedexView extends MVPUtil.BaseView {
    void turnOnPokedexScreen();

    void turnOnStatsScreen();

    void turnOnPokemonSearchScreen();

    void showProgressBar();

    void clearPokemonInput();

    void hideProgressBar();

    void showPokemonSprite(String pokemonSprite);

    void showPokemonWeight(String pokemonWeight);

    void showPokemonHeight(String pokemonHeight);

    void showErrorToast();

    void showPokemonName(String pokemonName);
}
