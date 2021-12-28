package com.petr.userrepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubREST {
    @GET("/users/{user}")
    Call<GitHubRepo> getUser(@Path("user") String user);

    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> listRepos(@Path("user") String user);
}
