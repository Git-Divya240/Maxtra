package com.example.maxtra

import android.R.attr.bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.io.FileNotFoundException
import java.io.InputStream


class SetData : AppCompatActivity() {

    lateinit var Name: TextView
    lateinit var Number: TextView
    lateinit var Email: TextView
    lateinit var Address: TextView
    lateinit var City: TextView
    lateinit var Image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_data)

        Name = findViewById(R.id.name)
        Number = findViewById(R.id.number)
        Email = findViewById(R.id.email)
        Address = findViewById(R.id.address)
        City = findViewById(R.id.city)
        Image = findViewById(R.id.image)


        var bundle :Bundle ?=intent.getBundleExtra("data")
        var name = bundle?.getString("Name") // 1
        var image = bundle?.getString("Image") // 1
        var number = bundle?.getString("Number") // 1
        var email = bundle?.getString("email") // 1
        var city = bundle?.getString("City") // 1
        var address = bundle?.getString("Address") // 1


     /*   var inputStream: InputStream? = null
        try {
            inputStream = getApplicationContext().getContentResolver().openInputStream(uri)
           var  bitmap = BitmapFactory.decodeStream(inputStream)
            Glide.with(this@SetData).load(bitmap).into(Image)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
*/

        var Uri  = Uri.parse(image)
        Image.setImageURI(Uri)


        //var strUser: String? = intent.getStringExtra("Name") // 2

        Name.setText(name)
        Number.setText(number)
        City.setText(city)
        Email.setText(email)
        Address.setText(address)

      //  Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }
}