package com.example.modifydata

import io.realm.RealmObject

open class Data : RealmObject() {
    var date = ""
    var temperature = ""
    var precipitation = ""
    var daylightHours = ""
    var windSpeed = ""
    var cloudCover = ""
    var vaporPressure = ""
    var humidity = ""
    var seaLevelPressure = ""
    var localBarometricPressure = ""
    var highestTemperature = ""
    var lowestTemperature = ""
    var weather = ""
}