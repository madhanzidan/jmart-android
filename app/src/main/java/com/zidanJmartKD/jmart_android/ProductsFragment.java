package com.zidanJmartKD.jmart_android;

import static com.zidanJmartKD.jmart_android.FilterFragment.filterCategory;
import static com.zidanJmartKD.jmart_android.FilterFragment.filterName;
import static com.zidanJmartKD.jmart_android.FilterFragment.filtered;
import static com.zidanJmartKD.jmart_android.FilterFragment.highestPriceFilter;
import static com.zidanJmartKD.jmart_android.FilterFragment.lowestPriceFilter;
import static com.zidanJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.zidanJmartKD.R;
import com.zidanJmartKD.jmart_android.model.Product;
import com.zidanJmartKD.jmart_android.model.ProductCategory;
import com.zidanJmartKD.jmart_android.request.GetFilteredProductRequest;
import com.zidanJmartKD.jmart_android.request.GetProductRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class ProductsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    EditText pageNum;
    Button nextPageBtn, prevPageBtn, goBtn;

    public ProductsFragment() {}//Empty constructor sebagai syarat

    public static ProductsFragment newInstance(String param1) {
        ProductsFragment productsFragment = new ProductsFragment();
        Bundle b = new Bundle();
        b.putString(ARG_PARAM1, param1);
        productsFragment.setArguments(b);
        return productsFragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_products, container, false);
        ListView listItems = v.findViewById(R.id.listViewProducts);
        Gson gson = new Gson();

        nextPageBtn = (Button) v.findViewById(R.id.buttonNextPageProducts);
        prevPageBtn = (Button) v.findViewById(R.id.buttonPrevPageProducts);
        goBtn = (Button) v.findViewById(R.id.buttonGoPageProducts);
        pageNum = (EditText) v.findViewById(R.id.insertPageNumberProducts);

        //Request to GetProductRequest
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<Product> productsReturned = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Product product = gson.fromJson(obj.toString(), Product.class);
                        productsReturned.add(product);
                    }
                    ArrayAdapter<Product> allItemsAdapter = new ArrayAdapter<Product>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, productsReturned);
                    listItems.setAdapter(allItemsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "There is no product", Toast.LENGTH_SHORT).show();
            }
        };
        int pages = Integer.parseInt(pageNum.getText().toString()) - 1;
        //int pages = 0;

        GetProductRequest newGetProduct = new GetProductRequest(pages, listener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        queue.add(newGetProduct);

        //Button response
        prevPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = Integer.parseInt(pageNum.getText().toString());
                if(currentPage == 1) {
                    Toast.makeText(getContext(), "There is no previous page", Toast.LENGTH_SHORT).show();
                }
                else {
                    currentPage--;
                    pageNum.setText(String.valueOf(currentPage));
                }
            }
        });

        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = Integer.parseInt(pageNum.getText().toString());
                currentPage++;
                pageNum.setText(String.valueOf(currentPage));
            }
        });

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!filtered) {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                List<Product> productsReturned = new ArrayList<>();

                                for (int i = 9; i< jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Product product = gson.fromJson(obj.toString(), Product.class);
                                    productsReturned.add(product);
                                }
                                ArrayAdapter<Product> allItemsAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, productsReturned);
                                listItems.setAdapter(allItemsAdapter);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "Sorry something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    };
                    int pages = Integer.parseInt(pageNum.getText().toString());
                    GetProductRequest newGetProduct = new GetProductRequest(pages, listener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
                    queue.add(newGetProduct);
                } else {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                List<Product> productsReturned = new ArrayList<>();

                                for(int i = 0; i < jsonArray.length(); ++i) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Product product = gson.fromJson(obj.toString(), Product.class);
                                    productsReturned.add(product);
                                }
                                ArrayAdapter<Product> allItemsAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, productsReturned);
                                listItems.setAdapter(allItemsAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "Sorry, something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    };

                    int pages = Integer.parseInt(pageNum.getText().toString()) - 1;
                    String searchName = filterName;
                    int minPrice  = lowestPriceFilter;
                    int maxPrice = highestPriceFilter;
                    ProductCategory category = filterCategory;

                    GetFilteredProductRequest newFilteredProduct = new GetFilteredProductRequest(pages, getLoggedAccount().id, searchName, minPrice, maxPrice, category, listener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
                    queue.add(newFilteredProduct);
                }
            }
        });
        return v;
    }



    public void onResume() {
        super.onResume();
        Gson gson = new Gson();
        ListView listItems = getView().findViewById(R.id.listViewProducts);

        if (!filtered) {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        List<Product> productsReturned = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); ++i) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Product product = gson.fromJson(obj.toString(), Product.class);
                            productsReturned.add(product);
                        }

                        ArrayAdapter<Product> allItemsAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, productsReturned);
                        listItems.setAdapter(allItemsAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Sorry, something went wrong", Toast.LENGTH_SHORT).show();
                }
            };

            int pages = Integer.parseInt(pageNum.getText().toString()) - 1;
            GetProductRequest newGetProduct = new GetProductRequest(pages, listener, errorListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
            queue.add(newGetProduct);
        } else {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        List<Product> productsReturned = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Product product = gson.fromJson(obj.toString(), Product.class);
                            productsReturned.add(product);
                        }
                        ArrayAdapter<Product> allItemsAdapter = new ArrayAdapter<Product>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, productsReturned);
                        listItems.setAdapter(allItemsAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Sorry, something went wrong", Toast.LENGTH_SHORT).show();
                }
            };

            int pages = Integer.parseInt(pageNum.getText().toString()) - 1;
            String searchName = filterName;
            int minPrice = lowestPriceFilter;
            int maxPrice = highestPriceFilter;
            ProductCategory category = filterCategory;

            GetFilteredProductRequest newFilteredProduct = new GetFilteredProductRequest(pages, getLoggedAccount().id, searchName, minPrice, maxPrice, category, listener, errorListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
            queue.add(newFilteredProduct);
        }
    }
}