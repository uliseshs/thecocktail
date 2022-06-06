package com.example.thecocktail1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ordinaryDrink extends AppCompatActivity {

    TextView textViewOrdinary, txtInstructions;
    ImageView imageView, imageView1, imageView2, imageView3, imageView4, imageView5;
    RequestQueue requestQueue;
    Layout ss;

    private final String url1 = "https://www.thecocktaildb.com/images/media/drink/ywxwqs1461867097.jpg";
    private static final String url = "https://www.thecocktaildb.com/api/json/v1/1/random.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary_drink);

        requestQueue = Volley.newRequestQueue(this);
        initui();
        jsonObjectRequest();
        imageRequest();

    }


    private void initui(){
        textViewOrdinary = findViewById(R.id.txtViewOrdinary);
        txtInstructions = findViewById(R.id.txtInstructions);
        imageView = findViewById(R.id.imgView);




    }

    private void jsonObjectRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("drinks");
                            int size = jsonArray.length();
                            for (int i=0; i<size; i++){
                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                                String strDrink = jsonObject.getString("strDrink");
                                String strInstructions = jsonObject.getString("strInstructions");
                                textViewOrdinary.append("Drink: " + strDrink + "\n");
                                txtInstructions.append("Instructions: " + strInstructions + "\n");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        requestQueue.add(jsonObjectRequest);
    }


    public void imageRequest(){
        ImageRequest imageRequest = new ImageRequest(
                url1,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                },
                200,
                90,
                null,
                null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(imageRequest);
    }


}