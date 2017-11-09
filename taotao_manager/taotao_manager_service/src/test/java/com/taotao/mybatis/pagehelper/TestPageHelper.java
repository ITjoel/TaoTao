package com.taotao.mybatis.pagehelper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class TestPageHelper {

    @Test
    public void testPageHelper() throws Exception {
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext-*.xml");
        //获得mapper代理对象
        TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
        //设置分页条件
        PageHelper.startPage(1, 30);
        //查询条件
        TbItemExample example = new TbItemExample();
        //通过查询条件查询出所有的TbItem 结果集
        List<TbItem> tbItems = mapper.selectByExample(example);
        //用PageInfo包装结果集
        PageInfo<TbItem> tbItemPageInfo = new PageInfo<TbItem>(tbItems) ;
        System.out.println(tbItemPageInfo.getTotal());
        System.out.println(tbItemPageInfo.getPages());
        System.out.println(tbItemPageInfo.getPageNum());
        System.out.println(tbItemPageInfo.getPageSize());
    }
}
