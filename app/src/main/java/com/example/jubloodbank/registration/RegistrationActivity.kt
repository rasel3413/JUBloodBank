package com.example.jubloodbank.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.jubloodbank.HomePage.HomePageActivity
import com.example.jubloodbank.R
import com.example.jubloodbank.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {


    lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val blood = ArrayList<String>()
        val hall = ArrayList<String>()
        blood.addAll(resources.getStringArray(R.array.blood))
        hall.addAll(resources.getStringArray(R.array.hall))
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, hall)


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, blood)
        binding.bloodGroup.setAdapter(adapter)
        binding.edRegHall.setAdapter(adapter2)
        binding.submit.setOnClickListener {



            if (binding.edRegName.text.toString().isEmpty()) {
                binding.edRegName.error = "This field is required. "
            } else if (binding.edRegEmail.text.toString().isEmpty()) {
                binding.edRegEmail.error = "This field is required. "
            } else if (binding.edPhoneNumber.text.toString().isEmpty()) {
                binding.edPhoneNumber.error = "This field is required. "
            } else if (binding.edRegHall.text.toString().isEmpty()) {
                binding.edRegHall.error = "This field is required. "
            } else if (binding.edRegBatch.text.toString().isEmpty()) {
                binding.edRegBatch.error = "This field is required. "
            } else if (binding.bloodGroup.text.toString().isEmpty()) {
                binding.bloodGroup.error = "This field is required. "
            } else if (binding.edRegPassword.text.toString().isEmpty()) {
                binding.edRegPassword.error = "This field is required. "
            } else if (binding.edRegConfirmPassword.text.toString().isEmpty()) {
                binding.edRegConfirmPassword.error = "This field is required. "
            }
            else    if (!hall.contains(binding.edRegHall.text.toString())){
                binding.edRegHall.error="Select Valid Hall name "
            }


            else {
                if (binding.edRegPassword.text.toString() == binding.edRegConfirmPassword.text.toString()) {
                    val user = User(
                        name = binding.edRegName.text.toString(),
                        email = binding.edRegEmail.text.toString(),
                        phone = binding.edPhoneNumber.text.toString(),
                        hallName = binding.edRegHall.text.toString(),
                        batch = binding.edRegBatch.text.toString(),
                        Blood = binding.bloodGroup.text.toString()
                    )
                    Registration(
                        user,
                        binding.edRegPassword.text.toString(),
                        this@RegistrationActivity
                    ) { success ->
                        if (success) {
                            startActivity(Intent(this, HomePageActivity::class.java))
                        }

                    }
                } else {
                    Toast.makeText(this, "password incorrect", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}


