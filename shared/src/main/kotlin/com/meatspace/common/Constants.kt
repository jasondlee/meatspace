package com.meatspace.common

object Constants {
    const val APP_NAME = "Meatspace"
    const val API_VERSION = "v1"

    object JWT {
        const val ISSUER = "https://meatspace.com"
        const val EXPIRATION_TIME = 86400L // 24 hours in seconds
    }

    object Validation {
        const val MIN_USERNAME_LENGTH = 3
        const val MAX_USERNAME_LENGTH = 50
        const val MIN_PASSWORD_LENGTH = 8
        const val MIN_GROUP_NAME_LENGTH = 3
        const val MAX_GROUP_NAME_LENGTH = 255
    }

    object ErrorCodes {
        const val INVALID_CREDENTIALS = "INVALID_CREDENTIALS"
        const val USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS"
        const val USER_NOT_FOUND = "USER_NOT_FOUND"
        const val GROUP_NOT_FOUND = "GROUP_NOT_FOUND"
        const val MEETING_NOT_FOUND = "MEETING_NOT_FOUND"
        const val UNAUTHORIZED = "UNAUTHORIZED"
        const val FORBIDDEN = "FORBIDDEN"
        const val VALIDATION_ERROR = "VALIDATION_ERROR"
    }
}
