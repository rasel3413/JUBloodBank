package com.example.jubloodbank.requestblood

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.jubloodbank.HomePage.HomePageActivity
import com.example.jubloodbank.R
import com.example.jubloodbank.databinding.ActivityRegistrationBinding
import com.example.jubloodbank.databinding.ActivityRequestBloodBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar

class RequestBloodActivity : AppCompatActivity() {
    lateinit var binding: ActivityRequestBloodBinding
    private val calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestBloodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseMessaging.getInstance().subscribeToTopic("All")


        binding.back.setOnClickListener {
            onBackPressed()
        }
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val firebaseDatabase = FirebaseDatabase.getInstance()

        val blood = ArrayList<String>()
        val amount = ArrayList<String>()
        blood.addAll(resources.getStringArray(R.array.blood))
        amount.addAll(resources.getStringArray(R.array.amount))

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, blood)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, amount)
        binding.bloodtype.setAdapter(adapter)
        binding.bloodamount.setAdapter(adapter2)

        binding.Date.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                    val formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)

                    // Set the formatted date to the TextInputEditText
                    binding.Date.setText(formattedDate.toString())
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), // Initial month
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            // Show the dialog
            datePickerDialog.show()
        }

        binding.Time.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                    val formattedTime = String.format(
                        "%02d:%02d %s",
                        hourOfDay,
                        minute,
                        if (hourOfDay >= 12) "PM" else "AM"
                    )

                    binding.Time.setText(formattedTime)
                },
                calendar.get(Calendar.HOUR_OF_DAY), // Initial hour
                calendar.get(Calendar.MINUTE), // Initial minute
                false
            )

            // Show the dialog
            timePickerDialog.show()
        }
        val currentUserUid = firebaseAuth.currentUser?.uid


        binding.submit.setOnClickListener {

            if(binding.pName.text.toString().isEmpty()){
                binding.pName.error="This Field is required"
            }
            else if(binding.bloodtype.text.toString().isEmpty()){
                binding.bloodtype.error="This Field is required"
            }
            else if(binding.bloodamount.text.toString().isEmpty()){
                binding.bloodamount.error="This Field is required"
            }
            else if(binding.location.text.toString().isEmpty()){
                binding.location.error="This Field is required"
            }
            else if(binding.hospital.text.toString().isEmpty()){
                binding.hospital.error="This Field is required"
            }
            else if(binding.Date.text.toString().isEmpty()){
                binding.Date.error="This Field is required"
            }
            else if(binding.Time.text.toString().isEmpty()){
                binding.Time.error="This Field is required"
            }
            else if(binding.personName.text.toString().isEmpty()){
                binding.personName.error="This Field is required"
            }



            else{

                val usersRef = firebaseDatabase.getReference("requests")
                val newUserRef = usersRef.push()
                var patient=binding.pName.text.toString()
                var Blood=binding.bloodtype.text.toString()
                var amuont=binding.bloodamount.text.toString()
                var Date=binding.Date.text.toString()
                var Time=binding.Time.text.toString()
                var Hname=binding.hospital.text.toString()
                var Location=binding.location.text.toString()

                var personName=binding.personName.text.toString()
                var phone=binding.phonenumber.text.toString()
                var note=binding.note.text.toString()
                val currentTime = -System.currentTimeMillis()
                val request = RequestData(patient,Blood,amuont,Date,Time,Hname,Location,personName,phone,note,currentUserUid,   currentTime)

                newUserRef.setValue(request)
                GlobalScope.launch {
                    FcmNotificationSender.sendPushNotification("/topics/All","Blood Needed",Blood)

                }

                startActivity(Intent(this,HomePageActivity::class.java))



            }

        }


    }

}
