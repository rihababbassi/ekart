package com.test.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView ff;
    private TextView Restauration, Electricité, Quincaillerie, Industriel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        getSupportActionBar().hide();

        Restauration = (TextView) findViewById(R.id.restauration);
        Electricité = (TextView) findViewById(R.id.electricity);
        Quincaillerie = (TextView) findViewById(R.id.quincaillerie);
        Industriel = (TextView) findViewById(R.id.industry);
        ff = (ImageView) findViewById(R.id.ff);

        ff.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        Restauration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Restauration");
                startActivity(intent);
            }
        });

        Electricité.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Electricité");
                startActivity(intent);
            }
        });

        Quincaillerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Quincaillerie");
                startActivity(intent);
            }
        });

        Industriel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Industriel");
                startActivity(intent);
            }
        });
    }
}