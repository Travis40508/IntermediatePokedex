package com.elkcreek.rodneytressler.intermediatepokedex.ui.PokedexView;

import com.elkcreek.rodneytressler.intermediatepokedex.repository.database.PokemonDatabaseService;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonApi;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonService;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.*;

public class PokedexPresenterTest {

    private PokedexPresenter presenter;
    @Mock
    private PokemonService pokemonService;

    @Mock
    private PokemonDatabaseService pokemonDatabaseService;

    @Mock
    private PokedexView pokedexView;

    private PokemonApi.Pokemon pokemon;
    private String pokemonName;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = Mockito.spy(new PokedexPresenter(pokemonService, pokemonDatabaseService));
        presenter.setView(pokedexView);
        presenter.subscribe();

        pokemonName = "Bulbasaur";
        pokemon = new PokemonApi.Pokemon();
        pokemon.setSpriteUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png");
    }

    @Test
    public void verifyOnButtonTurnsPokedexScreenOn() {
        presenter.onButtonClicked();

        Mockito.verify(pokedexView).turnOnPokedexScreen();
    }

    @Test
    public void verifyOnButtonTurnsOnStatsScreen() {
        presenter.onButtonClicked();

        Mockito.verify(pokedexView).turnOnStatsScreen();
    }

    @Test
    public void verifyOnButtonTurnsOnSearchScreen() {
        presenter.onButtonClicked();

        Mockito.verify(pokedexView).turnOnPokemonSearchScreen();
    }

    @Test
    public void verifySearchButtonShowsProgressBar() {
        Mockito.when(pokemonDatabaseService.findPokemonByName(pokemonName)).thenReturn(Single.just(pokemon));
        presenter.searchClicked(pokemonName);

        Mockito.verify(pokedexView).showProgressBar();
    }

    @Test
    public void verifySearchButtonClearsEditTest() {
        Mockito.when(pokemonDatabaseService.findPokemonByName(pokemonName)).thenReturn(Single.just(pokemon));
        presenter.searchClicked(pokemonName);

        Mockito.verify(pokedexView).clearPokemonInput();
    }

    @Test
    public void verifySpriteShownWhenDataseContainsPokemon() {
        Mockito.when(pokemonDatabaseService.findPokemonByName(pokemonName)).thenReturn(Single.just(pokemon));
        presenter.checkDatabase(pokemonName);

        Mockito.verify(pokedexView).showPokemonSprite(pokemon.getSpriteUrl());
    }

    @Test
    public void verifyHideProgressBarOnSuccessfulDatabasePokemonReturn() {
        Mockito.when(pokemonDatabaseService.findPokemonByName(pokemonName)).thenReturn(Single.just(pokemon));
        presenter.checkDatabase(pokemonName);

        Mockito.verify(pokedexView).hideProgressBar();
    }

    @Test
    public void verifyShowPokemonNameOnSuccessfulPokemonDatabaseReturn() {
        Mockito.when(pokemonDatabaseService.findPokemonByName(pokemonName)).thenReturn(Single.just(pokemon));
        presenter.checkDatabase(pokemonName);

        Mockito.verify(pokedexView).showPokemonName(pokemon.getPokemonName());
    }

    @Test
    public void verifyShowWeightOnSuccessfulPokemonDatabaseReturn() {
        Mockito.when(pokemonDatabaseService.findPokemonByName(pokemonName)).thenReturn(Single.just(pokemon));
        presenter.checkDatabase(pokemonName);

        Mockito.verify(pokedexView).showPokemonWeight("Weight - " + pokemon.getPokemonWeight());
    }

    @Test
    public void verifyShowHeightOnSuccessfulPokemonDatabaseReturn() {
        Mockito.when(pokemonDatabaseService.findPokemonByName(pokemonName)).thenReturn(Single.just(pokemon));
        presenter.checkDatabase(pokemonName);

        Mockito.verify(pokedexView).showPokemonHeight("Height - " + pokemon.getPokemonWeight());
    }

    @Test(expected = Exception.class)
    public void verifyThrowableCallWheneverUnSuccessfulPokemonDatabaseReturn() {
        Mockito.when(pokemonDatabaseService.findPokemonByName(pokemonName)).thenThrow(Exception.class);
        presenter.checkDatabase(pokemonName);
    }

    @Test
    public void verifySpriteShownWhenNetworkCallSuccessful() {
        Mockito.when(pokemonService.getPokemon(pokemonName)).thenReturn(Observable.just(pokemon));
        presenter.performNetworkCall(pokemonName);

        Mockito.verify(pokedexView).showPokemonSprite(pokemon.getSpriteUrl());
    }

}