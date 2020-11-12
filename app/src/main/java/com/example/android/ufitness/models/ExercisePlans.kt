package com.example.android.ufitness.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "exercisePlans")
data class ExercisePlans(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "exerciseId") val exerciseId: Int,
    @ColumnInfo(name = "planId") val planId: Int,
    @ColumnInfo(name = "isTimeBased") val isTimeBased: Boolean,
    @ColumnInfo(name = "repeatCount") val repeatCount: Int
) : Parcelable