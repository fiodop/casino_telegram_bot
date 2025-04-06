package com.casino_telegram_bot.service;

import com.casino_telegram_bot.config.BotConfig;
import com.casino_telegram_bot.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Configuration
public class TelegramBot extends TelegramLongPollingBot {
    Logger logger = LoggerFactory.getLogger(TelegramBot.class);
    private final AppUserService appUserService;

    private final BotConfig botConfig;


    private static HashMap<Long, Integer> videoQueque;


    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    public TelegramBot(AppUserService appUserService, BotConfig botConfig) {
        this.appUserService = appUserService;
        this.botConfig = botConfig;
        setBotMenu();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String username = update.getMessage().getFrom().getUserName();
            switch (messageText){
                case "/start":
                    logger.info("INFO:user with chat id " + username + " started bot");
                    register(update);
                    sendWelcomeMessage(chatId);
                    break;
                case "/myProfile":
                    myProfile(update);

                    break;
                case "/buyStars":


                    break;
                case "/motivation":
                    if (videoQueque == null) {
                        videoQueque = new HashMap<>();
                    }

                    if (videoQueque.getOrDefault(chatId, 0) == 8) {
                        videoQueque.remove(chatId);
                    }

                    videoQueque.put(chatId, videoQueque.getOrDefault(chatId, 0) + 1);
                    int videoId = videoQueque.get(chatId);
                    String videoPath = "src/main/resources/arsen_makaryan_motivation/" + videoId + ".mp4";

                    sendVideo(String.valueOf(chatId), videoPath);
                    break;
                default:
                    sendMessage(String.valueOf(chatId), "Данная команда не поддерживается");
            }
        }
    }

    /**
     * Method, which sends welcome message to user
     *
     * @param chatId
     */
    private void sendWelcomeMessage(long chatId) {
        WelcomeMessageService service = new WelcomeMessageService();
        String welcomeMessage = service.getWelcomeMessage();

        sendMessage(String.valueOf(chatId), welcomeMessage);
    }

    /**
     * Method which saves info about user when receiving /start command
     *
     * @param update object of class Update.class which contains info about chat update
     */
    private void register(Update update) {
        AppUser user = AppUser.builder()
                .username(update.getMessage().getFrom().getUserName())
                .registrationDate(LocalDateTime.now())
                .balance(0)
                .gamesCounter(0)
                .totalSum(0)
                .build();
        logger.info("INFO:registration of user: " + user.getUsername());
        appUserService.registerAppUser(user);
    }

    private void myProfile(Update update) {


    }


    /**
     * Method which set commands' menu
     */
    public void setBotMenu(){
        List<BotCommand> commands = List.of(
                new BotCommand("/start", "Запуск бота"),
                new BotCommand("/motivation", "прислать гемблинг мотивацию")
        );

        SetMyCommands setMyCommands = new SetMyCommands(commands, null, null);

        try{
            execute(setMyCommands);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    /**
     * Basic method which sends video from path which u entered
     *
     * @param chatId chat id with user
     * @param videoPath path of video which u want to send
     */
    private void sendVideo(String chatId, String videoPath){
        File videoFile = new File(videoPath).getAbsoluteFile();

        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(chatId);
        sendVideo.setVideo(new InputFile(videoFile));

        try{
            logger.info("INFO: Sending video to - " + chatId);
            execute(sendVideo);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    /**
     * Basic method which sends text message
     *
     * @param chatId chat id with user
     * @param messageText text to send
     */
    private void sendMessage(String chatId, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messageText);

        try{
            logger.info("INFO: Sending message in chat - " + chatId);
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }


}
