package com.curator.oeuvre.service;

import static com.curator.oeuvre.constant.ErrorCode.*;

import com.curator.oeuvre.constant.ErrorCode;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.exception.BadRequestException;
import com.curator.oeuvre.exception.InternalServerException;
import io.github.jav.exposerversdk.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExpoNotificationServiceImpl implements ExpoNotificationService {

    public void sendMessage(User user, String title, String body, Map<String, Object> data) {

        String token = user.getFcmToken();
        if (token == null) {
            log.info("token = {}", user.getFcmToken());
            throw new BadRequestException(INVALID_NOTIFICATION_TOKEN);
        }
//        if (!PushClient.isExponentPushToken(token)) {
//            log.info("token = {}", user.getFcmToken());
//            throw new BadRequestException(INVALID_NOTIFICATION_TOKEN);
//        }
        ExpoPushMessage expoPushMessage = new ExpoPushMessage();
        expoPushMessage.getTo().add(token);
        expoPushMessage.setTitle(title);
        expoPushMessage.setBody(body);
        expoPushMessage.setData(data);

        List<ExpoPushMessage> expoPushMessages = new ArrayList<>();
        expoPushMessages.add(expoPushMessage);

        try {
            PushClient pushClient = new PushClient();
            List<List<ExpoPushMessage>> chunks = pushClient.chunkPushNotifications(
                    expoPushMessages);

            List<CompletableFuture<List<ExpoPushTicket>>> messageRepliesFutures = new ArrayList<>();

            for (List<ExpoPushMessage> chunk : chunks) {
                messageRepliesFutures.add(pushClient.sendPushNotificationsAsync(chunk));
            }
            //Todo 메서드 인자가 user로 바뀌면 데이터 베이스에 꽂기
//            Notification notification = Notification.builder().title(title).message(body).user(user)
//                    .notificationCategory(notificationCategory).linkUrl(linkUrl).build();
//            notificationRepository.save(notification);
            List<ExpoPushTicket> allTickets = new ArrayList<>();
            for (CompletableFuture<List<ExpoPushTicket>> messageReplyFuture : messageRepliesFutures) {
                try {
                    allTickets.addAll(messageReplyFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    log.error("error message = {}", e.getMessage());
                    throw new InternalServerException(NOTIFICATION_SERVER_ERROR);
                }
            }

            List<ExpoPushMessageTicketPair<ExpoPushMessage>> zippedMessagesTickets = pushClient.zipMessagesTickets(
                    expoPushMessages, allTickets);

            List<ExpoPushMessageTicketPair<ExpoPushMessage>> okTicketMessages = pushClient.filterAllSuccessfulMessages(
                    zippedMessagesTickets);
            String okTicketMessagesString = okTicketMessages.stream()
                    .map(p -> "Title: " + p.message.getTitle() + ", Id:" + p.ticket.getId()).collect(
                            Collectors.joining(","));
            log.info("Recieved OK ticket for " + okTicketMessages.size() + " messages: "
                    + okTicketMessagesString);

            List<ExpoPushMessageTicketPair<ExpoPushMessage>> errorTicketMessages = pushClient.filterAllMessagesWithError(
                    zippedMessagesTickets);
            String errorTicketMessagesString = errorTicketMessages.stream().map(
                    p -> "id: " + user.getId() + ", " + "Title: " + p.message.getTitle() + ", Error: "
                            + p.ticket.getDetails()
                            .getError()).collect(Collectors.joining(","));
            log.info("Recieved ERROR ticket for " + errorTicketMessages.size() + " messages: "
                    + errorTicketMessagesString);
        } catch (PushClientException | PushNotificationException | GenericJDBCException e) {
            log.info("error message = {}", e.getMessage());
            throw new InternalServerException(NOTIFICATION_SERVER_ERROR);
        }
    }
}
