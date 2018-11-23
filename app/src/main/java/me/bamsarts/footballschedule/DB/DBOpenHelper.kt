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
            FavouriteData.FavoriteMatch, true, FavouriteData.id to INTEGER+ PRIMARY_KEY+ AUTOINCREMENT,
            FavouriteData.idEvent to TEXT + UNIQUE,
            FavouriteData.dateEvent to TEXT,
            FavouriteData.strHomeTeam to TEXT,
            FavouriteData.strAwayTeam to TEXT,
            FavouriteData.intHomeScore to TEXT,
            FavouriteData.intAwayScore to TEXT,
            FavouriteData.idHomeTeam to TEXT,
            FavouriteData.idAwayTeam to TEXT,
            FavouriteData.idSoccerXML to TEXT,
            FavouriteData.strDate to TEXT
        )
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable("FavoriteMatch", true)
    }
}

val Context.database: DBOpenHelper
    get() = DBOpenHelper.getInstance(applicationContext)
