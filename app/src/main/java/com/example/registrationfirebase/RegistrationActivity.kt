package com.example.registrationfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseAppLifecycleListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {


    private lateinit var txtName:TextInputLayout
    private lateinit var txtLastName:TextInputLayout
    private lateinit var txtEmail:TextInputLayout
    private lateinit var txtPass: TextInputLayout
    private lateinit var progressbar: ProgressBar
    private lateinit var dbReferene:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        txtName=findViewById(R.id.txtname)
        txtLastName=findViewById(R.id.lastname)
        txtEmail=findViewById(R.id.txtemail)
        txtPass=findViewById(R.id.txtpassword)
        progressbar=findViewById(R.id.progressBar)

        //instanciamos

        database=FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()

        dbReferene=database.reference.child("User")



    }


        fun register(view:View){

            createNewAcount()
        }

       private fun createNewAcount(){

           val name:String=txtName.getEditText()?.text.toString().trim()    //val name:String=txtName.text.toString()  //diferncia entre un editext y un TextInputLayout
           val lastname:String=txtLastName.getEditText()?.text.toString().trim()
           val email:String=txtEmail.getEditText()?.text.toString().trim()
           val password:String=txtPass.getEditText()?.text.toString().trim()

           if(!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(lastname)&& !TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){
                progressbar.visibility=View.VISIBLE

               auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                   task ->
                   if(task.isComplete){
                       val user:FirebaseUser?=auth.currentUser
                       verificadEmail(user)

                       val userBD=dbReferene.child(user?.uid.toString())
                       userBD.child("Name").setValue(name)
                       userBD.child("LastName").setValue(lastname)
                       action()



                   }
               }
           }

       }

        private fun action(){

        startActivity(Intent(this,LoginActivity::class.java))

        }

        private fun verificadEmail(user:FirebaseUser?){
            user?.sendEmailVerification()?.addOnCompleteListener(this){
                task ->
                if(task.isComplete){
                    Toast.makeText(this,"Correo enviado", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"Error al enviar el correo", Toast.LENGTH_LONG).show()
                }
            }

        }
}
