package com.danieldisu.hnnotify.application.interest

import com.danieldisu.hnnotify.domain.data.Interest
import com.danieldisu.hnnotify.framework.repositories.data.InterestDBO
import com.danieldisu.hnnotify.framework.repositories.interest.InterestRepository
import org.springframework.stereotype.Service

@Service
class EditInterest(
    private val interestRepository: InterestRepository,
) {

    operator fun invoke(request: EditInterestRequest): EditInterestResponse {
        if (!interestRepository.existsById(request.interestId)) return EditInterestResponse.InterestNotFound
        if (existsInterestWithThatName(request)) return EditInterestResponse.DuplicatedInterestName

        return save(request).toResponse()
    }

    private fun existsInterestWithThatName(request: EditInterestRequest) =
        interestRepository.existsByInterestNameAndUserId(
            request.interestName,
            request.userId
        )

    private fun save(request: EditInterestRequest) = kotlin.runCatching {
        interestRepository.save(request.toInterestDBO())
    }
}

data class EditInterestRequest(
    val userId: String,
    val interestId: String,
    val interestName: String,
    val interestKeywords: List<String>,
)

sealed class EditInterestResponse {

    data class SuccessfullyUpdated(
        val interest: Interest
    ) : EditInterestResponse()

    object InterestNotFound : EditInterestResponse()

    object DuplicatedInterestName : EditInterestResponse()

    data class UnknownError(
        val cause: Throwable
    ) : EditInterestResponse()

}

private fun Result<InterestDBO>.toResponse(): EditInterestResponse =
    this.fold(
        onSuccess = { EditInterestResponse.SuccessfullyUpdated(it.toDomain()) },
        onFailure = { EditInterestResponse.UnknownError(it) }
    )

private fun EditInterestRequest.toInterestDBO() =
    InterestDBO.from(
        interestId = interestId,
        userId = userId,
        interestName = interestName,
        interestKeywords = interestKeywords
    )
