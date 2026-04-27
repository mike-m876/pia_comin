package com.drinksafe.manager.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.drinksafe.manager.R
import com.drinksafe.manager.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * Actividad principal que actúa como host del Navigation Component.
 * Contiene el NavHostFragment que gestiona todos los Fragments de la app.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Prueba de conexión a Firebase Firestore
        testFirebaseConnection()
    }

    private fun testFirebaseConnection() {
        val testData = hashMapOf(
            "fecha" to System.currentTimeMillis(),
            "mensaje" to "Conexión exitosa desde DrinkSafe Manager",
            "estado" to "OK"
        )

        db.collection("logs")
            .add(testData)
            .addOnSuccessListener { documentReference ->
                Log.d("FirebaseTest", "Documento añadido con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FirebaseTest", "Error al añadir documento", e)
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
