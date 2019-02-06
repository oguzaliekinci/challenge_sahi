package com.sahibinden.challenge.base;

import android.content.Context;
import android.support.v4.app.FragmentManager;

public interface BaseFragmentManager {
    Context getFragmentContext();

    FragmentManager getHostFragmentManager();
}
