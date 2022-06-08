package com.training.memorama

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.training.memorama.databinding.ActivityGameBinding
import java.util.*


class Game : AppCompatActivity() {

    private lateinit var binding : ActivityGameBinding
    var backCard:Int = 0
    var cardShuffle: ArrayList<Int>? = null
    lateinit var imgsList:Array<Int>
    var _checks:Int = 0
    var score:Int = 0
    val btnPanel = arrayOfNulls<ImageButton>(6)
    var pause:Boolean = false
    var _baseTurn:ImageButton? = null
    var _matchTurn:ImageButton? = null
    var _baseTurnImg:Int = 0
    var _matchTurnImg:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadImages()
        botonesMenu()
        loadConfig()
    }
    private fun loadImages(){
        this.imgsList = arrayOf<Int>(
            R.drawable.f0,
            R.drawable.f1,
            R.drawable.f2,
        )
        backCard = R.drawable.fondo
    }
    private fun loadConfig() {
        cardShuffle = shuffleArrayCards(imgsList.size * 2)
//        for (i in 0 until btnPanel.size) {
//            btnPanel[i]!!.scaleType = ImageView.ScaleType.CENTER_CROP
//            btnPanel[i]!!.setImageResource(imgsList[cardShuffle!![i]])
//        }
        Thread.sleep(1000)
        for (i in 0 until btnPanel.size) {
            btnPanel[i]!!.scaleType = ImageView.ScaleType.CENTER_CROP
            btnPanel[i]!!.setImageResource(backCard)
        }
        for (i in cardShuffle!!.indices) {
            btnPanel[i]!!.isEnabled = true
            btnPanel[i]!!.setOnClickListener {
                if (!pause) {
                    evaluate(i, btnPanel[i]!!)
                }
            }
        }
        _checks = 0
        score = 0
        binding.tvScore.text = "$score"
    }
    private fun evaluate(i: Int, btnTap: ImageButton) {
        if (_baseTurn == null) {
            _baseTurn = btnTap
            _baseTurn!!.scaleType = ImageView.ScaleType.CENTER_CROP
            _baseTurn!!.setImageResource(imgsList[cardShuffle!!.get(i)])
            _baseTurn!!.isEnabled = false
            _baseTurnImg = cardShuffle!!.get(i)
        } else {
            pause = true
            _matchTurn = btnTap
            _matchTurn!!.scaleType = ImageView.ScaleType.CENTER_CROP
            _matchTurn!!.setImageResource(imgsList[cardShuffle!!.get(i)])
            _matchTurn!!.isEnabled = false
            _matchTurnImg = cardShuffle!!.get(i)
            if (_baseTurnImg === _matchTurnImg) {
                _baseTurn = null
                pause = false
                _checks++
                score++
                binding.tvScore.text = "$score"
                if (_checks === 3) {
                    val toast =
                        Toast.makeText(applicationContext, "Has ganado!!", Toast.LENGTH_LONG)
                    toast.show()
                }
            } else {
                Thread.sleep(500)
                pause = false
                _baseTurn!!.scaleType = ImageView.ScaleType.CENTER_CROP
                _baseTurn!!.setImageResource(R.drawable.fondo)
                _baseTurn!!.isEnabled = true
                _baseTurn = null
                btnTap.scaleType = ImageView.ScaleType.CENTER_CROP
                btnTap.setImageResource(R.drawable.fondo)
                btnTap.isEnabled = true
                if (score > 0) {
                    score--
                    binding.tvScore.text = "$score"
                }
            }
        }
    }
    private fun botonesMenu(){
        binding.btnReset.setOnClickListener(View.OnClickListener { loadConfig() })
        binding.btnOut.setOnClickListener(View.OnClickListener { finish() })
    }
    private fun shuffleArrayCards(longitud: Int): ArrayList<Int> {
        val res: ArrayList<Int> = ArrayList<Int>()
        for (i in 0 until longitud)
            res.add(i % longitud / 2)
        res.shuffle()
        loadCards()
        return res
    }
    private fun loadCards() {
        btnPanel[0] = binding.ib11
        btnPanel[1] = binding.ib12
        btnPanel[2] = binding.ib13
        btnPanel[3] = binding.ib21
        btnPanel[4] = binding.ib22
        btnPanel[5] = binding.ib23
        binding.tvScore.text = "$score"
    }
}