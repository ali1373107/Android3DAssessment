package com.example.myapplication;

import androidx.room.*
@Dao
interface PoiDao {
    @Query("SELECT * FROM Poi WHERE id=:id")
    fun getPoiById(id:Long): POI?

    @Query("SELECT * FROM Poi")
    fun getAllpois():List<POI>
    @Insert
    fun insert(poi:POI):Long
    @Update
    fun update(poi:POI):Int
    @Delete
    fun delete(poi:POI):Int
}
