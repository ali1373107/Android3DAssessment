package com.example.myapplication
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf


class Addpoi : AppCompatActivity() {
    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.poi_add)
        val add = findViewById<Button>(R.id.btnAdd)
        add.setOnClickListener {
            val name = findViewById<EditText>(R.id.etName)
            val nameValue = name.text.toString()
            val type = findViewById<EditText>(R.id.etType)
            val typeValue = type.text.toString()
            val description = findViewById<EditText>(R.id.etDescription)
            val decValue = description.text.toString()
            val intent = Intent()
            val bundle = bundleOf(
                "com.example.myapplication.nameValue" to nameValue,
                "com.example.myapplication.typeValue" to typeValue,
                "com.example.myapplication.decValue" to decValue
            )
            intent.putExtras(bundle)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}