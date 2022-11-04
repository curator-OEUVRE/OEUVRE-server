package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface BlockService {

    void postUserBlock(User me, Long userNo);

    void deleteUserBlock(User me, Long userNo);
}
