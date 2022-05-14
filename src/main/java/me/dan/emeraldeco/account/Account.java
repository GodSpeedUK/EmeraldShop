package me.dan.emeraldeco.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.dan.pluginapi.file.gson.GsonUtil;

import java.util.UUID;


@RequiredArgsConstructor
@Getter
@Setter
public class Account {

    private final UUID uuid;

    private double balance = 0;

    public void save() {
        GsonUtil.save(AccountManager.getDirectory(), uuid.toString(), this);
    }

}
