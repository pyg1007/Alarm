package kr.ryan.weatheralarm.util

import kotlin.math.*

/**
 * WeatherAlarm
 * Class: calculatorLatitudeAndLongitude
 * Created by pyg10.
 * Created On 2022-02-13.
 * Description:
 */
object CalculatorLatitudeAndLongitude {

    // 기상청 격자 변환
    const val RE = 6317.00877
    const val GRID = 5.0
    const val SLAT1 = 30.0
    const val SLAT2 = 60.0
    const val OLON = 126.0
    const val OLAT = 38.0
    const val XO = 43
    const val YO = 136

    const val DEGRAD = Math.PI / 180.0
    const val RADDEG = 180.0 / Math.PI

    const val TO_GRID = 0
    const val TO_GPS = 1

    fun convertGRIDTOGPS(mode: Int, lat: Double, lng: Double): LatXLngY {

        val re = RE / GRID
        val slat1 = SLAT1 * DEGRAD
        val slat2 = SLAT2 * DEGRAD
        val olon = OLON * DEGRAD
        val olat = OLAT * DEGRAD

        var sn = tan(Math.PI * 0.25 + slat2 * 0.5) / tan(Math.PI * 0.25 + slat1 * 0.5)
        sn = ln(cos(slat1) / cos(slat2)) / ln(sn)

        var sf = tan(Math.PI * 0.25 + slat1 * 0.5)
        sf = sf.pow(sn) * cos(slat1) / sn
        var ro = tan(Math.PI * 0.25 + olat * 0.5)
        ro = re * sf / ro.pow(sn)
        val rs = LatXLngY()

        if (mode == TO_GRID) {
            rs.lat = lat
            rs.lng = lng
            var ra = tan(Math.PI * 0.25 + (lat) * DEGRAD * 0.5)
            ra = re * sf / ra.pow(sn)
            var theta = lng * DEGRAD - olon
            if (theta > Math.PI) theta -= 2.0 * Math.PI
            if (theta < -Math.PI) theta += 2.0 * Math.PI
            theta *= sn
            rs.x = floor(ra * sin(theta) + XO + 0.5)
            rs.y = floor(ro - ra * cos(theta) + YO + 0.5)
        } else {
            rs.x = lat
            rs.y = lng
            val xn = lat - XO
            val yn = ro - lng + YO
            var ra = sqrt(xn * xn + yn * yn)
            if (sn < 0.0) {
                ra = -ra
            }
            var alat = (re * sf / ra).pow((1.0 / sn))
            alat = 2.0 * atan(alat) - Math.PI * 0.5

            var theta: Double
            if (abs(xn) <= 0.0) {
                theta = 0.0
            } else {
                if (abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5
                    if (xn < 0.0) {
                        theta = -theta
                    }
                } else theta = atan2(xn, yn)
            }
            val alon = theta / sn + olon
            rs.lat = alat * RADDEG
            rs.lng = alon * RADDEG
        }
        return rs
    }

    class LatXLngY {
        var lat: Double = 0.0
        var lng: Double = 0.0

        var x: Double = 0.0
        var y: Double = 0.0
    }


}