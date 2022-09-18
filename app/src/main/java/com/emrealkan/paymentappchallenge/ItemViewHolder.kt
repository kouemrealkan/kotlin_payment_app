package com.emrealkan.paymentappchallenge

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var tvTitle : TextView
    var tvPaymentPeriod : TextView
    var tvPeriodDay : TextView

    var layout : ConstraintLayout

    var btn : Button

    init {
        tvTitle = itemView.findViewById(R.id.tv_title)
        tvPaymentPeriod = itemView.findViewById(R.id.tv_payment_period)
        tvPeriodDay  = itemView.findViewById(R.id.tv_period_day)
        layout = itemView.findViewById(R.id.one_payment_type_item_layout)
        btn = itemView.findViewById(R.id.btn_go_to_payment_page)
    }
}