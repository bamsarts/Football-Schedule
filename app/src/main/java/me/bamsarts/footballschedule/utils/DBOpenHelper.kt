package me.bamsarts.footballschedule.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "favorite_db") {

    companion object {
        private var instance: DBOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBOpenHelper {
            if (instance == null) {
                instance = DBOpenHelper(ctx.applicationContext)
            }
            return instance as DBOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            "FavoriteMatch", true,
            "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "idEvent" to TEXT,
            "idSoccerXML" to TEXT,
            "idHomeTeam" to TEXT,
            "idAwayTeam" to TEXT,
            "strHomeTeam" to TEXT,
            "strAwayTeam" to TEXT,
            "intHomeScore" to TEXT,
            "intAwayScore" to TEXT,
            "dateEvent" to TEXT,
            "strDate" to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable("FavoriteMatch", true)
    }
}

val Context.database: DBOpenHelper
    get() = DBOpenHelper.getInstance(applicationContext)
