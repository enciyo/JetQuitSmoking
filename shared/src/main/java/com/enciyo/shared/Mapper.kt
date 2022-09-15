package com.enciyo.shared

interface Mapper<in I, out O> {
    fun mapTo(input: I): O
}