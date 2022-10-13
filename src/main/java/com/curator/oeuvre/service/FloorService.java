package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.floor.request.PostFloorRequestDto;
import com.curator.oeuvre.dto.floor.response.PostFloorResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface FloorService {

    PostFloorResponseDto postFloor(User user, PostFloorRequestDto postFloorRequestDto);
}
