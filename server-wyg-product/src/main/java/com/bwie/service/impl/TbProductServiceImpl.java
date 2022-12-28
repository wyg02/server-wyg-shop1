package com.bwie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bwie.mapper.TbProductTypeMapper;
import com.bwie.pojo.TbProduct;
import com.bwie.pojo.TbProductType;
import com.bwie.service.TbProductService;
import com.bwie.mapper.TbProductMapper;
import com.bwie.utils.ResultResponse;
import com.bwie.vo.IdVo;
import com.bwie.vo.PageInfoVo;
import com.bwie.vo.ProductVo;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 魏阳光
* @description 针对表【tb_product(产品表)】的数据库操作Service实现
* @createDate 2022-12-21 17:06:45
*/
@Service
public class TbProductServiceImpl extends ServiceImpl<TbProductMapper, TbProduct>
    implements TbProductService{

    @Resource
    private TbProductTypeMapper typeMapper;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    /**
     * 添加产品
     * @param productVo
     * @return
     */
    @Override
    public ResultResponse addProduct(ProductVo productVo) {
        if (productVo == null || productVo.getProductName() == null) {
            return ResultResponse.FAILED().message("产品名不能为空");
        }

        TbProductType tbProductType = typeMapper.selectById(productVo.getTypeId());

        if (tbProductType == null) {
            return ResultResponse.FAILED().message("该类型不存在");
        }

        TbProduct tbProduct = new TbProduct();
        BeanUtils.copyProperties(productVo, tbProduct);
        baseMapper.insert(tbProduct);
        return ResultResponse.SUCCESS().message("添加成功");
    }

    @Override
    public ResultResponse deleteProduct(IdVo idVo) {
        TbProduct one = baseMapper.selectById(idVo.getId());
        if (one == null) {
            return ResultResponse.FAILED().message("该产品不存在");
        }
        baseMapper.deleteById(idVo.getId());
        return ResultResponse.SUCCESS().message("删除成功");
    }

    @Override
    public ResultResponse updateProduct(ProductVo productVo) {
        //--1判断该品牌是否存在
        TbProduct one = baseMapper.selectById(productVo.getProductId());
        if (one == null) {
            return ResultResponse.FAILED().message("产品不存在，无法修改");
        }
        TbProduct tbProduct = new TbProduct();
        BeanUtils.copyProperties(productVo, tbProduct);
        tbProduct.setUpdateTime(null);
        baseMapper.updateById(tbProduct);
        return ResultResponse.SUCCESS().message("修改成功");
    }

    @Override
    public ResultResponse listProduct(PageInfoVo pageInfoVo) {
        //没有搜素关键字使用Mysql查询
        if (pageInfoVo != null && pageInfoVo.getKeyword() == null) {
            LambdaQueryWrapper<TbProduct> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(TbProduct::getCreateTime);
            Page<TbProduct> tbProductPage = new Page<>(pageInfoVo.getPageNum(), pageInfoVo.getPageSize());
            Page<TbProduct> productPage = baseMapper.selectPage(tbProductPage, wrapper);
            //封装返回对象
            Page<ProductVo> page = new Page<>();

            List<ProductVo> collect = productPage.getRecords().stream().map(item -> {
                ProductVo productVo = new ProductVo();
                BeanUtils.copyProperties(item, productVo);
                return productVo;
            }).collect(Collectors.toList());

            page.setRecords(collect);
            page.setTotal(productPage.getTotal());
            page.setCurrent(productPage.getCurrent());
            page.setSize(productPage.getSize());
            return ResultResponse.SUCCESS().data("page", page);
        }
        //-------------------存在搜索关键字使用ES高亮查询-------------------------
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //构建查询条件
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("productName", pageInfoVo.getKeyword());
        //构建分页
        PageRequest pageRequest = PageRequest.of(pageInfoVo.getPageNum() - 1, pageInfoVo.getPageSize());

        // QueryBuilders.re

        //查询高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>").postTags("</font>").field("productName");
        //根据创建时间添加倒序排列
        NativeSearchQuery query = queryBuilder.withQuery(matchQueryBuilder)
                .withSort(SortBuilders.fieldSort("createTime.keyword").order(SortOrder.DESC))
                .withHighlightBuilder(highlightBuilder)
                .withPageable(pageRequest).build();
        //取得查询数据
        SearchHits<TbProduct> search = elasticsearchRestTemplate.search(query, TbProduct.class);

        List<ProductVo> productVos = search.getSearchHits().stream().map(item -> {
            ProductVo productVo = new ProductVo();
            TbProduct content = item.getContent();
            String productName = item.getHighlightField("productName").get(0);
            content.setProductName(productName);
            BeanUtils.copyProperties(content, productVo);
            return productVo;
        }).collect(Collectors.toList());
        //封装结果返回
        Page<ProductVo> productVoPage = new Page<>();
        productVoPage.setCurrent(pageInfoVo.getPageNum());
        productVoPage.setSize(pageInfoVo.getPageSize());
        productVoPage.setRecords(productVos);
        productVoPage.setTotal(search.getTotalHits());
        return ResultResponse.SUCCESS().data("page",productVoPage);
    }
}




