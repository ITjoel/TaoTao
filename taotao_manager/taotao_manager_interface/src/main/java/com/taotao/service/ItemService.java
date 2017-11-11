package com.taotao.service;

import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
    EasyUIResult getItemList(int page,int rows);
    TaotaoResult addItem(TbItem item, String desc);
}
