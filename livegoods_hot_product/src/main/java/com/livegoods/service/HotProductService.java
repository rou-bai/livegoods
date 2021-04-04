package com.livegoods.service;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Items;

public interface HotProductService {
    /*
    根据城市查询热门商品
     */

    Result<Items> getHostProducts(String city);
}
