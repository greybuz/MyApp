package com.brivo.brivointerviewapp.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.brivo.brivointerviewapp.R
import com.brivo.brivointerviewapp.model.School
import com.google.gson.Gson

class SchoolActivity : AppCompatActivity() {

    private lateinit var school: School

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school)

        val schoolJson: String? = intent.getStringExtra("SCHOOL")
        school = Gson().fromJson(schoolJson, School::class.java)

        val tvSchoolName = findViewById<TextView>(R.id.tvSchoolName)
        tvSchoolName.text = school.schoolName
        val tvAddress = findViewById<TextView>(R.id.tvAddress)
        tvAddress.text = school.address
        val tvWebsite = findViewById<TextView>(R.id.tvWebsite)
        tvWebsite.text = "Website: " + school.website
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        tvEmail.text = "Email: " + school.email
        val tvPhone = findViewById<TextView>(R.id.tvPhone)
        tvPhone.text = "Phone: " + school.phone_number
        val tvNeighbourhood = findViewById<TextView>(R.id.tvNeighbourhood)
        tvNeighbourhood.text = "Neighbourhood: " + school.neighborhood
        val tvBorough = findViewById<TextView>(R.id.tvBorough)
        tvBorough.text = "Borough: " + school.borough
        val tvCity = findViewById<TextView>(R.id.tvCity)
        tvCity.text = "City: " + school.city
        val tvState = findViewById<TextView>(R.id.tvState)
        tvState.text = "State: " + school.state
        val tvZip = findViewById<TextView>(R.id.tvZip)
        tvZip.text = "Zip: " + school.zip
        val tvDescription = findViewById<TextView>(R.id.tvDescription)
        tvDescription.text = school.overview
        val tvActivities = findViewById<TextView>(R.id.tvActivities)
        tvActivities.text = school.extracurricularActivities
    }
}