package com.casino_telegram_bot.service;

import org.springframework.stereotype.Service;

@Service
public class WelcomeMessageService {
    private static final String WELCOME_MESSAGE = "Привет! Добро пожаловать в **Маркарян Казино** 🎰✨\n\n" +
            "Здесь вы играете за звезды Telegram — и наслаждаетесь увлекательными играми! Однако, обратите внимание, что это казино не имеет " +
            "никакого отношения к Арсену Маркаряну. Всё веселье и азарт — исключительно для вас и ваших друзей!\n\n" +
            "Удачи в игре и пусть вам повезет! 🍀🎉";

    public String getWelcomeMessage() {
        return WELCOME_MESSAGE;
    }
}
