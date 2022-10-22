package com.example.lima_material

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lima_material.databinding.ActivityMainBinding
import com.example.lima_material.databinding.ActivityTelaInicialBinding

class TelaInicialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTelaInicialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTelaInicialBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}