package com.brivo.brivointerviewapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.RecyclerView
import com.brivo.brivointerviewapp.R
import com.brivo.brivointerviewapp.datamanager.NetworkManager
import com.brivo.brivointerviewapp.model.School
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), SchoolsAdapter.ISchoolItem {

    private lateinit var rvSchools: RecyclerView
    private lateinit var schoolsAdapter: SchoolsAdapter

    private val onQueryTextChanged = object : OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            schoolsAdapter.filter.filter(newText)
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvSchools = findViewById(R.id.rvSchools)
        NetworkManager().getSchools(object : NetworkManager.IOnGetSchoolsListener {
            override fun onSuccess(schools: List<School>) {
                schoolsAdapter.setDataSource(schools)
            }

            override fun onFailed(error: String?) {
                Toast.makeText(this@MainActivity, error, Toast.LENGTH_LONG).show()
            }
        })
        val svSchools = findViewById<SearchView>(R.id.svSchools)
        svSchools.setOnQueryTextListener(onQueryTextChanged)
        schoolsAdapter = SchoolsAdapter(this)
        rvSchools.adapter = schoolsAdapter
    }

    override fun clicked(school: School) {
        val intent = Intent(this, SchoolActivity::class.java)
        intent.putExtra("SCHOOL", Gson().toJson(school))
        startActivity(intent)
    }
}

class SchoolsAdapter(private val listener: ISchoolItem) :
    RecyclerView.Adapter<SchoolsAdapter.SchoolViewHolder>(), Filterable {

    private var schools: ArrayList<School> = ArrayList()
    private var schoolsFiltered: ArrayList<School> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SchoolViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_school,
            parent, false
        )

        return SchoolViewHolder(itemView)
    }

    fun setDataSource(schools: List<School>) {
        this.schools = schools as ArrayList<School>
        this.schoolsFiltered = schools
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.name.text = schoolsFiltered[position].schoolName
        holder.city.text = (schools[position].city)
        holder.itemView.setOnClickListener {
            listener.clicked(schoolsFiltered[position])
        }
    }

    interface ISchoolItem {
        fun clicked(school: School)
    }

    override fun getItemCount(): Int {
        return schoolsFiltered.size
    }

    class SchoolViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val city: TextView = itemView.findViewById(R.id.tvCity)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charString = constraint?.toString()?.lowercase() ?: ""
                schoolsFiltered = if (charString.isEmpty()) schools else {
                    val filteredList = ArrayList<School>()
                    schools
                        .filter {
                            (it.schoolName!!.lowercase().contains(constraint!!)) or
                                    (it.city!!.lowercase().contains(constraint))

                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = schoolsFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                schoolsFiltered = if (results?.values == null) {
                    ArrayList()
                } else {
                    results.values as ArrayList<School>
                }
                notifyDataSetChanged()
            }
        }
    }
}