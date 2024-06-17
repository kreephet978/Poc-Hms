package com.example.pochms

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pochms.databinding.ActivityAppLinkBinding
import com.huawei.agconnect.applinking.AGConnectAppLinking

class AppLinkActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityAppLinkBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAppLinkBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        AGConnectAppLinking.getInstance().getAppLinking(this).addOnSuccessListener {
            val deepLink = it.deepLink
            printLogInUI(deepLink.toString())
        }


    }

    private fun printLogInUI(log: String) {
        binding.layoutLog.run {
            tvLog.append("$log\n")
            svLog.fullScroll(View.FOCUS_DOWN)
        }
    }
}