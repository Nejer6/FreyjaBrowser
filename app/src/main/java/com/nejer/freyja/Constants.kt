package com.nejer.freyja

import androidx.compose.ui.graphics.Color
import com.nejer.freyja.database.DatabaseRepository

lateinit var APP: MainActivity
lateinit var REPOSITORY: DatabaseRepository

object Constants {
    object Keys {
        const val FOLDERS_DATABASE = "folders_database"
        const val URLS_TABLE = "urls_table"
    }

    object Screens {
        const val MAIN_SCREEN = "main_screen"
        const val ARCHIVE_SCREEN = "archive_screen"
    }

    object Text {
        const val BACK_TO_WEB = "back to web"
        const val EMPTY_FOLDER = "empty folder"
        const val ELEMENTS_INSIDE = "elements inside"
        const val LINK = "link"
        const val ARCHIVE = "archive"
        const val SAVE = "save"
    }

    object Colors {
        val Orange = Color(255,204,0,255)
        val Yellow = Color(254, 229, 177, 255)
        val DarkBlue = Color(32, 62, 95, 255)
    }

    object Tags {
        const val TAG = "tag"
        const val URLS = "urls"
    }

    object Urls {
        const val YANDEX = "yandex.ru/"
    }

    object WebViewSettings {
        const val MIME_TYPE = "text/plain"
        const val ENCODING = "utf8"
        const val HTTP = "http://"
        const val HTTPS = "https://"
        const val USER_AGENT =
            "Mozilla/5.0 (Linux; Android 9; ZTE 2050RU) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Mobile Safari/537.36"
        val BLOCK_LIST = listOf(
            "zyf03k.xyz",
            "http://mvd-tl.online",
            "i.bimbolive.com",
            "ht-cdn.trafficjunky.net",
            "hw-cdn2.adtng.com",
            "rf.bongacams25.com",
            "appzery.com"
        )
    }
}