package com.petr.userrepo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.widget.ListView;
import android.widget.TextView;

//import com.petr.userrepo.R;

public class GitHubUserRepos extends AppCompatActivity {

    TextView userName;
    TextView textInfo;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repo_activity);

        Intent intent = getIntent();
        String user = intent.getStringExtra("EXTRA_TEXT");

        userName = (TextView) findViewById(R.id.user_name);
        textInfo = (TextView) findViewById(R.id.text_info);
        listView = (ListView) findViewById(R.id.list_item);

        userName.setText(user);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        GitHubREST client = retrofit.create(GitHubREST.class);
        Call<List<GitHubRepo>> repo = client.listRepos(user);

        repo.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> repo, Response<List<GitHubRepo>> response) {
                List<GitHubRepo> repos = response.body();
                GitHubRepoAdapter adapter = new GitHubRepoAdapter(GitHubUserRepos.this, -1, repos);
                listView.setAdapter(adapter);

                if(adapter.getCount() == 0) {
                    textInfo.setText("У данного пользователя нет публичных репозиториев");
                }
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Toast.makeText(GitHubUserRepos.this,"Ошибка!",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }
}
