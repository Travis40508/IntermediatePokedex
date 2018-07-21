package com.elkcreek.rodneytressler.intermediatepokedex.ui.PokedexView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkcreek.rodneytressler.intermediatepokedex.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PokedexFragment extends Fragment implements PokedexView {

    @OnClick(R.id.button_on)
    protected void onButtonClicked() {

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

    }
}
