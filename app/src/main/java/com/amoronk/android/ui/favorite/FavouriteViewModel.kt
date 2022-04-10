package com.amoronk.android.ui.favorite

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
class FavouriteViewModel @Inject constructor(private val repository: DevRepository) : ViewModel() {

    private val mDisposable = CompositeDisposable()

    private var _favDataResult: MutableLiveData<List<Devs.DevEntity>> = MutableLiveData()
    var favDataResult: LiveData<List<Devs.DevEntity>> = _favDataResult


    fun fetchFavDevs(fav: Boolean) {
        mDisposable.add(
            repository.getFavDev(fav)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _favDataResult.value = it
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