package com.zidanJmartKD.jmart_android;

import static com.zidanJmartKD.jmart_android.LoginActivity.getLoggedAccount;
import static com.zidanJmartKD.jmart_android.LoginActivity.setLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.zidanJmartKD.R;
import com.zidanJmartKD.jmart_android.model.Account;
import com.zidanJmartKD.jmart_android.model.Store;
import com.zidanJmartKD.jmart_android.request.AccountRequest;
import com.zidanJmartKD.jmart_android.request.RequestFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class AboutMeActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        this.setTitle("About Me");

        //On Account Details Card
        CardView accountDetailsCard = findViewById(R.id.accountDetailsCard);
        TextView nameAboutMe = findViewById(R.id.nameAboutMe);
        TextView emailAboutMe = findViewById(R.id.emailAboutMe);
        TextView balanceAboutMe = findViewById(R.id.balanceAboutMe);
        Button buttonRegisterStoreAboutMe = findViewById(R.id.buttonRegisterStoreAboutMe);
        Button buttonTopUpAboutMe = findViewById(R.id.buttonTopUpAboutMe);
        EditText inputTopUpAboutMe = findViewById(R.id.inputTopUpAmountAboutMe);

        //On Register Store Card
        CardView registerStoreCard = findViewById(R.id.registerStoreCard);
        Button buttonRegisterRegisterCard = findViewById(R.id.buttonRegisterRegisterCard);
        Button buttonCancelRegisterCard = findViewById(R.id.buttonCancelRegisterCard);
        EditText inputNameRegisterStoreCard = findViewById(R.id.inputNameRegisterStoreCard);
        EditText inputAddressRegisterStoreCard = findViewById(R.id.inputAddressRegisterStoreCard);
        EditText inputPhoneNumberRegisterStoreCard = findViewById(R.id.inputPhoneNumberRegisterStoreCard);

        //On Store Card
        CardView storeCard = findViewById(R.id.storeCard);
        TextView nameStoreCard = findViewById(R.id.nameStoreCard);
        TextView addressStoreCard = findViewById(R.id.addressStoreCard);
        TextView phoneNumberStoreCard = findViewById(R.id.phoneNumberStoreCard);

        //Mengambil informasi user dari account login
        nameAboutMe.setText(getLoggedAccount().name);
        emailAboutMe.setText(getLoggedAccount().email);
        balanceAboutMe.setText(String.valueOf(getLoggedAccount().balance));

        if(getLoggedAccount().store != null)
        {
            storeCard.setVisibility(View.VISIBLE);
            nameStoreCard.setText(getLoggedAccount().store.name);
            addressStoreCard.setText(getLoggedAccount().store.address);
            phoneNumberStoreCard.setText(getLoggedAccount().store.phoneNumber);
            buttonRegisterStoreAboutMe.setVisibility(View.GONE);
        }

        //Muncul card baru dari button Register Store
        buttonRegisterStoreAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStoreCard.setVisibility(View.VISIBLE);
                buttonRegisterStoreAboutMe.setVisibility(View.GONE);
            }
        });
        buttonCancelRegisterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStoreCard.setVisibility(View.INVISIBLE);
                buttonRegisterStoreAboutMe.setVisibility(View.VISIBLE);
            }
        });

        //Registrasi Store Baru
        buttonRegisterRegisterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputNameRegisterStoreCard.getText().toString().isEmpty() || inputAddressRegisterStoreCard.getText().toString().isEmpty()
                        || inputPhoneNumberRegisterStoreCard.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "All store information should be filled", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest newStoreRequest = AccountRequest.registerStore(
                            "account",
                            getLoggedAccount().id,
                            inputNameRegisterStoreCard.getText().toString(),
                            inputAddressRegisterStoreCard.getText().toString(),
                            inputPhoneNumberRegisterStoreCard.getText().toString(),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = new JSONObject(response);
                                        getLoggedAccount().store = gson.fromJson(jsonObject.toString(), Store.class);
                                        storeCard.setVisibility(View.VISIBLE);
                                        buttonRegisterStoreAboutMe.setVisibility(View.GONE);
                                        Toast.makeText(AboutMeActivity.this, "Store Registration Success", Toast.LENGTH_SHORT).show();
                                        nameStoreCard.setText(getLoggedAccount().store.name);
                                        addressStoreCard.setText(getLoggedAccount().store.address);
                                        phoneNumberStoreCard.setText(getLoggedAccount().store.phoneNumber);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Sorry, something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                    RequestQueue queue = Volley.newRequestQueue(AboutMeActivity.this);
                    queue.add(newStoreRequest);
                }
            }
        });

        //Isi Top Up
        buttonTopUpAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputTopUpAboutMe.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Amout of top up should be filled", Toast.LENGTH_SHORT).show();
                else {
                    StringRequest topUpRequest = AccountRequest.topUp(getLoggedAccount().id, Double.parseDouble(inputTopUpAboutMe.getText().toString()), new Response.Listener<String>() {
                        public void onResponse(String response) {
                            if (response.toLowerCase().equals("true"))
                                Toast.makeText(AboutMeActivity.this, "Top Up Success", Toast.LENGTH_SHORT).show();
                            StringRequest updateLoggedAccount = RequestFactory.getById(
                                    "account",
                                    getLoggedAccount().id,
                                    new Response.Listener<String>() {
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                Account account = gson.fromJson(jsonObject.toString(), Account.class);
                                                setLoggedAccount(account);
                                                balanceAboutMe.setText(String.valueOf(getLoggedAccount().balance));

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                        }
                                    });
                            RequestQueue queue = Volley.newRequestQueue(AboutMeActivity.this);
                            queue.add(updateLoggedAccount);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),
                                    "Sorry, something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                    RequestQueue queue = Volley.newRequestQueue(AboutMeActivity.this);
                    queue.add(topUpRequest);
                }
            }
        });


    }
}