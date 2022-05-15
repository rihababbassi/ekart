package com.test.shopping.Prevalent;



import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.test.shopping.Interface.ItemClickListner;
import com.test.shopping.R;

import java.text.BreakIterator;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName,txtProductPrice,txtProductQuantity,txtProductTva;

    private ItemClickListner itemClickListner;

    public CartViewHolder(View itemView) {
        super(itemView);
        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
        txtProductTva = itemView.findViewById(R.id.cart_product_tva);


    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}


