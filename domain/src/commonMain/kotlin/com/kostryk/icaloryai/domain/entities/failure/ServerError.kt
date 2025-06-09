package com.kostryk.icaloryai.domain.entities.failure

sealed class ServerError : Failure.FeatureFailure() {
    object ServerCommon : ServerError()
}