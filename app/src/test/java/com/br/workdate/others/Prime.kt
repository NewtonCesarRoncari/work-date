package com.br.workdate.others

import org.junit.Test

class Prime {

    private fun isPrime(number: Int): Boolean {
        for (i in 2 until number) {
            if (number % i == 0) return true
        }
        return false
    }

    @Test
    fun showPrime() {
        for (i in 1..100) {
            if (isPrime(i)) {
                println("$i")
            } else {
                println("prime $i")
            }
        }
    }
}
