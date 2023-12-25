package com.example.pagingwithkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.pagingwithkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Btn.setOnClickListener {
            if (savedInstanceState == null) {
                // If the fragment is not already added, add it
                val fragment = ListFragment()
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        fragment
                    ) // Replace with your fragment container ID
                    .addToBackStack(null)
                    .commit()
            }
        }

    }
}