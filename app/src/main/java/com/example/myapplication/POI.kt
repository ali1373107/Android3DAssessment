package com.example.myapplication;
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Poi")
data class POI (
        @PrimaryKey(autoGenerate = true) val id: Long,
        var name: String,
        var type: String,
        var description:String,
        val lat:Double,
        val lon:Double
)
