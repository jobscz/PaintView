package com.mark.paintview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * 画笔&&路径保存类
 */
public class PaintData {

    private Path path;
    private Paint paint;

    public PaintData(Path path, Paint paint) {
        this.path = path;
        this.paint = paint;
    }

    public void draw(Canvas canvas){
        canvas.drawPath(path,paint);
    }
}
