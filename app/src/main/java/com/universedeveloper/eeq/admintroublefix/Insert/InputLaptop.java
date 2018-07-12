package com.universedeveloper.eeq.admintroublefix.Insert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.universedeveloper.eeq.admintroublefix.R;
import com.universedeveloper.eeq.admintroublefix.Utility.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InputLaptop extends AppCompatActivity {
    EditText txt_serilaptop, txt_prosessor, txt_gpu;
    Button btn_pilihfoto, btn_simpan;
    ImageView image;
    Spinner spinner_merk1, spinner_ram1, spinner_hdd1, spinner_layar1;
    String spinner_merk, spinner_ram, spinner_hdd, spinner_layar;

    Bitmap bitmap, decoded;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100

    ProgressDialog pDialog;
    private static final String TAG = InputLaptop.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String url = "http://universedeveloper.com/gudangandroid/ferdirockers/input_laptop.php";  //directory php ning server
    String tag_json_obj = "json_obj_req";
    Intent i;
    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_laptop);

        txt_serilaptop = (EditText) findViewById(R.id.txt_serilaptop);
        txt_prosessor = (EditText) findViewById(R.id.txt_prosessor);
        txt_gpu = (EditText) findViewById(R.id.txt_gpu);
        btn_pilihfoto = (Button) findViewById(R.id.btn_pilihfoto);
        btn_simpan = (Button) findViewById(R.id.btn_simpan);
        image = (ImageView) findViewById(R.id.image);
        spinner_merk1 = (Spinner) findViewById(R.id.spinner_merk);
        spinner_ram1 = (Spinner) findViewById(R.id.spinner_ram);
        spinner_hdd1 = (Spinner) findViewById(R.id.spinner_hdd);
        spinner_layar1 = (Spinner) findViewById(R.id.spinner_layar);

        //untuk spinner_merk
        String[] merk = new String[] {
                "Pilih Merk","Apple","ASUS", "hp", "Lenovo", "Acer", "Samsung", "Toshiba", "Fujitsu", "Axioo"
        };
        ArrayAdapter adapter_laptop;
        adapter_laptop = new ArrayAdapter(this, android.R.layout.simple_spinner_item, merk);
        adapter_laptop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_merk1.setAdapter(adapter_laptop);
        spinner_merk = spinner_merk1.getSelectedItem().toString();
        //////////////////////////////////////

        //untuk spinner_ram
        String[] ram = new String[] {
                "Pilih RAM","512MB", "1GB", "2GB", "3GB", "4GB", "8GB", "16GB", "32GB", "64GB"
        };
        ArrayAdapter adapter_ram;
        adapter_ram = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ram);
        adapter_ram.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ram1.setAdapter(adapter_ram);
        spinner_ram = spinner_ram1.getSelectedItem().toString();
        //////////////////////////////////////

        //untuk spinner_hdd
        String[] hdd = new String[] {
                "Pilih Hard Disk","40GB", "80GB", "128GB", "128GB SSD","256GB", "256GB SSD", "320GB", "320GB SSD", "500GB", "500GB SSD", "750GB", "750GB SSD", "1TB", "1TB SSD", "2TB", "2TB SSD", "4TB", "8TB", "16TB"
        };
        ArrayAdapter adapter_hdd;
        adapter_hdd = new ArrayAdapter(this, android.R.layout.simple_spinner_item, hdd);
        adapter_hdd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_hdd1.setAdapter(adapter_hdd);
        spinner_hdd = spinner_hdd1.getSelectedItem().toString();
        //////////////////////////////////////

        //untuk spinner_layar
        String[] layar = new String[] {
                "Pilih Ukuran Layar","10 Inch", "12 Inch", "13 Inch", "14 Inch", "14,5 Inch", "15 Inch", "16 Inch", "17 Inch"
        };
        ArrayAdapter adapter_layar;
        adapter_layar = new ArrayAdapter(this, android.R.layout.simple_spinner_item, layar);
        adapter_layar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_layar1.setAdapter(adapter_layar);
        spinner_layar = spinner_layar1.getSelectedItem().toString();
        //////////////////////////////////////

        //ambil foto dari galery
        btn_pilihfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_merk = spinner_merk1.getSelectedItem().toString();
                spinner_ram = spinner_ram1.getSelectedItem().toString();
                spinner_hdd = spinner_hdd1.getSelectedItem().toString();
                spinner_layar = spinner_layar1.getSelectedItem().toString();

                if (spinner_merk == "Pilih Merk"){
                    Toast.makeText(InputLaptop.this, "Anda harus memilih Merk Laptop", Toast.LENGTH_SHORT).show();
                }

                else if (spinner_ram == "Pilih RAM"){
                    Toast.makeText(InputLaptop.this, "Anda harus memilih RAM", Toast.LENGTH_SHORT).show();
                }

                else if (spinner_hdd == "Pilih Hard Disk"){
                    Toast.makeText(InputLaptop.this, "Anda harus memilih Hard Disk", Toast.LENGTH_SHORT).show();
                }

                else if (spinner_layar == "Pilih Ukuran Layar"){
                    Toast.makeText(InputLaptop.this, "Anda harus memilih Ukuran Layar", Toast.LENGTH_SHORT).show();
                }
                else {
                    InputLaptop();
                }
            }
        });
    }

    private void InputLaptop() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Mengirim Pesan ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {  //jika berhasil maka :

                        Log.e("Successfully Register!", jObj.toString());
                        Toast.makeText(InputLaptop.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        txt_serilaptop.setText("");
                        txt_prosessor.setText("");
                        txt_gpu.setText("");
                        image.setImageResource(0);

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Mengirim Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String > getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("seri_laptop", txt_serilaptop.getText().toString());
                params.put("merk_laptop", spinner_merk);
                params.put("ram", spinner_ram);
                params.put("hdd", spinner_hdd);
                params.put("layar", spinner_layar);
                params.put("gpu", txt_gpu.getText().toString());
                params.put("prosessor", txt_prosessor.getText().toString());
                params.put("gambar", getStringImage(decoded));

                //kembali ke parameters
                Log.e(TAG, "" + params);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    //untuk upload image, compress .JPEG ke bitmap
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    //untuk memilih gambar
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 512));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        image.setImageBitmap(decoded);
    }

    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
