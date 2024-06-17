package com.example.pochms

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.pochms.R
import com.example.pochms.data.AccessTokenModel
import com.example.pochms.data.NotificationMessageBody
import com.example.pochms.data.NotificationMessageModel
import com.example.pochms.databinding.ActivityPushNotificationBinding
import com.huawei.agconnect.AGConnectOptionsBuilder
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PushNotificationActivity : AppCompatActivity() {
    private var pushToken: String = "";
    private var accessToken: String = ""
    private lateinit var binding: ActivityPushNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPushNotificationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getToken()
        getAccessToken()
        binding.sendNotification.setOnClickListener {
            sendNotification(pushToken)
        }
        binding.btnGetToken.setOnClickListener {

        }
    }

    private fun getToken() {
        //printLogInUI("getToken:begin")
        lifecycleScope.launch {
            try {
                val token = withContext(Dispatchers.IO) {
                    // read from agconnect-services.json
                    val appId = AGConnectOptionsBuilder().build(this@PushNotificationActivity)
                        .getString(APP_ID)
                    pushToken = HmsInstanceId.getInstance(this@PushNotificationActivity).getToken(appId, "HCM")
                    Log.i(TAG, "get token:$pushToken")
                    if (pushToken.isNotEmpty()) {
                        sendRegTokenToServer(pushToken)
                    }
                    pushToken
                }
                //printLogInUI("get token:$token")
            } catch (e: ApiException) {
                Log.e(TAG, "get token failed, $e")
                //printLogInUI("get token failed, $e")
            }
        }
    }

    private fun getAccessToken() {
        //printLogInUI("getAccessToken:begin")
        AccessTokenClient.getClient().create(AccessTokenInterface::class.java)
            .createAccessToken(
                "client_credentials",
                "2d6484b597e8138d63275167c43c44c66c783654bde4fed3e38e2d7464438bcf",
                "111255883"
            ).enqueue(object : Callback<AccessTokenModel> {
                override fun onFailure(call: Call<AccessTokenModel>, t: Throwable) {
                    Log.d("PushActivity", "ERROR : " + t.message)
                    //printLogInUI("getAccessToken:${t.message}")
                }

                override fun onResponse(
                    call: Call<AccessTokenModel>,
                    response: Response<AccessTokenModel>
                ) {
                    if (response.isSuccessful) {
                        Log.d("PushActivity", "Token " + response.body()?.access_token)
                        //printLogInUI("getAccessToken : Token  ${response.body()?.access_token}")
                        accessToken = response.body()?.access_token.toString()
                    }
                }
            })
        //printLogInUI("getAccessToken:End")
    }

    private fun sendRegTokenToServer(token: String) {
        Log.i(TAG, "sending token to server. token:$token")
    }

    private fun sendNotification(pushToken: String) {
        //printLogInUI("sendNotification:begin")
        val notifMessageBody: NotificationMessageBody = NotificationMessageBody.Builder(
            "NOTIFICATIN TITTLE HERE",
            "NOTIFICATION BODY HERE",
            arrayOf(pushToken)
        )
            .build()

        NotificationClient.getClient().create(NotificationInterface::class.java)
            .createNotification(
                "Bearer $accessToken",
                notifMessageBody
            )
            .enqueue(object : Callback<NotificationMessageModel> {
                override fun onFailure(call: Call<NotificationMessageModel>, t: Throwable) {
                    //printLogInUI("sendNotification:ERROR ${t.message}")
                    Log.d("PushActivity", "ERROR :  " + t.message)
                }

                override fun onResponse(
                    call: Call<NotificationMessageModel>,
                    response: Response<NotificationMessageModel>
                ) {
                    if (response.isSuccessful) {
                        //printLogInUI("sendNotification: Response  ${response.body()}")
                        Log.d("PushActivity", "Response " + response.body())
                    }
                }

            })
    }

    private fun printLogInUI(log: String) {
        binding.layoutLog.run {
            tvLog.append("$log\n")
            svLog.fullScroll(View.FOCUS_DOWN)
        }
    }

    companion object {
        private const val TAG = "PUSH"
        private const val HCM = "HCM"
        private const val APP_ID = "111255883"
        private const val Bearer =
            "DQEBACBUrMEmidclKWnGIalt4x/L0mjQ75RWQOmZ41ptAWvv0OiuzfmuSerAunhKRvWDalU4KIPObmhPZo8qNVpnIyvfZo15gfCBDQ=="
    }
}