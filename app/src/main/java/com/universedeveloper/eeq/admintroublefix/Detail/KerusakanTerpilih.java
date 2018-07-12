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
import com.universedeveloper.eeq.admintroublefix.Edit.EditKerusakan;
import com.universedeveloper.eeq.admintroublefix.Insert.InputKerusakan;
import com.universedeveloper.eeq.admintroublefix.Kerusakan;
import com.universedeveloper.eeq.admintroublefix.R;
import com.universedeveloper.eeq.admintroublefix.Utility.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class KerusakanTerpilih extends AppCompatActivity {

    TextView atas,  txt_jenis_kerusakan, txt_detail, txt_solusi;
    ImageView image1;

    String id, judul, jenis_kerusakan, detail, solusi, gambar;

    Button btnEdit;
    Button btnHapus;

    JSONArray string_json = null;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String url_hapus = "http://universedeveloper.com/gudangandroid//ferdirockers/list_detail/hapus_kerusakan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerusakan_terpilih);

        id = getIntent().getStringExtra("id_kerusakan");
        judul = getIntent().getStringExtra("nama_kerusakan");
        jenis_kerusakan = getIntent().getStringExtra("jenis_kerusakan");
        detail = getIntent().getStringExtra("detail");
        solusi = getIntent().getStringExtra("solusi");
        gambar = getIntent().getStringExtra("gambar");

        atas = (TextView) findViewById(R.id.atas);
        txt_jenis_kerusakan = (TextView) findViewById(R.id.txt_jenis_kerusakan);
        txt_detail = (TextView) findViewById(R.id.txt_detail);
        txt_solusi = (TextView) findViewById(R.id.txt_solusi);
        image1 = (ImageView) findViewById(R.id.image1);

        atas.setText(judul);
        txt_jenis_kerusakan.setText(jenis_kerusakan);
        txt_detail.setText(detail);
        txt_solusi.setText(solusi);
        Glide.with(KerusakanTerpilih.this)
                .load(gambar)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(image1);



        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (KerusakanTerpilih.this, EditKerusakan.class);
                intent.putExtra("id_kerusakan", id);
                intent.putExtra("nama_kerusakan", atas.getText().toString());
                intent.putExtra("detail", txt_detail.getText().toString());
                intent.putExtra("solusi", txt_solusi.getText().toString());
                intent.putExtra("gambar", gambar);
                startActivity(intent);
            }
        });

        btnHapus = (Button) findViewById(R.id.btnHapus);
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(KerusakanTerpilih.this);

                builder.setTitle("Hapus Kerusakan");
                builder.setMessage("Apakah kamu ingin menghapus kerusakan dari daftar kerusakan ?");

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
            pDialog = new ProgressDialog(KerusakanTerpilih.this);
            pDialog.setMessage("Mohon Tunggu ... !");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        public String doInBackground(String... params) {

            try {

                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("id_kerusakan",id));

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

                    Toast.makeText(KerusakanTerpilih.this, "Hapus data berhasil", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();



                }
            });
        }
    }
}
