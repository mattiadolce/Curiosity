package com.example.curiosity

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CircolarProgressBar(context: Context, attrs: AttributeSet): View(context, attrs){ //definiti questi parametri android studio capisce che stiamo definendo una view personalizzata
    val rect = RectF(0f,0f,0f,0f)
    //dimensione del rettangolo dipende da come la viewgroup ha strutturato la view, dipende da onMeasure e lo sappiamo solo dentro onDraw

    val paintBg = Paint()
    val paintFg = Paint()

    val paintTx = Paint()
    val paintFg2 = Paint()
    var percent = 0.75f
    set(value) {
        field = min(value.toFloat(),1f) //se matte più di 1 corregge errore
        requestLayout() //ricalcola misure layout
        invalidate() //nel punto in cui cambiamo qualcosa e serve ridisegnare la view
    }

    init { //tutto ciò che non va nel costruttore va nell'init
        //per prendere i colori messi nel .xml dell'activity

        paintBg.color = Color.GRAY
        paintBg.style = Paint.Style.STROKE
        paintBg.isAntiAlias=true //smussa squadrature di parti circolari, evita effetto a quadretti

        paintFg.color = Color.BLUE
        paintFg.style = Paint.Style.STROKE
        paintFg.isAntiAlias=true

        paintFg2.color = Color.parseColor("#23AAFF")
        paintFg2.style = Paint.Style.FILL
        paintFg2.isAntiAlias=true

        paintTx.color = Color.BLACK
        paintTx.textAlign = Paint.Align.CENTER

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircolarProgressBar, 0,0)
        paintBg.color =  typedArray.getColor(R.styleable.CircolarProgressBar_bgColor, Color.GRAY)
        paintFg.color =  typedArray.getColor(R.styleable.CircolarProgressBar_fgColor, Color.BLUE)
        paintFg2.color =  typedArray.getColor(R.styleable.CircolarProgressBar_fg2Color, Color.parseColor("#23AAFF"))


    }
    private fun initPaint(size: Float) {
        paintBg.strokeWidth = size*0.2f
        paintFg.strokeWidth = size*0.2f
        paintTx.textSize = size*0.2f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val pad = 0.25f
        var size  = min(width, height).toFloat()
        size -= size * pad
        val radius = size/2
        val cx = width/2f
        val cy = height/2f
        val left = cx-radius
        val top = cy-radius
        val right = left + size
        val bottom = top + size
        rect.set(left, top, right, bottom)

        initPaint(size)
        canvas?.drawArc(rect,0F,360F,false,paintBg)

        val angleDegree = percent*360f
        canvas?.drawArc(rect,0F,angleDegree,false,paintFg)

        val delta = (paintTx.descent() + paintTx.ascent())/2f //somma delle due distanze / 2 per trovare metà dell'altezza
        canvas?.drawText("${(percent*100).toInt()}%", cx, cy-delta, paintTx)

        val angleRad = angleDegree*Math.PI/180
        val circleX = radius * cos(angleRad).toFloat()
        val circleY = radius * sin(angleRad).toFloat()
        canvas?.drawCircle(circleX+cx, circleY+cy, radius*0.25f, paintFg2)
    }
}