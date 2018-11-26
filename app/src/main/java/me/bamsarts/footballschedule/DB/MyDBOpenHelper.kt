package me.bamsarts.footballschedule.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDBOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "db_favourite") {

    companion object {
        private var instance: MyDBOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDBOpenHelper {
            if (instance == null) {
                instance = MyDBOpenHelper(ctx.applicationContext)
            }
            return instance as MyDBOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            FavouriteData.TABLE_FAVOURITE_MATCH, true, FavouriteData.ID to INTEGER+ PRIMARY_KEY+ AUTOINCREMENT,
            FavouriteData.ID_EVENT to TEXT + UNIQUE,
            FavouriteData.DATE_EVENT to TEXT,
            FavouriteData.strHomeTeam to TEXT,
            FavouriteData.strAwayTeam to TEXT,
            FavouriteData.intHomeScore to TEXT,
            FavouriteData.intAwayScore to TEXT,
            FavouriteData.idHomeTeam to TEXT,
            FavouriteData.idAwayTeam to TEXT,
            FavouriteData.strDate to TEXT
        )
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable("TABLE_FAVOURITE_MATCH", true)
    }
}

val Context.database: MyDBOpenHelper
    get() = MyDBOpenHelper.getInstance(applicationContext)
