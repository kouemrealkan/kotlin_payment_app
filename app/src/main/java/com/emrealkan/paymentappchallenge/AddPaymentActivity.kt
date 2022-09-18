package com.emrealkan.paymentappchallenge

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emrealkan.paymentappchallenge.databinding.ActivityAddPaymentBinding
import com.emrealkan.paymentappchallenge.db.DatabaseOpenHelper
import com.emrealkan.paymentappchallenge.model.Payment
import com.emrealkan.paymentappchallenge.model.PaymentType

class AddPaymentActivity : AppCompatActivity() {

    private var datePickerDialog : DatePickerDialog? = null
    private var calendar :Calendar? =null
    private var year: Int = 0
    private var month : Int = 0
    private var dayOfMonth : Int = 0

    val db by lazy { DatabaseOpenHelper(this) }
    lateinit var binding : ActivityAddPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var intent = getIntent()

        var paymentTypeId = intent.getIntExtra("id",-1)


        showData(db.getPaymentTypeWithId(paymentTypeId))


        binding.imageButton.setOnClickListener {
            calendar = Calendar.getInstance()
            year = calendar!!.get(Calendar.YEAR)
            month = calendar!!.get(Calendar.MONTH)
            dayOfMonth = calendar!!.get(Calendar.DAY_OF_MONTH)
            datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { datePicker, year, month, day -> binding.dateText.text = "$day/$month/$year" }, year, month, dayOfMonth)
            datePickerDialog!!.show()
        }

        binding.savePaymentBtn.setOnClickListener {
            var payment = Payment()
            payment.paymentAmount = binding.etPaymentAmount.text.toString().toDouble()
            payment.paymentDate = binding.dateText.text.toString()
            payment.paymentTypeId = binding.tvwId.text.toString().toInt()

            db.insertPaymentData(payment)

            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }




    }

    fun showData(paymentType: PaymentType){
        binding.tvwId.text = paymentType.id.toString()
        binding.tvwTitle.text = paymentType.title
        binding.tvwPPeriod.text = paymentType.paymentPeriod
        binding.tvwPDay.text = paymentType.periodDay.toString()
    }
}