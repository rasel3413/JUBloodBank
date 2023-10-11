package com.example.jubloodbank.bloodlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jubloodbank.R
import com.example.jubloodbank.bloodlist.data.BloodEntity
import com.example.jubloodbank.requestblood.RequestData

class BloodAdapter(private var blood: MutableList<BloodEntity>):RecyclerView.Adapter<BloodAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BloodAdapter.MyViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.bloodlistrow,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BloodAdapter.MyViewHolder, position: Int) {
        val currentItem:BloodEntity=blood[position]
        holder.txt1.text= "Name: ${currentItem.Name}"
        holder.txt3.text="Phone Number: ${currentItem.phoneNumber}"
        holder.txtblood.text="Blood Group: ${currentItem.bloodGroup}"
        holder.txtHall.text="Hall: ${currentItem.HallName}"


    }

    override fun getItemCount(): Int {
      return  blood.size
    }
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val txt1: TextView =itemView.findViewById(R.id.tvFirstName)
        val txt3: TextView =itemView.findViewById(R.id.tvPhoneNumber)
        val txtHall: TextView =itemView.findViewById(R.id.tvHall)
        val txtblood: TextView =itemView.findViewById(R.id.tvBloodGroup)

    }


}