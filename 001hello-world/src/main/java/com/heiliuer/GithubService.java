package com.heiliuer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by Administrator on 2016/11/20 0020.
 */
public interface GithubService {

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);


    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> listContributors(@Path("owner") String owner,
                                             @Path("repo") String repo);


}
