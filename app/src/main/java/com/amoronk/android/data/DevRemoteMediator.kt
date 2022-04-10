package com.amoronk.android.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.amoronk.android.data.local.LocalDataSource
import com.amoronk.android.data.local.model.Devs
import com.amoronk.android.data.remote.ApiDataSource
import com.amoronk.android.data.remote.mappers.mapDevRemoteModeltoDevModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException

@ExperimentalPagingApi
class DevRemoteMediator(
    private val apiDataSource: ApiDataSource,
    private val localDataSource: LocalDataSource
) :
    RxRemoteMediator<Int, Devs.DevEntity>() {

    private val dao = localDataSource.getDevDao()
    private val devRemoteKeysDao = localDataSource.getRemoteKeysDao()

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, Devs.DevEntity>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)

                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.prevKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.nextKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    apiDataSource.getLagdevs(
                        page = page,
                        pageSize = NETWORK_PAGE_SIZE
                    )
                        .map { mapDevRemoteModeltoDevModel(it) }
                        .map { insertToDb(page, loadType, it) }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.endOfPage) }
                        .onErrorReturn { MediatorResult.Error(it) }
                }

            }
            .onErrorReturn { MediatorResult.Error(it) }
    }

    @Suppress("DEPRECATION")
    private fun insertToDb(page: Int, loadType: LoadType, data: Devs): Devs {

        localDataSource.getDbService().beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
//            devRemoteKeysDao.clearRemoteKeys()
//            dao.clearDevs()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data.endOfPage) null else page + 1
            val keys = data.devs.map {
                Devs.DevRemoteKeys(devId = it.devId, prevKey = prevKey, nextKey = nextKey)
            }
            devRemoteKeysDao.insertAll(keys)
            Log.d("DATA", data.devs.size.toString())
            dao.saveDevsData(data.devs)
            localDataSource.getDbService().setTransactionSuccessful()


        } finally {
            localDataSource.getDbService().endTransaction()
        }

        return data
    }


    private fun getRemoteKeyForLastItem(state: PagingState<Int, Devs.DevEntity>): Devs.DevRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            devRemoteKeysDao.remoteKeysByDevId(repo.devId)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, Devs.DevEntity>): Devs.DevRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { dev ->
            devRemoteKeysDao.remoteKeysByDevId(dev.devId)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Devs.DevEntity>): Devs.DevRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.devId?.let { id ->
                devRemoteKeysDao.remoteKeysByDevId(id)
            }
        }
    }

    companion object {
        const val INVALID_PAGE = -1
        const val NETWORK_PAGE_SIZE = 20

    }

}

