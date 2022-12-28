package com.bwie.service;

import com.bwie.pojo.TbRoom;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bwie.utils.ResultResponse;
import com.bwie.vo.IdVo;
import com.bwie.vo.PageInfoVo;
import com.bwie.vo.RoomInfoVo;

/**
* @author 魏阳光
* @description 针对表【tb_room(直播房间表)】的数据库操作Service
* @createDate 2022-12-25 19:40:51
*/
public interface TbRoomService extends IService<TbRoom> {

    ResultResponse addRoom(RoomInfoVo roomInfoVo);

    ResultResponse listRoom(PageInfoVo pageInfoVo);

    ResultResponse getRoom(IdVo idVo);
}
