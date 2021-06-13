package com.danieldisu.hnnotify.domain.mappings

import com.danieldisu.hnnotify.domain.data.Interest
import com.danieldisu.hnnotify.framework.repositories.data.InterestDBO

const val INTEREST_KEYWORD_SEPARATOR = ";;"

fun InterestDBO.toDomain() = Interest(
    interestName = interestName,
    userId = user.id,
    interestKeywords = interestKeywords.split(INTEREST_KEYWORD_SEPARATOR),
)

