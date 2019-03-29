package com.mark.paintview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.mark.paintview.view.PaintView;

public class MainActivity extends AppCompatActivity {

    PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        paintView = findViewById(R.id.paint_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 0, Menu.NONE, "橡皮擦");
        menu.add(1, 1, Menu.NONE, "正常");
        menu.add(1, 2, Menu.NONE, "直线");
        menu.add(1, 3, Menu.NONE, "椭圆");
        menu.add(1, 4, Menu.NONE, "正圆");
        menu.add(1, 5, Menu.NONE, "矩形");
        menu.add(1, 6, Menu.NONE, "圆角矩形");
        menu.add(1, 7, Menu.NONE, "等腰三角形");
        menu.add(1, 8, Menu.NONE, "直角三角形");
        menu.add(1, 9, Menu.NONE, "清屏");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case 0:
                paintView.setDrawMode(PaintView.DRAW_MODE_ERASE);
                break;

            case 1:
                paintView.setDrawMode(PaintView.DRAW_MODE_NORMAL);
                break;

            case 2:
                paintView.setDrawMode(PaintView.DRAW_MODE_LINE);
                break;

            case 3:
                paintView.setDrawMode(PaintView.DRAW_MODE_OVAL);
                break;

            case 4:
                paintView.setDrawMode(PaintView.DRAW_MODE_CIRCLE);
                break;

            case 5:
                paintView.setDrawMode(PaintView.DRAW_MODE_RECT);
                break;
            case 6:
                paintView.setDrawMode(PaintView.DRAW_MODE_ROUND_RECT);
                break;
            case 7:
                paintView.setDrawMode(PaintView.DRAW_MODE_TRIANGLE_ONE);
                break;
            case 8:
                paintView.setDrawMode(PaintView.DRAW_MODE_TRIANGLE_TWO);
                break;
            case 9:
                paintView.clear();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onFabClick(View view) {
        switch (view.getId()) {
            case R.id.fab_clear:
                paintView.clear();
                break;

            case R.id.fab_undo:
                paintView.undo();
                break;

            case R.id.fab_redo:
                paintView.redo();
                break;
        }
    }
}
