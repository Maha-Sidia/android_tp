package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityCitiesBinding

class CitiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityCitiesBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val pays = arrayOf(
            "Portugal", "Espagne", "France", "Italie", "Allemagne", "Autriche", "Suisse",
            "Denmark", "Suède", "Norvège", "Finlande", "Islande", "Turquie", "Grèce", "Chypre",
            "Luxembourg", "Russie", "Ukraine", "Serbie", "Bosnie")

        val adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,pays)
        binding.listeview.adapter=adapter

        binding.listeview.setOnItemClickListener { adapter, view, i, l ->
           val selected_city=pays[i]
           binding.textView.text="la ville sélictionnée est :  $selected_city"
            startActivity(Intent(Intent.ACTION_VIEW,Uri.parse("http://www.google.fr/search?q= $selected_city")))


        }
    }
}