package com.joaograca.stockmarket.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(entities: List<CompanyListingEntity>)

    @Query("DELETE * FROM companylistingentity")
    suspend fun clearCompanyListings()

    @Query(
        """
            SELECT *
            FROM companylistingentity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR 
                UPPER(:query) == symbol
        """
    )
    suspend fun searchCOmpanyListing(query: String): List<CompanyListingEntity>
}