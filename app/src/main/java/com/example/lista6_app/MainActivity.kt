package com.example.lista6_app

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btmNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        if(savedInstanceState == null){
            btmNav.selectedItemId = R.id.btm_center
        } else {
            btmNav.selectedItemId = savedInstanceState.getInt("btmElem", R.id.btm_center)
        }

    }

    override fun onBackPressed() {
        val btmNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        when(btmNav.selectedItemId) {
            R.id.btm_center -> super.onBackPressed()
            R.id.btm_right -> if(!backToRight) {
                btmNav.selectedItemId = R.id.btm_center
            }
            else -> btmNav.selectedItemId = R.id.btm_center
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        val btmNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        outState.putInt("btmElem", btmNav.selectedItemId)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        var backToRight = false
    }
}