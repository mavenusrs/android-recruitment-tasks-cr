package com.netguru.codereview.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netguru.codereview.network.ShopListApiMock
import com.netguru.codereview.network.ShopListRepository
import com.netguru.codereview.network.model.ShopListItemResponse
import com.netguru.codereview.network.model.ShopListResponse
import com.netguru.codereview.ui.model.ResultState
import com.netguru.codereview.ui.model.ShopList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val shopListRepository = ShopListRepository(ShopListApiMock())

    private val _shopListsLiveData =
        MutableLiveData<ResultState<List<ShopList?>?>>()
    internal val shopListsLiveData: LiveData<ResultState<List<ShopList?>?>>
        get() = _shopListsLiveData

    private val eventLiveData = MutableLiveData<String?>()

    internal fun getShopList() {
        viewModelScope.launch {
            try {
                val lists = shopListRepository.getShopLists()
                val data = mutableListOf<Pair<ShopListResponse?, List<ShopListItemResponse?>>>()
                for (list in lists) {
                    list?.listId?.apply {
                        val items = shopListRepository.getShopListItems(this)
                        data.add(list to items)
                    }
                }

                val shopLists = data.map { list ->
                    list.first?.let { ShopList(it, list.second) }
                }

                _shopListsLiveData.postValue(ResultState.Success(shopLists))
            } catch (error: Throwable) {
                _shopListsLiveData.value = ResultState.Failed(error)
            }

        }
        getUpdateEvents()
    }

    internal fun events(): LiveData<String?> = eventLiveData

    private fun getUpdateEvents() {
        viewModelScope.launch {
            shopListRepository.updateEvents().collect {
                eventLiveData.postValue(it)
            }
        }
    }
}
