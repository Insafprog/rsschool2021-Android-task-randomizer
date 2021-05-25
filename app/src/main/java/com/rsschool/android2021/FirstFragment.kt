package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

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
        val previousMin = arguments?.getInt(MIN_VALUE_KEY)
        val previousMax = arguments?.getInt(MAX_VALUE_KEY)
        previousMin?.let { if (it != 0) minEditText?.setText(it.toString()) }
        previousMax?.let { if (it != 0) maxEditText?.setText(it.toString()) }

        previousResult?.text = "Previous result: ${result.toString()}"

        var min = if (minEditText?.text.isNullOrEmpty()) 0 else minEditText?.text.toString().toInt()
        var max = if (maxEditText?.text.isNullOrEmpty()) 0 else maxEditText?.text.toString().toInt()

        generateButton?.isEnabled = min < max

        generateButton?.setOnClickListener {
            listener?.onFirstFragmentActionEvent(min, max)
        }

        fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
            this.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!p0.isNullOrEmpty())
                        onTextChanged.invoke(p0.toString())
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            })
        }

        minEditText?.onTextChanged {
            min = it.toInt()
            generateButton?.isEnabled = min < max
        }

        maxEditText?.onTextChanged {
            max = it.toInt()
            generateButton?.isEnabled = min < max
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ActionEventListener
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int, min: Int, max: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}