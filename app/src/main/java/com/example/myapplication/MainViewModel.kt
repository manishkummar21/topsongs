package com.example.myapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.entities.SongWithImages
import com.example.myapplication.helper.ResultWrapper
import com.example.myapplication.model.FeedResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.function.Predicate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {

    val clickedItem: MutableLiveData<SongWithImages> = MutableLiveData()

    fun getTopSongs(): MutableLiveData<ResultWrapper<List<SongWithImages>>> {
        val response = MutableLiveData<ResultWrapper<List<SongWithImages>>>()
        viewModelScope.launch {
            response.postValue(ResultWrapper.Loading)
            flowOf(mainRepo.getTopDBSongs(), mainRepo.getTopOnlineSongs())
                .flattenConcat()
                .take(1)
                .catch { e ->
                    response.postValue(ResultWrapper.Error(e.message ?: "Something Went Wrong"))
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    response.postValue(ResultWrapper.Success(it))

                }
        }
        return response
    }
}