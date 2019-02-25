package com.projects.andreafranco.workforcetracking.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.io.ByteArrayOutputStream;

import static android.graphics.Color.GRAY;

public class ImageUtils {

    public static Bitmap getCustomBitmap(int width, int height, Bitmap image, String name, String surname) {
        //We are assuming that the whole image has the same color, so we can extract a fixed position
        int pixel = image.getPixel(0, 0);
        int r = Color.red(pixel);
        int g = Color.green(pixel);
        int b = Color.blue(pixel);
        int circleBackgroundColor = Color.rgb(r, g, b);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(circleBackgroundColor);
        canvas.drawRect(0F, 0F, (float) width, (float) height, paint);

        //Set the text
        Paint paintText = new Paint();
        paintText.setColor(getContrastColor(circleBackgroundColor));
        paintText.setTextSize(100);
        paintText.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        paintText.setTextAlign(Paint.Align.CENTER);
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paintText.descent() + paintText.ascent()) / 2)) ;
        canvas.drawText(name.subSequence(0, 1).toString().toUpperCase() + surname.subSequence(0, 1).toString().toUpperCase(),
                xPos,
                yPos,
                paintText);
        return bitmap;
    }

    private static int getContrastColor(int color) {
        // get existing colors
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int blue = Color.blue(color);
        int green = Color.green(color);

        // find compliments
        red = (~red) & 0xff;
        blue = (~blue) & 0xff;
        green = (~green) & 0xff;

        return Color.argb(alpha, red, green, blue);
    }

    public static byte[] convertBitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream output= new ByteArrayOutputStream(); bitmap.compress(Bitmap.CompressFormat.PNG, 0, output);
        return output.toByteArray();
    }

    public static int getShiftStatusColor(int shiftStatusId) {
        int color = GRAY;
        switch (shiftStatusId) {
            case 0://Negative
                color = Color.RED;
                break;
            case 1://Positive
                color = Color.GREEN;
                break;
            case 2://Neutral
                color = Color.DKGRAY;
                break;
            default:
                color = Color.BLACK;
                break;
        }
        return color;
    }
}
