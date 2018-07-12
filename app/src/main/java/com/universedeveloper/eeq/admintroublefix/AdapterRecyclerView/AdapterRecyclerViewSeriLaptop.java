package com.universedeveloper.eeq.admintroublefix.AdapterRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.universedeveloper.eeq.admintroublefix.Detail.LaptopTerpilih;
import com.universedeveloper.eeq.admintroublefix.Detail.LaptopTerpilih;
import com.universedeveloper.eeq.admintroublefix.ModelTroubleshoot;
import com.universedeveloper.eeq.admintroublefix.R;

import java.util.ArrayList;

public class AdapterRecyclerViewSeriLaptop extends RecyclerView.Adapter<AdapterRecyclerViewSeriLaptop.ViewHolder> implements Filterable {
    private ArrayList<ModelTroubleshoot> mArrayList;
    private ArrayList<ModelTroubleshoot> mFilteredList;
    private Context context;

    public AdapterRecyclerViewSeriLaptop(Context context, ArrayList<ModelTroubleshoot> arrayList) {
        this.context = context;
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;

    }
    @Override
    public AdapterRecyclerViewSeriLaptop.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_recycler_view_serilaptop, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerViewSeriLaptop.ViewHolder viewHolder, int i) {
        viewHolder.txt_id_laptop.setText(mFilteredList.get(i).getId_laptop());
        viewHolder.txt_merklaptop.setText(mFilteredList.get(i).getMerk_laptop());
        viewHolder.txt_gambar.setText(mFilteredList.get(i).getGambar());
        viewHolder.txt_prosessor.setText(mFilteredList.get(i).getProsessor());
        viewHolder.txt_ram.setText(mFilteredList.get(i).getRam());
        viewHolder.txt_gpu.setText(mFilteredList.get(i).getGpu());
        viewHolder.txt_hdd.setText(mFilteredList.get(i).getHdd());
        viewHolder.txt_layar.setText(mFilteredList.get(i).getLayar());
        viewHolder.txt_serilaptop.setText((mFilteredList.get(i).getSeri_laptop())); //untuk mengirim url gambar ke berbagai activity
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

                        if (androidVersion.getSeri_laptop().toLowerCase().contains(charString) || androidVersion.getDetail().toLowerCase().contains(charString)) {
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
        private TextView txt_serilaptop,txt_merklaptop,txt_id_laptop,txt_prosessor,txt_ram,txt_gpu,txt_hdd,txt_layar,txt_gambar;
        private ImageView image;

        public ViewHolder(View view) {
            super(view);

            txt_serilaptop = (TextView)view.findViewById(R.id.txt_serilaptop);
            txt_merklaptop = (TextView)view.findViewById(R.id.spinner_merk);
            txt_id_laptop = (TextView)view.findViewById(R.id.txt_id_laptop);
            txt_prosessor = (TextView)view.findViewById(R.id.txt_prosessor);
            txt_ram = (TextView)view.findViewById(R.id.spinner_ram);
            txt_gpu = (TextView)view.findViewById(R.id.txt_gpu);
            txt_hdd = (TextView)view.findViewById(R.id.spinner_hdd);
            txt_layar = (TextView)view.findViewById(R.id.spinner_layar);
            image = (ImageView)view.findViewById(R.id.image);
            txt_gambar = (TextView)view.findViewById(R.id.txt_gambar);



            view.setOnClickListener(this);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detail = new Intent(view.getContext(), LaptopTerpilih.class);
                    detail.putExtra("id_laptop", txt_id_laptop.getText());
                    detail.putExtra("merk_laptop", txt_merklaptop.getText());
                    detail.putExtra("prosessor", txt_prosessor.getText());
                    detail.putExtra("ram", txt_ram.getText());
                    detail.putExtra("gpu", txt_gpu.getText());
                    detail.putExtra("hdd", txt_hdd.getText());
                    detail.putExtra("layar", txt_layar.getText());
                    detail.putExtra("gambar", txt_gambar.getText());
                    detail.putExtra("seri_laptop", txt_serilaptop.getText());
                    view.getContext().startActivity(detail);
                }
            });
        }

        @Override
        public void onClick(View view) {
            Intent detail = new Intent(view.getContext(), LaptopTerpilih.class);
            detail.putExtra("id_laptop", txt_id_laptop.getText());
            detail.putExtra("merk_laptop", txt_merklaptop.getText());
            detail.putExtra("prosessor", txt_prosessor.getText());
            detail.putExtra("ram", txt_ram.getText());
            detail.putExtra("gpu", txt_gpu.getText());
            detail.putExtra("hdd", txt_hdd.getText());
            detail.putExtra("layar", txt_layar.getText());
            detail.putExtra("gambar", txt_gambar.getText());
            detail.putExtra("seri_laptop", txt_serilaptop.getText());
            view.getContext().startActivity(detail);
        }
    }
}
