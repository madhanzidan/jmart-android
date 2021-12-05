package com.zidanJmartKD.jmart_android;

import static com.zidanJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zidanJmartKD.R;
import com.zidanJmartKD.jmart_android.model.ProductCategory;
import com.zidanJmartKD.jmart_android.model.Shipment;
import com.zidanJmartKD.jmart_android.request.CreateProductRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateProductActivity extends AppCompatActivity {
    boolean conditionUsed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        Button buttonCancelCreateProduct = findViewById(R.id.buttonCancelCreateProduct);
        Button buttonCreateCreateProduct = findViewById(R.id.buttonCreateCreateProduct);
        EditText inputNameCreateProduct = findViewById(R.id.inputNameCreateProduct);
        EditText inputWeightCreateProduct = findViewById(R.id.inputWeightCreateProduct);
        EditText inputPriceCreateProduct = findViewById(R.id.inputPriceCreateProduct);
        EditText inputDiscountCreateProduct = findViewById(R.id.inputDiscountCreateProduct);
        RadioGroup radioGroup = findViewById(R.id.radioGroupConditionCreateProduct);
        Spinner spinnerCategoryCreateProduct = findViewById(R.id.spinnerCategoryCreateProduct);
        Spinner spinnerShipmentCreateProduct = findViewById(R.id.spinnerShipmentPlanCreateProduct);

        //Button cancel untuk kembali ke MainMenuActivity
        buttonCancelCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainMenuPage = new Intent(CreateProductActivity.this, MainActivity.class);
                startActivity(toMainMenuPage);
            }
        });

        //Meng-create product
        buttonCreateCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null)
                                Toast.makeText(getApplicationContext(), "Your product successfully created", Toast.LENGTH_SHORT).show();
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Create Product Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Sorry, something went wrong", Toast.LENGTH_SHORT).show();
                    }
                };
                if(inputNameCreateProduct.getText().toString().isEmpty() || inputWeightCreateProduct.getText().toString().isEmpty() ||
                    inputPriceCreateProduct.getText().toString().isEmpty() || inputDiscountCreateProduct.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "All fields should be filled", Toast.LENGTH_SHORT).show();
                else {
                    String productName = inputNameCreateProduct.getText().toString();
                    int productWeight = Integer.parseInt(inputWeightCreateProduct.getText().toString());
                    double productPrice = Double.parseDouble(inputPriceCreateProduct.getText().toString());
                    double productDiscount = Double.parseDouble(inputDiscountCreateProduct.getText().toString());
                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            switch (checkedId) {
                                case R.id.radioButtonNewCreateProduct:
                                    conditionUsed = false;
                                    break;
                                case R.id.radioButtonUsedCreateProduct:
                                    conditionUsed = true;
                                    break;
                            }
                        }
                    });
                    ProductCategory productCategory = ProductCategory.valueOf(spinnerCategoryCreateProduct.getSelectedItem().toString());

                    byte productShipment = 1 << 0;
                    switch (spinnerShipmentCreateProduct.getSelectedItem().toString()) {
                        case "INSTANT":
                            productShipment = 1 << 0;
                            break;
                        case "SAME DAY":
                            productShipment = 1 << 1;
                            break;
                        case "NEXT DAY":
                            productShipment = 1 << 2;
                            break;
                        case "REGULER":
                            productShipment = 1 << 3;
                            break;
                        case "KARGO":
                            productShipment = 1 << 4;
                            break;
                    }
                    CreateProductRequest createProduct = new CreateProductRequest(productName, productWeight, conditionUsed, productPrice, productDiscount, productCategory, productShipment, listener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(CreateProductActivity.this);
                    queue.add(createProduct);

                    Intent toMainMenuPage = new Intent(CreateProductActivity.this, MainActivity.class);
                    startActivity(toMainMenuPage);
                }
            }
        });
    }
}