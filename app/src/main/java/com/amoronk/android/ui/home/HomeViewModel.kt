package com.amoronk.android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.amoronk.android.data.local.model.Devs
import com.amoronk.android.data.repository.DevRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DevRepository) : ViewModel() {

    private val mDisposable = CompositeDisposable()

    private var _pagingDataResult:MutableLiveData<PagingData<Devs.DevEntity>> = MutableLiveData()
    var pagingDataResult:LiveData<PagingData<Devs.DevEntity>> = _pagingDataResult



    fun fetchDevs(){
        mDisposable.add(
            repository.getLagosDevs()
                .cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _pagingDataResult.value = it
                    },
                    {
                        //error
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        mDisposable.dispose()
    }


}