package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navHostFragment.navController
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getmaximumOccurance(intArrayOf(1, 1, 1, 1, 2, 3, 4, 5).toList())
    }

    fun getmaximumOccurance(array: List<Int?>): Int {
        val map = HashMap<Int, Int>()
        var max = 0
        var element = -1
        for (value in array) {
            value?.let {
                var occurance = 0
                if (map.contains(it)) {
                    occurance = map.get(it)!! + 1
                    map.put(it, occurance)
                } else {
                    occurance += 1
                    map.put(it, occurance)
                }
                if (occurance > max) {
                    element = it
                    max = occurance
                }
            }

        }
        return element
    }


    fun findPerfectSquare(x: Int, y: Int, total: Int) {
        if (x == 0 || y == 0) {
            print("Number of perfect square is $total")
        }
        //find the smallest value between x and y
        var numberofsquare = 0
        if (x > y) {
            numberofsquare = x / y
            findPerfectSquare(y, y % x, numberofsquare + total)
        } else if (y > x) {
            numberofsquare = y / x
            findPerfectSquare(x, y % x, numberofsquare + total)
        }
    }
}