package com.example.lima_material

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.lima_material.databinding.ActivityTelaCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class TelaCadastroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityTelaCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTelaCadastroBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = Firebase.auth

        binding.buttonCriarConta.setOnClickListener {

            if (TextUtils.isEmpty(binding.editTextEmailCadastro.text)) {
                binding.editTextEmailCadastro.error =
                    "Campo e-mail não pode ficar em branco"
            } else if (TextUtils.isEmpty(binding.editTextCriarSenha.text)) {
                binding.editTextCriarSenha.error =
                    "Campo senha não pode ficar em branco"
            } else
                criarConta(
                    binding.editTextEmailCadastro.text.toString(),
                    binding.editTextCriarSenha.text.toString())
        }
}


    private fun criarConta(email: String, senha: String) {

        auth.createUserWithEmailAndPassword(
            email,
            senha
        ).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                telaInicial()
            } else {
                Log.w(TAG, "createUserWithEmailAndPassword:failure",task.exception)
                Toast.makeText(
                    baseContext, "Erro de autenticação",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun telaInicial() {

        val intent = Intent(this, TelaInicialActivity::class.java)

        startActivity(intent)

        finish()

    }

}

