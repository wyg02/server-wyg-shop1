package com.bwie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bwie.pojo.TbProduct;
import com.bwie.service.TbProductService;
import com.bwie.mapper.TbProductMapper;
import org.springframework.stereotype.Service;

/**
* @author 魏阳光
* @description 针对表【tb_product(产品表)】的数据库操作Service实现
* @createDate 2022-12-21 19:56:52
*/
@Service
public class TbProductServiceImpl extends ServiceImpl<TbProductMapper, TbProduct>
    implements TbProductService{

}




