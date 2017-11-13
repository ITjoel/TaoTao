package com.taotao.content.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
            easyUITreeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            //添加列表
            list.add(easyUITreeNode);
        }
        return list;
    }

    @Override
    public TaotaoResult insertContentCat(long parentId, String name) {
        // 1、接收两个参数：parentId、name
        // 2、向tb_content_category表中插入数据。
        // a)创建一个TbContentCategory对象
        TbContentCategory tbContentCategory = new TbContentCategory();
        // b)补全TbContentCategory对象的属性
        tbContentCategory.setIsParent(false);
        tbContentCategory.setName(name);
        tbContentCategory.setParentId(parentId);
        //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
        tbContentCategory.setSortOrder(1);
        //状态。可选值:1(正常),2(删除)
        tbContentCategory.setStatus(1);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        // c)向tb_content_category表中插入数据
        tbContentCategoryMapper.insert(tbContentCategory);
        TbContentCategory parentNode  = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        //判断父节点是否为叶子节点
        if (!parentNode .getIsParent()){
            parentNode .setIsParent(true);
            //更新父节点
            tbContentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        // 4、需要主键返回。
        // 5、返回TaotaoResult，其中包装TbContentCategory对象
        return TaotaoResult.ok(tbContentCategory);
    }
}
