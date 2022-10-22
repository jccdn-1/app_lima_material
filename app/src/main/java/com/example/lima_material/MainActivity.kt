package com.example.lima_material

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.lima_material.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;
    private lateinit var googleSignInCliente: GoogleSignInClient;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        //vinculação do binding com o arquivo de layout de acordo com o nome da classe através deste metodo.
        binding = ActivityMainBinding.inflate(layoutInflater);

        //variavel de visualização
        val view = binding.root;

        setContentView(view);
        v
        val txt_btn_google = binding.buttonGoogle.getChildAt(0) as TextView;
        txt_btn_google.text = "Fazer login com sua Conta Google"
    }
}