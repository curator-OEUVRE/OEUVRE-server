package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.Picture;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.floor.request.PatchFloorQueueRequestDto;
import com.curator.oeuvre.dto.floor.request.PatchFloorRequestDto;
import com.curator.oeuvre.dto.floor.request.PostFloorRequestDto;
import com.curator.oeuvre.dto.floor.response.GetFloorResponseDto;
import com.curator.oeuvre.dto.floor.response.GetHomeFloorResponseDto;
import com.curator.oeuvre.dto.floor.response.PostFloorResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FloorService {

    void postHashtag(String hashtag, Picture picture);

    void updateHashtag(Picture originalPicture, List<String> newHashtags);

    PostFloorResponseDto postFloor(User user, PostFloorRequestDto postFloorRequestDto);

    GetFloorResponseDto getFloor(User user, Long floorNo);

    void patchFloor(User user, Long floorNo, PatchFloorRequestDto patchFloorRequestDto);

    void patchFloorQueue(User user, List<PatchFloorQueueRequestDto> patchFloorQueueRequestDto);

    PageResponseDto<List<GetHomeFloorResponseDto>> getHomeFloors(User user, Integer page, Integer size);
}
