package bd.com.techjobs.androiddevelopment.dynamicspinnertestrnd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Spinner spnrcategory, spnrIndustry;
    Button buttontest;
    ArrayList<String> stringscategory;
    ArrayList<String> stringsindusty;

    ArrayAdapter<String> categoryadapter;
    ArrayAdapter<String> industryadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spnrcategory =(Spinner)findViewById(R.id.spnr_advancesearch_Category);
        spnrIndustry = (Spinner) findViewById(R.id.spnr_advancesearch_Industry);

        buttontest = (Button)findViewById(R.id.button);

        stringscategory = new java.util.ArrayList<>();
        stringsindusty = new java.util.ArrayList<>();

        stringscategory.add("Select Category");

        loaddata();
        categoryadapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, stringscategory);
        spnrcategory.setAdapter(categoryadapter);

        industryadapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, stringsindusty);
        spnrIndustry.setAdapter(industryadapter);

        buttontest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a= spnrcategory.getSelectedItemPosition();
                Toast.makeText(getApplicationContext(), a+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loaddata() {
		//your api link put in there
        String spnrdynamicURL = "http://example.com/your-link";



        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, spnrdynamicURL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String rescode = obj.getString("response");
                            JSONObject newobj = obj.getJSONObject("message");
                            JSONArray arr = newobj.getJSONArray("category");
                            if (arr.length() > 0) {
                                for (int i = 0; i < arr.length(); i++) {
                                    JSONObject arrobj = arr.getJSONObject(i);
                                    String categorytitle = arrobj.getString("title");
                                    stringscategory.add(categorytitle);
                                }
                            }
                            JSONArray arrindustry= newobj.getJSONArray("industries");
                            if (arrindustry.length() > 0) {
                                for (int i = 0; i < arrindustry.length(); i++) {
                                    JSONObject arrobjid = arrindustry.getJSONObject(i);
                                    String industytitle = arrobjid.getString("title");

                                    stringsindusty.add(industytitle);
                                }
                            }
                            categoryadapter.notifyDataSetChanged();
                            industryadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Internet Problem Occour", Toast.LENGTH_LONG).show();
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

}
