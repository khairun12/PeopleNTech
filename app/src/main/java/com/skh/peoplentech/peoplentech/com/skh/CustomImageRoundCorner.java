package com.skh.peoplentech.peoplentech.com.skh;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.RectF;

import com.squareup.picasso.Transformation;

/**
 * Created by samir on 4/17/2017.
 */

public class CustomImageRoundCorner implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
       // canvas.drawCircle(r, r, r, paint);
       // canvas.drawRoundRect(new RectF(0, 100, 100, 200), 6, 6, paint);

        //canvas.drawRect(new RectF(0, 110, 100, 290), paint);
        canvas.drawRoundRect(new RectF(0, r*1.5f, r*2, 10), 6, 6, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}