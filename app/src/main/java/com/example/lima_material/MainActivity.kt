package com.example.lima_material

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lima_material.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1077298632714-n12cm2prgtddlnc92tuqgu52kf81qov8.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInCliente = GoogleSignIn.getClient(this, gso)


        //personalização do texto que irá aparecer no botão Google na tela de login
        val txt_btn_google = binding.buttonGoogle.getChildAt(0) as TextView
        txt_btn_google.text = getString(R.string.text_fazer_login_google)


        binding.buttonEntrar.setOnClickListener {

            if(TextUtils.isEmpty(binding.editTextUsuario.text)) {
                binding.editTextUsuario.error =
                    "Campo e-mail não pode estar em branco."
            } else if (TextUtils.isEmpty(binding.editTextSenha.text)) {
                binding.editTextSenha.error =
                    "Campo senha não pode estar em branco."
            } else loginUsuarioSenha(
                binding.editTextUsuario.text.toString(),
                binding.editTextSenha.text.toString())
        }

        binding.buttonGoogle.setOnClickListener {
            signIn()
        }

    }

    private fun signIn() {

        val intent = googleSignInCliente.signInIntent

        abrirActivity.launch(intent)

    }

    var abrirActivity = registerForActivityResult(

        ActivityResultContracts.StartActivityForResult()

    ) {

        result: ActivityResult ->

        if(result.resultCode == RESULT_OK){
            val intent = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)

            try{

                val conta = task.getResult(ApiException::class.java)
                loginComGoogle(conta.idToken!!)

        } catch (exception: ApiException) {


         }
        }
    }

    private fun loginComGoogle(token: String) {

        val credencial = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credencial).addOnCompleteListener(this) {

            task: Task<AuthResult> ->

            if (task.isSuccessful) {
                abrirTelaInicial()
            } else {
                Toast.makeText(
                    baseContext, "Erro de autenticação.",
                    Toast.LENGTH_SHORT).show()
            }

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