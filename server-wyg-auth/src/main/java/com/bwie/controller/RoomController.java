package com.bwie.controller;

import com.bwie.service.TbRoomService;
import com.bwie.utils.ResultResponse;
import com.bwie.vo.IdVo;
import com.bwie.vo.PageInfoVo;
import com.bwie.vo.RoomInfoVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author Sun
 * @Version 1.0
 * @description: TODO
 * @date 2022/12/25 19:41:01
 */
@RestController
@RequestMapping("room")
public class RoomController {
    @Resource
    private TbRoomService tbRoomService;

    @PostMapping("add-room")
    public ResultResponse addRoom(@RequestBody RoomInfoVo roomInfoVo){
        return tbRoomService.addRoom(roomInfoVo);
    }

    @PostMapping("get-room")
    public ResultResponse getRoom(@RequestBody IdVo idVo){
        return tbRoomService.getRoom(idVo);
    }

    @PostMapping("list-room")
    public ResultResponse listRoom(@RequestBody PageInfoVo pageInfoVo){
        return tbRoomService.listRoom(pageInfoVo);
    }


}
