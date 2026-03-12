package com.drinksafe.manager.ui.splash

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.drinksafe.manager.R

public class SplashFragmentDirections private constructor() {
  public companion object {
    public fun actionSplashToDashboard(): NavDirections =
        ActionOnlyNavDirections(R.id.action_splash_to_dashboard)
  }
}
