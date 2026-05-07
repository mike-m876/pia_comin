package com.drinksafe.manager.ui.login

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.drinksafe.manager.R

public class RegisterFragmentDirections private constructor() {
  public companion object {
    public fun actionRegisterToDashboard(): NavDirections =
        ActionOnlyNavDirections(R.id.action_register_to_dashboard)
  }
}
