package com.elkcreek.rodneytressler.intermediatepokedex.ui.MainActivityView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.elkcreek.rodneytressler.intermediatepokedex.R;
import com.elkcreek.rodneytressler.intermediatepokedex.ui.PokedexView.PokedexFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        attachView();
    }

    @Override
    public void attachView() {
        presenter = new MainPresenter();
        presenter.setView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setPokedexFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, PokedexFragment.newInstance()).commit();
    }
}
