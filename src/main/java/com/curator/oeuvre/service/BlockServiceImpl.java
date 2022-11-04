package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.Block;
import com.curator.oeuvre.domain.Following;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.exception.BadRequestException;
import com.curator.oeuvre.exception.ForbiddenException;
import com.curator.oeuvre.exception.NotFoundException;
import com.curator.oeuvre.repository.BlockRepository;
import com.curator.oeuvre.repository.FollowingRepository;
import com.curator.oeuvre.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.curator.oeuvre.constant.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final UserRepository userRepository;
    private final BlockRepository blockRepository;
    private final FollowingRepository followingRepository;

    @Override
    @Transactional
    public void postUserBlock(User me, Long userNo) {

        User user = userRepository.findByNoAndStatus(userNo, 1).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND));

        if(Objects.equals(me.getNo(), userNo)) throw new ForbiddenException(CANNOT_BLOCK_MYSELF);

        if (blockRepository.existsByBlockUserNoAndBlockedUserNoAndStatus(me.getNo(), userNo, 1))
            throw new BadRequestException(ALREADY_BLOCKED);

        Block block = Block.builder()
                .blockUser(me)
                .blockedUser(user)
                .build();
        blockRepository.save(block);

        followingRepository.deleteByFollowUserNoAndFollowedUserNoAndStatus(me.getNo(), userNo, 1);
    }

    @Override
    @Transactional
    public void deleteUserBlock(User me, Long userNo) {

        userRepository.findByNoAndStatus(userNo, 1).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND));

        if (!blockRepository.existsByBlockUserNoAndBlockedUserNoAndStatus(me.getNo(), userNo, 1))
            throw new BadRequestException(BLOCK_NOT_FOUND);

        blockRepository.deleteByBlockUserNoAndBlockedUserNoAndStatus(me.getNo(), userNo, 1);
    }
}