package com.emrealkan.paymentappchallenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.emrealkan.paymentappchallenge.databinding.ActivityMainBinding
import com.emrealkan.paymentappchallenge.db.DatabaseOpenHelper
import com.emrealkan.paymentappchallenge.model.PaymentType

class MainActivity : AppCompatActivity() {
    val db by lazy { DatabaseOpenHelper(this) }
    lateinit var binding : ActivityMainBinding
   // var paymentTypeList : MutableList<PaymentType>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.paymentTypeRvlist.setHasFixedSize(true)
        val lm = LinearLayoutManager(this)
        lm.orientation = LinearLayoutManager.VERTICAL
        binding.paymentTypeRvlist.layoutManager = lm






          showData(db.readPaymentTypeData())


          binding.button.setOnClickListener {

          var intent = Intent(this,AddPaymentTypeActivity::class.java)
          startActivity(intent)

        }


    }

    fun showData(list:MutableList<PaymentType>){
        var adapter = ItemAdapter(this,list)
        binding.paymentTypeRvlist.adapter = adapter
    }
}