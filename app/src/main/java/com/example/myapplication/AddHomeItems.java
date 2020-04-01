package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class AddHomeItems extends AppCompatActivity {

    EditText hName, hQty;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home_items);

        hName = findViewById(R.id.hName);
        hQty = findViewById(R.id.hQty);
        submit = findViewById(R.id.submit_home);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(hName.getText().toString().isEmpty() || hQty.getText().toString().isEmpty())
                {
                    Toast.makeText(AddHomeItems.this, "Please fill all the fields to continue..!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String name = hName.getText().toString();
                    int Qty = Integer.parseInt(hQty.getText().toString());
                    String itemchar = hName.getText().toString().toUpperCase().charAt(0)+"";

                    HomeItems home = new HomeItems();
                    home.setItemname(name);
                    home.setItemquantity(Qty);
                    home.setItemchar(itemchar);

                    Backendless.Persistence.save(home, new AsyncCallback<HomeItems>() {
                        @Override
                        public void handleResponse(HomeItems response) {
                            Toast.makeText(AddHomeItems.this, "Item Added Successfully!", Toast.LENGTH_SHORT).show();
                            AddHomeItems.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(AddHomeItems.this, "Error:"+ fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
