package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class OfficeActivity extends AppCompatActivity {

    EditText name, desc;
    Button add, view, upload;
    ImageView img;

    final int REQUEST_CODE_OFFICE = 333;

    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);

        init();

        sqLiteHelper = new SQLiteHelper(this, "OfficeDB.sqlite",null,1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS OFFICE (Id INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR, TEXT VARCHAR, IMAGE BLOB)");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        OfficeActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_OFFICE
                );
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    sqLiteHelper.insertData("OFFICE",
                            name.getText().toString().trim(),
                            desc.getText().toString().trim(),
                            imageViewToByte(img)
                    );
                    Toast.makeText(getApplicationContext(), "Image Added Successfully!", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    desc.setText("");
                    img.setImageResource(R.mipmap.ic_launcher);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OfficeActivity.this, OfficeList.class);
                startActivity(intent);
            }
        });
    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_OFFICE)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_OFFICE);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "You don't have the permissions to Perform this Action", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_OFFICE && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init()
    {
        name = findViewById(R.id.officeitemname);
        desc = findViewById(R.id.officeitemtext);
        add = findViewById(R.id.officeadditem);
        view = findViewById(R.id.viewofficelist);
        upload = findViewById(R.id.officeuploadbtn);
        img = findViewById(R.id.officeimg);
    }
}
