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
import com.bumptech.glide.Glide;
import com.universedeveloper.eeq.admintroublefix.Detail.KerusakanTerpilih;
import com.universedeveloper.eeq.admintroublefix.R;
import com.universedeveloper.eeq.admintroublefix.Utility.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InputKerusakan extends AppCompatActivity {
    EditText txt_namakerusakan, txt_keterangan, txt_solusi;
    Button btn_pilihfoto, btn_simpan;
    ImageView image;
    Spinner spinner1;

    String spinner;

    Bitmap bitmap, decoded;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100

    ProgressDialog pDialog;
    private static final String TAG = InputKerusakan.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String url = "http://universedeveloper.com/gudangandroid/ferdirockers/input_kerusakan.php";  //directory php ning server
    String tag_json_obj = "json_obj_req";
    Intent i;
    int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_kerusakan);

        txt_namakerusakan = (EditText) findViewById(R.id.txt_namakerusakan);
        txt_keterangan = (EditText) findViewById(R.id.txt_keterangan);
        txt_solusi = (EditText) findViewById(R.id.txt_solusi);
        btn_pilihfoto = (Button) findViewById(R.id.btn_pilihfoto);
        btn_simpan = (Button) findViewById(R.id.btn_simpan);
        image = (ImageView) findViewById(R.id.image);
        spinner1 = (Spinner) findViewById(R.id.spinner);

        //untuk spinner
        String[] kategori = new String[] {
                "Pilih kerusakan","Ringan", "Sedang", "Parah"
        };
        ArrayAdapter adapter_kerusakan;
        adapter_kerusakan = new ArrayAdapter(this, android.R.layout.simple_spinner_item, kategori);
        adapter_kerusakan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter_kerusakan);
        spinner = spinner1.getSelectedItem().toString();
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
                spinner = spinner1.getSelectedItem().toString();

                if (spinner == "Pilih Kerusakan"){
                    Toast.makeText(InputKerusakan.this, "Anda harus memilih tingkat kerusakan", Toast.LENGTH_SHORT).show();
                }else {
                    InputKerusakan();
                }
            }
        });
    }

    private void InputKerusakan() {
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
                        Toast.makeText(InputKerusakan.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        txt_namakerusakan.setText("");
                        txt_keterangan.setText("");
                        txt_solusi.setText("");
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
                params.put("nama_kerusakan", txt_namakerusakan.getText().toString());
                params.put("jenis_kerusakan", spinner);
                params.put("detail", txt_keterangan.getText().toString());
                params.put("solusi", txt_solusi.getText().toString());
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
