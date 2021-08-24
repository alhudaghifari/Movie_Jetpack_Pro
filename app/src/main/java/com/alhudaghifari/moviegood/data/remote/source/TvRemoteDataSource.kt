package com.alhudaghifari.moviegood.data.remote.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alhudaghifari.moviegood.api.TvService
import com.alhudaghifari.moviegood.data.remote.ApiResponse
import com.alhudaghifari.moviegood.data.remote.model.TvDetailResponse
import com.alhudaghifari.moviegood.data.remote.model.TvItem
import com.alhudaghifari.moviegood.data.remote.model.TvResponse
import com.alhudaghifari.moviegood.utils.EspressoIdlingResource
import com.alhudaghifari.moviegood.vo.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.random.Random

class TvRemoteDataSource @Inject constructor(
    private val service: TvService
) {
    fun getOnTheAir(): LiveData<ApiResponse<List<TvItem>>> {
        val data = MutableLiveData<ApiResponse<List<TvItem>>>()
        EspressoIdlingResource.increment()
        service.getOnTheAir().enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        val listMovie = result.tvItems
                        if (listMovie != null)
                            data.postValue(ApiResponse.success(listMovie))
                        else
                            data.postValue(ApiResponse.empty(response.message() ?: "Error Happen a", mutableListOf()))
                    } else {
                        data.postValue(ApiResponse.empty(response.message() ?: "Error Happen b", mutableListOf()))
                    }
                } else {
                    data.postValue(ApiResponse.error(response.message() ?: "Error Happen c", mutableListOf()))
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                data.postValue(ApiResponse.error(t.message.toString(), mutableListOf()))
                EspressoIdlingResource.decrement()
            }
        })
        return data
    }

    fun getPopularTv(currentIdMovie: Int): LiveData<Resource<List<TvItem>>> {
        val dataLive = MutableLiveData<Resource<List<TvItem>>>()
        EspressoIdlingResource.increment()
        dataLive.postValue(Resource.loading(null))
        service.getPopular().enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data?.tvItems != null) {
                        val list = ArrayList<TvItem>()
                        val map = HashMap<Int, Int>()
                        val sizeList = data.tvItems.size
                        var randomInt = Random.nextInt(0, sizeList)
                        for (i in 1..5) {
                            if (map.containsKey(randomInt) || data.tvItems[randomInt].id == currentIdMovie) {
                                while (map.containsKey(randomInt) || data.tvItems[randomInt].id == currentIdMovie) {
                                    randomInt = Random.nextInt(0, sizeList)
                                }
                            }
                            map[randomInt] = randomInt
                            list.add(data.tvItems[randomInt])
                        }
                        dataLive.postValue(Resource.success(list))
                    }
                } else {
                    dataLive.postValue(Resource.error(response.message() ?: "Error happen",null))
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                dataLive.postValue(Resource.error(t.message.toString(),null))
                EspressoIdlingResource.decrement()
            }

        })
        return dataLive
    }

    fun getDetailTv(idTv: String): LiveData<ApiResponse<TvDetailResponse>> {
        val data = MutableLiveData<ApiResponse<TvDetailResponse>>()
        EspressoIdlingResource.increment()
        service.getDetailTv(idTv).enqueue(object : Callback<TvDetailResponse> {
            override fun onResponse(call: Call<TvDetailResponse>, response: Response<TvDetailResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        data.postValue(ApiResponse.success(result))
                    } else {
                        data.postValue(ApiResponse.empty(response.message() ?: "Error Happen a", TvDetailResponse()))
                    }
                } else {
                    data.postValue(ApiResponse.error(response.message() ?: "Error Happen b", TvDetailResponse()))
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvDetailResponse>, t: Throwable) {
                data.postValue(ApiResponse.error(t.message.toString(), TvDetailResponse()))
                EspressoIdlingResource.decrement()
            }
        })
        return data
    }
}