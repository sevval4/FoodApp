package com.example.foodapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
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
import com.google.firebase.firestore.FirebaseFirestore
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation


class MainActivity : AppCompatActivity() ,BesinFragment.SelectedItemsListener{
    //val db = FirebaseFirestore.getInstance()

    private lateinit var binding: ActivityMainBinding

    private var selectedItems: List<Besin> = emptyList()

    override fun onSelectedItemsList(selectedItems: List<Besin>) {
        this.selectedItems = selectedItems
    }

    fun getSelectedItems(): List<Besin> {
        return selectedItems
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val veri = intent.getStringExtra("veri") ?: ""

        val fragment = MainFragment().apply {
            arguments = Bundle().apply {
                putString("veri", "gönderilecek veri $veri")
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        binding.datePickerEditText.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this, { _, year, monthOfYear, dayOfMonth ->
                    val selectedDate = "$year-${monthOfYear + 1}-$dayOfMonth"
                    binding.datePickerEditText.setText(selectedDate)
                }, 2024, 1, 21
            )
            datePickerDialog.show()
        }
        fun replaceFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
                .commit()
        }

        binding.bottomNavigation.apply {
            add(CurvedBottomNavigation.Model(1, "Main", R.drawable._290849_document_done_excellent_list_note_icon))
            add(CurvedBottomNavigation.Model(2, "", R.drawable._983529_appointment_calendar_coronavirus_date_event_icon))
            add(CurvedBottomNavigation.Model(3, "Kategori", R.drawable._515151_body_exercise_fitness_health_meditation_icon))
            add(CurvedBottomNavigation.Model(4, "", R.drawable._042280_dumbell_gym_healthy_life_take_exercise_training_icon))
            add(CurvedBottomNavigation.Model(5, "Su", R.drawable._244992_fruit_fruits_grape_icon))

            setOnClickMenuListener { item ->
                when (item.id) {
                    1 -> replaceFragment(MainFragment())
                    2 -> replaceFragment(HaftalikPlanFragment())
                    3 -> replaceFragment(BedenTakipFragment())
                    4 -> replaceFragment(AgirlikTakipFragment())
                    5 -> replaceFragment(KategoriFragment())
                }
            }

            show(1)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            if (currentFragment is KategoriFragment) {
                binding.bottomNavigation.show(1)
            } else if (currentFragment is BesinFragment) {
                binding.bottomNavigation.show(2)
            } else if (currentFragment is SuFragment) {
                binding.bottomNavigation.show(3)
            }

        }





    }

}
