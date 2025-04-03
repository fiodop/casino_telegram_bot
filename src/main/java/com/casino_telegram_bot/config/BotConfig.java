package com.casino_telegram_bot.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;


@Configuration
@Getter
@Data
public class BotConfig {
    private String botName = "MarkaryanCasinoBot";
    private String botToken = "";

}
