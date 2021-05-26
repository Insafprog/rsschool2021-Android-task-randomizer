package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minEditText: EditText? = null
    private var maxEditText: EditText? = null

    private var listener: ActionEventListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minEditText = view.findViewById(R.id.min_value)
        maxEditText = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)

        previousResult?.text = "Previous result: ${result.toString()}"

        var min = if (minEditText?.text.isNullOrEmpty()) 0 else minEditText?.text.toString().toInt()
        var max = if (maxEditText?.text.isNullOrEmpty()) 0 else maxEditText?.text.toString().toInt()

        generateButton?.isEnabled = false

        generateButton?.setOnClickListener {
            listener?.onFirstFragmentActionEvent(min, max)
        }


        fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
             this.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    try {
                        if (p0.isNullOrEmpty()) generateButton?.isEnabled = false
                        else {
                            previousResult?.isFocusable = false
                            onTextChanged.invoke(p0.toString())
                        }
                    } catch (e: NumberFormatException) {
                        Snackbar.make(view, "You entered too large a number", Snackbar.LENGTH_LONG).show()
                        previousResult?.isFocusable = true
                        previousResult?.requestFocus()
                        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                        generateButton?.isEnabled = false
                        onTextChanged.invoke("")
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            })

        }
            minEditText?.onTextChanged {
                if(it.isEmpty()) {
                    minEditText?.setText("")
                }else {
                    min = it.toInt()
                    generateButton?.isEnabled = min < max
                }
            }

            maxEditText?.onTextChanged {
                if(it.isEmpty()) {
                    max = 0
                    maxEditText?.setText("")
                }else {
                    max = it.toInt()
                    generateButton?.isEnabled = min < max
                }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ActionEventListener
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}