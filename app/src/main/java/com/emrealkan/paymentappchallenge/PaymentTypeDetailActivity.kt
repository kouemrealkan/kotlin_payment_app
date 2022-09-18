package com.emrealkan.paymentappchallenge

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.Gravity
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.hardware.display.DisplayManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.emrealkan.paymentappchallenge.databinding.ActivityPaymentTypeDetailBinding
import com.emrealkan.paymentappchallenge.databinding.PopupBinding
import com.emrealkan.paymentappchallenge.db.DatabaseOpenHelper
import com.emrealkan.paymentappchallenge.model.Payment
import com.emrealkan.paymentappchallenge.model.PaymentType

class PaymentTypeDetailActivity : AppCompatActivity(), SelectListener {
    val db by lazy { DatabaseOpenHelper(this) }
    var paymentId : Int = 0
    lateinit var binding : ActivityPaymentTypeDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPaymentTypeDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


         val myIntent = intent
         val paymentTypeId =   myIntent.getIntExtra("id",-1)

         println(paymentTypeId)


         showPaymentTypeData(db.getPaymentTypeWithId(paymentTypeId))

         showData(db.getPaymentListWithPaymentTypeId(paymentTypeId))


        binding.rvPayment.setHasFixedSize(true)
        val lm = LinearLayoutManager(this)
        lm.orientation = LinearLayoutManager.VERTICAL
        binding.rvPayment.layoutManager = lm


        showData(db.getPaymentListWithPaymentTypeId(paymentTypeId))



        binding.updatePaymentTypeRouteBtn.setOnClickListener {
            val intentUpdate = Intent(this,AddPaymentTypeActivity::class.java)
            intentUpdate.putExtra("id",binding.textView9.text.toString().toInt())
           // intentUpdate.putExtra("title",binding.textView3.text)
           // intentUpdate.putExtra("paymentPeriod",binding.textView7.text)
           // intentUpdate.putExtra("periodDay",binding.textView8.text)
            startActivity(intentUpdate)
        }


        binding.btnPageDetailGoToPayment.setOnClickListener {
            var paymentIntent = Intent(this,AddPaymentActivity::class.java)
            paymentIntent.putExtra("id",binding.textView9.text.toString().toInt())
            startActivity(paymentIntent)
        }







    }

    fun showData(list: MutableList<Payment>){
        var adapter = PaymentItemAdapter(this,list,this)
        binding.rvPayment.adapter = adapter
    }

    fun showPaymentTypeData(paymentType: PaymentType){
        binding.textView3.text = paymentType.title
        binding.textView7.text = paymentType.paymentPeriod
        binding.textView8.text = paymentType.periodDay.toString()
        binding.textView9.text = paymentType.id.toString()
        //binding.pymntId.text = paymentType.id.toString()
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

            db.deletePayment(Id)
            Toast.makeText(this, "Ödeme başarıyla silindi", Toast.LENGTH_LONG).show()
            popMsg.dismiss()
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }

        popupBinding.btnCancel.setOnClickListener {
             Toast.makeText(this, "İşlem İptal Edildi", Toast.LENGTH_LONG).show()
             popMsg.dismiss()
        }

    }

    override fun onItemClicked(paymentId: Int) {
        showPopup(paymentId)
    }


}