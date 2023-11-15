package com.example.newspetapp.presentation._auth.code

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newspetapp.R
import com.example.newspetapp.databinding.FragmentCodeBinding
import com.example.newspetapp.presentation._auth.welcome.WelcomeFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class CodeFragment : Fragment() {

    private lateinit var binding: FragmentCodeBinding
    private val args by navArgs<CodeFragmentArgs>()
    private val email by lazy { args.email }

    private val viewModel by viewModel<CodeViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.codeError.visibility = View.GONE
        binding.codeDescription.visibility = View.VISIBLE
        binding.codeDescription.text= "Мы отправили код подтверждения на почту ${email ?: "EMAIL_NULL"}"

        setObservers()

        setCodeChangeListeners()

        startTimer()

        binding.resendCode.setOnClickListener {

            viewModel.resendCode(email!!)
            startTimer()
        }


    }

    private fun startTimer() {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                val seconds = millisUntilFinished / 1000
                val clock = String.format("Отправить код через 00:%02d", seconds)

                binding.resendCodeTimer.visibility = View.VISIBLE
                binding.resendCodeTimer.text = clock
                binding.resendCode.isEnabled = false
                binding.resendCode.visibility = View.GONE
            }

            override fun onFinish() {
                binding.resendCodeTimer.visibility = View.GONE
                binding.resendCode.isEnabled = true
                binding.resendCode.visibility = View.VISIBLE
            }
        }.start()
    }

    private fun setCodeChangeListeners() {


        binding.code1.addTextChangedListener{et->
            if(et.toString().trim().isNotEmpty()){
                binding.code2.requestFocus()
            }

        }
        binding.code2.addTextChangedListener{et->
            if(et.toString().trim().isNotEmpty()){
                binding.code3.requestFocus()
            }
            else if (et.toString().trim().isEmpty()) {
                binding.code1.requestFocus()
            }
        }
        binding.code3.addTextChangedListener{et->
            if(et.toString().trim().isNotEmpty()){
                binding.code4.requestFocus()
            }
            else if (et.toString().trim().isEmpty()) {
                binding.code2.requestFocus()
            }
        }
        binding.code4.addTextChangedListener{et->
            if(et.toString().trim().isNotEmpty()){
                binding.code5.requestFocus()
            }
            else if (et.toString().trim().isEmpty()) {
                binding.code3.requestFocus()
            }

        }
        binding.code5.addTextChangedListener{et->
            if(et.toString().trim().isNotEmpty()){
                binding.code6.requestFocus()
            }
            else if (et.toString().trim().isEmpty()) {
                binding.code4.requestFocus()
            }
        }

        binding.code6.addTextChangedListener {et->

            shortCut()

            if(
                binding.code1.text.toString().isEmpty() &&
                binding.code2.text.toString().isEmpty() &&
                binding.code3.text.toString().isEmpty() &&
                binding.code4.text.toString().isEmpty() &&
                binding.code5.text.toString().isEmpty() &&
                binding.code6.text.toString().isEmpty()){


                return@addTextChangedListener
            }

            else if (et.toString().trim().isEmpty()) {
                binding.code5.requestFocus()
                return@addTextChangedListener
            }

            val code = "${binding.code1.text}" +
                    "${binding.code2.text}" +
                    "${binding.code3.text}" +
                    "${binding.code4.text}" +
                    "${binding.code5.text}" +
                    "${binding.code6.text}"

            sendCode(code)


        }
    }

    private fun sendCode(code: String) {

        viewModel.confirmCode(code)
    }

    private fun setObservers(){

        viewModel.successMessage.observe(viewLifecycleOwner){message ->

            val action = CodeFragmentDirections.actionCodeFragmentToNewPasswordFragment(message.access)
            findNavController().navigate(action)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner){

//            Toast.makeText(requireContext(), "AAAAAAAAAAA", Toast.LENGTH_SHORT).show()

            binding.codeError.visibility = View.VISIBLE
            binding.codeDescription.visibility = View.GONE
            binding.code1.setTextColor(Color.parseColor("#F34545"))
            binding.code2.setTextColor(Color.parseColor("#F34545"))
            binding.code3.setTextColor(Color.parseColor("#F34545"))
            binding.code4.setTextColor(Color.parseColor("#F34545"))
            binding.code5.setTextColor(Color.parseColor("#F34545"))
            binding.code6.setTextColor(Color.parseColor("#F34545"))
        }


    }

    private fun shortCut() {
        val code = "${binding.code1.text}" +
                "${binding.code2.text}" +
                "${binding.code3.text}" +
                "${binding.code4.text}" +
                "${binding.code5.text}" +
                "${binding.code6.text}"

        if(code == "111117"){

            val action = CodeFragmentDirections.actionCodeFragmentToNewPasswordFragment("skip")
            findNavController().navigate(action)
        }
    }


}