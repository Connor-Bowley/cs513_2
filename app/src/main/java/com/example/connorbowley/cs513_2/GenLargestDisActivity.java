package com.example.connorbowley.cs513_2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GenLargestDisActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtBudget;
    Button btnGenerate, btnBack;
    MyDBHelper dbHelper;
    LinearLayout lin;
    private final static int offColor = Color.argb(255,220,220,220);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_largest_dis);

        txtBudget = findViewById(R.id.txtBudget);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnBack = findViewById(R.id.btnBack_GenLarDis);
        lin = findViewById(R.id.linItemList);

        dbHelper = new MyDBHelper(this);

        btnGenerate.setOnClickListener(this);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(GenLargestDisActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == btnGenerate.getId()){
            if(txtBudget.getText().toString().isEmpty()) {
                Toast.makeText(this,"Enter a budget",Toast.LENGTH_LONG).show();
                return;
            }
            lin.removeAllViews();
            double budget = Double.parseDouble(txtBudget.getText().toString());

            //generate discount
            ArrayList<Coupon> coupons = dbHelper.getAllCoupons();
            Coupon.DiscountResult result = Coupon.generateLargestDiscount(coupons,budget);

            //display results
            TextView txtGenCost = new TextView(getApplicationContext());
            txtGenCost.setText("Total Cost: "+String.format("%.2f",result.cost));
            txtGenCost.setTextColor(Color.BLACK);
            lin.addView(txtGenCost);

            TextView txtGenDiscount = new TextView(getApplicationContext());
            txtGenDiscount.setText("Total Discount: "+String.format("%.2f",result.discount));
            txtGenDiscount.setTextColor(Color.BLACK);
            lin.addView(txtGenDiscount);
            for (int i = 0; i < result.coupons.size(); i++) {
                Coupon coupon = result.coupons.get(i);

                LinearLayout layout = new LinearLayout(getApplicationContext());
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layout.setGravity(Gravity.CENTER_VERTICAL);

                LinearLayout linItems = new LinearLayout(getApplicationContext());
                linItems.setOrientation(LinearLayout.VERTICAL);

                TextView txtCouponDiscount = new TextView(getApplicationContext());
                txtCouponDiscount.setText(String.format("%.2f",coupon.getDiscount()));
                txtCouponDiscount.setTextColor(Color.BLACK);

                if(i % 2 == 0){
                    layout.setBackgroundColor(offColor);
                }

                ArrayList<Product> items = coupon.getProducts();
                for (int j = 0; j < items.size(); j++) {
                    TextView txtItem = new TextView(getApplicationContext());
                    txtItem.setTextColor(Color.BLACK);
                    txtItem.setText(items.get(j).getName());
                    linItems.addView(txtItem);
                }

                layout.addView(linItems,0);
                layout.addView(txtCouponDiscount,1);
                lin.addView(layout);

                LinearLayout.LayoutParams linItemsParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                linItemsParams.weight = 1;

                LinearLayout.LayoutParams couponDiscountParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                couponDiscountParams.weight = 0;

                linItems.setLayoutParams(linItemsParams);
                txtCouponDiscount.setLayoutParams(couponDiscountParams);
            }
        }
    }
}
