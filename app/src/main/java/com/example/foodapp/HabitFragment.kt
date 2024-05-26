import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.foodapp.R

class HabitFragment : Fragment() {


    private lateinit var mainView: View
    private lateinit var buttonContainer: LinearLayout

    private var x1: Float = 0.toFloat()
    private var x2: Float = 0.toFloat()
    private val MIN_DISTANCE = 150

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_habit, container, false)

        mainView = view.findViewById(R.id.mainView)
        buttonContainer = view.findViewById(R.id.buttonContainer)

        mainView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> x1 = event.x
                MotionEvent.ACTION_UP -> {
                    x2 = event.x
                    val deltaX = x2 - x1
                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        // Sağa doğru çekme işlemi gerçekleşti
                        showButtons()
                    }
                }
            }
            true
        }

        return view
    }

    private fun showButtons() {
        buttonContainer.visibility = View.VISIBLE
    }
}