package com.br.workdate

import org.junit.Test

class Fibonacci {

    var results = hashMapOf(
        1 to 1,
        2 to 2,
        3 to 3
    )

    private fun fib(x: Int): Int {
        if (x < 3) return (results.getValue(x))
        return if (results.containsKey(x)) {
            (results.getValue(x - 1) + results.getValue(x - 2))
        } else {
            results[x] = (fib(x - 1) + fib(x - 2))
            results.getValue(x)
        }
    }

    @Test
    fun test() {
        for (i in 1..697) {
            println("${fib(i)}")
        }
    }

}