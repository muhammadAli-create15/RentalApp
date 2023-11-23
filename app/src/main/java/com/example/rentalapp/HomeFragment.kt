package com.example.rentalapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the Places API here if needed
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val editLocation = rootView.findViewById<EditText>(R.id.editLocation)
        val editText = rootView.findViewById<EditText>(R.id.editRentals)
        val editSecurity = rootView.findViewById<EditText>(R.id.editSecurity)
        val minAmount = rootView.findViewById<EditText>(R.id.edtMinimum)
        val maxAmount = rootView.findViewById<EditText>(R.id.edtMaximum)

        val location=editLocation.text.toString()
        val rental=editText.text.toString()
        val security=editSecurity.text.toString()
        val minimum=minAmount.text.toString()
        val maximum=maxAmount.text.toString()

        if (location.isEmpty() || rental.isEmpty()||security.isEmpty()||minimum.isEmpty()||maximum.isEmpty())
        {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return rootView
        }

        editText.setOnTouchListener { view, event ->
            val DRAWABLE_RIGHT = 2 // Index for the right drawable
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (view.right - editText.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                    // Click detected on the drawableRight
                    // Show your options here, e.g., display a dialog or a dropdown menu
                    showOptions(editText,editText) // Pass the EditText as an anchorView
                    true // Consume the touch event
                } else {
                    false // Not on the drawableRight
                }
            } else {
                false // Other touch events
            }
        }
        editSecurity.setOnClickListener {


        }

        editLocation.setOnClickListener {
            // Handle the click event here
            // For example, show a toast message
            // Places.initialize(requireContext(), "Your Places API Key")
        }

        return rootView
    }

    private fun showOptions(anchorView: View,editText: EditText) {
        val popupMenu = PopupMenu(requireContext(), anchorView)
        popupMenu.menu.add("Single Room")
        popupMenu.menu.add("Double Rooms")
        popupMenu.menu.add("Apartments")
        popupMenu.menu.add("Self-Contained")

        popupMenu.setOnMenuItemClickListener { item ->
            // Handle the selected option here
            val selectedOption = item.title.toString()
            // Perform actions based on the selected option
            editText.text.clear()
            editText.text.append(selectedOption)

            true
        }

        popupMenu.show()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

