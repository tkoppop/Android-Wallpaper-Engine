package com.example.wallpaperengine;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

class MainActivity extends AppCompatActivity {

    ListView listView;
    List<DataHandler> dataHandlerList;
    SwipeRefreshLayout swipeRefreshLayout;
    WallpaperAdapter wallpaperAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = findViewById(R.id.list);
        dataHandlerList = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.swipe);
        loadData("First");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData("Refresh");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title,thumbnail,image;
                title = dataHandlerList.get(position).getTitle();
                image = dataHandlerList.get(position).getImage();
                Intent intent = new Intent(getApplicationContext(), ViewWallpaper.class);
                intent.putExtra("title", title);
                intent.putExtra("image",image);
                startActivity(intent);
            }
        });
    }
    private void loadData(String type){
        swipeRefreshLayout.setRefreshing(true);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://wallpaper-engine-andriod.herokuapp.com/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshLayout.setRefreshing(false);
                        parseJSON(response, type);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
    private void parseJSON(String res, String type){
        String title, thumbnail, image;
        if(type.equals("Refresh")){
            dataHandlerList.clear();
            wallpaperAdapter.notifyDataSetChanged();
        }
        try{
            JSONArray jsonArray = new JSONArray(res);
            for(int i = 0 ; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                title = jsonObject.get("title").toString();
                thumbnail = jsonObject.get("thumbnail").toString();
                image = jsonObject.get("image").toString();
                dataHandlerList.add(new DataHandler(title, thumbnail, image));
            }
            wallpaperAdapter = new WallpaperAdapter(getApplicationContext(), R.layout.list_items,dataHandlerList);
            listView.setAdapter(wallpaperAdapter);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

}
