package com.yymstaygold.lostandfound.client.util.retrofit;

import com.yymstaygold.lostandfound.client.entity.UploadImageResult;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

public interface LFSApiService {
    @POST("upload_image")
    Observable<UploadImageResult> uploadImage(@Body RequestBody body);
}
