package com.bwie.service;

import com.bwie.pojo.TbProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bwie.utils.ResultResponse;
import com.bwie.vo.IdVo;
import com.bwie.vo.PageInfoVo;
import com.bwie.vo.ProductVo;

/**
* @author 魏阳光
* @description 针对表【tb_product(产品表)】的数据库操作Service
* @createDate 2022-12-21 17:06:45
*/
public interface TbProductService extends IService<TbProduct> {

    ResultResponse addProduct(ProductVo productVo);

    ResultResponse deleteProduct(IdVo idVo);

    ResultResponse updateProduct(ProductVo productVo);

    ResultResponse listProduct(PageInfoVo pageInfoVo);
}
