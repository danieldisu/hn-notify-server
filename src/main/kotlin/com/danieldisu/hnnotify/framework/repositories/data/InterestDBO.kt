package com.danieldisu.hnnotify.framework.repositories.data

import com.danieldisu.hnnotify.domain.data.Interest
import org.hibernate.annotations.CreationTimestamp
import java.sql.Date
import java.util.*
import javax.persistence.*

private const val INTEREST_KEYWORD_SEPARATOR = ";;"

@Entity
@Table(name = "interest")
data class InterestDBO(
    @Id
    @Column
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne
    @JoinColumn(name = "userId")
    val user: UserDBO,

    val interestName: String,

    val interestKeywords: String,

    @CreationTimestamp
    val addedOn: Date = Date(0L)
) {

    fun toDomain() = Interest(
        interestName = interestName,
        userId = user.id,
        interestKeywords = interestKeywords.split(INTEREST_KEYWORD_SEPARATOR),
    )

    companion object {

        fun from(
            interestId: String,
            userId: String,
            interestName: String,
            interestKeywords: List<String>
        ) = InterestDBO(
            id = interestId,
            user = UserDBO(userId),
            interestName = interestName,
            interestKeywords = interestKeywords.joinToString(separator = INTEREST_KEYWORD_SEPARATOR)
        )

        fun from(
            user: UserDBO,
            interestName: String,
            interestKeywords: List<String>
        ) = InterestDBO(
            user = user,
            interestName = interestName,
            interestKeywords = interestKeywords.joinToString(separator = INTEREST_KEYWORD_SEPARATOR)
        )

    }

}
