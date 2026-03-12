package com.drinksafe.manager.ui.dashboard

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.drinksafe.manager.R

public class DashboardFragmentDirections private constructor() {
  public companion object {
    public fun actionDashboardToRegistrar(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboard_to_registrar)

    public fun actionDashboardToListaBebidas(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboard_to_listaBebidas)
  }
}
