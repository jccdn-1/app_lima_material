package com.example.lima_material

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.lima_material.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var googleSignInCliente: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //vinculação do binding com o arquivo de layout de acordo com o nome da classe através deste metodo.
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = Firebase.auth

        //personalização do texto que irá aparecer no botão Google na tela de login
        val txt_btn_google = binding.buttonGoogle.getChildAt(0) as TextView
        txt_btn_google.text = "Fazer login com sua Conta Google"


        binding.buttonEntrar.setOnClickListener {

            if(binding.editTextUsuario.text.isNullOrEmpty()) {
                Toast.makeText(
                    baseContext,"Campo e-mail vazio ou inválido.",
                    Toast.LENGTH_LONG
                ).show()
            } else if (binding.editTextSenha.text.isNullOrEmpty()) {
                Toast.makeText(
                    baseContext,"Campo senha vazio ou inválido.",
                    Toast.LENGTH_LONG
                ).show()
            } else loginUsuarioSenha(
                binding.editTextUsuario.text.toString(),
                binding.editTextSenha.text.toString())

        }

    }

    private fun loginUsuarioSenha(usuario: String, senha: String) {
        auth.signInWithEmailAndPassword(
            usuario,
            senha
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    abrirTelaInicial()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCustomToken:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Erro de autenticação.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)

                }
            }
    }

    fun abrirTelaInicial() {

        Toast.makeText(
            baseContext, "Autenticação efetuada.",
            Toast.LENGTH_SHORT).show()

        binding.editTextUsuario.text.clear()
        binding.editTextSenha.text.clear()

        val intent = Intent(this, TelaInicialActivity::class.java)

        startActivity(intent)

        finish()

    }

}