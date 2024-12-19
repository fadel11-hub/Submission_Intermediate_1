package com.dicoding.picodiploma.loginwithanimation.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.response.main.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.response.main.StoriesResponse
import com.dicoding.picodiploma.loginwithanimation.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _listStories = MutableLiveData<List<ListStoryItem>?>()
    val listStories: LiveData<List<ListStoryItem>?> get() = _listStories

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getAllStories(authorization: String) {
        ApiConfig.apiInstant
            .getAllStories(authorization)
            .enqueue(object : Callback<StoriesResponse> {
                override fun onResponse(
                    call: Call<StoriesResponse>,
                    response: Response<StoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        _listStories.postValue(response.body()?.listStory.orEmpty())
                    } else {
                        _listStories.postValue(emptyList())
                    }
                }

                override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                    _listStories.postValue(emptyList())
                }
            })
    }
}
