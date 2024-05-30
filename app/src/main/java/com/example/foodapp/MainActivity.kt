package com.example.foodapp

import HaftalikPlanFragment
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.util.Log
import android.view.inputmethod.InputBinding
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.replace

import androidx.navigation.NavController
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.model.Besin
import com.example.foodapp.model.Egzersiz
import com.google.firebase.firestore.FirebaseFirestore
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation


class MainActivity : AppCompatActivity(), BesinFragment.SelectedItemsListener,
    EgzersizFragment.SelectedEgzersizListener {

    private lateinit var binding: ActivityMainBinding
    private var selectedItems: List<Besin> = emptyList()
    private var selectedEgzersiz: List<Egzersiz> = emptyList()

    override fun onSelectedItemsList(selectedItems: List<Besin>) {
        this.selectedItems = selectedItems
    }

    override fun onSelectedEgzersizList(selectedEgzersiz: List<Egzersiz>) {
        this.selectedEgzersiz = selectedEgzersiz
    }

    fun getSelectedItems(): List<Besin> = selectedItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragmentContainer, SplashScreenFragment())
            }
        }

        binding.bottomNavigation.visibility = View.GONE

        Handler(Looper.getMainLooper()).postDelayed({
            binding.bottomNavigation.visibility = View.VISIBLE
        }, SPLASH_SCREEN_DURATION)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.apply {
            add(
                CurvedBottomNavigation.Model(
                    1,
                    "Main",
                    R.drawable._290849_document_done_excellent_list_note_icon
                )
            )
            add(
                CurvedBottomNavigation.Model(
                    2,
                    "",
                    R.drawable._983529_appointment_calendar_coronavirus_date_event_icon
                )
            )
            add(
                CurvedBottomNavigation.Model(
                    3,
                    "Kategori",
                    R.drawable._515151_body_exercise_fitness_health_meditation_icon
                )
            )
            add(
                CurvedBottomNavigation.Model(
                    4,
                    "",
                    R.drawable._042280_dumbell_gym_healthy_life_take_exercise_training_icon
                )
            )
            add(CurvedBottomNavigation.Model(5, "Su", R.drawable._244992_fruit_fruits_grape_icon))

            setOnClickMenuListener { item ->
                when (item.id) {
                    1 -> replaceFragment(MainFragment())
                    2 -> replaceFragment(HaftalikPlanFragment())
                    3 -> replaceFragment(BedenTakipFragment())
                    4 -> replaceFragment(FruitProcessingFragment())
                    5 -> replaceFragment(KategoriFragment())
                }
            }
            show(1)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            when (currentFragment) {
                is KategoriFragment -> binding.bottomNavigation.show(1)
                is BesinFragment -> binding.bottomNavigation.show(2)
                is SuFragment -> binding.bottomNavigation.show(3)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, fragment)
        }
    }

    companion object {
        private const val SPLASH_SCREEN_DURATION = 2000L
    }
}