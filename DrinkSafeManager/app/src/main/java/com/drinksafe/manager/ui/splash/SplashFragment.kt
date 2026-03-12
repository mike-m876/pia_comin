package com.drinksafe.manager.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.drinksafe.manager.R
import com.drinksafe.manager.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Splash Screen de DrinkSafe Manager.
 * Muestra el logo y nombre del sistema durante 2.5 segundos
 * con animaciones de entrada, luego navega al Dashboard.
 */
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciarAnimaciones()
        navegarAlDashboard()
    }

    /** Inicia las animaciones del splash con efecto de entrada suave */
    private fun iniciarAnimaciones() {
        // Animación del logo: escala + fade in
        val animLogo = AnimationSet(true).apply {
            addAnimation(ScaleAnimation(0.5f, 1f, 0.5f, 1f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f).apply {
                duration = 800
            })
            addAnimation(AlphaAnimation(0f, 1f).apply { duration = 800 })
        }
        binding.ivLogo.startAnimation(animLogo)

        // Animación del título con delay
        val animTitulo = AlphaAnimation(0f, 1f).apply {
            duration = 700
            startOffset = 500
            fillAfter = true
        }
        binding.tvNombreApp.startAnimation(animTitulo)

        // Animación del subtítulo con más delay
        val animSubtitulo = AlphaAnimation(0f, 1f).apply {
            duration = 700
            startOffset = 800
            fillAfter = true
        }
        binding.tvSubtitulo.startAnimation(animSubtitulo)

        // Animación de la barra de carga
        val animLoader = AlphaAnimation(0f, 1f).apply {
            duration = 500
            startOffset = 1200
            fillAfter = true
        }
        binding.progressBar.startAnimation(animLoader)
    }

    /** Navega al Dashboard después del tiempo del splash */
    private fun navegarAlDashboard() {
        lifecycleScope.launch {
            delay(2500) // 2.5 segundos de splash
            if (isAdded) {
                findNavController().navigate(R.id.action_splash_to_dashboard)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
