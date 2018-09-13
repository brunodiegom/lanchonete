package com.brunodiego.calculadoraanimal.component

/**
 * Utils class to provide data to Log.
 */
object Logger {

    val tag: String
        get() {
            var tag = ""
            val ste = Thread.currentThread().stackTrace
            for (i in ste.indices) {
                if (ste[i].methodName == "getTag") {
                    tag = "(" + ste[i + 1].fileName + ":" + ste[i + 1].lineNumber + ")"
                }
            }
            return tag
        }

}