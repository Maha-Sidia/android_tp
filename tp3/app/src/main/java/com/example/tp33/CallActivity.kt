package com.example.tp33

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CallActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        val editTextPhoneNumber = findViewById<EditText>(R.id.editTextPhoneNumber)
        val buttonCall = findViewById<Button>(R.id.buttonCall)

        buttonCall.setOnClickListener {
            val phoneNumber = editTextPhoneNumber.text.toString()
            if (phoneNumber.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phoneNumber")
                startActivity(intent)
            }
        }
    }
}