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
        val interestExists = interestRepository.existsById(request.interestId)
        if (!interestExists) return EditInterestResponse.InterestNotFound

        return save(request).toResponse()
    }

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

    data class SuccessFullyUpdated(
        val interest: Interest
    ) : EditInterestResponse()

    object InterestNotFound : EditInterestResponse()

    data class UnknownError(
        val cause: Throwable
    ) : EditInterestResponse()

}

private fun Result<InterestDBO>.toResponse(): EditInterestResponse =
    this.fold(
        onSuccess = { EditInterestResponse.SuccessFullyUpdated(it.toDomain()) },
        onFailure =
        { EditInterestResponse.UnknownError(it) }
    )

private fun EditInterestRequest.toInterestDBO() =
    InterestDBO.from(
        interestId = interestId,
        userId = userId,
        interestName = interestName,
        interestKeywords = interestKeywords
    )
