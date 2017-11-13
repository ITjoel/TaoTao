package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(defaultValue = "0") Long id) {
        List<EasyUITreeNode> catList = contentCategoryService.getContentCatList(id);
        return catList;
    }

    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult createCategory(Long parentId, String name) {
        TaotaoResult taotaoResult = contentCategoryService.insertContentCat(parentId, name);
        return taotaoResult;
    }
}
