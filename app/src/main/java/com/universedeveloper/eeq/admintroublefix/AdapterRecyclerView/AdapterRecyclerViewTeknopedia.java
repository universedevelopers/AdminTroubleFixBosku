package com.universedeveloper.eeq.admintroublefix.AdapterRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.universedeveloper.eeq.admintroublefix.Detail.TeknopediaTerpilih;
import com.universedeveloper.eeq.admintroublefix.Detail.TeknopediaTerpilih;
import com.universedeveloper.eeq.admintroublefix.ModelTroubleshoot;
import com.universedeveloper.eeq.admintroublefix.R;
import com.universedeveloper.eeq.admintroublefix.Teknopedia;

import java.util.ArrayList;

public class AdapterRecyclerViewTeknopedia extends RecyclerView.Adapter<AdapterRecyclerViewTeknopedia.ViewHolder> implements Filterable {
    private ArrayList<ModelTroubleshoot> mArrayList;
    private ArrayList<ModelTroubleshoot> mFilteredList;
    private Context context;

    public AdapterRecyclerViewTeknopedia(Context context, ArrayList<ModelTroubleshoot> arrayList) {
        this.context = context;
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;

    }
    @Override
    public AdapterRecyclerViewTeknopedia.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_recycler_view_teknopedia, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerViewTeknopedia.ViewHolder viewHolder, int i) {
        viewHolder.txt_id_komponen.setText(mFilteredList.get(i).getId_komponen());
        viewHolder.txt_detail.setText(mFilteredList.get(i).getDetail());
        viewHolder.txt_gambar.setText(mFilteredList.get(i).getGambar());
        viewHolder.txt_namakomponen.setText((mFilteredList.get(i).getNama_komponen())); //untuk mengirim url gambar ke berbagai activity
        Glide.with(context)
                .load(mFilteredList.get(i).getGambar())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<ModelTroubleshoot> filteredList = new ArrayList<>();

                    for (ModelTroubleshoot androidVersion : mArrayList) {

                        if (androidVersion.getNama_komponen().toLowerCase().contains(charString) || androidVersion.getDetail().toLowerCase().contains(charString)) {
                            filteredList.add(androidVersion);
                        }
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ModelTroubleshoot>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txt_namakomponen,txt_id_komponen,txt_detail,txt_gambar;
        private ImageView image;

        public ViewHolder(View view) {
            super(view);

            txt_namakomponen = (TextView)view.findViewById(R.id.txt_namakomponen);
            txt_id_komponen = (TextView)view.findViewById(R.id.txt_id_komponen);
            txt_detail = (TextView)view.findViewById(R.id.txt_detail);
            image = (ImageView)view.findViewById(R.id.image);
            txt_gambar = (TextView)view.findViewById(R.id.txt_gambar);



            view.setOnClickListener(this);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detail = new Intent(view.getContext(), TeknopediaTerpilih.class);
                    detail.putExtra("id_komponen", txt_id_komponen.getText());
                    detail.putExtra("detail", txt_detail.getText());
                    detail.putExtra("gambar", txt_gambar.getText());
                    detail.putExtra("nama_komponen", txt_namakomponen.getText());
                    view.getContext().startActivity(detail);
                }
            });
        }

        @Override
        public void onClick(View view) {
            Intent detail = new Intent(view.getContext(), TeknopediaTerpilih.class);
            detail.putExtra("id_komponen", txt_id_komponen.getText());
            detail.putExtra("detail", txt_detail.getText());
            detail.putExtra("gambar", txt_gambar.getText());
            detail.putExtra("nama_komponen", txt_namakomponen.getText());
            view.getContext().startActivity(detail);
        }
    }
}
