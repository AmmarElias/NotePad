package com.example.android.notepad;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;

public class LinedEditText extends EditText {

    private Paint mPaint;
    private int lines = 0;
    private float fontHeight = 0;

    private float leading = 0;
    private Context context;

    public LinedEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
        this.context = context;
    }

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        this.context = context;
    }

    public LinedEditText(Context context) {
        super(context);
        initPaint();
        this.context = context;
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#CDDCD5"));
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        int lineHeight = getLineHeight();
        int viewHeight = getHeight() - 20;
        lines = viewHeight / lineHeight;
        setGravity(Gravity.TOP);
        float textSize = getTextSize();
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        fontHeight = fontMetrics.descent - fontMetrics.ascent;
        leading = fontMetrics.leading;// leading == 0
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = getLineCount();
        if (count <= lines) {
            for (int i = 1; i < lines; i++) {
                canvas.drawLine(0, fontHeight * i + leading * i + 10, getWidth(),
                        fontHeight * i + leading * i + 10, mPaint);
            }
        } else {
            for (int i = 1; i < count; i++) {
                canvas.drawLine(0, fontHeight * i + leading * i + 10, getWidth(),
                        fontHeight * i + leading * i + 10, mPaint);
            }
        }
    }
}
