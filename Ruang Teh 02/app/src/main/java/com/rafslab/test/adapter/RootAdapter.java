package com.rafslab.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.rafslab.test.R;
import com.rafslab.test.models.Data;
import com.rafslab.test.models.Root;
import com.rafslab.test.models.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RootAdapter extends RecyclerView.Adapter<RootAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Root> rootList;
    private final LayoutInflater inflater;

    public RootAdapter(Context mContext, List<Root> rootList) {
        this.mContext = mContext;
        this.rootList = rootList;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Root root = rootList.get(position);
        String path = root.getTitle().replace(" ", "");
        if (root.getTitle().equals("Classic")) {
            holder.title.setVisibility(View.GONE);
        } else {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(root.getTitle());
        }
        getData(holder.childList, holder.progressBar, path);

    }
    private void getData(RecyclerView recyclerView, ProgressBar progressBar, String path){
        String URL = "https://raw.githubusercontent.com/raisalfs/parsejson/master/Test/" + path + ".json";
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        final List<Data> dataList = new ArrayList<>();
                        try {
                            for (int i = 0; i<response.length(); i++){
                                JSONObject object = response.getJSONObject(i);
                                Data data = new Data();
                                data.setTitle(object.getString("title"));
                                data.setPrice(object.getString("price"));
                                data.setImage(object.getString("image"));
                                if (object.getString("price").equals("-")) {
                                    List<Type> typeList = new ArrayList<>();
                                    JSONArray typeArray = object.getJSONArray("type");
                                    for (int i1 = 0; i1<typeArray.length(); i1++){
                                        JSONObject typeObject = typeArray.getJSONObject(i1);
                                        Type type = new Type();
                                        type.setValue(typeObject.getString("value"));
                                        type.setPrice(typeObject.getString("price"));
                                        typeList.add(type);
                                    }
                                    data.setTypeList(typeList);
                                }
                                dataList.add(data);
                                progressBar.setVisibility(View.GONE);
                                setDataListV(recyclerView, dataList);
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
    private void setDataListV(RecyclerView recyclerView, List<Data> dataList){
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new ChildAdapter(mContext, dataList));
    }
    @Override
    public int getItemCount() {
        return rootList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final RecyclerView childList;
        private final ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childList = itemView.findViewById(R.id.list);
            title = itemView.findViewById(R.id.title);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }
}
