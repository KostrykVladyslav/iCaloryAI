package com.kostryk.icaloryai.domain.converter

abstract class Mapper<in T, out E> {

    abstract fun map(from: T): E

    open fun map(from: List<T>) = from.map { map(it) }

}