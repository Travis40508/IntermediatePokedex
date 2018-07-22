package com.elkcreek.rodneytressler.intermediatepokedex.ui.PokedexView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.intermediatepokedex.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class PokedexFragment extends Fragment implements PokedexView {

    @Inject protected PokedexPresenter presenter;
    @BindView(R.id.pokedex_screen)
    protected FrameLayout pokedexScreen;
    @BindView(R.id.pokemon_stats_screen)
    protected FrameLayout statsScreen;
    @BindView(R.id.input_pokemon_name)
    protected EditText pokemonInput;
    @BindView(R.id.pokemon_progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.image_pokemon)
    protected ImageView pokemonSpriteImage;
    @BindView(R.id.text_pokemon_height)
    protected TextView pokemonHeightText;
    @BindView(R.id.text_pokemon_weight)
    protected TextView pokemonWeightText;
    @BindView(R.id.text_pokemon_name)
    protected TextView pokemonNameText;

    @OnClick(R.id.button_on)
    protected void onButtonClicked() {
        presenter.onButtonClicked();
    }

    @OnClick(R.id.button_search)
    protected void onSearchClicked() {
        presenter.searchClicked(pokemonInput.getText().toString().toLowerCase());
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        attachView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokedex, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static PokedexFragment newInstance() {

        Bundle args = new Bundle();

        PokedexFragment fragment = new PokedexFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void attachView() {
        presenter.setView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void turnOnPokedexScreen() {
        pokedexScreen.setBackgroundColor(getResources().getColor(R.color.colorPokedexScreen));
    }

    @Override
    public void turnOnStatsScreen() {
        statsScreen.setBackgroundColor(getResources().getColor(R.color.colorScreen));
    }

    @Override
    public void turnOnPokemonSearchScreen() {
        pokemonInput.setBackgroundColor(getResources().getColor(R.color.colorScreen));
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearPokemonInput() {
        pokemonInput.getText().clear();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showPokemonSprite(String pokemonSprite) {
        Glide.with(getContext()).load(pokemonSprite).into(pokemonSpriteImage);
    }

    @Override
    public void showPokemonWeight(String pokemonWeight) {
        pokemonWeightText.setText(pokemonWeight);
    }

    @Override
    public void showPokemonHeight(String pokemonHeight) {
        pokemonHeightText.setText(pokemonHeight);
    }

    @Override
    public void showErrorToast() {
        Toast.makeText(getContext(), R.string.service_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPokemonName(String pokemonName) {
        pokemonNameText.setText(pokemonName);
    }
}
