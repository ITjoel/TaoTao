package com.taotao.content.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    TbContentMapper tbContentMapper ;

    @Override
    public TaotaoResult addContent(TbContent tbContent) {
        //补全属性
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        tbContentMapper.insert(tbContent);
        return TaotaoResult.ok();
    }
}
