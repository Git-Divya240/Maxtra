package com.example.maxtra

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.File


class MainActivity : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etNumber:EditText
    lateinit var etEmail: EditText
    lateinit var etAddress:EditText
    lateinit var etCity:EditText
    lateinit var etImage:EditText
    lateinit var imagePath:String

    var uri: Uri? =null
    val REQUEST_CODE = 100

    val MIN_PASSWORD_LENGTH = 6;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        viewInitializations()
    }

    fun viewInitializations() {
        etName = findViewById(R.id.name)
        etNumber = findViewById(R.id.number)
        etEmail = findViewById(R.id.email)
        etAddress = findViewById(R.id.address)
        etCity = findViewById(R.id.city)
        etImage = findViewById(R.id.image)


        // To show back button in actionbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Checking if the input in form is valid
    fun validateInput(): Boolean {

        if (etImage.text.toString().equals("")) {
            etImage.setError("Please Select Image")
            return false
        }
        if (etName.text.toString().equals("")) {
            etName.setError("Please Enter Name")
            return false
        }
        if (etNumber.text.toString().equals("")) {
            etNumber.setError("Please Enter Number")
            return false
        }
        if (etEmail.text.toString().equals("")) {
            etEmail.setError("Please Enter Email")
            return false
        }
        if (etAddress.text.toString().equals("")) {
            etAddress.setError("Please Enter Address")
            return false
        }
        if (etCity.text.toString().equals("")) {
            etCity.setError("Please Enter City")
            return false
        }

        // checking the proper email format
        if (!isEmailValid(etEmail.text.toString())) {
            etEmail.setError("Please Enter Valid Email")
            return false
        }
        // checking the proper number format

        if (!isNumberValid(etNumber.text.toString())) {
            etNumber.setError("Please Enter Valid Number")
            return false
        }
       /* // checking minimum password Length
        if (etPassword.text.length < MIN_PASSWORD_LENGTH) {
            etPassword.setError("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters")
            return false
        }

        // Checking if repeat password is same
        if (!etPassword.text.toString().equals(etRepeatPassword.text.toString())) {
            etRepeatPassword.setError("Password does not match")
            return false
        }*/
        return true
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isNumberValid(email: String): Boolean {
        return Patterns.PHONE.matcher(email).matches()
    }

    // Hook Click Event
    fun perfoileUpdate (view: View) {
        if (validateInput()) {

            // Input is valid, here send data to your server

            val Name = etName.text.toString()
            val Image = etImage.text.toString()
            val Number = etNumber.text.toString()
            val email = etEmail.text.toString()
            val Address = etAddress.text.toString()
            val City = etCity.text.toString()

            // Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show()
            // Here you can call you API

            var v = Bundle();
            v.putString("Name",Name);
            v.putString("Number",Number);
            v.putString("email",email);
            v.putString("Address",Address);
            v.putString("City",City);
            v.putString("Image",imagePath);

            var  intent=Intent(this,SetData::class.java);
            intent.putExtra("data",v)
            startActivity(intent)
        }
    }
    fun selectImage(view: View){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            var ll=data?.data
            var path=File(ll?.path)


             uri = getUriWhenPickedFromGallery(data)



         //   imagePath= uri?.let { getRealPathFromURI(it) }!!

            imagePath= path.toString();
            etImage.setText(imagePath)
            etImage.isEnabled=false


            Log.d("TAG", "onActivityResult: "+imagePath)
           // imageView.setImageURI(data?.data) // handle chosen image
        }
    }

    private fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor: Cursor? = contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }
}