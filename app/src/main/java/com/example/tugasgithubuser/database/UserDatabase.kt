package com.example.tugasgithubuser.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserDatabase {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class.java) {
                    INSTANCE = databaseBuilder(context.applicationContext,
                        UserDatabase::class.java, "favoriteuser_database")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as UserDatabase
        }
    }
}