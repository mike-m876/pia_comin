package com.drinksafe.manager.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.drinksafe.manager.DrinkSafeApplication
import com.drinksafe.manager.R
import com.drinksafe.manager.databinding.FragmentRegisterBinding
import com.drinksafe.manager.viewmodel.PerfilViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    private val perfilViewModel: PerfilViewModel by activityViewModels {
        val repo = (requireActivity().application as DrinkSafeApplication).perfilRepository
        PerfilViewModel.Factory(repo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if (password.length >= 6) {
                    registerUser(username, email, password)
                } else {
                    Toast.makeText(requireContext(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGoToLogin.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun registerUser(username: String, email: String, password: String) {
        binding.progressBar.isVisible = true
        binding.btnRegister.isEnabled = false

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()

                    user?.updateProfile(profileUpdates)

                    // Guardar en Firestore
                    val userMap = hashMapOf(
                        "uid" to user?.uid,
                        "username" to username,
                        "email" to email,
                        "fechaRegistro" to System.currentTimeMillis()
                    )

                    db.collection("usuarios").document(user?.uid!!).set(userMap)
                        .addOnSuccessListener {
                            // Sincronizar localmente para el Dashboard
                            perfilViewModel.actualizarNombreUsuario(username)
                            
                            binding.progressBar.isVisible = false
                            findNavController().navigate(R.id.action_register_to_dashboard)
                        }
                } else {
                    binding.progressBar.isVisible = false
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
