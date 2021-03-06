package com.example.thecocktail1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
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

public class Cocktail extends AppCompatActivity {

    TextView txtViewCocktail, strInstructions;
    ImageView imgView;
    RequestQueue requestQueue;

    private static final String url = "https://www.thecocktaildb.com/api/json/v1/1/random.php";
    private static final String urlImg = "https://www.thecocktaildb.com/images/media/drink/sih81u1504367097.jpg";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail);

        requestQueue = Volley.newRequestQueue(this);
        initui();
        jsonObjectRequest();
        imageRequest();
    }

    public void openActivityOrdinary(View view){
        Intent intent = new Intent(this, ordinaryDrink.class);
        startActivity(intent);
    }

    public void openActivityRandom(View view){
        Intent intent = new Intent(this, cocktailrandom.class);
        startActivity(intent);
    }

    private void initui(){

        txtViewCocktail = findViewById(R.id.txtViewCocktail);
        strInstructions = findViewById(R.id.strInstruction);
        imgView = findViewById(R.id.imgView);
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
                                String strInstruction = jsonObject.getString("strInstructions");
                                txtViewCocktail.append("Drink: "  + strDrink + " \n");
                                strInstructions.append("Instructions: " + strInstruction + " \n");
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
                urlImg,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imgView.setImageBitmap(response);
                    }
                },
                200,
                200,
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