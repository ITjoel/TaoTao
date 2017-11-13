package com.taotao.content.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public List<TbContent> getContentList(long cid) {
        //根据分类id查询内容列表
        TbContentExample example = new TbContentExample();
        //设置查询条件
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> list = tbContentMapper.selectByExample(example);
        return list;
    }
}
