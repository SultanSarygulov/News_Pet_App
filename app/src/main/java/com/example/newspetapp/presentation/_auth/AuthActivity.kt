package com.example.newspetapp.presentation._auth

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.newspetapp.R
import com.example.newspetapp.data.ConnectivityObserver
import com.example.newspetapp.data.NetworkConnectivityObserver
import com.example.newspetapp.databinding.ActivityAuthBinding
import com.example.newspetapp.databinding.DialogConnectionLostBinding
import com.example.newspetapp.di.Constants.TAG
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_auth)

        val connectivityObserver = NetworkConnectivityObserver(applicationContext)
        connectivityObserver.observe().onEach { status ->

            Log.d(TAG, "onCreate: ${status.name}")

            if(status == ConnectivityObserver.Status.Lost ||
                status == ConnectivityObserver.Status.Unavailable){

                showInternetDialog()

                Toast.makeText(this, "NOOOO CONNNECTIIIOON", Toast.LENGTH_SHORT).show()
            }

        }.launchIn(lifecycleScope)
    }

    private fun showInternetDialog() {

        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val binding = DialogConnectionLostBinding.bind(inflater.inflate(R.layout.dialog_connection_lost, null))
        dialogBuilder.setView(binding.root)

        val internetDialog = dialogBuilder.create()
        internetDialog.setCancelable(false)

        binding.internetDialogButton.setOnClickListener {

            internetDialog.cancel()
        }


        internetDialog.show()
    }
}