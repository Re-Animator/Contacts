package com.reanimator.contacts

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.reanimator.contacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this) {
            finishAndRemoveTask()
        }

        supportFragmentManager.commit {
            replace(R.id.left_panel_fragment_container, ContactsListFragment())
            setReorderingAllowed(true)
        }
    }
}