package com.demo.model.response

/**
 * This is class use for showing Current Weather Response
 */
data class CurrentTemperatureResponse(
    var base: String?,
    var clouds: Clouds?,
    var cod: Int?,
    var coord: Coord?,
    var dt: Int?,
    var id: Int?,
    var main: Main?,
    var name: String?,
    var sys: Sys?,
    var timezone: Int?,
    var visibility: Int?,
    var weather: List<Weather?>?,
    var wind: Wind?
)

