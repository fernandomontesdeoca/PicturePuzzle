package com.example.fernandomontes.tilegame;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class TileGameActivity extends AppCompatActivity {

    private ImageView[] viewTiles;
    ArrayList<Integer> randIndex;
    private int[] selectedIndex = new int[2];
    private long tenthOfSec = 0;
    public static boolean isRunning = false;
    private boolean wasRunning = false;
    private long hours = 0;
    private long minutes = 0;
    private long seconds = 0;
    public static String time = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tile_game);

        //isRunning = true;
        startRun();

        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("image_path");

        Bitmap picture = BitmapFactory.decodeFile(imagePath);  //.decodeResource(getResources(), R.drawable.baby);
        // loads in slices
        Bitmap[][] tiles = splitBitmap(picture, 3, 3);


        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        int width = display.widthPixels, height = display.heightPixels - getStatusBarHeight() - getSoftButtonsBarHeight();

        width = width / 3;
        height = height / 3;

        // resizes slices
        for (int r = 0; r < 3; ++r)
            for (int c = 0; c < 3; ++c) {
                tiles[r][c] = Bitmap.createScaledBitmap(tiles[r][c], width, height, false);
            }


        randIndex = new ArrayList<>();
        for (int i = 0; i < 9; ++i)
            randIndex.add(i);
        Collections.shuffle(randIndex);


        // set imageviews with tile images
        ImageView[] viewTiles = new ImageView[9];

        viewTiles[0] = (ImageView) findViewById(R.id.image_view1);
        viewTiles[1] = (ImageView) findViewById(R.id.image_view2);
        viewTiles[2] = (ImageView) findViewById(R.id.image_view3);
        viewTiles[3] = (ImageView) findViewById(R.id.image_view4);
        viewTiles[4] = (ImageView) findViewById(R.id.image_view5);
        viewTiles[5] = (ImageView) findViewById(R.id.image_view6);
        viewTiles[6] = (ImageView) findViewById(R.id.image_view7);
        viewTiles[7] = (ImageView) findViewById(R.id.image_view8);
        viewTiles[8] = (ImageView) findViewById(R.id.image_view9);

        int tileIndex = 0;

        Bitmap[] randPics = new Bitmap[9];
        for (int c = 0; c < 3; ++c)
            for (int r = 0; r < 3; ++r)
                randPics[randIndex.get(tileIndex++)] = tiles[r][c];
        tileIndex = 0;
        for (int i = 0; i < 9; ++i) {
            int randI = randIndex.get(tileIndex);
            System.out.println("R: " + randI + " I: " + randIndex.indexOf(randI));
            viewTiles[tileIndex].setImageBitmap(randPics[tileIndex]);
            viewTiles[tileIndex].setOnClickListener(new TileClicked(tileIndex, randI, viewTiles, this, time ));
            tileIndex++;
        }

    }
    private Bitmap[][] splitBitmap(Bitmap bitmap, int xCount, int yCount) {

        Bitmap[][] bitmaps = new Bitmap[xCount][yCount];
        int width, height;

        width = bitmap.getWidth() / xCount;
        height = bitmap.getHeight() / yCount;

        // Loop the array and create bitmaps for each coordinate
        for (int x = 0; x < xCount; ++x) {
            for (int y = 0; y < yCount; ++y) {
                // Create the sliced bitmap
                bitmaps[x][y] = Bitmap.createBitmap(bitmap, x * width, y * height, width, height);
            }
        }
        return bitmaps;
    }

    //https://stackoverflow.com/questions/29398929/how-get-height-of-the-status-bar-and-soft-key-buttons-bar
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    // https://stackoverflow.com/questions/24657666/android-get-real-screen-size
    private int getSoftButtonsBarHeight() {
        int navBarHeight = 0;
        Resources resources = getApplicationContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return navBarHeight;
    }

    private void startRun() {
        isRunning = true;
        final Handler handle = new Handler();
        handle.post(new Thread() {

            public void run() {
                long totalSeconds = tenthOfSec / 10;

                seconds = ((totalSeconds) % 60);
                minutes = (totalSeconds / 60) % 60;
                hours = (totalSeconds / (600));

                setTitle(String.format("%d:%02d:%02d:%01d", hours, minutes, seconds, tenthOfSec % 10));
                time = String.format("%d:%02d:%02d:%01d", hours, minutes, seconds, (tenthOfSec % 10));

                System.out.printf("isrunning: ", isRunning);
                if (isRunning)
                    tenthOfSec++;

                handle.postDelayed(this, 100);
            }
        });

    }
}


  /*  //inherited call when the app is brought back to focus
    public void onResume(){
        super.onResume();

        //if isRunning is true then continue the timer else the thread is not incrementing time
        isRunning = wasRunning;

    }

    public void onPause(){
        super.onPause();

        //save running state when app is stopped
        wasRunning = isRunning;

        //stop thread from incrementing time
        isRunning = false;
    }

    public void onSaveInstanceState(Bundle save){
        super.onSaveInstanceState(save);
        save.putLong("10th_of_sec", tenthOfSec);
        save.putBoolean("is_running", isRunning);
        save.putBoolean("was_running", wasRunning);
    }


}
  */