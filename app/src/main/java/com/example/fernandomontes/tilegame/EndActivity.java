package com.example.fernandomontes.tilegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EndActivity extends AppCompatActivity {

    private TextView timeView;
    private Button b;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        timeView = (TextView) findViewById(R.id.timeDisplay);
        b = (Button) findViewById(R.id.restartButton);
        Intent last = getIntent();
        String stringPath = last.getStringExtra("string_path");

        timeView.setText(stringPath);


    }

    public void restart(View v){
        Intent t = new Intent(this, StartGameActivity.class);
        t.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(t);
        finish();
    }
}
