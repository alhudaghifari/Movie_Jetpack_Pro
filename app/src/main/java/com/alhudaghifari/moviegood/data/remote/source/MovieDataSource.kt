package com.alhudaghifari.moviegood.data.remote.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alhudaghifari.moviegood.api.MovieService
import com.alhudaghifari.moviegood.data.remote.MovieDetailResponse
import com.alhudaghifari.moviegood.data.remote.MovieItem
import com.alhudaghifari.moviegood.data.remote.MovieResponse
import com.alhudaghifari.moviegood.vo.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.random.Random

class MovieDataSource @Inject constructor(
    private val service: MovieService
) {
    fun getNowPlaying() : LiveData<Resource<MovieResponse>> {
        val data = MutableLiveData<Resource<MovieResponse>>()
        data.postValue(Resource.loading(null))
        service.getNowPlaying().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message() ?: "Error happen",null))
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message.toString(),null))
            }
        })
        return data
    }

    fun getPopularMovies(currentIdMovie: Int): LiveData<Resource<List<MovieItem>>> {
        val dataLive = MutableLiveData<Resource<List<MovieItem>>>()
        dataLive.postValue(Resource.loading(null))
        service.getPopularMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data?.results != null) {
                        val list = ArrayList<MovieItem>()
                        val map = HashMap<Int, Int>()
                        val sizeList = data.results.size
                        var randomInt = Random.nextInt(0, sizeList)
                        for (i in 1..5) {
                            if (map.containsKey(randomInt) || data.results[randomInt].id == currentIdMovie) {
                                while (map.containsKey(randomInt) || data.results[randomInt].id == currentIdMovie) {
                                    randomInt = Random.nextInt(0, sizeList)
                                }
                            }
                            map[randomInt] = randomInt
                            list.add(data.results[randomInt])
                        }
                        dataLive.postValue(Resource.success(list))
                    }
                } else {
                    dataLive.postValue(Resource.error(response.message() ?: "Error happen",null))
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                dataLive.postValue(Resource.error(t.message.toString(),null))
            }
        })
        return dataLive
    }

    fun getDetailMovie(idMovie: String): LiveData<Resource<MovieDetailResponse>> {
        val data = MutableLiveData<Resource<MovieDetailResponse>>()
        data.postValue(Resource.loading(null))
        service.getDetailMovie(idMovie).enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                if (response.isSuccessful) {
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message() ?: "Error happen",null))
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message.toString(),null))
            }
        })
        return data
    }
}