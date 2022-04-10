package com.amoronk.android.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amoronk.android.data.local.model.Devs
import com.amoronk.android.data.repository.DevRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: DevRepository) : ViewModel() {

    private val mDisposable = CompositeDisposable()
    private var _devDataResult: MutableLiveData<Devs.DevEntity> = MutableLiveData()
    var devDataResult: LiveData<Devs.DevEntity> = _devDataResult

    fun updateLagosDev(dev: Devs.DevEntity) {
        repository.updateDevDetails(dev)
    }

    fun fetchDevData( id:String){
        mDisposable.add(
            repository.getDev(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _devDataResult.value = it
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