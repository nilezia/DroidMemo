package app.nileza.droidmemo.utils

import java.text.SimpleDateFormat
import java.util.*

class Utility {
    companion object {
        fun getTimeStamp(): String {
            var date = ""
            try {
                val df = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH)
                date = df.format(Calendar.getInstance().time)

            } catch (e: Exception) {

            }
            return date
        }
    }
}

