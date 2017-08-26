package com.example.fernandomontes.tilegame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartGameActivity extends AppCompatActivity {

        private Bitmap myImg;
        private String mCurrentPhotoPath;
        private ImageView imageView;
        private Button startGame;

        private final int CALL_CAMERA = 1;

        @Override
        protected void onCreate(Bundle save) {
            super.onCreate(save);
            setContentView(R.layout.activity_start_game);

            imageView = (ImageView) findViewById(R.id.image_view_result);
            startGame = (Button) findViewById(R.id.startButton);

            // load in a message and test here


            if(save != null) {
                //   int number = save.getInt("saved_image");
                //  Log.d("TILEGAME", number + "");
                mCurrentPhotoPath = save.getString("saved_image");
                myImg = BitmapFactory.decodeFile(mCurrentPhotoPath);//MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageView.setImageBitmap(myImg);
                startGame.setEnabled(true);
            }


        }

        //create tile activity
    public void startGame(View v){
        Intent tile = new Intent(this,TileGameActivity.class);
        tile.putExtra("image_path", mCurrentPhotoPath);
        startActivity(tile);

    }

        public void getPicture(View v){

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.fernandomontes.tilegame.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CALL_CAMERA);
                }
            }
        }


        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == RESULT_OK && requestCode == CALL_CAMERA) {
                // get image
                //Uri imageUri = data.getData();
                //Log.d("CAMERA_APP", imageUri.toString());
                try {

                    myImg = BitmapFactory.decodeFile(mCurrentPhotoPath);//MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    imageView.setImageBitmap(myImg);

                    startGame.setEnabled(true);

                } catch(Exception e){
                    e.printStackTrace();
                }

            }
        }

        public void onSaveInstanceState(Bundle save){
            super.onSaveInstanceState(save);
            save.putString("saved_image", mCurrentPhotoPath);
            // save.putInt("saved_image", 4);
        }

        private File createImageFile() throws IOException {
            // Create an image file name
            //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + "TILEGAME";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",     	/* suffix */
                    storageDir  	/* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = image.getAbsolutePath();
            return image;
        }

    }




