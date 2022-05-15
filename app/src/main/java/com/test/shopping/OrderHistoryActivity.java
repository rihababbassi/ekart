package com.test.shopping;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.shopping.Model.HistoryOrder;


public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView historyList;
    private DatabaseReference historyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        getSupportActionBar().hide();

        historyRef = FirebaseDatabase.getInstance().getReference().child("History");
        historyList = findViewById(R.id.history_list);
        historyList.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<HistoryOrder> options =
                new FirebaseRecyclerOptions.Builder<HistoryOrder>()
                        .setQuery(historyRef, HistoryOrder.class)
                        .build();
        FirebaseRecyclerAdapter<HistoryOrder, OrderHistoryActivity.OrderHistoryViewHolder> adapter =
                new FirebaseRecyclerAdapter<HistoryOrder, OrderHistoryActivity.OrderHistoryViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OrderHistoryActivity.OrderHistoryViewHolder holder, final int position, @NonNull final HistoryOrder model) {

                        holder.userName.setText("Nom: " + model.getName());
                        holder.userPhoneNumber.setText("N°téléphone: " + model.getPhone());
                        holder.userTotalPrice.setText("Prix total = Dt." + model.getTotalAmount());
                        holder.userDateTime.setText("commandé le: " + model.getDate() + " " + model.getTime());
                        holder.userShippingAddress.setText("Adresse de livraison: " + model.getAddress() + ", " + model.getCity());
                        holder.showOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String uID = getRef(position).getKey();
                                Intent intent = new Intent(OrderHistoryActivity.this, AdminUserProductsActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });



                    }

                    @NonNull
                    @Override
                    public OrderHistoryActivity.OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_layout, parent, false);
                        return new OrderHistoryViewHolder(view);
                    }
                };
        historyList.setAdapter(adapter);
        adapter.startListening();

    }


    public static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

        public TextView userName, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress;
        public Button showOrdersBtn;

        public OrderHistoryViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            userShippingAddress = itemView.findViewById(R.id.order_address_city);
            showOrdersBtn = itemView.findViewById(R.id.show_all_product_btn);
        }
    }


}






