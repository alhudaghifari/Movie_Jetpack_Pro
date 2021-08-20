package com.alhudaghifari.moviegood.data.remote.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alhudaghifari.moviegood.api.TvService
import com.alhudaghifari.moviegood.data.TvDataInterface
import com.alhudaghifari.moviegood.data.remote.TvDetailResponse
import com.alhudaghifari.moviegood.data.remote.TvItem
import com.alhudaghifari.moviegood.data.remote.TvResponse
import com.alhudaghifari.moviegood.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.random.Random

class TvDataSource @Inject constructor(
    private val service: TvService
) : TvDataInterface {
    override fun getOnTheAir(): LiveData<Resource<TvResponse>> {
        val data = MutableLiveData<Resource<TvResponse>>()
        data.postValue(Resource.loading(null))

        service.getOnTheAir().enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                if (response.isSuccessful) {
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message() ?: "Error happen",null))
                }
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message.toString(),null))
            }

        })
        return data
    }

    override fun getPopularTv(currentIdMovie: Int): LiveData<Resource<List<TvItem>>> {
        val dataLive = MutableLiveData<Resource<List<TvItem>>>()
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
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                dataLive.postValue(Resource.error(t.message.toString(),null))
            }

        })
        return dataLive
    }

    override fun getDetailTv(idTv: String): LiveData<Resource<TvDetailResponse>> {
        val data = MutableLiveData<Resource<TvDetailResponse>>()
        data.postValue(Resource.loading(null))
        service.getDetailTv(idTv).enqueue(object : Callback<TvDetailResponse> {
            override fun onResponse(call: Call<TvDetailResponse>, response: Response<TvDetailResponse>) {
                if (response.isSuccessful) {
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message() ?: "Error happen",null))
                }
            }

            override fun onFailure(call: Call<TvDetailResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message.toString(),null))
            }

        })
        return data
    }
}