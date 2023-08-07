package com.arm.hetao.request

import android.util.Log
import androidx.lifecycle.MutableLiveData
import cn.leancloud.LCObject
import cn.leancloud.LCQuery
import com.arm.hetao.bean.BaseResponseBean
import com.arm.hetao.bean.ChannelItemBean
import com.arm.hetao.bean.DataContent
import com.arm.hetao.bean.LoginInfo
import com.arm.hetao.bean.MenuListAdapterBean
import com.arm.hetao.bean.MenuListBean
import com.arm.hetao.bean.MenuListItemBean
import com.arm.hetao.bean.UserInfoBean
import com.arm.hetao.bean.VersionBean
import com.arm.hetao.bean.VideoListBean
import com.arm.hetao.config.Config
import com.arm.hetao.utils.TimeUtils
import com.arm.hetao.utils.UserInfoManager
import com.arm.hetao.utils.fromJsonToAnyByMoshi
import com.arm.hetao.utils.fromJsonToListByMoshi
import com.safframework.http.interceptor.AndroidLoggingInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


/**
 *    author : heyueyang
 *    time   : 2023/01/16
 *    desc   :
 *    version: 1.0
 */
class Require {

    private var retrofitInstance: Retrofit? = null

    private val defValue = arrayListOf(
        "金色摇篮班2" to "http://tyy1.chenlongsoft.com/live/4de44ec2-d6f7-4495-bb8b-1d191501081b/index.m3u8",
        "科学发现室" to "http://tyy1.chenlongsoft.com/live/bd082b9b-3cea-4a93-aae1-1746d0f2f87f/index.m3u8",
        "户外" to "http://tyy1.chenlongsoft.com/live/6a00de6f-76a7-4e95-bcee-b51e6bb9b288/index.m3u8",
        "餐厅" to "http://tyy1.chenlongsoft.com/live/63f01df8-9c6c-4105-bcc6-b0054a3dbeab/index.m3u8",
        "编程教室" to "http://tyy1.chenlongsoft.com/live/0c90e898-c184-41c4-b6bd-cd9ffa92bd47/index.m3u8",
        "贝贝家" to "http://tyy1.chenlongsoft.com/live/1ba454fa-d4ef-44d2-a569-a9644f27e040/index.m3u8",
        "全脑开发" to "http://tyy1.chenlongsoft.com/live/46becf23-7e16-472f-9d31-81457c3d9a6d/index.m3u8",
    )

    private val objectId = "64bf8ea4aebece3efcacaf43"

    private fun getRetrofit(): Retrofit {
        if (retrofitInstance == null) {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(AndroidLoggingInterceptor.build())
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            retrofitInstance = Retrofit.Builder()
                .baseUrl("http://www.chenlongsoft.com:8091")
                .client(httpClient.build())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }
        return retrofitInstance!!
    }

    suspend fun getChannel(data: MutableLiveData<ArrayList<Pair<String, String>>>) {
        Log.e("TAG", "getChannel")
        val userInfo = UserInfoManager.getUserInfoBean()
        if (userInfo == null) {
            Log.e("TAG", "返回默认数据")
            data.postValue(defValue)
            return
        }
        val apiService = getRetrofit().create(Api::class.java)
        val responseBean =
            apiService.getChannel(
                userInfo.gc_id ?: "",
                userInfo.ouid ?: "",
                userInfo.s_id ?: ""
            ).execute()
        val body = responseBean.body()
        if (!responseBean.isSuccessful || body == null) {
            data.postValue(defValue)
            return
        }
        if ("EXCEPTION" == body.dataType) {
            //这里就说明账号信息失效，需要重新请求用户信息
            getUserInfo(true, data)
            return
        }
        if (body.dataContent?.length!! > 2) {
            val fromJson: List<ChannelItemBean> =
                fromJsonToListByMoshi(body.dataContent)
            val list = arrayListOf<Pair<String, String>>()
            fromJson.forEach {
                list.add(it.name to it.channel[0].url)
            }
            data.postValue(list)
        } else {
            data.postValue(defValue)
        }
    }

