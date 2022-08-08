package com.example.myweatherapp.viewModel

import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.data.impl.APIImpl
//import com.example.data.impl.APIImpl
import com.example.myweatherapp.BuildConfig
import com.example.myweatherapp.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainViewModel : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun createRequestParams(myLocation:Location?): HashMap<String, String> {
        val now = LocalDateTime.now()
        val baseTime = when {
            now.hour > 11 -> {
                if (now.minute < 40) "${now.hour - 1}00"
                else "${now.hour}00"
            }
            now.hour == 10 -> {
                if (now.minute < 40) "0900"
                else "1000"
            }
            now.hour in 1..9 -> {
                if (now.minute < 40) "0${now.hour - 1}00"
                else "0${now.hour}00"
            }
            now.hour == 0 -> {
                if (now.minute < 40) "2300"
                else "0000"
            }
            else -> "0000"
        }

        val baseDate = if (now.hour != 0) {
            when {
                now.monthValue > 10 && now.dayOfMonth > 10 -> "${now.year}${now.monthValue}${now.dayOfMonth}"
                now.monthValue > 10 && now.dayOfMonth < 10 -> "${now.year}${now.monthValue}0${now.dayOfMonth}"
                now.monthValue < 10 && now.dayOfMonth > 10 -> "${now.year}0${now.monthValue}${now.dayOfMonth}"
                now.monthValue < 10 && now.dayOfMonth < 10 -> "${now.year}0${now.monthValue}0${now.dayOfMonth}"
                else -> "20220801"
            }
        } else {
            val date =
                if (baseTime != "0000") now.minusDays(1)
                else now

            when {
                date.monthValue > 10 && date.dayOfMonth > 10 -> "${date.year}${date.monthValue}${date.dayOfMonth}"
                date.monthValue > 10 && date.dayOfMonth < 10 -> "${date.year}${date.monthValue}0${date.dayOfMonth}"
                date.monthValue < 10 && date.dayOfMonth > 10 -> "${date.year}0${date.monthValue}${date.dayOfMonth}"
                date.monthValue < 10 && date.dayOfMonth < 10 -> "${date.year}0${date.monthValue}0${date.dayOfMonth}"
                else -> "20220801"
            }
        }

        return HashMap<String, String>().apply {
            put("serviceKey", BuildConfig.SERVICE_KEY)
            put("dataType", "JSON")
            put("base_date", baseDate)
            put("base_time", baseTime)
            put("nx", myLocation?.latitude?.toInt().toString())
            put("ny", myLocation?.longitude?.toInt().toString())
            //이렇게 메인 엑티비티 객체를 만들면 메모리 릭??
        }
    }

    fun getWeatherData(data: HashMap<String, String>) {
        Log.e(javaClass.simpleName, "getWeatherData: $data", )
///*        CoroutineScope(Dispatchers.IO).launch {
//            var pureum = APIImpl().getWeatherData(data)
//            Log.e(javaClass.simpleName, "API_Data: $pureum", )
//        }*/
    }
}