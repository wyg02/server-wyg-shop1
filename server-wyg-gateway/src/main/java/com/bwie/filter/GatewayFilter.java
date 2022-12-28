package com.bwie.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.nacos.api.utils.StringUtils;
import com.bwie.utils.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class GatewayFilter implements GlobalFilter, Ordered {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 全局过滤
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取Request、Response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 解决中文乱码问题
        HttpHeaders headers = response.getHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 打印输出URI
        String requestUrl = exchange.getRequest().getURI().getRawPath();
        System.out.println(requestUrl);
        System.out.println(request.getURI());

        //添加白名单放行
        if(requestUrl.contains("/login")
            || requestUrl.contains("/add")
        ){
            return chain.filter(exchange);
        }


        // 拦截
        //从请求头部取得token
        String token = request.getHeaders().getFirst("token");
        if(StringUtils.isEmpty(token)) {
            //设置状态码
            response.setStatusCode(HttpStatus.UNAUTHORIZED);

            //封装响应数据   设置数据编码
            ResultResponse message = ResultResponse.FAILED().message("网关拦截");
            String msg = JSON.toJSONString(message);
            DataBuffer dataBuffer = response.bufferFactory().wrap(msg.getBytes(StandardCharsets.UTF_8));

            //拦截
            log.info("头部没有token");
            return response.writeWith(Mono.just(dataBuffer));
        }
        //从Redis获取Token
        String value = (String)redisTemplate.opsForValue().get(token);
        if(value==null){
            //设置状态码
            response.setStatusCode(HttpStatus.UNAUTHORIZED);

            //封装响应数据   设置数据编码
            ResultResponse message = ResultResponse.FAILED().message("网关拦截");
            String msg = JSON.toJSONString(message);
            DataBuffer dataBuffer = response.bufferFactory().wrap(msg.getBytes(StandardCharsets.UTF_8));

            //拦截
            log.info("redis没有token");
            return response.writeWith(Mono.just(dataBuffer));
        }
        //Redis续命
        redisTemplate.expire(token,30, TimeUnit.MINUTES);
        // 放行
        return chain.filter(exchange);
    }

    /**
     * 排序，越小越先执行
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}