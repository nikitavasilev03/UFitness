package com.example.android.ufitness.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "exercises")
data class Exercise(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,
        val name: String,
        val description: String
) : Parcelable