package com.emrealkan.paymentappchallenge

import android.content.Intent
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.hardware.display.DisplayManagerCompat
import com.emrealkan.paymentappchallenge.databinding.ActivityAddPaymentTypeBinding
import com.emrealkan.paymentappchallenge.databinding.ActivityMainBinding
import com.emrealkan.paymentappchallenge.databinding.PopupBinding
import com.emrealkan.paymentappchallenge.db.DatabaseOpenHelper
import com.emrealkan.paymentappchallenge.model.PaymentType

class AddPaymentTypeActivity : AppCompatActivity() {
    val db by lazy { DatabaseOpenHelper(this) }
    lateinit var binding : ActivityAddPaymentTypeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddPaymentTypeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var list = arrayListOf<String>("Aylık","Yıllık","Haftalık")


        var adapter : ArrayAdapter<*> = ArrayAdapter<String?>(this,android.R.layout.simple_spinner_item,
            list as List<String?>
        )
        binding.spinner.adapter = adapter



        var dataIntent = intent
        var id =  dataIntent.getIntExtra("id",-1)

        if(id != -1){
            showPaymentTypeData(db.getPaymentTypeWithId(id))

        }else{
            binding.labelPaymentPeriod.visibility = View.INVISIBLE
            binding.btnDeletePaymentType.visibility = View.INVISIBLE
        }



        binding.btnDeletePaymentType.setOnClickListener {
            showPopup(id)
        }




        binding.button4.setOnClickListener {

            if(id != -1){
               // val paymentType : PaymentType = db.getPaymentTypeWithId(id)
                updatePaymentTypeData(id)
            }else{
                var pt = PaymentType()
                pt.title = binding.etTitle.text.toString()
                pt.paymentPeriod = binding.spinner.selectedItem.toString()
                if(binding.etPeriodDay.text == null){
                    pt.periodDay = null
                }else{
                    pt.periodDay = binding.etPeriodDay.text.toString().toInt()

                }
                db.insertPaymentTypeData(pt)
            }
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }



    }


    fun showPaymentTypeData(paymentType: PaymentType){
       binding.etTitle.setText(paymentType.title)
        binding.labelPaymentPeriod.text = "Mevcut Ödeme Periyodu :" +  paymentType.paymentPeriod
        binding.etPeriodDay.setText(paymentType.periodDay.toString())
        //binding.pymntId.text = paymentType.id.toString()
    }

    fun updatePaymentTypeData(Id:Int){
        var paymentType = db.getPaymentTypeWithId(Id)
        paymentType.title = binding.etTitle.text.toString()
        paymentType.paymentPeriod = binding.spinner.selectedItem.toString()
        paymentType.periodDay = binding.etPeriodDay.text.toString().toInt()
        db.updatePaymentType(paymentType)
    }


    fun showPopup(Id: Int){

        var width : Int
        var height : Int

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            val defaultDisplay = DisplayManagerCompat.getInstance(this).getDisplay(Display.DEFAULT_DISPLAY)

            val displayContext = createDisplayContext(defaultDisplay!!)
            width = displayContext.resources.displayMetrics.widthPixels
            height = displayContext.resources.displayMetrics.heightPixels
        }
        else
        {
            height = windowManager.defaultDisplay.height
            width = windowManager.defaultDisplay.width
        }



        val popupBinding = PopupBinding.inflate(layoutInflater)
        val popMsg = PopupWindow(popupBinding.root,width,height)

        popMsg.showAtLocation(popupBinding.root, Gravity.CENTER,0,0)

        popupBinding.btnRemove.setOnClickListener {

            db.deletePaymentType(Id)
            Toast.makeText(this, "Ödeme Tipi başarıyla silindi", Toast.LENGTH_LONG).show()
            popMsg.dismiss()
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }

        popupBinding.btnCancel.setOnClickListener {
            Toast.makeText(this, "İşlem İptal Edildi", Toast.LENGTH_LONG).show()
            popMsg.dismiss()
        }

    }






}