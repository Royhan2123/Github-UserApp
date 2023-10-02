package com.example.tugasgithubuser.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userEntity: UserEntity)

    @Delete
    fun delete(userEntity: UserEntity)

    @Query("SELECT * FROM userentity ORDER BY username ASC")
    fun getAllFavoriteUsers(): LiveData<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM userentity WHERE username= :username AND isFavorite = 1)")
    fun isFavorite(username: String): Boolean
}