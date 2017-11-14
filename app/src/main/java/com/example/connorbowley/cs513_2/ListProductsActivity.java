package com.example.connorbowley.cs513_2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListProductsActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnBack, btnUpdate;
    LinearLayout lin;
    MyDBHelper dbHelper;
    ArrayList<CheckBox> checkBoxes = new ArrayList<>(); //parallel to layouts
    ArrayList<EditText> prices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);

        btnBack = (Button)findViewById(R.id.btnBack_ListProducts);
        btnUpdate = (Button)findViewById(R.id.btnUpdate_ListProducts);
        lin = (LinearLayout)findViewById(R.id.linProductList_ListProducts);

        btnBack.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent i = new Intent( ListProductsActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                }
        );

        btnUpdate.setOnClickListener(this);

        dbHelper = new MyDBHelper(this);
        ArrayList<Product> products = dbHelper.getAllProducts();
        if (products == null){
            Toast.makeText(this,"Unable to retrieve products",Toast.LENGTH_SHORT).show();
        }
        else {
            if(products.size() == 0) {
                Toast.makeText(this,"No products to list",Toast.LENGTH_SHORT).show();
            }
            else {
                for(int i = 0; i < products.size(); i++) {
                    Product product = products.get(i);
                    LinearLayout layout = new LinearLayout(getApplicationContext());
                    layout.setOrientation(LinearLayout.HORIZONTAL);

                    CheckBox checkBox = new CheckBox(getApplicationContext());
                    checkBox.setText(product.getName());
                    checkBox.setTextColor(Color.BLACK);
                    checkBox.setTag(product);

                    EditText price = new EditText(getApplicationContext());
                    price.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    price.setText(String.format("%.2f",product.getPrice()));
                    price.setTextColor(Color.BLACK);

                    layout.addView(checkBox,0);
                    layout.addView(price,1);

                    checkBoxes.add(checkBox);
                    prices.add(price);
                    lin.addView(layout);

                    ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                    LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    checkBoxParams.weight = 1;

                    LinearLayout.LayoutParams priceParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    priceParams.weight = 0;

                    checkBox.setLayoutParams(checkBoxParams);
                    price.setLayoutParams(priceParams);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnUpdate.getId()) {
            //get list of products to update and change the prices
            ArrayList<Product> toUpdate = new ArrayList<>();
            boolean found = false;
            for (int i = 0; i < checkBoxes.size(); i++) {
                boolean checked = checkBoxes.get(i).isChecked();
                found |= checked;
                if(checked) {
                    Product product = (Product)checkBoxes.get(i).getTag();
                    product.setPrice(Double.parseDouble(prices.get(i).getText().toString()));
                    toUpdate.add(product);
                }
            }
            if(!found)
                Toast.makeText(this,"No boxes were checked for updating",Toast.LENGTH_LONG).show();
            else if(dbHelper.updateProducts(toUpdate))
                Toast.makeText(this,"Prices updated successfully",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"false was returned",Toast.LENGTH_SHORT).show();
        }
    }
}
