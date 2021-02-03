package com.example.modifydata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import org.apache.commons.csv.CSVFormat
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private var arrayYear = arrayListOf<String>()
    private var arrayMonth = arrayListOf<String>()
    private var arrayDay = arrayListOf<String>()
    private var array = arrayListOf<String>()
    private var date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        realm = Realm.getDefaultInstance()

//        readData()

        modify()
//        modify("precipitation")
//        modify("daylightHours")
//        modify("windSpeed")
//        modify("cloudCover")
//        modify("vaporPressure")
//        modify("seaLevelPressure")
//        modify("localBarometricPressure")
//        modify("highestTemperature")
//        modify("lowestTemperature")

    }

    private fun modify() {
//        for (i in 0 until arrayYear.size) arrayYear.removeAt(0)
//        for (i in 0 until arrayMonth.size) arrayMonth.removeAt(0)
//        for (i in 0 until arrayDay.size) arrayDay.removeAt(0)
        for (i in 0 until array.size) array.removeAt(0)
        val realmResultAll = realm.where<Data>().findAll()
        for (i in 0 until realmResultAll.size) array.add(realmResultAll[i]?.date!!)

        val realmResult = realm.where<Data>()
            .contains("cloudCover",".")
            .findAll()
        for (i in 0 until realmResult.size) array.remove(realmResult[i]?.date!!)

        realm.executeTransaction {
            for (i in 0 until array.size) {
                val realmResult2 = realm.where<Data>()
                        .equalTo("date", array[i])
                        .findFirst()
                realmResult2?.cloudCover = realmResult2?.cloudCover + ".0"
            }
        }

//        for (i in 0 until arrayYear.size) date += "${arrayYear[i]}/${arrayMonth[i]}/${arrayDay[i]}"
//
//        Log.e("date", date + "NoneError")
    }

    private fun readData() {
        val reader = BufferedReader(InputStreamReader(resources.assets.open("originalData.csv")))
        reader.use {
            val records = CSVFormat.EXCEL.parse(reader)
            realm.executeTransaction {
                val target = realm.where<Data>().findAll()
                target.deleteAllFromRealm()
                records.records.forEach { record ->
                    val obj = realm.createObject<Data>()
                    obj.date = record[0]
                    obj.temperature = record[1]
                    obj.precipitation = record[2]
                    obj.daylightHours = record[3]
                    obj.windSpeed = record[4]
                    obj.cloudCover = record[5]
                    obj.vaporPressure = record[6]
                    obj.humidity = record[7]
                    obj.seaLevelPressure = record[8]
                    obj.localBarometricPressure = record[9]
                    obj.highestTemperature = record[10]
                    obj.lowestTemperature = record[11]
                    obj.weather = record[12]
                }
            }
        }
    }
}