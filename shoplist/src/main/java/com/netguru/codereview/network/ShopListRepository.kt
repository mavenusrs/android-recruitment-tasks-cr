package com.netguru.codereview.network

class ShopListRepository(private val shopListApi: ShopListApi) {

    internal suspend fun getShopLists() = shopListApi.getShopLists()

    internal suspend fun getShopListItems(listId: String) = shopListApi.getShopListItems(listId)

    internal fun updateEvents() = shopListApi.getUpdateEvents()
}
