package com.example.przemeksokolowski.dietingcontroller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.example.przemeksokolowski.dietingcontroller.data.ApiUtils
import com.example.przemeksokolowski.dietingcontroller.data.WebAPI
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if  (login_field.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }

        login_button.setOnClickListener {
            val login = login_field.text.toString()
            val password = password_field.text.toString()

            val response = ApiUtils.getWebApi().login(login, password).execute()

            if (response.isSuccessful) {
                val userId = response.body()!!.id
                if (userId == -1) {
                    login_field.error = "Incorrect data!"
                    password_field.error = "Incorrect data!"
                } else {
                    startActivity(ApiUtils.createIntentWithLoggedUserId(
                            applicationContext, parent.javaClass, userId))
                }
            } else {
                ApiUtils.noApiConnectionDialog(applicationContext, parent)
            }
        }


        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }
}
