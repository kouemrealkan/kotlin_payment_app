package com.emrealkan.paymentappchallenge.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.emrealkan.paymentappchallenge.model.Payment
import com.emrealkan.paymentappchallenge.model.PaymentType

class DatabaseOpenHelper(val context : Context): SQLiteOpenHelper(context, DatabaseOpenHelper.DATABASE_NAME, null, DatabaseOpenHelper.DATABASE_VERSION) {
    private val TABLE_NAME="payment_type"
    private val COL_ID = "id"
    private val COL_TITLE = "title"
    private val COL_PAYMENT_PERIOD = "payment_period"
    private val COL_PERIOD_DAY = "period_day"


    companion object {
        private val DATABASE_NAME = "payment_tracking"
        private val DATABASE_VERSION = 1
    }

    override fun onCreate(p0: SQLiteDatabase?) {
       var query = "CREATE TABLE $TABLE_NAME($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, $COL_TITLE TEXT, $COL_PAYMENT_PERIOD TEXT, $COL_PERIOD_DAY INTEGER)"

       var query2 = "CREATE TABLE payment(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, payment_amount DOUBLE, payment_date TEXT,payment_type_id INTEGER)"




        //p0!!.execSQL(query)


        p0!!.execSQL(query2)



    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }


    fun insertPaymentTypeData(paymentType: PaymentType){
        val sqliteDb = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TITLE , paymentType.title)
        contentValues.put(COL_PAYMENT_PERIOD , paymentType.paymentPeriod)
        contentValues.put(COL_PERIOD_DAY , paymentType.periodDay)
        val result = sqliteDb.insert(TABLE_NAME,null,contentValues)
        Toast.makeText(context,if(result != -1L) "Kayıt Başarılı" else "Kayıt yapılamadı.", Toast.LENGTH_SHORT).show()
    }

    fun insertPaymentData(payment: Payment){
        val sqliteDb = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("payment_amount" , payment.paymentAmount)
        contentValues.put("payment_date" , payment.paymentDate)
        contentValues.put("payment_type_id" , payment.paymentTypeId)
        val result = sqliteDb.insert("payment",null,contentValues)
        Toast.makeText(context,if(result != -1L) "Ödeme Başarılı" else "Ödeme yapılamadı.", Toast.LENGTH_SHORT).show()
    }


    private fun getAllPaymentTypeList():Cursor{
        val query =  "SELECT * FROM $TABLE_NAME"
        val sqLiteDb = this.readableDatabase
        return sqLiteDb!!.rawQuery(query,null)
    }

    private fun getPaymentTypeById(Id :Int):Cursor{
        val sqLiteDb = this.readableDatabase
        val query = "Select * from payment_type WHERE Id= ?"
        return sqLiteDb!!.rawQuery(query, arrayOf(Id.toString()))
    }

    private fun getPaymentListByPaymentTypeId(paymentTypeId:Int):Cursor{
        val sqLiteDb = this.readableDatabase
        val query = "Select * from payment WHERE payment_type_id= ?"
        return sqLiteDb!!.rawQuery(query, arrayOf(paymentTypeId.toString()))
    }

    @SuppressLint("Range")
    fun getPaymentListWithPaymentTypeId(Id:Int):MutableList<Payment>{
        var paymentList = mutableListOf<Payment>()
        val sqlitedb = this.writableDatabase

        val c : Cursor = getPaymentListByPaymentTypeId(Id)
        if(c.moveToFirst()){
            do {
                val payment = Payment()
                payment.id = c.getInt(0)
                payment.paymentAmount = c.getDouble(c.getColumnIndex("payment_amount"))
                payment.paymentDate = c.getString(c.getColumnIndex("payment_date"))
                payment.paymentTypeId = c.getInt(c.getColumnIndex("payment_type_id"))

                paymentList.add(payment)

            }while (c.moveToNext())
        }
        c.close()
        sqlitedb.close()
        return paymentList
    }

    @SuppressLint("Range")
    fun getPaymentTypeWithId(Id: Int):PaymentType{

        var paymentType = PaymentType()

        val sqlitedb = this.writableDatabase

        val c : Cursor = getPaymentTypeById(Id)
        if(c.moveToFirst()){
            do {
                val pt = PaymentType()
                pt.id = c.getInt(c.getColumnIndex("id"))
                pt.title = c.getString(c.getColumnIndex(COL_TITLE))
                pt.paymentPeriod = c.getString(c.getColumnIndex(COL_PAYMENT_PERIOD))
                pt.periodDay = c.getString(c.getColumnIndex(COL_PERIOD_DAY)).toInt()

                paymentType = pt

            }while (c.moveToNext())
        }
        c.close()
        sqlitedb.close()
        return paymentType
    }

    @SuppressLint("Range")
    fun readPaymentTypeData() : MutableList<PaymentType>{
        var ptList = mutableListOf<PaymentType>()

        var pt : PaymentType
        val sqlitedb = this.writableDatabase

        val c : Cursor = getAllPaymentTypeList()

        if(c.moveToFirst()){
            do {
                val paymentType = PaymentType()
                paymentType.id = c.getInt(c.getColumnIndex(COL_ID))
                paymentType.title = c.getString(c.getColumnIndex(COL_TITLE))
                paymentType.paymentPeriod = c.getString(c.getColumnIndex(COL_PAYMENT_PERIOD))
                paymentType.periodDay = c.getString(c.getColumnIndex(COL_PERIOD_DAY)).toInt()
                ptList.add(paymentType)

            }while (c.moveToNext())
        }
        c.close()
        sqlitedb.close()
        return ptList
    }


    fun updatePaymentType(paymentType: PaymentType){
        val cv =ContentValues()
        cv.put("title",paymentType.title)
        cv.put("payment_period",paymentType.paymentPeriod)
        cv.put("period_day",paymentType.periodDay)
        val sqlitedb = this.writableDatabase
        sqlitedb!!.update("payment_type",cv,"Id = ?", arrayOf(paymentType.id.toString()))
        sqlitedb.close()
    }


    fun deletePayment(id: Int){
        val sqlitedb = this.writableDatabase
        sqlitedb!!.delete("payment","Id = ?", arrayOf(id.toString()))
        if(sqlitedb.isOpen){
            sqlitedb!!.close()
        }
    }

    fun deletePaymentType(id: Int){
        val sqlitedb = this.writableDatabase
        sqlitedb!!.delete("payment_type","Id = ?", arrayOf(id.toString()))
        if(sqlitedb.isOpen){
            sqlitedb!!.close()
        }
    }
}