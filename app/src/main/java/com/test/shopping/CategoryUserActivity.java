package com.test.shopping;

import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.test.shopping.Model.Products;
import com.test.shopping.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.test.shopping.Model.Products;
import com.test.shopping.ViewHolder.ProductViewHolder;

public class CategoryUserActivity extends AppCompatActivity {

    private String category;
    private RecyclerView category_list;
    private ImageView ferm;
    private TextView cate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_user);
        getSupportActionBar().hide();


        ferm = (ImageView) findViewById(R.id.ferm);
        cate = (TextView) findViewById(R.id.category);

        ferm.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
        Intent intent = new Intent(CategoryUserActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
               finish();
            }
        });


        category = getIntent().getExtras().get("category").toString();
        cate.setText(category);

        category_list = findViewById(R.id.category_list);
        category_list.setLayoutManager(new LinearLayoutManager(CategoryUserActivity.this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(reference.orderByChild("category").startAt(category).endAt(category) , Products.class).build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
                holder.txtProductName.setText(model.getPname());
                holder.txtProductDescription.setText(model.getDescription());
                holder.txtProductPrice.setText(model.getPrice());
                holder.txtProductTva.setText(model.getTva());
                Picasso.get().load(model.getImage()).into(holder.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CategoryUserActivity.this,ProductDetailsActivity.class);
                        intent.putExtra("pid",model.getPid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_items_layout, viewGroup, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };

        category_list.setAdapter(adapter);
        adapter.startListening();
    }
}