package com.yymstaygold.lostandfound.client.util.retrofit;

import com.yymstaygold.lostandfound.client.entity.LoginResult;
import com.yymstaygold.lostandfound.client.entity.ResultWithoutData;
import com.yymstaygold.lostandfound.client.entity.UploadImageResult;
import com.yymstaygold.lostandfound.client.entity.UserInfo;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

public interface LFSApiService {
    @POST("upload_image")
    Observable<UploadImageResult> uploadImage(@Body RequestBody body);

    @GET("login")
    Observable<LoginResult> login(@Query("phoneNumber") String phoneNumber,
                                  @Query("channelId") String channelId);

    @POST("change_info")
    Observable<ResultWithoutData> changeInfo(@Body UserInfo userInfo);
}
