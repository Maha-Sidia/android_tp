package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityWorldBinding

class WorldActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWorldBinding.inflate(layoutInflater)
        var userSelected = false // ✅ Flag local
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val continentToPaysMap = mapOf(
            "Afrique" to R.array.PaysAfr,
            "Europe" to R.array.PaysEur,
            "Asie" to R.array.PaysAsie,
            "Océanie" to R.array.PaysOc,
            "Amérique" to R.array.PaysAm
        )

        // Remplir le spinner des continents
        val continents = resources.getStringArray(R.array.continents)
        val continentsAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, continents)
        continentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerContinents.adapter = continentsAdapter

        // Lors du changement de continent
        binding.spinnerContinents.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedContinent = continents[position]
                val paysArrayId = continentToPaysMap[selectedContinent] ?: return
                val paysList = resources.getStringArray(paysArrayId)

                val paysAdapter = ArrayAdapter(
                    this@WorldActivity,
                    android.R.layout.simple_spinner_item,
                    paysList
                )
                paysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerPays.adapter = paysAdapter

                userSelected = false // 🚫 Empêche la recherche auto
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Lors du toucher sur le spinner de pays
        binding.spinnerPays.setOnTouchListener { _, _ ->
            userSelected = true
            false
        }

        // Lors de la sélection d’un pays
        binding.spinnerPays.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                if (userSelected) {
                    val selectedContinent = binding.spinnerContinents.selectedItem.toString()
                    val selectedPays = parent?.getItemAtPosition(pos).toString()

                    // Affiche le message
                    val message = getString(R.string.selection_message, selectedContinent, selectedPays)
                    binding.selectedInfo.text = message

                    // Lance la recherche
                    val url = "https://fr.wikipedia.org/wiki/${selectedPays.replace(" ", "_")}"
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

                    userSelected = false // ✅ Reset le flag
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}
