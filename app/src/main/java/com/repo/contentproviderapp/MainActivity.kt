package com.repo.contentproviderapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.repo.contentproviderapp.Model.RandomTextData
import com.repo.contentproviderapp.Adapter.RandomTextAdapter


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: RandomTextAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.topAppBar)) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.setPadding(0, statusBarHeight, 0, 0)
            insets
        }


        val editText = findViewById<EditText>(R.id.editTextLength)
        val buttonGenerate = findViewById<Button>(R.id.buttonGenerate)
        val buttonDeleteAll = findViewById<Button>(R.id.buttonDeleteAll)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewStrings)
        val stringList = mutableListOf<RandomTextData>()

        val onDeleteClick: (Int) -> Unit = { position ->
            stringList.removeAt(position)
            adapter.notifyItemRemoved(position)
        }

        adapter = RandomTextAdapter(stringList, onDeleteClick)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        buttonGenerate.setOnClickListener {
            val lengthInput = editText.text.toString().toIntOrNull()
            if (lengthInput != null) {
                val result = queryRandomString(this, lengthInput)
                result?.let {
                    stringList.add(0, it)
                    adapter.notifyItemInserted(0)
                    recyclerView.scrollToPosition(0)
                }
                    ?: run {
                    Toast.makeText(this, "No result returned", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDeleteAll.setOnClickListener {
            stringList.clear()
            adapter.notifyDataSetChanged()
        }
    }
}
