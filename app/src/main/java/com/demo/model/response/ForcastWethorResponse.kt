package com.demo.model.response

/**
 * This is class use for showing Future Weather Response
 */
data class ForcastWethorResponse(
    var city: City?,
    var cnt: Int?,
    var cod: String?,
    var list: List<Weth>?,
    var message: Int?
)

data class City(
    var coord: Coord?,
    var country: String?,
    var id: Int?,
    var name: String?,
    var population: Int?,
    var sunrise: Int?,
    var sunset: Int?,
    var timezone: Int?
)

data class Coord(
    var lat: Double?,
    var lon: Double?
)

data class Weth(
    var clouds: Clouds?,
    var dt: Long?,
    var dt_txt: String?,
    var main: Main?,
    var sys: Sys?,
    var weather: List<Weather>?,
    var wind: Wind?
)

data class Clouds(
    var all: Int?
)

data class Main(
    var feels_like: Double?,
    var grnd_level: Int?,
    var humidity: Int?,
    var pressure: Int?,
    var sea_level: Int?,
    var temp: Double?,
    var temp_max: Double?,
    var temp_min: Double?
)

data class Sys(
    var pod: String?
)

data class Weather(
    var description: String?,
    var icon: String?,
    var id: Int?,
    var main: String?
)

data class Wind(
    var deg: Int?,
    var speed: Double?
)