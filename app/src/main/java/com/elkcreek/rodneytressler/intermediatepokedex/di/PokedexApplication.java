package com.elkcreek.rodneytressler.intermediatepokedex.di;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.elkcreek.rodneytressler.intermediatepokedex.di.components.DaggerApplicationComponent;
import com.elkcreek.rodneytressler.intermediatepokedex.di.modules.ApplicationModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class PokedexApplication extends Application implements HasSupportFragmentInjector{

    @Inject
    protected DispatchingAndroidInjector<Fragment> fragmentInjector;


    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
