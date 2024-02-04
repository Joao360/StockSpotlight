package com.joaograca.stockmarket.data.mapper

import com.joaograca.stockmarket.data.local.CompanyListingEntity
import com.joaograca.stockmarket.domain.model.CompanyListing

fun CompanyListingEntity.toDomain() = CompanyListing(name, symbol, exchange)

fun CompanyListing.toEntity() = CompanyListingEntity(name, symbol, exchange)