    suspend fun getUserInfo(
        isException: Boolean = false,
        data: MutableLiveData<ArrayList<Pair<String, String>>>? = null
    ): UserInfoBean? {
        var tempUserInfo = getUserInfoFromBmob()
        if (tempUserInfo == null || isException) {
            if (tempUserInfo == null) {
                Log.e("TAG", "从后端云没有获取到数据")
            } else {
                Log.e("TAG", "用户数据过期")
            }
            tempUserInfo = getUserInfo4Http()
        }
        if (tempUserInfo != null) {
            UserInfoManager.saveUserInfoBean(tempUserInfo)
        }
        if (tempUserInfo != null && isException && data != null) {
            getChannel(data)
        }
        return tempUserInfo
    }

    /**
     * 通过接口获取用户信息，并将获取到的用户数据存储到Bmob上
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getUserInfo4Http(
        phone: String = Config.DeviceInfo.Id,
        password: String = Config.DeviceInfo.Password
    ): UserInfoBean? = suspendCancellableCoroutine {
        getRetrofit().create(Api::class.java).getLoginInfo(
            Config.DeviceInfo.DeviceId,
            phone,
            password
        ).enqueue(object : Callback<BaseResponseBean<String>> {
            override fun onResponse(
                call: Call<BaseResponseBean<String>>,
                response: Response<BaseResponseBean<String>>
            ) {
                val body = response.body()
                if (body == null) {
                    it.resume(null, null)
                } else {
                    val info = fromJsonToAnyByMoshi<LoginInfo>(body.dataContent as String)
                    val userInfo = UserInfoBean(
                        info?.childlist?.get(0)?.gc_id ?: "",
                        info?.childlist?.get(0)?._nurseryid ?: "",
                        info?.s_id,
                        info?.childlist?.get(0)?.child_id ?: ""
                    )
                    UserInfoManager.saveUserInfoBean(userInfo)
                    updateObjectInfo(userInfo)
                    it.resume(userInfo, null)
                }
            }

            override fun onFailure(call: Call<BaseResponseBean<String>>, t: Throwable) {
                t.printStackTrace()
                it.resume(null, null)
            }
        })
    }

    private fun updateObjectInfo(userInfoBean: UserInfoBean) {
        Log.e("TAG", "开始更新远端数据")
        val lcObject = LCObject.createWithoutData("UserInfo", objectId)
        lcObject.put("gc_id", userInfoBean.gc_id)
        lcObject.put("ouid", userInfoBean.ouid)
        lcObject.put("s_id", userInfoBean.s_id)
        lcObject.put("child_id", userInfoBean.child_id)
        lcObject.saveInBackground().subscribe(object : io.reactivex.Observer<LCObject> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

            override fun onComplete() {

            }

            override fun onNext(t: LCObject) {
                Log.e("TAG", "更新成功")
            }

        })
    }


    /**
     * 获取在Bmob上存储的用户信息
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun getUserInfoFromBmob(): UserInfoBean? = suspendCancellableCoroutine {
        val lcQuery: LCQuery<*> = LCQuery<LCObject>("UserInfo")
        lcQuery.getInBackground(objectId).subscribe(object : io.reactivex.Observer<LCObject> {
            override fun onSubscribe(d: Disposable) {}
            override fun onNext(lcObject: LCObject) {
                if (lcObject.getString("s_id") == null) {
                    Log.e("TAG", "获取到的数据为空")
                    it.resume(null, null)
                } else {
                    Log.e("TAG", "返回的用户数据数据不为空")
                    it.resume(
                        UserInfoBean(
                            lcObject.getString("gc_id"),
                            lcObject.getString("ouid"),
                            lcObject.getString("s_id"),
                            lcObject.getString("child_id")
                        ), null
                    )
                }
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

            override fun onComplete() {}
        })
    }

    suspend fun getVideoList(page: Int): MutableList<VideoListBean>? {
        val userInfo = UserInfoManager.getUserInfoBean() ?: return null
        return suspendCancellableCoroutine<MutableList<VideoListBean>?> {
            Log.e("TAG", userInfo.child_id ?: "id是空的！！")
            getRetrofit().create(Api::class.java)
                .getVideoList(userInfo.child_id ?: "", 10, 0, userInfo.s_id ?: "", page)
                .enqueue(object : Callback<BaseResponseBean<List<DataContent>>> {
                    override fun onResponse(
                        call: Call<BaseResponseBean<List<DataContent>>>,
                        response: Response<BaseResponseBean<List<DataContent>>>
                    ) {
                        Log.e("TAG", "onResponse")
                        val body = response.body()
                        if (body == null) {
                            Log.e("TAG", "无数据")
                            it.resume(null, null)
                        } else {
                            Log.e("TAG", "有数据")
                            val dataList = body.dataContent
                            val list = mutableListOf<VideoListBean>()
                            dataList?.forEach { dataContent ->
                                var coverUrl = ""
                                var videoUrl = ""
                                if (dataContent.circle_resource.isNullOrEmpty()) {
                                    coverUrl = ""
                                    videoUrl = ""
                                } else {
                                    coverUrl =
                                        Config.Http.BASE_VIDEO_ULR + dataContent.circle_resource[0].resouce_img
                                    videoUrl =
                                        Config.Http.BASE_VIDEO_ULR + dataContent.circle_resource[0].resouce_url
                                }
                                val time = dataContent.exchange_sendtime?.let { sendTime ->
                                    TimeUtils.getFriendlyTimeSpanByNow(sendTime)
                                } ?: ""
                                val item = VideoListBean(
                                    content = dataContent.exchange_content ?: "",
                                    time = time,
                                    coverUrl = coverUrl,
                                    videoUrl = videoUrl
                                )
                                list.add(item)
                            }
                            Log.e("TAG", "解析完成")
                            it.resume(list, null)
                        }
                    }

                    override fun onFailure(
                        call: Call<BaseResponseBean<List<DataContent>>>,
                        t: Throwable
                    ) {
                        t.printStackTrace()
                        it.resume(null, null)
                    }
                })
        }
    }

    suspend fun getMenuList(date: String): MutableList<MenuListBean>? {
        val userInfoBean = UserInfoManager.getUserInfoBean() ?: return null
        return suspendCancellableCoroutine {
            getRetrofit().create(Api::class.java)
                .getMenuList(date, userInfoBean.ouid ?: "", userInfoBean.s_id ?: "")
                .enqueue(object : Callback<BaseResponseBean<List<MenuListBean>>> {
                    override fun onResponse(
                        call: Call<BaseResponseBean<List<MenuListBean>>>,
                        response: Response<BaseResponseBean<List<MenuListBean>>>
                    ) {
                        val body = response.body()
                        if (body == null) {
                            it.resume(null, null)
                        } else {
                            val list = mutableListOf<MenuListBean>()
                            body.dataContent?.forEach { menu ->
                                menu.cbImg = Config.Http.BASE_VIDEO_ULR + menu.cbImg
                                list.add(menu)
                            }
                            it.resume(list, null)
                        }
                    }

                    override fun onFailure(
                        call: Call<BaseResponseBean<List<MenuListBean>>>,
                        t: Throwable
                    ) {
                        t.printStackTrace()
                        it.resume(null, null)
                    }
                })
        }
    }

    suspend fun getMenuList2(startDateStr: String): MutableList<MenuListAdapterBean> {
        val dates = mutableListOf<String>()
        for (i in 0..20) {
            if (i == 0) {
                dates.add(startDateStr)
            } else {
                val newDate = TimeUtils.getPreviousDay(dates[i - 1]) ?: ""
                dates.add(newDate)
            }
        }
        val list = mutableListOf<MenuListAdapterBean>()
        dates.forEach { date ->
            withContext(Dispatchers.IO) {
                val sb = StringBuffer()
                val urls = mutableListOf<String>()
                val menuList = getMenuList(date)
                if (!menuList.isNullOrEmpty()) {
                    menuList.forEach {
                        sb.append(it.cbName + "\n")
                        urls.add(it.cbImg)
                    }
                    list.add(MenuListAdapterBean(date, MenuListItemBean(sb.toString(), urls)))
                }
            }
        }
        return list
    }

    suspend fun getNewVersion(): VersionBean? {
        return suspendCancellableCoroutine {
            getRetrofit().create(Api::class.java)
                .getNewVersion(
                    "285c614743cd04a463caa5f8f96ecf36",
                    "5b9642001395246f7d7626929599dc1a"
                )
                .enqueue(object : Callback<BaseResponseBean<VersionBean>> {
                    override fun onResponse(
                        call: Call<BaseResponseBean<VersionBean>>,
                        response: Response<BaseResponseBean<VersionBean>>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            it.resume(response.body()!!.data, null)
                        } else {
                            it.resume(null, null)
                        }
                    }

                    override fun onFailure(
                        call: Call<BaseResponseBean<VersionBean>>,
                        t: Throwable
                    ) {
                        t.printStackTrace()
                        it.resume(null, null)
                    }

                })
        }
    }


}