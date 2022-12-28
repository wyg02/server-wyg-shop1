package com.bwie.controller;

import com.bwie.service.TbProductService;
import com.bwie.utils.ResultResponse;
import com.bwie.vo.IdVo;
import com.bwie.vo.PageInfoVo;
import com.bwie.vo.ProductVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Sun
 * @Version 1.0
 * @description: TODO
 * @date 2022/12/21 17:07:13
 */
@RestController
@RequestMapping("/product")
public class TbProductController {
    @Resource
    private TbProductService tbProductService;

    @ApiOperation("产品添加")
    @PostMapping("add-product")
    public ResultResponse addProduct(@RequestBody ProductVo productVo){
        return tbProductService.addProduct(productVo);
    }

    @ApiOperation("产品删除")
    @PostMapping("delete-product")
    public ResultResponse deleteProduct(@RequestBody IdVo idVo){
        return tbProductService.deleteProduct(idVo);
    }

    @ApiOperation("产品修改")
    @PostMapping("update-product")
    public ResultResponse updateProduct(@RequestBody ProductVo productVo){
        return tbProductService.updateProduct(productVo);
    }

    @ApiOperation("产品列表查询")
    @PostMapping("list-product")
    public ResultResponse listProduct(@RequestBody PageInfoVo pageInfoVo){
        return tbProductService.listProduct(pageInfoVo);
    }
}
