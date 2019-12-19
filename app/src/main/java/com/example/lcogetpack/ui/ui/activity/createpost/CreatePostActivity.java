package com.example.lcogetpack.ui.ui.activity.createpost;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.AppConstants;
import com.example.lcogetpack.data.utils.Method;
import com.example.lcogetpack.databinding.ActivityCreatePostBinding;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.activity.tackimage.TakePictureActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.lcogetpack.data.utils.Method.hud;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = CreatePostViewModel.class.getName();
    public Activity activity;
    public Context context;
    public ActivityCreatePostBinding binding;
    public CreatePostViewModel viewModel;
    private static final String VIDEO_DIRECTORY = "/ifco";
    public int GALLERY = 11, CAMERA = 22;
    public int TYPE = 2;
    public String media_path = "";
    public static final int TAKE_PICTURE = 1;
    public String ImageName;
    private Boolean profileImage = false;
    public String imagePath = null;
    public String image_uri;
    public String image_name;
    public String image_format;
    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        activity = CreatePostActivity.this;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post);
       /* viewModel = new CreatePostViewModel(context, activity, binding);
        binding.setCreatePostView(viewModel);*/
        ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(CreatePostActivity.this);

        binding.createPostId.imgSearch.setVisibility(View.GONE);
        binding.createPostId.toolbarTitle.setText("Create Post");

        binding.imgChooseImage.setOnClickListener(this);
        binding.imgChooseVideo.setOnClickListener(this);
        binding.tvCreatePost.setOnClickListener(this);
        binding.createPostId.civBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_choose_image:
                profileImage = true;
                ImageName = "ProfileImage";
                startCropImageActivity(ImageName);
                break;

            case R.id.img_choose_video:
                showPictureDialog();
                break;

            case R.id.tv_create_post:
                if (!media_path.isEmpty()) {
                    Method.logProgressDialogCall(activity);
                    new UploadFileToServer().execute();
                } else {
                    Toast.makeText(context, "Please choose media ", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.civ_back_btn:
                activity.finish();
                break;
        }
    }

    public void startCropImageActivity(String img) {
        Intent intent = new Intent(this, TakePictureActivity.class);
        String docFileNameOnDevice = img + "_" + System.currentTimeMillis();
        intent.putExtra("DOC_FILE_NAME", docFileNameOnDevice);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if (requestCode == TAKE_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    imagePath = data.getStringExtra("URI");
                    if (imagePath != null) {
                        if (profileImage) {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(new File(imagePath)));
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                                TYPE = 0;
                                binding.imgShowImage.setVisibility(View.VISIBLE);
                                binding.videoView.setVisibility(View.GONE);
                                binding.imgShowImage.setImageBitmap(bitmap);
                                image_uri = imagePath;
                                image_format = ".png";
                                image_name = "Profile Image";
                                media_path = imagePath;
                                AppConstants.IMAGE_PATH = imagePath;
                                AppConstants.IMAGE_NAME = image_name;
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                        }
                    }
                }
            }
        } else if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();

                String selectedVideoPath = getPath(contentURI);
                Log.d(TAG + " path", selectedVideoPath);
                saveVideoToInternalStorage(selectedVideoPath);
                AppConstants.VIDEO_PATH = selectedVideoPath;
                media_path = selectedVideoPath;
                TYPE = 1;
                binding.videoView.setVisibility(View.VISIBLE);
                binding.imgShowImage.setVisibility(View.GONE);
                binding.videoView.setVideoURI(contentURI);
                binding.videoView.requestFocus();
                binding.videoView.start();

            }

        } else if (requestCode == CAMERA) {
            Uri contentURI = data.getData();
            String recordedVideoPath = getPath(contentURI);
            Log.d(TAG, recordedVideoPath);
            saveVideoToInternalStorage(recordedVideoPath);
            AppConstants.VIDEO_PATH = recordedVideoPath;
            media_path = recordedVideoPath;
            TYPE = 1;
            binding.videoView.setVisibility(View.VISIBLE);
            binding.imgShowImage.setVisibility(View.GONE);
            binding.videoView.setVideoURI(contentURI);
            binding.videoView.requestFocus();
            binding.videoView.start();
        } else {
        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select video from gallery",
                "Record video from camera"};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            chooseVideoFromGallary();
                            break;
                        case 1:
                            takeVideoFromCamera();
                            break;
                    }
                });
        pictureDialog.show();
    }

    public void chooseVideoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takeVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULL POINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    private void saveVideoToInternalStorage(String filePath) {
        File newfile;
        try {
            File currentFile = new File(filePath);
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + VIDEO_DIRECTORY);
            newfile = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".mp4");

            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }

            if (currentFile.exists()) {

                InputStream in = new FileInputStream(currentFile);
                OutputStream out = new FileOutputStream(newfile);

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                Log.v(TAG, "Video file saved successfully.");
            } else {
                Log.v(TAG, "Video saving failed. Source file missing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            Log.d("PPPPPP", progress[0] + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFileToServer();
        }

        public String uploadFileToServer() {

            File file = new File(media_path);

            RequestBody file_type = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(TYPE));
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), binding.edCreatePost.getText().toString().trim());
            RequestBody requestBodyId = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBodyId);

            ApiInterface mService = retrofit.create(ApiInterface.class);
            Call<ResponsModel> responseModelCall = mService.uploadVideoToServer(file_type, description, body);
            responseModelCall.enqueue(new Callback<ResponsModel>() {
                @Override
                public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                    if (response.isSuccessful()) {
                        Method.hud.dismiss();
                        finish();
                    } else {
                        hud.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponsModel> call, Throwable t) {
                    t.getMessage();
                    hud.dismiss();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("PPPPPPPP", "Response from server: " + result);
            super.onPostExecute(result);
        }

    }
}