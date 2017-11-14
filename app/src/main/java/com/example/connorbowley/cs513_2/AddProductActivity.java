package com.example.connorbowley.cs513_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener{

    Button   subAddProduct, btnBack;
    EditText txtName, txtPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        subAddProduct = (Button) findViewById(R.id.subAddProduct);
        txtName       = (EditText)findViewById(R.id.txtName);
        txtPrice      = (EditText)findViewById(R.id.txtPrice);
        btnBack       = (Button) findViewById(R.id.btnBack_AddProduct);

        subAddProduct.setOnClickListener(this);

        btnBack.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent i = new Intent( AddProductActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == subAddProduct.getId()) {
            double price = -1.0;
            try {
                price = Double.parseDouble(txtPrice.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this,"The price must be a number",Toast.LENGTH_SHORT).show();
                return;
            }
            if(price < 0) {
                Toast.makeText(this,"The price must be non-negative",Toast.LENGTH_SHORT).show();
                return;
            }
            String name = txtName.getText().toString();
            if(name.isEmpty()) {
                Toast.makeText(this,"The name cannot be empty",Toast.LENGTH_SHORT).show();
                return;
            }
            Product product = new Product(name,price);

            MyDBHelper dbHelper = new MyDBHelper(this);
            if(dbHelper.insertProduct(product)) {
                Toast.makeText(this,"Product inserted",Toast.LENGTH_SHORT).show();
                txtName.setText("");
                txtPrice.setText("");
            }
        }
    }

}
