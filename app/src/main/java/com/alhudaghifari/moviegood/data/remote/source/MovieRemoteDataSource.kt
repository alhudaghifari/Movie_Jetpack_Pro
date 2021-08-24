package com.alhudaghifari.moviegood.data.remote.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alhudaghifari.moviegood.api.MovieService
import com.alhudaghifari.moviegood.data.remote.ApiResponse
import com.alhudaghifari.moviegood.data.remote.model.MovieDetailResponse
import com.alhudaghifari.moviegood.data.remote.model.MovieItem
import com.alhudaghifari.moviegood.data.remote.model.MovieResponse
import com.alhudaghifari.moviegood.utils.EspressoIdlingResource
import com.alhudaghifari.moviegood.vo.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.random.Random

class MovieRemoteDataSource @Inject constructor(
    private val service: MovieService
) {
    fun getNowPlaying() : LiveData<ApiResponse<List<MovieItem>>> {
        val data = MutableLiveData<ApiResponse<List<MovieItem>>>()
        EspressoIdlingResource.increment()
        service.getNowPlaying().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        val listMovie = result.results
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

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                data.postValue(ApiResponse.error(t.message.toString(), mutableListOf()))
                EspressoIdlingResource.decrement()
            }
        })
        return data
    }

    fun getPopularMovies(currentIdMovie: Int): LiveData<Resource<List<MovieItem>>> {
        val dataLive = MutableLiveData<Resource<List<MovieItem>>>()
        EspressoIdlingResource.increment()
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
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                dataLive.postValue(Resource.error(t.message.toString(),null))
                EspressoIdlingResource.decrement()
            }
        })
        return dataLive
    }

    fun getDetailMovie(idMovie: String): LiveData<ApiResponse<MovieDetailResponse>> {
        val data = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        EspressoIdlingResource.increment()
        service.getDetailMovie(idMovie).enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        data.postValue(ApiResponse.success(result))
                    } else {
                        data.postValue(ApiResponse.empty(response.message() ?: "Error Happen a", MovieDetailResponse()))
                    }
                } else {
                    data.postValue(ApiResponse.error(response.message() ?: "Error Happen b", MovieDetailResponse()))
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                data.postValue(ApiResponse.error(t.message.toString(), MovieDetailResponse()))
                EspressoIdlingResource.decrement()
            }
        })
        return data
    }

}