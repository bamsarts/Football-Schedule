package me.bamsarts.footballschedule.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "favorite_db") {

    companion object {
        private var instance: DBOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBOpenHelper {
            if (instance == null) {
                instance =
                        DBOpenHelper(ctx.applicationContext)
            }
            return instance as DBOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            Favorite.FavoriteMatch, true, Favorite.id to INTEGER+ PRIMARY_KEY+ AUTOINCREMENT,
            Favorite.idEvent to TEXT + UNIQUE,
            Favorite.dateEvent to TEXT,
            Favorite.strHomeTeam to TEXT,
            Favorite.strAwayTeam to TEXT,
            Favorite.intHomeScore to TEXT,
            Favorite.intAwayScore to TEXT,
            Favorite.idHomeTeam to TEXT,
            Favorite.idAwayTeam to TEXT,
            Favorite.idSoccerXML to TEXT,
            Favorite.strDate to TEXT
        )
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable("FavoriteMatch", true)
    }
}

val Context.database: DBOpenHelper
    get() = DBOpenHelper.getInstance(applicationContext)
