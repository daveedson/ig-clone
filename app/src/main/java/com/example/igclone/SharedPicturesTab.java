package com.example.igclone;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharedPicturesTab extends Fragment implements View.OnClickListener {

    private ImageView imgPlaceholder;
    private Button btnShareImage;
    private EditText edtEnterCaption;
    Bitmap receivedImageBitmap;


    public SharedPicturesTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shared_pictures_tab, container, false);

        imgPlaceholder =view.findViewById(R.id.imgplaceholder);
        btnShareImage = view.findViewById(R.id.btnShareImage);
        edtEnterCaption = view.findViewById(R.id.edtEnterCaption);

       imgPlaceholder.setOnClickListener(SharedPicturesTab.this);
       btnShareImage.setOnClickListener(SharedPicturesTab.this);

        return view;
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){

            case R.id.imgplaceholder:

                if (android.os.Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                    requestPermissions(new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                    }else {


                    getChosenImage();
                }


                break;


            case R.id.btnShareImage:

                if (receivedImageBitmap != null){


                    if (edtEnterCaption.getText().toString().equals("")){
                        Toast.makeText(getContext(),"Error: You must specify a description",Toast.LENGTH_LONG).show();

                    }else{


                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parse = new ParseFile("pic.png",bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture", parse);
                        parseObject.put("image_des",parse);
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());

                       final ProgressDialog pd = new ProgressDialog(getContext());
                        pd.setMessage("Loading... ");
                        pd.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e==null){
                                    Toast.makeText(getContext(), "Done!!!", Toast.LENGTH_SHORT).show();
                                }else{

                                    Toast.makeText(getContext(), "Unknown error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                pd.dismiss();
                            }
                        });




                    }


                }else{

                    Toast.makeText(getContext(),"Error: You must select an image",Toast.LENGTH_LONG).show();
                }



                break;



        }


    }

    private void getChosenImage() {


       // Toast.makeText(getContext(),"Now we can access the images",Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == 1000){
            if (grantResults.length> 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                getChosenImage();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == 2000){

            if (requestCode== Activity.RESULT_OK){
                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn,null,null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
               imgPlaceholder.setImageBitmap(receivedImageBitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


    }
}
