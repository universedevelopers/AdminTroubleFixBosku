
package com.universedeveloper.eeq.admintroublefix.Detail;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.universedeveloper.eeq.admintroublefix.Edit.EditLaptop;
import com.universedeveloper.eeq.admintroublefix.Edit.EditTeknopedia;
import com.universedeveloper.eeq.admintroublefix.Insert.InputLaptop;
import com.universedeveloper.eeq.admintroublefix.R;
import com.universedeveloper.eeq.admintroublefix.Utility.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LaptopTerpilih extends AppCompatActivity {

    TextView txt_merk,  txt_seri, txt_proc, txt_ram, txt_gpu, txt_hdd, txt_layar;
    ImageView image1;

    String id, merk, seri, proc, ram, gpu, hdd, layar, gambar;

    Button btnEdit;
    Button btnHapus;

    JSONArray string_json = null;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String url_hapus = "http://universedeveloper.com/gudangandroid//ferdirockers/list_detail/hapus_laptop.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_terpilih);

        id = getIntent().getStringExtra("id_laptop");
        merk = getIntent().getStringExtra("merk_laptop");
        seri = getIntent().getStringExtra("seri_laptop");
        proc = getIntent().getStringExtra("prosessor");
        ram = getIntent().getStringExtra("ram");
        gpu = getIntent().getStringExtra("gpu");
        hdd = getIntent().getStringExtra("hdd");
        layar = getIntent().getStringExtra("layar");
        gambar = getIntent().getStringExtra("gambar");

        txt_merk = (TextView) findViewById(R.id.txt_merk);
        txt_seri = (TextView) findViewById(R.id.txt_seri);
        txt_proc = (TextView) findViewById(R.id.txt_proc);
        txt_ram = (TextView) findViewById(R.id.txt_ram);
        txt_gpu = (TextView) findViewById(R.id.txt_gpu);
        txt_hdd = (TextView) findViewById(R.id.txt_hdd);
        txt_layar = (TextView) findViewById(R.id.txt_layar);
        image1 = (ImageView) findViewById(R.id.image1);

        txt_merk.setText(merk);
        txt_seri.setText(seri);
        txt_proc.setText(proc);
        txt_ram.setText(ram);
        txt_gpu.setText(gpu);
        txt_hdd.setText(hdd);
        txt_layar.setText(layar);
        Glide.with(LaptopTerpilih.this)
                .load(gambar)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(image1);

        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LaptopTerpilih.this, EditLaptop.class);
                intent.putExtra("id_laptop", id);
                intent.putExtra("seri_laptop", txt_seri.getText().toString());
                intent.putExtra("prosessor", txt_proc.getText().toString());
                intent.putExtra("gpu", txt_gpu.getText().toString());
                intent.putExtra("gambar", gambar);
                startActivity(intent);
            }
        });

        btnHapus = (Button) findViewById(R.id.btnHapus);
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LaptopTerpilih.this);

                builder.setTitle("Hapus Laptop");
                builder.setMessage("Apakah kamu ingin menghapus laptop dari daftar laptop ?");

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        new HapusData().execute();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public class HapusData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LaptopTerpilih.this);
            pDialog.setMessage("Mohon Tunggu ... !");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        public String doInBackground(String... params) {

            try {

                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("id_laptop",id));

                JSONObject json = jsonParser.makeHttpRequest(url_hapus, "GET", params1);
                string_json = json.getJSONArray("promo");



            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            runOnUiThread(new Runnable() {
                public void run() {

                    Toast.makeText(LaptopTerpilih.this, "Hapus data berhasil", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();



                }
            });
        }
    }
}
