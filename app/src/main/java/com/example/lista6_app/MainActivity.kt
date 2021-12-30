package com.example.lista6_app

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btmNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        btmNav.selectedItemId = R.id.btm_center
    }

    override fun onBackPressed() {
        val btmNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        when(btmNav.selectedItemId) {
            R.id.btm_center -> super.onBackPressed()
            else -> btmNav.selectedItemId = R.id.btm_center
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}