package com.training.memorama

import android.R
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.training.memorama.databinding.ActivityGameBinding
import java.util.*


class Game : AppCompatActivity() {

    private lateinit var binding : ActivityGameBinding
    var fondo:Int = 0
    var arrayBarajado: ArrayList<Int>? = null
    lateinit var imagenes:Array<Int>
    var acierto:Int = 0
    var puntuacion:Int = 0
    val botonera = arrayOfNulls<ImageButton>(6)
    var pause:Boolean = false
    var primero:ImageButton? = null
    var primeroImg:Int = 0
    var segundoImg:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadImgs()
        botonesMenu()
        iniciar()
    }
    fun loadImgs(){
        imagenes = arrayOf<Int>(
            com.training.memorama.R.drawable.f0,
            com.training.memorama.R.drawable.f1,
            com.training.memorama.R.drawable.f2,
        )
        fondo = com.training.memorama.R.drawable.fondo
    }
    fun barajar(longitud: Int): ArrayList<Int> {
        val resultadoA: ArrayList<Int> = ArrayList<Int>()
        for (i in 0 until longitud) resultadoA.add(i % longitud / 2)
        Collections.shuffle(resultadoA)
        return resultadoA
    }
    fun cargarBotones() {
        botonera[0] = binding.ib11
        botonera[1] = binding.ib12
        botonera[2] = binding.ib13
        botonera[3] = binding.ib21
        botonera[4] = binding.ib22
        botonera[5] = binding.ib23
        binding.tvScore.text = "Puntuación: $puntuacion"
    }
    fun comprobar(i: Int, imgb: ImageButton) {
        if (primero == null) {
            primero = imgb
            primero!!.setScaleType(
                ImageView.ScaleType.CENTER_CROP
            )
            primero!!.setImageResource(imagenes[arrayBarajado!!.get(i)])
            primero!!.setEnabled(false)
            primeroImg = arrayBarajado!!.get(i)
        } else {
            pause = true
            imgb.scaleType = ImageView.ScaleType.CENTER_CROP
            imgb.setImageResource(imagenes[arrayBarajado!!.get(i)])
            imgb.isEnabled = false
            segundoImg = arrayBarajado!!.get(i)
            if (primeroImg === segundoImg) {
                primero = null
                pause = false
                acierto++
                puntuacion++
                binding.tvScore.text = "Puntuación: $puntuacion"
                if (acierto === 3) {
                    val toast =
                        Toast.makeText(applicationContext, "Has ganado!!", Toast.LENGTH_LONG)
                    toast.show()
                }
            } else {
                Thread.sleep(2000)
                primero!!.setScaleType(ImageView.ScaleType.CENTER_CROP)
                primero!!.setImageResource(com.training.memorama.R.drawable.fondo)
                imgb.scaleType = ImageView.ScaleType.CENTER_CROP
                imgb.setImageResource(com.training.memorama.R.drawable.fondo)
                primero!!.setEnabled(true)
                imgb.isEnabled = true
                primero = null
                pause = false
                if (puntuacion > 0) {
                    puntuacion--
                    binding.tvScore.text = "Puntuación: $puntuacion"
                }

            }
        }
    }
    fun iniciar() {
        arrayBarajado = barajar(imagenes.size * 2)
        cargarBotones()
        for (i in 0 until botonera.size) {
            botonera[i]!!.scaleType = ImageView.ScaleType.CENTER_CROP
            botonera[i]!!.setImageResource(imagenes[arrayBarajado!![i]])
        }
        Thread.sleep(1000)
        for (i in 0 until botonera.size) {
            botonera[i]!!.scaleType = ImageView.ScaleType.CENTER_CROP
            botonera[i]!!.setImageResource(fondo)
        }
        for (i in arrayBarajado!!.indices) {
            botonera[i]!!.isEnabled = true
            botonera[i]!!.setOnClickListener { if (!pause) comprobar(i, botonera[i]!!) }
        }
        acierto = 0
        puntuacion = 0
        binding.tvScore.text = "Puntuación: $puntuacion"
    }
    fun botonesMenu(){

        binding.btnReset.setOnClickListener(View.OnClickListener { iniciar() })
        binding.btnOut.setOnClickListener(View.OnClickListener { finish() })
    }
}