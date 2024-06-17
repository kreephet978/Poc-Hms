package com.example.pochms

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pochms.data.AccessTokenModel
import com.example.pochms.data.NotificationMessageBody
import com.example.pochms.data.NotificationMessageModel
import com.example.pochms.databinding.ActivityMainBinding
import com.huawei.agconnect.AGConnectOptionsBuilder
import com.huawei.agconnect.applinking.AGConnectAppLinking
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnGoToNotificationPage.setOnClickListener {
            startActivity(Intent(this, PushNotificationActivity::class.java))
        }
        binding.btnGoToDynamicLinkPage.setOnClickListener {
            startActivity(Intent(this, AppLinkActivity::class.java))
        }
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