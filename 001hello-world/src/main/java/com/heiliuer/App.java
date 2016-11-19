package com.heiliuer;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/11/20 0020.
 */
public class App {


    public static void main(String[] args) {


        OkHttpClient client = getOkHttpClient();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubService githubService = retrofit.create(GithubService.class);

        Call<List<Repo>> reposCall = githubService.listRepos("heiliuer");
        try {
            Response<List<Repo>> response = reposCall.execute();
            List<Repo> repos = response.body();
            Call<List<Contributor>> contributorsCall = githubService.listContributors("heiliuer", repos.get(0).getName());
            Response<List<Contributor>> contributorsResponse = contributorsCall.execute();
            List<Contributor> contributors = contributorsResponse.body();
            System.out.println(contributors.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }
}
