package com.drinksafe.manager.ui.database

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.drinksafe.manager.R

public class ListaBebidasFragmentDirections private constructor() {
  public companion object {
    public fun actionListaBebidasToDetalleBebida(): NavDirections =
        ActionOnlyNavDirections(R.id.action_listaBebidas_to_detalleBebida)

    public fun actionListaBebidasToRegistrar(): NavDirections =
        ActionOnlyNavDirections(R.id.action_listaBebidas_to_registrar)
  }
}
