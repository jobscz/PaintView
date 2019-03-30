package com.mark.paintview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.mark.paintview.PaintData;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PaintView extends View {


    static final String TAG = "PaintView";

    public static final int DRAW_MODE_ERASE = 0;//橡皮擦

    public static final int DRAW_MODE_NORMAL = 1;//普通绘制
    public static final int DRAW_MODE_LINE = 2;//直线
    public static final int DRAW_MODE_OVAL = 3;//椭圆
    public static final int DRAW_MODE_CIRCLE = 4;//正圆
    public static final int DRAW_MODE_RECT = 5;//矩形
    public static final int DRAW_MODE_ROUND_RECT = 6;//圆角矩形
    public static final int DRAW_MODE_TRIANGLE_ONE = 7;//等腰三角形
    public static final int DRAW_MODE_TRIANGLE_TWO = 8;//直角三角形
    public static final int DRAW_MODE_ARROW = 9;//箭头


    Paint mStrokePaint, mErasePaint;
    Path mDrawPath;
    Canvas mBufferCanvas;
    Bitmap mBufferBitmap;

    Random random;

    int drawMode = DRAW_MODE_NORMAL;

    List<PaintData> mUndoList, mRedoList;

    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {

        setLayerType(LAYER_TYPE_SOFTWARE, null);


        random = new Random();
        mDrawPath = new Path();

        mUndoList = new ArrayList<>();
        mRedoList = new ArrayList<>();

        mStrokePaint = new Paint();
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setDither(true);
        mStrokePaint.setStrokeWidth(16);
        mStrokePaint.setStrokeJoin(Paint.Join.ROUND);
        mStrokePaint.setStrokeCap(Paint.Cap.ROUND);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(Color.RED);

        mErasePaint = new Paint();
        mErasePaint.setAntiAlias(true);
        mErasePaint.setDither(true);
        mErasePaint.setStrokeWidth(16);
        mErasePaint.setStrokeJoin(Paint.Join.ROUND);
        mErasePaint.setStrokeCap(Paint.Cap.ROUND);
        mErasePaint.setStyle(Paint.Style.STROKE);
        mErasePaint.setAlpha(0);
        mErasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));


        post(new Runnable() {
            @Override
            public void run() {
                mBufferBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                mBufferCanvas = new Canvas(mBufferBitmap);
            }
        });
    }


    float startX, startY, endX, endY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                mStrokePaint.setColor(randomColor());
                mDrawPath.reset();
                mDrawPath.moveTo(startX, startY);
                break;
            case MotionEvent.ACTION_MOVE:
                endX = event.getX();
                endY = event.getY();
                if (drawMode == DRAW_MODE_NORMAL || drawMode == DRAW_MODE_ERASE) {
                    mDrawPath.lineTo(endX, endY);
                    drawPath();
                    startX = endX;
                    startY = endY;
                } else {
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startX = event.getX();
                startY = event.getY();
                handleUp();
                mDrawPath.reset();
                break;

        }

        return true;
    }


    void drawPath() {
        if (mBufferCanvas == null || mBufferBitmap == null) {
            return;
        }
        if (drawMode == DRAW_MODE_ERASE) {
            mBufferCanvas.drawPath(mDrawPath, mErasePaint);
        } else {
            mBufferCanvas.drawPath(mDrawPath, mStrokePaint);
        }
        invalidate();
    }

    /**
     * 处理手指抬起
     * 保存路径并绘制最终形状
     */
    void handleUp() {
        if (drawMode == DRAW_MODE_ERASE) {
            mUndoList.add(new PaintData(new Path(mDrawPath), new Paint(mErasePaint)));
        } else if (drawMode == DRAW_MODE_NORMAL) {
            mUndoList.add(new PaintData(new Path(mDrawPath), new Paint(mStrokePaint)));
        } else if (drawMode == DRAW_MODE_LINE) {
            mUndoList.add(new PaintData(new Path(shapePath), new Paint(mStrokePaint)));
            drawShapePath();
        } else if (drawMode == DRAW_MODE_OVAL) {
            mUndoList.add(new PaintData(new Path(shapePath), new Paint(mStrokePaint)));
            drawShapePath();
        } else if (drawMode == DRAW_MODE_OVAL) {
            mUndoList.add(new PaintData(new Path(shapePath), new Paint(mStrokePaint)));
            drawShapePath();
        } else if (drawMode == DRAW_MODE_CIRCLE) {
            mUndoList.add(new PaintData(new Path(shapePath), new Paint(mStrokePaint)));
            drawShapePath();
        } else if (drawMode == DRAW_MODE_RECT) {
            mUndoList.add(new PaintData(new Path(shapePath), new Paint(mStrokePaint)));
            drawShapePath();
        } else if (drawMode == DRAW_MODE_ROUND_RECT) {
            mUndoList.add(new PaintData(new Path(shapePath), new Paint(mStrokePaint)));
            drawShapePath();
        } else if (drawMode == DRAW_MODE_TRIANGLE_ONE) {
            mUndoList.add(new PaintData(new Path(shapePath), new Paint(mStrokePaint)));
            drawShapePath();
        } else if (drawMode == DRAW_MODE_TRIANGLE_TWO) {
            mUndoList.add(new PaintData(new Path(shapePath), new Paint(mStrokePaint)));
            drawShapePath();
        } else if (drawMode == DRAW_MODE_ARROW) {
            mUndoList.add(new PaintData(new Path(shapePath), new Paint(mStrokePaint)));
            drawShapePath();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBufferBitmap != null) {
            canvas.drawBitmap(mBufferBitmap, 0, 0, null);
            drawTempShape(canvas);
        }
    }

    /**
     * 手指抬起时绘制最终形状
     */
    void drawShapePath() {
        mBufferCanvas.drawPath(shapePath, mStrokePaint);
        shapePath.reset();
        shapeRectF.setEmpty();
        startX = startY = endX = endY = 0;
        invalidate();

    }


    /**
     * 手指滑动时临时绘制形状
     *
     * @param canvas
     */
    Path shapePath = new Path();
    RectF shapeRectF = new RectF();

    void drawTempShape(Canvas canvas) {
        if (drawMode == DRAW_MODE_LINE) {
            drawLine(canvas);
        } else if (drawMode == DRAW_MODE_OVAL) {
            drawOval(canvas);
        } else if (drawMode == DRAW_MODE_CIRCLE) {
            drawCircle(canvas);
        } else if (drawMode == DRAW_MODE_RECT) {
            drawRect(canvas);
        } else if (drawMode == DRAW_MODE_ROUND_RECT) {
            drawRoundRect(canvas);
        } else if (drawMode == DRAW_MODE_TRIANGLE_ONE) {
            drawTriangleOne(canvas);
        } else if (drawMode == DRAW_MODE_TRIANGLE_TWO) {
            drawTriangleTwo(canvas);
        } else if (drawMode == DRAW_MODE_ARROW) {
            drawArrow(canvas);
        }
    }


    /**
     * 绘制直线
     *
     * @param canvas
     */
    void drawLine(Canvas canvas) {
        shapePath.reset();
        shapePath.moveTo(startX, startY);
        shapePath.lineTo(endX, endY);
        canvas.drawPath(shapePath, mStrokePaint);
    }

    /**
     * 绘制椭圆
     *
     * @param canvas
     */
    void drawOval(Canvas canvas) {
        shapeRectF.setEmpty();
        shapeRectF.set(startX, startY, endX, endY);
        shapePath.reset();
        shapePath.addOval(shapeRectF, Path.Direction.CCW);
        canvas.drawPath(shapePath, mStrokePaint);
    }

    /**
     * 绘制正圆
     * @param canvas
     */
    void drawCircle(Canvas canvas) {
        float cx = (startX + endX) / 2;
        float cy = (startY + endY) / 2;
        float radius = Math.abs(startX - endX) / 2;
        shapePath.reset();
        shapePath.addCircle(cx, cy, radius, Path.Direction.CCW);
        canvas.drawPath(shapePath, mStrokePaint);
    }

    /**
     * 绘制圆角矩形
     * @param canvas
     */
    void drawRoundRect(Canvas canvas) {
        shapeRectF.setEmpty();
        shapeRectF.set(startX, startY, endX, endY);
        float rx = 60;
        float ry = 60;
        shapePath.reset();
        shapePath.addRoundRect(shapeRectF, rx, ry, Path.Direction.CCW);
        canvas.drawPath(shapePath, mStrokePaint);
    }

    /**
     * 绘制矩形
     * @param canvas
     */
    void drawRect(Canvas canvas) {
        shapeRectF.setEmpty();
        shapeRectF.set(startX, startY, endX, endY);
        shapePath.reset();
        shapePath.addRoundRect(shapeRectF, 0, 0, Path.Direction.CCW);
        canvas.drawPath(shapePath, mStrokePaint);
    }

    /**
     * 等腰三角形
     *
     * @param canvas
     */
    float[] points = new float[12];

    void drawTriangleOne(Canvas canvas) {
        points[0] = (startX + endX) / 2;
        points[1] = startY;
        points[2] = endX;
        points[3] = endY;

        points[4] = endX;
        points[5] = endY;
        points[6] = startX;
        points[7] = endY;

        points[8] = startX;
        points[9] = endY;
        points[10] = (startX + endX) / 2;
        points[11] = startY;
        shapePath.reset();
        shapePath.moveTo(points[0], points[1]);
        shapePath.lineTo(points[2], points[3]);
        shapePath.lineTo(points[6], points[7]);
        shapePath.lineTo(points[0], points[1]);
        canvas.drawPath(shapePath, mStrokePaint);
    }

    /**
     * 左直角三角形
     *
     * @param canvas
     */
    void drawTriangleTwo(Canvas canvas) {
        points[0] = startX;
        points[1] = startY;
        points[2] = endX;
        points[3] = endY;

        points[4] = endX;
        points[5] = endY;
        points[6] = startX;
        points[7] = endY;

        points[8] = startX;
        points[9] = endY;
        points[10] = startX;
        points[11] = startY;
        shapePath.reset();
        shapePath.moveTo(points[0], points[1]);
        shapePath.lineTo(points[2], points[3]);
        shapePath.lineTo(points[6], points[7]);
        shapePath.lineTo(points[0], points[1]);
        canvas.drawPath(shapePath, mStrokePaint);
    }

    /**
     * 绘制箭头
     * @param canvas
     */
    void drawArrow(Canvas canvas) {
        // TODO: 19-3-30 箭头
    }


    public void undo() {
        reDraw(mUndoList);
    }

    public void redo() {
        reDraw(mRedoList);
    }

    public void reDraw(List<PaintData> dataList) {
        int size = dataList.size();
        if (size <= 0) {
            return;
        }
        PaintData data = dataList.remove(size - 1);
        if (dataList == mUndoList) {
            mRedoList.add(data);
        } else {
            mUndoList.add(data);
        }
        mBufferCanvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
        for (PaintData paintData : mUndoList) {
            paintData.draw(mBufferCanvas);
        }
        invalidate();
    }

    /**
     * 设置绘制类型
     *
     * @param mode
     */
    public void setDrawMode(int mode) {
        this.drawMode = mode;
    }


    public void setFillStyle(boolean isFill){
        mStrokePaint.setStyle(isFill ? Paint.Style.FILL:Paint.Style.STROKE);
    }
    public boolean isFillStyle(){
        return mStrokePaint.getStyle() != Paint.Style.STROKE;
    }

    public void clear() {
        clearList();
        mBufferCanvas.drawColor(0, PorterDuff.Mode.SRC_OUT);
        invalidate();
    }

    void clearList() {
        mUndoList.clear();
        mRedoList.clear();
    }


    /**
     * 返回随机颜色
     *
     * @return
     */
    int randomColor() {
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
