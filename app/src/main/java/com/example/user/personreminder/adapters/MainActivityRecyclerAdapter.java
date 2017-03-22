package com.example.user.personreminder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.personreminder.R;
import com.example.user.personreminder.data.ContactList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 19-03-2017.
 */

public class MainActivityRecyclerAdapter extends RecyclerView.Adapter<MainActivityRecyclerAdapter.DataHolder> {

    private LayoutInflater inflater;
    private List<ContactList> data = new ArrayList<>();
    private itemClickCallback itemClickCallback;

    public MainActivityRecyclerAdapter(Context c, List<ContactList> listData){
        inflater = LayoutInflater.from(c);
        data.addAll(listData);
    }

    public interface itemClickCallback {
        void onItemClick (int p);
    }

    public void setItemClickCallback (final itemClickCallback itemClickCallback){
        this.itemClickCallback = itemClickCallback ;
    }

    public MainActivityRecyclerAdapter() {
        super();
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        ContactList item = data.get(position);
        holder.textView_name.setText(item.getName());
        holder.textView_contact_number.setText(item.getNumber());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_main_item,parent,false);
        DataHolder dataHolder = new DataHolder(view);
        return dataHolder;
    }

    class DataHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        ImageView imageView;
        TextView textView_name;
        TextView textView_contact_number;
        View container ;

        public DataHolder(View viewItem){
            super(viewItem);
            imageView = (ImageView) viewItem.findViewById(R.id.image);
            textView_name = (TextView) viewItem.findViewById(R.id.name);
            textView_contact_number = (TextView) viewItem.findViewById(R.id.contact_number);
            container = (View) viewItem.findViewById(R.id.card_view);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.card_view){
                ContactList item = data.get(getAdapterPosition());
                Log.d("Main", item.getId() + "");
                itemClickCallback.onItemClick(item.getId());
            }
        }
    }
}
