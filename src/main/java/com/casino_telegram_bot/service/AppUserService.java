package com.casino_telegram_bot.service;

import com.casino_telegram_bot.entity.AppUser;
import com.casino_telegram_bot.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AppUserService {
    Logger logger = Logger.getLogger(AppUserService.class.getName());
    private final AppUserRepository appUserRepository;
    public void registerAppUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }
}
