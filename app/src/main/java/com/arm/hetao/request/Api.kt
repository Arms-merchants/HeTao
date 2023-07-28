package com.arm.hetao.request

import com.arm.hetao.bean.BaseResponseBean
import com.arm.hetao.bean.DataContent
import com.arm.hetao.bean.MenuListBean
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *    author : heyueyang
 *    time   : 2022/06/20
 *    desc   :
 *    version: 1.0
 */
interface Api {

    @FormUrlEncoded
    @POST("http://www.chenlongsoft.com:8091/rest/session/video/channel")
    fun getChannel(
        @Field("gc_id") gc_id: String,
        @Field("ouid") ouid: String,
        @Field("s_id") s_id: String
    ): Call<BaseResponseBean<String>>

    @FormUrlEncoded
    @POST("http://www.chenlongsoft.com:8091/rest/session/login/android")
    fun getLoginInfo(
        @Field("device_id") device_id: String,
        @Field("id") id: String,
        @Field("password") password: String
    ): Call<BaseResponseBean<String>>

    @FormUrlEncoded
    @POST("http://www.chenlongsoft.com:8091/rest/circle/query/exchangelist4")
    fun getVideoList(
        @Field("child_id") child_id: String,
        @Field("count") count: Int,
        @Field("newTime") newTime: Int,
        @Field("s_id") s_id: String,
        @Field("startpage") startpage: Int
    ): Call<BaseResponseBean<List<DataContent>>>

    @FormUrlEncoded
    @POST("http://www.chenlongsoft.com:8091/rest/food/query/day/maa")
    fun getMenuList(
        @Field("date")date:String,
        @Field("ouId")ouId:String,
        @Field("sid")sid:String,

    ):Call<BaseResponseBean<List<MenuListBean>>>


}