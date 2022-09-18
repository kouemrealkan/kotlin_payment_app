package com.emrealkan.paymentappchallenge

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emrealkan.paymentappchallenge.model.PaymentType

class ItemAdapter(val ctx : Context, val list: MutableList<PaymentType>): RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_payment_type_item,parent,false)
        val vHolder = ItemViewHolder(view)
        return  vHolder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
       holder.tvTitle.setText(list.get(position).title)
        holder.tvPaymentPeriod.setText(list.get(position).paymentPeriod)
        holder.tvPeriodDay.setText(list.get(position).periodDay.toString())
        holder.btn.setOnClickListener {
            var intent = Intent(ctx,AddPaymentActivity::class.java)
            intent.putExtra("id",list.get(position).id)
            ctx.startActivity(intent)
        }
        holder.layout.setOnClickListener {
            var intent2 = Intent(ctx,PaymentTypeDetailActivity::class.java)
            intent2.putExtra("id",list.get(position).id)
            ctx.startActivity(intent2)
        }

    }

    override fun getItemCount(): Int {
      return list.size
    }
}