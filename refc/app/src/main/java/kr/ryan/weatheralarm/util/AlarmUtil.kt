package kr.ryan.weatheralarm.util

import java.security.SecureRandom

/**
 * WeatherAlarm
 * Class: AlarmUtil
 * Created by pyg10.
 * Created On 2022-01-25.
 * Description:
 */

/**
 *
 *  1001 ~ INT MAX 까지의 난수 생성
 *
 */
fun createRandomNumber(): Int = SecureRandom().nextInt(Int.MAX_VALUE - 1001) + 1001
