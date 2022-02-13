package com.netguru.codereview.ui.model

import com.netguru.codereview.network.model.ShopListItemResponse
import com.netguru.codereview.network.model.ShopListResponse

class ShopList(
    val id: String,
    val userId: Int?,
    val listName: String?,
    val iconUrl: String?,
    val items: List<ShopListItemResponse?>?
) {
    constructor(list: ShopListResponse, items: List<ShopListItemResponse?>?) : this(
        list.listId,
        list.userId,
        list.listName,
        list.listName,
        items
    )
}
