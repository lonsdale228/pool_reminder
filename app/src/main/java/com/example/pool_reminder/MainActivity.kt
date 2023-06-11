package com.example.pool_reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pool_reminder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Services())


        binding.bottomNavView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.chemistry->replaceFragment(Chemistry())
                R.id.services->replaceFragment(Services())
                R.id.clients->replaceFragment(Clients())
                R.id.timetable->replaceFragment(Timetable())

                else->{

                }

            }
            true
        }

    }
    private fun replaceFragment(fragment:Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}