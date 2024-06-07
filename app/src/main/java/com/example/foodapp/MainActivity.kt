package com.example.foodapp

import com.example.foodapp.fragment.WeeklyPlanFragment
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.fragment.BodyTrackingFragment
import com.example.foodapp.fragment.FoodFragment
import com.example.foodapp.fragment.ExerciseFragment
import com.example.foodapp.fragment.FruitProcessingFragment
import com.example.foodapp.fragment.CategoryFragment
import com.example.foodapp.fragment.MainFragment
import com.example.foodapp.fragment.SplashScreenFragment
import com.example.foodapp.fragment.WaterFragment
import com.example.foodapp.model.Besin
import com.example.foodapp.model.Egzersiz
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : AppCompatActivity(), FoodFragment.SelectedItemsListener,
    ExerciseFragment.SelectedEgzersizListener {
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
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
        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.your_web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()


        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragmentContainer, SplashScreenFragment())
            }
        }

        performGoogleLogin()

        binding.bottomNavigation.visibility = View.GONE

        Handler(Looper.getMainLooper()).postDelayed({
            binding.bottomNavigation.visibility = View.VISIBLE
        }, SPLASH_SCREEN_DURATION)

        setupBottomNavigation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_ONE_TAP) {
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(data)
                val idToken = credential.googleIdToken
                if (idToken != null) {
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(firebaseCredential)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                replaceFragment(MainFragment())
                            } else {
                                Log.e("MainActivity", "Google  giriş başarısız")
                            }
                        }
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
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
                    2 -> replaceFragment(WeeklyPlanFragment())
                    3 -> replaceFragment(BodyTrackingFragment())
                    4 -> replaceFragment(FruitProcessingFragment())
                    5 -> replaceFragment(CategoryFragment())
                }
            }
            show(1)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            when (currentFragment) {
                is CategoryFragment -> binding.bottomNavigation.show(1)
                is FoodFragment -> binding.bottomNavigation.show(2)
                is WaterFragment -> binding.bottomNavigation.show(3)
            }
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, fragment)
        }
    }


    private fun performGoogleLogin() {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, signInOptions)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQ_ONE_TAP)
        Log.d("MainActivity", "Google giriş başarılı: $signInIntent")
    }

    companion object {
        private const val SPLASH_SCREEN_DURATION = 2000L
        private const val REQ_ONE_TAP = 1
        private const val RC_SIGN_IN = 2
    }
}