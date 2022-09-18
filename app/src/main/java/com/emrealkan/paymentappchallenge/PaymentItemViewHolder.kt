package com.emrealkan.paymentappchallenge

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class PaymentItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var twPaymentId : TextView
    var twPaymentAmount : TextView
    var twPaymentDate : TextView

    var layout : ConstraintLayout

    init {
       twPaymentId = itemView.findViewById(R.id.payment_rv_id)
       twPaymentAmount = itemView.findViewById(R.id.payment_rv_amount)
       twPaymentDate = itemView.findViewById(R.id.payment_rv_date)
        layout = itemView.findViewById(R.id.payment_detail_item_layout)
    }
}