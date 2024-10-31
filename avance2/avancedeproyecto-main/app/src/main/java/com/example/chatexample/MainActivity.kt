package com.example.chatexample

import fragment.FragmentChats
import fragment.FragmentProfile
import fragment.FragmentUsers
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chatexample.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Verificación de autenticación
        if (firebaseAuth.currentUser == null) {
            goToLogin()
        }

        // Configuración del botón de logout
        binding.logoutButton.setOnClickListener {
            firebaseAuth.signOut() // Cierra la sesión
            goToLogin() // Redirige al login
        }

        // Configuración de la navegación y fragmentos
        viewFragmentProfile()
        binding.bottomNV.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_profile -> {
                    viewFragmentProfile()
                    true
                }
                R.id.item_users -> {
                    viewFragmentUsers()
                    true
                }
                R.id.item_chats -> {
                    viewFragmentChats()
                    true
                }
                else -> false
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this, OptionsLoginActivity::class.java))
        finish() // Finaliza MainActivity para evitar volver sin autenticación
    }

    private fun viewFragmentProfile() {
        binding.tvTitle.text = "Profile"
        val fragment = FragmentProfile()
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentFL.id, fragment)
            .commit()
    }

    private fun viewFragmentUsers() {
        binding.tvTitle.text = "Users"
        val fragment = FragmentUsers()
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentFL.id, fragment)
            .commit()
    }

    private fun viewFragmentChats() {
        binding.tvTitle.text = "Chats"
        val fragment = FragmentChats()
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentFL.id, fragment)
            .commit()
    }
}
