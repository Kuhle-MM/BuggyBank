package vcmsa.projects.buggybank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment

class CreatPopUpFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_creat_pop_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCreateCategory : Button = view.findViewById(R.id.btnCreateCategory)
        val btnCreateTransaction : Button = view.findViewById(R.id.btnCreateTransaction)

        btnCreateTransaction.setOnClickListener{
            val frag1 = CreateTransactionFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, frag1)
                .addToBackStack(null) //allows the program to remember hte previous frag when user clicks the 'back button'
                .commit()
            dismiss() //this closes the popup after navigating to the createTransaction fragment
        }

        btnCreateCategory.setOnClickListener{
            val frag2 = CreateCategoryFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, frag2)
                .addToBackStack(null) //allows the program to remember hte previous frag when user clicks the 'back button'
                .commit()
            dismiss() //this closes the popup after navigating to the createCategory fragment
        }
    }

}