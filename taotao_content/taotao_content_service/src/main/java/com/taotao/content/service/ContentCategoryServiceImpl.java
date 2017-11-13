package com.taotao.content.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(long parentId) {
        //根据parentid查询子节点列表
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        //设置查询条件
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        //返回结果List
        List<EasyUITreeNode> list = new ArrayList<>();
        for (TbContentCategory tbContentCategory : tbContentCategories) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tbContentCategory.getId());
            easyUITreeNode.setText(tbContentCategory.getName());
            easyUITreeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
            //添加列表
            list.add(easyUITreeNode);
        }
        return list;
    }
}
