package com.projects.andreafranco.workforcetracking.local;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;

import com.projects.andreafranco.workforcetracking.local.entity.FunctionEntity;
import com.projects.andreafranco.workforcetracking.local.entity.ShiftEntity;
import com.projects.andreafranco.workforcetracking.local.entity.TeamEntity;
import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;
import com.projects.andreafranco.workforcetracking.ui.component.CircleImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DataGenerator {

    //Users
    private static final String[] USER_NAME = new String[]{
            "Name1", "Name2", "Name3", "Name4"};

    private static final String[] USER_SURNAME = new String[]{
            "Name1", "Name2", "Name3", "Name4"};

    private static final String[] USER_EMAIL = new String[]{
            "a@aa.com", "a@a.com", "b@a.com", "c@a.com"};

    private static final String[] USER_USERNAME = new String[]{
            "1", "2", "3", "4"};

    private static final String[] USER_PSW = new String[]{
            "1", "2", "3", "4"};

    private static final double[] USER_LATITUDE = new double[]{
            46.098354, 46.099998, 46.100921, 46.102089};

    private static final double[] USER_LONGITUDE = new double[]{
            13.222394, 13.221718, 13.216976, 13.215656};

    private static final int[] USER_TEAM = new int[]{
            1,1,1,1};

    private static final int[] USER_SHIFT = new int[]{
            1,1,2,3};

    private static final int[] USER_FUNCTION = new int[]{
            1,2,2,2};

    //Teams
    private static final String[] TEAM_NAME = new String[]{
            "one", "two", "three"};

    //Shifts
    private static final String[] SHIFT_NAME = new String[]{
            "ON SHIFT", "OFF SHIFT", "BREAK"};
    //Shifts status
    private static final int[] SHIFT_STATUS = new int[]{
            1, 0, 2};

    //Shifts
    private static final String[] FUNCTION_NAME = new String[]{
            "Supervisor", "Operator"};

    public static List<UserEntity> generateUsers(Context context) {
        List<UserEntity> users = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < USER_NAME.length; i++) {
            Bitmap bitmap = createCustomBitmap(200, 200, new CircleImageView(context).getBitmap(), USER_NAME[i],USER_SURNAME[i]);
            byte[] imageData = bitmapAsByteArray(bitmap);
            UserEntity user = new UserEntity(
                    id,
                    USER_NAME[i],
                    USER_SURNAME[i],
                    USER_USERNAME[i],
                    USER_EMAIL[i],
                    USER_PSW[i],
                    imageData,
                    USER_LATITUDE[i],
                    USER_LONGITUDE[i],
                    USER_TEAM[i],
                    USER_SHIFT[i],
                    USER_FUNCTION[i],
                    new Date());
            users.add(user);
            id++;
        }
        return users;
    }

    public static List<TeamEntity> generateTeams() {
        List<TeamEntity> teams = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < TEAM_NAME.length; i++) {
            TeamEntity team = new TeamEntity(
                    id,
                    TEAM_NAME[i]);
            teams.add(team);
            id++;
        }
        return teams;
    }

    public static List<ShiftEntity> generateShifts() {
        List<ShiftEntity> shifts = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < SHIFT_NAME.length; i++) {
            ShiftEntity shift = new ShiftEntity(
                    id,
                    SHIFT_NAME[i],
                    SHIFT_STATUS[i]);
            shifts.add(shift);
            id++;
        }
        return shifts;
    }

    public static List<FunctionEntity> generateFunctions() {
        List<FunctionEntity> functions = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < FUNCTION_NAME.length; i++) {
            FunctionEntity function = new FunctionEntity(
                    id,
                    FUNCTION_NAME[i]);
            functions.add(function);
            id++;
        }
        return functions;
    }

    /**
     * TODO these classes will be removed as soos as we get data from WS
     * @param width
     * @param height
     * @param image
     * @param name
     * @param surname
     * @return
     */
    private static Bitmap createCustomBitmap(int width, int height, Bitmap image, String name, String surname) {
        //We are assuming that the whole image has the same color, so we can extract a fixed position
        int pixel = 5176198;
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

    private static byte[] bitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream output= new ByteArrayOutputStream(); bitmap.compress(Bitmap.CompressFormat.PNG, 0, output);
        return output.toByteArray();
    }
}
