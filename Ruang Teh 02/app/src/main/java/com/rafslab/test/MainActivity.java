package com.rafslab.test;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.rafslab.test.adapter.ChildAdapter;
import com.rafslab.test.adapter.RootAdapter;
import com.rafslab.test.models.Data;
import com.rafslab.test.models.Root;
import com.rafslab.test.models.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list_item);
        TextView title = findViewById(R.id.title);
        TextView titleSecondary = findViewById(R.id.title_secondary);
        Toolbar toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        String titleText = "Teh Tarik Ray";
        String secondary = "Ruang teh 02. Lintas Timur";
        title.setText(titleText);
        titleSecondary.setText(secondary);
        getRootData();
    }
    private void getRootData(){
        String URL = "https://raw.githubusercontent.com/raisalfs/parsejson/master/Test.json";
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        final List<Root> rootList = new ArrayList<>();
                        try {
                            for (int i = 0; i< response.length(); i++){
                                JSONObject object = response.getJSONObject(i);
                                Root data = new Root();
                                data.setTitle(object.getString("title"));
                                rootList.add(data);
                                progressBar.setVisibility(View.GONE);
                                setDataList(recyclerView, rootList);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
    private void setDataList(RecyclerView recyclerView, List<Root> rootList){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RootAdapter(this, rootList));
    }
}