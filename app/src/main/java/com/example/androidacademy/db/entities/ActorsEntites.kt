package com.example.androidacademy.db.entities

import androidx.room.*

@Entity(
    tableName = DbContract.ActorContract.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = MovieEntities::class,
        parentColumns = arrayOf(DbContract.MovieContract.COLUMN_NAME_ID),
        childColumns = arrayOf(DbContract.ActorContract.COLUMN_NAME_MOVIE_ID),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = [DbContract.ActorContract.COLUMN_NAME_MOVIE_ID])]
)
class ActorsEntites (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbContract.ActorContract.COLUMN_NAME_ID)
    val id: Long?,
    @ColumnInfo(name = DbContract.ActorContract.COLUMN_NAME_ACTOR_ID)
    val actorId: Int,
    val name: String,
    @ColumnInfo(name = DbContract.ActorContract.COLUMN_NAME_IMAGE)
    val image: String?,
    @ColumnInfo(name = DbContract.ActorContract.COLUMN_NAME_MOVIE_ID)
    val movie: Long
    )