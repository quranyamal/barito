package org.tangaya.newsappretrofit.view.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.tangaya.newsappretrofit.R;
import org.tangaya.newsappretrofit.adapter.ArticleAdapter;
import org.tangaya.newsappretrofit.data.model.APIResponse;
import org.tangaya.newsappretrofit.data.source.NewsService;
import org.tangaya.newsappretrofit.data.source.RESTClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArticleAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String searchKeyword = getIntent().getExtras().getString("keyword");
        setTitle("Cari Berita: " + searchKeyword);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("mengambil data artikel...");
        progressDialog.show();

        NewsService service = RESTClient.getInstance().create(NewsService.class);

        //Call<APIResponse> articlesCall = service.getHeadlineNews();

        Call<APIResponse> articlesCall = service.getNewsByKeyword(searchKeyword, NewsService.API_KEY);
        articlesCall.enqueue(new Callback<APIResponse>() {

            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                Log.d("enqueue", "onResponse. response: " + response);

                progressDialog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Ups, terdapat kesalahan :(", Toast.LENGTH_SHORT).show();

                Log.d("onFailure call", call.toString());
                Log.d("onFailure throwable", t.toString());
            }
        });
    }

    private void generateDataList(APIResponse apiResponse) {

        Log.d("MainActivity", "articleList size: " + apiResponse.getArticles().size());
        recyclerView = findViewById(R.id.news_recycler);

        adapter = new ArticleAdapter(this, apiResponse.getArticles());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
