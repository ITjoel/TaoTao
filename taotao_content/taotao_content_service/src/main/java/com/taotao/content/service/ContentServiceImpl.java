package com.taotao.content.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_KEY}")
    private String CONTENT_KEY;

    @Override
    public TaotaoResult addContent(TbContent tbContent) {
        //补全属性
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        tbContentMapper.insert(tbContent);
        //缓存同步，清除redis中cid对应的缓存信息
        jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId().toString());
        return TaotaoResult.ok();
    }

    @Override
    public List<TbContent> getContentList(long cid) {

        try {
            String json = jedisClient.hget(CONTENT_KEY, cid + "");
            //判断是否命中缓存
            if (StringUtils.isNotBlank(json)) {
                //把json转换成list
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据分类id查询内容列表
        TbContentExample example = new TbContentExample();
        //设置查询条件
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> list = tbContentMapper.selectByExample(example);
        //向缓存中保存结果
        try {
            jedisClient.hset(CONTENT_KEY, cid + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
