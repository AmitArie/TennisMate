package com.tennismate.tennismate.chat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public final class MessagesFixtures extends FixturesData {
    private MessagesFixtures() {
        throw new AssertionError();
    }


    public static ChatMessage getImageMessage() {
        ChatMessage chatMessage = new ChatMessage(getRandomId(), getUser(), null);
        chatMessage.setImage(new ChatMessage.Image(getRandomImage()));
        return chatMessage;
    }


    public static ChatMessage getTextMessage() {
        return getTextMessage(getRandomMessage());
    }

    public static ChatMessage getTextMessage(String text) {
        return new ChatMessage(getRandomId(), getUser(), text);
    }

    public static ArrayList<ChatMessage> getMessages(Date startDate) {
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        for (int i = 0; i < 10/*days count*/; i++) {
            int countPerDay = rnd.nextInt(5) + 1;

            for (int j = 0; j < countPerDay; j++) {
                ChatMessage chatMessage;
                if (i % 2 == 0 && j % 3 == 0) {
                    chatMessage = getImageMessage();
                } else {
                    chatMessage = getTextMessage();
                }

                Calendar calendar = Calendar.getInstance();
                if (startDate != null) calendar.setTime(startDate);
                calendar.add(Calendar.DAY_OF_MONTH, -(i * i + 1));

                chatMessage.setCreatedAt(calendar.getTime());
                chatMessages.add(chatMessage);
            }
        }
        return chatMessages;
    }

    private static ChatUser getUser() {
        boolean even = rnd.nextBoolean();
        return new ChatUser(
                even ? "0" : "1",
                even ? names.get(0) : names.get(1),
                even ? avatars.get(0) : avatars.get(1),
                true);
    }
}
