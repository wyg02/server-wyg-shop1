package com.bwie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bwie.pojo.TbRoom;
import com.bwie.service.TbRoomService;
import com.bwie.mapper.TbRoomMapper;
import com.bwie.utils.CopyBeanUtils;
import com.bwie.utils.IdWorker;
import com.bwie.utils.ResultResponse;
import com.bwie.utils.TxLiveUtils;
import com.bwie.vo.IdVo;
import com.bwie.vo.PageInfoVo;
import com.bwie.vo.RoomInfoVo;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 魏阳光
* @description 针对表【tb_room(直播房间表)】的数据库操作Service实现
* @createDate 2022-12-25 19:40:51
*/
@Service
public class TbRoomServiceImpl extends ServiceImpl<TbRoomMapper, TbRoom>
    implements TbRoomService{

    /**
     * 创建一个直播间
     * @param roomInfoVo
     * @return
     */
    @Override
    public ResultResponse addRoom(RoomInfoVo roomInfoVo) {
        //--1 复制属性
        TbRoom tbRoom = new TbRoom();
        CopyBeanUtils.copy(roomInfoVo, tbRoom);

        //--2 生成推流地址
        tbRoom.setRoomNo(""+ IdWorker.getId());
        String pushUrl = TxLiveUtils.makePushUrl(tbRoom.getRoomNo(), roomInfoVo.getCloseTime());
        tbRoom.setPushUrl(pushUrl);

        //--3 生成播放地址
        String playUrl = TxLiveUtils.makePlayUrl(tbRoom.getRoomNo());
        tbRoom.setPlayUrl(playUrl);

        //--3 写入数据库
        tbRoom.setCoverUrl("https://upload.shenmazong.com/upload/955bb2ca-b4d6-4ab5-8e1e-69c35f277af7.png");
        save(tbRoom);

        return ResultResponse.SUCCESS();
    }

    /**
     * 分页列表
     * @param pageInfoVo
     * @return
     */
    @Override
    public ResultResponse listRoom(PageInfoVo pageInfoVo) {
        Page<TbRoom> tbRoomPage = new Page<>(pageInfoVo.getPageNum(), pageInfoVo.getPageSize());
        LambdaQueryWrapper<TbRoom> wrapper = new LambdaQueryWrapper<TbRoom>().orderByDesc(TbRoom::getOpenTime);
        Page<TbRoom> tbRoomPage1 = baseMapper.selectPage(tbRoomPage, wrapper);

        List<RoomInfoVo> collect = tbRoomPage1.getRecords().stream().map(item -> {
            RoomInfoVo roomInfoVo = new RoomInfoVo();
            CopyBeanUtils.copy(item, roomInfoVo);
            return roomInfoVo;
        }).collect(Collectors.toList());

        //封装结果返回
        Page<RoomInfoVo> roomInfoVoPage = new Page<>();
        roomInfoVoPage.setRecords(collect);
        roomInfoVoPage.setCurrent(pageInfoVo.getPageNum());
        roomInfoVoPage.setSize(pageInfoVo.getPageSize());
        roomInfoVoPage.setTotal(tbRoomPage.getTotal());

        return ResultResponse.SUCCESS().data("page",roomInfoVoPage);
    }

    /**
     * 根据Id获取直播间信息
     * @param idVo
     * @return
     */
    @Override
    public ResultResponse getRoom(IdVo idVo) {
        TbRoom tbRoom = baseMapper.selectById(idVo.getId());
        if (tbRoom!=null){
            return ResultResponse.SUCCESS().data("tbRoom",tbRoom);
        }
        return null;
    }
}




