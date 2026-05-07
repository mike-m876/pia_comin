package com.drinksafe.manager.ui.login

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.drinksafe.manager.R

public class LoginFragmentDirections private constructor() {
  public companion object {
    public fun actionLoginToDashboard(): NavDirections =
        ActionOnlyNavDirections(R.id.action_login_to_dashboard)

    public fun actionLoginToRegister(): NavDirections =
        ActionOnlyNavDirections(R.id.action_login_to_register)
  }
}
