package com.b1strudio.srvdabba.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.b1strudio.srvdabba.Common.Common;
import com.b1strudio.srvdabba.Interface.ItemClickListener;
import com.b1strudio.srvdabba.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        ,View.OnCreateContextMenuListener
    {

        public TextView food_name;
        public ImageView food_image;

        private ItemClickListener itemClickListener;

    public FoodViewHolder(View itemView) {
        super(itemView);

        food_name = (TextView)itemView.findViewById(R.id.food_name);
        food_image = (ImageView)itemView.findViewById(R.id.food_image);

        itemView.setOnCreateContextMenuListener(this);

        itemView.setOnClickListener(this);
    }

        public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

        @Override
        public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select the Action");

        contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0,0,getAdapterPosition(), Common.DELETE);
    }
    }


