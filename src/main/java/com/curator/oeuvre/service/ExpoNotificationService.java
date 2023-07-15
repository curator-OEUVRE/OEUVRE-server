package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public interface ExpoNotificationService {

    void sendMessage(User user, String title, String body, Map<String, Object> data);

    void postFcmLog(User user, String type, HashMap<String, Object> data);
}
