package com.br.workdate.others

import org.junit.Test
import java.util.*

class Prime {

    private fun isPrime(number: Int): Boolean {
        for (i in 2 until number) {
            if (number % i == 0) return true
        }
        return false
    }


    fun showPrime() {
        for (i in 1..100) {
            if (isPrime(i)) {
                println("$i")
            } else {
                println("prime $i")
            }
        }
    }

    @Test
    fun scan() {
        val scanner = Scanner(System.`in`)
        val myScan = scanner.nextLine().toInt()
        print(myScan in 1..9)
    }
}
