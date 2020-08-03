package com.example.dogs.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dogs.R;
import com.example.dogs.model.Breed;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchListViewAdapter extends BaseAdapter {
    //searchView code from: https://abhiandroid.com/ui/searchview
    Context mContext;
    LayoutInflater inflater;
    private List<Breed> breedsList = null;
    private ArrayList<Breed> arraylist;

    public SearchListViewAdapter(Context context, List<Breed> breedsList) {
        mContext = context;
        this.breedsList = breedsList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Breed>();
        this.arraylist.addAll(breedsList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return breedsList.size();
    }

    @Override
    public Breed getItem(int position) {
        return breedsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.search_list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(breedsList.get(position).getBreedName());
        return view;
    }

    // Filter
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        breedsList.clear();
        if (charText.length() == 0) {
            breedsList.addAll(arraylist);
        } else {
            for (Breed wp : arraylist) {
                if (wp.getBreedName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    breedsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}