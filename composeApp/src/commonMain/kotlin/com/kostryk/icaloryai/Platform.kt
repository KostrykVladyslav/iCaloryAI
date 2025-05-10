package com.kostryk.icaloryai

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform