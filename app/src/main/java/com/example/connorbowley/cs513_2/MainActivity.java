package com.example.connorbowley.cs513_2;

import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnAddCoupon = (Button)findViewById(R.id.btnAddCoupon);
        final Button btnAddProduct = (Button)findViewById(R.id.btnAddProduct);
        final Button btnAppLargestDiscount = (Button)findViewById(R.id.btnAppLargestDiscount);
        final Button btnListCoupons = (Button)findViewById(R.id.btnListCoupons);
        final Button btnListProducts = (Button)findViewById(R.id.btnListProducts);
        final Button btnBestDisList = (Button)findViewById(R.id.btnBestDisList);

        btnAddProduct.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent i = new Intent( MainActivity.this, AddProductActivity.class);
                        startActivity(i);
                    }
                }
        );

        btnListProducts.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent i = new Intent( MainActivity.this, ListProductsActivity.class);
                        startActivity(i);
                    }
                }
        );

        btnAddCoupon.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent i = new Intent( MainActivity.this, AddCouponActivity.class);
                        startActivity(i);
                    }
                }
        );

        btnAppLargestDiscount.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent i = new Intent( MainActivity.this, GenLargestDisActivity.class);
                        startActivity(i);
                    }
                }
        );
        btnListCoupons.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent i = new Intent( MainActivity.this, ListCouponsActivity.class);
                        startActivity(i);
                    }
                }
        );
        btnBestDisList.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View v){
                    Intent i = new Intent( MainActivity.this, DiscountUserListActivity.class);
                    startActivity(i);
                }
            }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reset_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection
//        Toast.makeText(this,"Resetting",Toast.LENGTH_LONG).show();
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Warning!");
        alertDialog.setMessage("Are you sure you want to reset the system?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                cw.deleteDatabase(MyDBHelper.DB_NAME);
                Toast.makeText(getApplicationContext(),"Reset confirmed",Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
        return true;
    }
}
