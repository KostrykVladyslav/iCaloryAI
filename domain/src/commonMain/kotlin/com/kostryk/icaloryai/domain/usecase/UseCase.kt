package com.kostryk.icaloryai.domain.usecase

interface UseCase<out Result> where Result : Any {
    open class Params
}