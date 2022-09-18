package com.emrealkan.paymentappchallenge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emrealkan.paymentappchallenge.model.Payment
import com.emrealkan.paymentappchallenge.model.PaymentType

class PaymentItemAdapter(val ctx : Context, val list: MutableList<Payment>,val selectListener: SelectListener): RecyclerView.Adapter<PaymentItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_payment_detail_item,parent,false)
        val vHolder = PaymentItemViewHolder(view)
        return  vHolder
    }

    override fun onBindViewHolder(holder: PaymentItemViewHolder, position: Int) {
         holder.twPaymentId.setText(list.get(position).id.toString())
         holder.twPaymentAmount.setText(list.get(position).paymentAmount.toString())
         holder.twPaymentDate.setText(list.get(position).paymentDate)
        holder.layout.setOnClickListener {
            selectListener.onItemClicked(list.get(position).id)
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }
}