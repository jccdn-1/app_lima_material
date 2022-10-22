package com.example.lima_material

import android.content.ContentValues.TAG
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

                auth.signInWithEmailAndPassword(
                    binding.editTextUsuario.text.toString(),
                    binding.editTextSenha.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success")
                            val user = auth.currentUser
                            Toast.makeText(baseContext, "Autenticação efetuada.",
                                Toast.LENGTH_SHORT).show()
                            //updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.exception)
                            Toast.makeText(baseContext, "Erro de autenticação.",
                                Toast.LENGTH_SHORT).show()
                            //updateUI(null)

        }
    }

    //public override fun onStart() {
        //super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        //val currentUser = auth.currentUser
        //updateUI(currentUser)
    }
    }
}
