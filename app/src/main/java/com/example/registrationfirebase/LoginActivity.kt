package com.example.registrationfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUser: TextInputLayout
    private lateinit var txtPassword: TextInputLayout
    private lateinit var progresbar:ProgressBar
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        txtUser=findViewById(R.id.txtemail_login)
        txtPassword=findViewById(R.id.txtpassword_login)
        progresbar=findViewById(R.id.progressBar)
        auth=FirebaseAuth.getInstance()




    }

    fun forgotpassword (view:View){
        startActivity(Intent(this, ForgotPassActivity::class.java))

    }

    fun register (view:View){

        startActivity(Intent(this, RegistrationActivity::class.java))

    }
    fun login (view:View){
        LoginUser()

    }

    private fun LoginUser(){

        val user:String=txtUser.getEditText()?.text.toString().trim()
        val password:String=txtPassword.getEditText()?.text.toString().trim()

        if(!TextUtils.isEmpty(user)&& !TextUtils.isEmpty(password)){
            progresbar.visibility=View.VISIBLE

            auth.signInWithEmailAndPassword(user,password).addOnCompleteListener(this){
                task ->

                if(task.isSuccessful){
                    action()
                  }
                else{
                    Toast.makeText(this, "Error en la authenticacion", Toast.LENGTH_LONG).show()


                }
            }

        }

    }


    private fun action(){
        startActivity(Intent(this, MainActivity::class.java))
    }
}
