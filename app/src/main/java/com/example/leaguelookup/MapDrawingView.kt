package com.example.leaguelookup

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MapDrawingView(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {
    private var lastX = 0f
    private var lastY = 0f
    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 10f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }
    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null

    private fun createBitmap(width: Int, height: Int): Bitmap {
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = createBitmap(w, h)
        canvas = Canvas(bitmap!!)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap!!, 0f, 0f, null)
    }
    private var lastAction: Int = MotionEvent.ACTION_CANCEL
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                val y = event.y
                lastX = x
                lastY = y
                lastAction = MotionEvent.ACTION_DOWN
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val y = event.y
                drawLineTouch(lastX, lastY, x, y, paint)
                lastX = x
                lastY = y
                lastAction = MotionEvent.ACTION_MOVE
                invalidate() // Request to redraw the view
                return true
            }
            MotionEvent.ACTION_UP -> {
                val x = event.x
                val y = event.y
                if(lastAction == MotionEvent.ACTION_DOWN){
                    drawCircleTouch(x, y, 30f, paint)
                }
                lastAction = MotionEvent.ACTION_UP
                invalidate() // Request to redraw the view
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun drawCircleTouch(xFloat: Float, yFloat: Float, radius: Float, paint: Paint){
        canvas?.drawCircle(xFloat, yFloat, radius, paint)
    }
    private fun drawLineTouch(x: Float, y: Float, dx: Float, dy: Float, paint: Paint){
        canvas?.drawLine(x, y, dx, dy, paint)
    }
    private fun clear() {
        val transparentPaint = Paint().apply {
            color = Color.TRANSPARENT
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        }
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), transparentPaint)
        invalidate()
    }
    fun clearDraw(){
        clear()
        createBitmap(width, height)
        invalidate()
    }
}