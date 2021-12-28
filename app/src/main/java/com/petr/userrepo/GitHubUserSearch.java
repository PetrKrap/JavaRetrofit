package com.petr.userrepo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import com.petr.javaretrofit.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubUserSearch extends AppCompatActivity {

    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        username = (EditText) findViewById(R.id.username);
    }

    public void getUserRepos(View view) {
        String user = username.getText().toString();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        GitHubREST client = retrofit.create(GitHubREST.class);
        Call<GitHubRepo> userExists = client.getUser(user);
        Call<List<GitHubRepo>> repo = client.listRepos(user);

        userExists.enqueue(new Callback<GitHubRepo>() {
            @Override
            public void onResponse(Call<GitHubRepo> userExists, Response<GitHubRepo> response) {
                if (response.code() == 200) {
                    // Toast.makeText(MainActivity.this, "Пользователь " + user + " найден", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GitHubUserSearch.this, GitHubUserRepos.class);
                    intent.putExtra("EXTRA_TEXT", user);
                    startActivity(intent);
                } else {
                    Toast.makeText(GitHubUserSearch.this, "Пользователь " + user + " не найден", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GitHubRepo> call, Throwable t) {
                Toast.makeText(GitHubUserSearch.this,"Ошибка!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
