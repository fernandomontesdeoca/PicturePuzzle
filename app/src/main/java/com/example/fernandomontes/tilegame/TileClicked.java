package com.example.fernandomontes.tilegame;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.drawable.BitmapDrawable;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.Toast;

        import java.util.ArrayList;

/**
 * Created by Fernando on 8/1/2017.
 */

public class TileClicked implements View.OnClickListener {
   // public static final String EXTRA_MESSAGE = "com.example.fernandomontes..tilegame.MESSAGE";

    public String finishedTime;
    static int[] selectedIndex;
    static {
        selectedIndex= new int[2];
        selectedIndex[0] = -1;
        selectedIndex[1] = -1;
    }
    ImageView[] viewTiles;
    Context g;
    AppCompatActivity a;


    int tileIndex;

    static int [] valuePosList = new int[9];

    TileClicked(int value, int tilePos, ImageView[] views, AppCompatActivity m, String time) {
        tileIndex = value;
        viewTiles = views;
        g = m.getApplicationContext();
        valuePosList[tilePos] = value;
        finishedTime = time;
        a = m;

        System.out.println("VALUE: " + valuePosList[tilePos] );
    }

    public void onClick(View v) {

        Log.d("TILE_GAME", "index: " + tileIndex);

        // first selection
        if (selectedIndex[0] == -1) {
            selectedIndex[0] = tileIndex;
            return;
        }

        // second selection, swap
        if (selectedIndex[0] != -1 && selectedIndex[1] == -1) {
            // Log.d("TILE_GAME", "index: " + tileIndex);
            selectedIndex[1] = tileIndex;
            swapTile(selectedIndex[1], selectedIndex[0]);
            selectedIndex[1] = -1;
            selectedIndex[0] = -1;
            // show their time
        }
        Log.d("TILE_GAME", "\t\tss");
        for (int i = 0; i < 9 - 1; ++i) {
            if(valuePosList[i] > valuePosList[i + 1])
                return;
        }

        // show won activity
        TileGameActivity.isRunning = false;

        Toast.makeText(g, "You Won!", Toast.LENGTH_SHORT).show();

         endGame();
    }
    private void endGame(){
        TileGameActivity.isRunning = false;
        Intent last = new Intent(a ,EndActivity.class);
        last.putExtra("string_path", a.getTitle());
        a.startActivity(last);
    }
    private void swapTile(int a, int b) {

        Bitmap temp = ((BitmapDrawable) (viewTiles[a].getDrawable())).getBitmap();
        viewTiles[a].setImageBitmap(((BitmapDrawable) (viewTiles[b].getDrawable())).getBitmap());
        viewTiles[b].setImageBitmap(temp);

        int t = valuePosList[a];
        valuePosList[a] = valuePosList[b];
        valuePosList[b] = t;

        for(int i = 0; i < 9; ++i)
            System.out.println("R: " + valuePosList[i] + " I: " + i);

    }
}