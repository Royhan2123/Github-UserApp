package com.example.tugasgithubuser.database
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class UserEntity (
    @PrimaryKey
    @ColumnInfo(name = "username")
    var username: String = "",

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String = "",

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = true,

    @ColumnInfo(name = "followersCount")
    var followersCount: String? = null,

    @ColumnInfo(name = "followingCount")
    var followingCount: String? = null
) : Parcelable