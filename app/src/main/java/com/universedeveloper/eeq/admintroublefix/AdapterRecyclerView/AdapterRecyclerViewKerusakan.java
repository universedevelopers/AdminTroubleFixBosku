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
import com.universedeveloper.eeq.admintroublefix.Detail.KerusakanTerpilih;
import com.universedeveloper.eeq.admintroublefix.ModelTroubleshoot;
import com.universedeveloper.eeq.admintroublefix.R;

import java.util.ArrayList;

public class AdapterRecyclerViewKerusakan extends RecyclerView.Adapter<AdapterRecyclerViewKerusakan.ViewHolder> implements Filterable {
    private ArrayList<ModelTroubleshoot> mArrayList;
    private ArrayList<ModelTroubleshoot> mFilteredList;
    private Context context;

    public AdapterRecyclerViewKerusakan(Context context, ArrayList<ModelTroubleshoot> arrayList) {
        this.context = context;
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;

    }
    @Override
    public AdapterRecyclerViewKerusakan.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_recycler_view_kerusakan, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerViewKerusakan.ViewHolder viewHolder, int i) {
        viewHolder.txt_id_kerusakan.setText(mFilteredList.get(i).getId_kerusakan());
        viewHolder.txt_detail.setText(mFilteredList.get(i).getDetail());
        viewHolder.txt_gambar.setText(mFilteredList.get(i).getGambar());
        viewHolder.txt_jenis_kerusakan.setText((mFilteredList.get(i).getJenis_kerusakan()));
        viewHolder.txt_namakerusakan.setText(mFilteredList.get(i).getNama_kerusakan());  //untuk mengirim url gambar ke berbagai activity
        Glide.with(context)
                .load(mFilteredList.get(i).getGambar())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(viewHolder.image);
        viewHolder.txt_solusi.setText(mFilteredList.get(i).getSolusi());
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

                        if (androidVersion.getNama_kerusakan().toLowerCase().contains(charString) || androidVersion.getDetail().toLowerCase().contains(charString)) {
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
        private TextView txt_namakerusakan,txt_jenis_kerusakan,txt_id_kerusakan,txt_detail,txt_solusi,txt_gambar;
        private ImageView image;

        public ViewHolder(View view) {
            super(view);

            txt_namakerusakan = (TextView)view.findViewById(R.id.txt_namakerusakan);
            txt_jenis_kerusakan = (TextView)view.findViewById(R.id.txt_jenis_kerusakan);
            txt_id_kerusakan = (TextView)view.findViewById(R.id.txt_id_kerusakan);
            txt_detail = (TextView)view.findViewById(R.id.txt_detail);
            image = (ImageView)view.findViewById(R.id.image);
            txt_solusi = (TextView)view.findViewById(R.id.txt_solusi);
            txt_gambar = (TextView)view.findViewById(R.id.txt_gambar);



            view.setOnClickListener(this);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detail = new Intent(view.getContext(), KerusakanTerpilih.class);
                    detail.putExtra("id_kerusakan", txt_id_kerusakan.getText());
                    detail.putExtra("detail", txt_detail.getText());
                    detail.putExtra("gambar", txt_gambar.getText());
                    detail.putExtra("jenis_kerusakan", txt_jenis_kerusakan.getText());
                    detail.putExtra("nama_kerusakan", txt_namakerusakan.getText());
                    detail.putExtra("solusi", txt_solusi.getText());
                    view.getContext().startActivity(detail);
                }
            });
        }

        @Override
        public void onClick(View view) {
            Intent detail = new Intent(view.getContext(), KerusakanTerpilih.class);
            detail.putExtra("id_kerusakan", txt_id_kerusakan.getText());
            detail.putExtra("detail", txt_detail.getText());
            detail.putExtra("gambar", txt_gambar.getText());
            detail.putExtra("jenis_kerusakan", txt_jenis_kerusakan.getText());
            detail.putExtra("nama_kerusakan", txt_namakerusakan.getText());
            detail.putExtra("solusi", txt_solusi.getText());
            view.getContext().startActivity(detail);
        }
    }
}
