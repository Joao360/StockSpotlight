package com.joaograca.stockmarket.data.mapper

import com.joaograca.stockmarket.data.local.CompanyListingEntity
import com.joaograca.stockmarket.data.remote.dto.CompanyInfoDto
import com.joaograca.stockmarket.domain.model.CompanyInfo
import com.joaograca.stockmarket.domain.model.CompanyListing

fun CompanyListingEntity.toDomain() = CompanyListing(
    name = name,
    symbol = symbol,
    exchange = exchange
)

fun CompanyListing.toEntity() = CompanyListingEntity(
    name = name,
    symbol = symbol,
    exchange = exchange
)

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol.orEmpty(),
        description = description.orEmpty(),
        name = name.orEmpty(),
        country = country.orEmpty(),
        industry = industry.orEmpty()
    )
}