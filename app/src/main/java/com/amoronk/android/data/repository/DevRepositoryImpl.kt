package com.amoronk.android.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.amoronk.android.data.DevRemoteMediator
import com.amoronk.android.data.local.LocalDataSource
import com.amoronk.android.data.local.model.Devs
import com.amoronk.android.data.remote.ApiDataSource
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class DevRepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val localDataSource: LocalDataSource,
    ) : DevRepository {
    private val devDao = localDataSource.getDevDao()


    override fun getLagosDevs(): Flowable<PagingData<Devs.DevEntity>> {
        return Pager(
            PagingConfig(
                pageSize = 10,
                maxSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            remoteMediator = DevRemoteMediator(
                apiDataSource = apiDataSource,
                localDataSource = localDataSource
            ),
            pagingSourceFactory = { devDao.getAllRecentDevs() }
        ).flowable
    }

    override fun getDev(id: String): Single<Devs.DevEntity> =
        devDao.getDevData(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    override fun getFavDev(isFav: Boolean): Single<List<Devs.DevEntity>> =
        devDao.getFavDevs(isFav).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun updateDevDetails(dev: Devs.DevEntity) {
        devDao.updateDevData(dev).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onComplete() {
                }

                override fun onError(e: Throwable) {
                }

            })
    }


    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}