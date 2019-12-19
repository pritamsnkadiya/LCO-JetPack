package com.example.lcogetpack.ui.ui.activity.tackimage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lcogetpack.R;
import com.example.lcogetpack.data.utils.AppConstants;
import com.theartofdev.edmodo.cropper.CropImageView;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.system.ErrnoException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class TakePictureActivity extends AppCompatActivity implements CropImageView.OnCropImageCompleteListener, CropImageView.OnSetImageUriCompleteListener {

    private CropImageView mCropImageView;
    private View mProgressView;
    private TextView mProgressViewText;
    private Uri mCropImageUri;
    //private Toolbar toolbar;
    String docFileName;
    RelativeLayout rl_buttons;
    Button bt_next;
    ImageView iv_camera, iv_rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        mCropImageView = findViewById(R.id.CropImageView);
        mProgressView = findViewById(R.id.ProgressView);
        mProgressViewText = findViewById(R.id.ProgressViewText);
        rl_buttons = findViewById(R.id.rl_buttons);
        iv_camera = findViewById(R.id.iv_camera);
        iv_rotate = findViewById(R.id.iv_rotate);

        iv_camera.setOnClickListener(onClickListener);
        iv_rotate.setOnClickListener(onClickListener);

        bt_next = findViewById(R.id.bt_next);

        Intent intent = getIntent();
        docFileName = intent.getStringExtra("DOC_FILE_NAME");

        CropImage.startPickImageActivity(this);
    }

    public void onCropImageClick(View view) {
        mCropImageView.getCroppedImageAsync(900, 900);
        mProgressViewText.setText("Cropping...");
        mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {

        Bitmap crop = result.getBitmap();

        if (result.getError() == null && crop != null) {
            mCropImageView.setImageBitmap(crop);
            new SaveImage().execute(crop);

        }

    }

    public void onCancelImageClick(View view) {
        finish();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.iv_camera:
                    CropImage.startPickImageActivity(TakePictureActivity.this);
                    break;

                case R.id.iv_rotate:
                    mCropImageView.rotateImage(90);

            }

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        mCropImageView.setOnSetImageUriCompleteListener(this);

        mCropImageView.setOnCropImageCompleteListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCropImageView.setOnSetImageUriCompleteListener(null);
        mCropImageView.setOnCropImageCompleteListener(null);
    }

    @Override
    public void onSetImageUriComplete(CropImageView cropImageView, Uri uri, Exception error) {
        mProgressView.setVisibility(View.INVISIBLE);
        if (error != null) {
            Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            rl_buttons.setVisibility(View.VISIBLE);
            bt_next.setVisibility(View.VISIBLE);

            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            boolean requirePermissions = false;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && isUriRequirePermission(imageUri)) {
                requirePermissions = true;
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }

            if (!requirePermissions) {
                mCropImageView.setImageUriAsync(imageUri);
                mProgressViewText.setText("Loading...");
                mProgressView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mCropImageView.setImageUriAsync(mCropImageUri);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean isUriRequirePermission(Uri uri) {
        try {
            ContentResolver resolver = getContentResolver();
            InputStream iStream = resolver.openInputStream(uri);
            iStream.close();
            return false;
        } catch (FileNotFoundException fe) {
            if (fe.getCause() instanceof ErrnoException) ;
            return false;
        } catch (Exception e) {

        }
        return false;
    }


    private void saveImage(Bitmap finalBitmap) {

        File extStore = new File(Environment.getExternalStorageDirectory() + File.separator + "LetzCatchUp" + File.separator + "Image");

        if (!extStore.exists()) {
            extStore.mkdirs();
        }

        String path = extStore.getAbsolutePath() + "/" + docFileName + ".png";
        Log.d("Image", "Save to: " + path);

        try {
            File myFile = new File(path);
            myFile.createNewFile();
            FileOutputStream out = new FileOutputStream(myFile);


            AppConstants.IMAGE_NAME = path;

            AppConstants.IMAGE_PATH = docFileName;


            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.flush();
            out.close();

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(new File(path)));
            sendBroadcast(intent);

            Intent data = new Intent();
            data.putExtra("URI", Uri.fromFile(myFile).getPath());
            setResult(RESULT_OK, data);
            finish();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class SaveImage extends AsyncTask<Bitmap, String, String> {

        @Override
        protected void onPreExecute() {
            mProgressViewText.setText("Saving...");
            mProgressView.setVisibility(View.VISIBLE);


        }

        @Override
        protected String doInBackground(Bitmap... bm) {

            saveImage(bm[0]);
            return "";
        }

        @Override
        protected void onProgressUpdate(String... str) {
        }

        @Override
        protected void onPostExecute(String str) {
            mProgressView.setVisibility(View.INVISIBLE);
        }
    }
}