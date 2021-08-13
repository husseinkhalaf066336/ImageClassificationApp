package com.eng_hussein_khalaf066336.tensorimageclassification.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.ConfigurationCompat;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.eng_hussein_khalaf066336.tensorimageclassification.ImagClassifier.ImageClassifier;
import com.eng_hussein_khalaf066336.tensorimageclassification.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1000;
    private static final int CAMERA_REQEUST_CODE = 10001;
    private static final int PICK_IMAGE_REQ_CODE = 10002;

    Locale locale;
    String DefaultLanguage;

    private  Bitmap photo  ;


    /**
     * UI Elements
     */
    private ImageView imageView;
    Button takePicture,insertPicture;
    private ListView listView;
    private ImageClassifier imageClassifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initalizing ui elements
        initializeUIElements();
    }

    /**
     * Method to initalize UI Elements. this method adds the on click
     */
    private void initializeUIElements() {
        imageView = findViewById(R.id.iv_capture);
        listView = findViewById(R.id.lv_probabilities);
        takePicture = findViewById(R.id.btn_take_picture);
        insertPicture = findViewById(R.id.btn_insert_picture);
        /*
         * Creating an instance of our tensor image classifier
         */
        try {
            imageClassifier = new ImageClassifier(this);
        } catch (IOException e) {
            Log.e("Image Classifier Error", "ERROR: " + e);
        }

        // adding on click listener to button
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking whether camera permissions are available.
                // if permission is avaialble then we open camera intent to get picture
                // otherwise reqeusts for permissions
                if (hasPermission()) {
                    openCamera();
                } else {
                    requestPermission();
                }
            }
        });
        insertPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent1, PICK_IMAGE_REQ_CODE);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // if this is the result of our camera image request
        if (requestCode == CAMERA_REQEUST_CODE) {
            if (data != null) {
                // getting bitmap of the image
                Bitmap photo = (Bitmap) Objects.requireNonNull(Objects.requireNonNull(data).getExtras()).get("data");
                // displaying this bitmap in imageview
                imageView.setImageBitmap(photo);

                // pass this bitmap to classifier to make prediction
                List<ImageClassifier.Recognition> predicitons = imageClassifier.recognizeImage(
                        photo, 0);

                // creating a list of string to display in list view
                final List<String> predicitonsList = new ArrayList<>();
                for (ImageClassifier.Recognition recog : predicitons) {
                    predicitonsList.add(recog.getName() + "  ::::::::::  " + recog.getConfidence());
                }

                // creating an array adapter to display the classification result in list view
                ArrayAdapter<String> predictionsAdapter = new ArrayAdapter<>(
                        this, R.layout.items_listview, predicitonsList);
                listView.setAdapter(predictionsAdapter);
            }
            else {
                Toast.makeText(this, "non Image", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == PICK_IMAGE_REQ_CODE && resultCode == RESULT_OK)
        {
            if (data != null)
            {
                try {
                    InputStream inputStream= getContentResolver().openInputStream(data.getData());
                    photo = BitmapFactory.decodeStream(inputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(photo);

                // pass this bitmap to classifier to make prediction
                List<ImageClassifier.Recognition> predicitons = imageClassifier.recognizeImage(
                        photo, 0);
                // creating a list of string to display in list view
                final List<String> predicitonsList = new ArrayList<>();
                for (ImageClassifier.Recognition recog : predicitons) {
                    predicitonsList.add(recog.getName() + "       " + recog.getConfidence());
                }

                // creating an array adapter to display the classification result in list view
                ArrayAdapter<String> predictionsAdapter = new ArrayAdapter<>(
                        this, R.layout.items_listview, predicitonsList);
                listView.setAdapter(predictionsAdapter);
            }}
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // if this is the result of our camera permission request
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (hasAllPermissions(grantResults)) {
                openCamera();
            } else {
                requestPermission();
            }
        }
    }

    /**
     * checks whether all the needed permissions have been granted or not
     *
     * @param grantResults the permission grant results
     * @return true if all the reqested permission has been granted,
     * otherwise returns false
     */
    private boolean hasAllPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED)
                return false;
        }
        return true;
    }

    /**
     * Method requests for permission if the android version is marshmallow or above
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // whether permission can be requested or on not
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
            }
            // request the camera permission permission
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * creates and starts an intent to get a picture from camera
     */
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQEUST_CODE);
    }

    /**
     * checks whether camera permission is available or not
     *
     * @return true if android version is less than marshmallo,
     * otherwise returns whether camera permission has been granted or not
     */
    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
}
