package vcmsa.projects.buggybank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TransactionRecords.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransactionRecords : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val layout = inflater.inflate(R.layout.fragment_second, container, false)
        val edtInput: EditText = layout.findViewById(R.id.edtInput)
        val btnPressMe: Button = layout.findViewById(R.id.btnFrag2)
        val tvOutput: TextView = layout.findViewById(R.id.tvOutput)

//        btnPressMe.setOnHoverListener{
//            btnPressMe.setBackgroundResource(R.color.white)
//        }

        btnPressMe.setOnClickListener {
            tvOutput.text = edtInput.text
//            btnPressMe.setBackgroundColor()
            edtInput.text.clear()
        }


        return layout
    }
}