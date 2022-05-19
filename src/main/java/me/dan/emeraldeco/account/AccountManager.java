package me.dan.emeraldeco.account;

import lombok.Getter;
import me.dan.emeraldeco.EconomyPlugin;
import me.dan.emeraldeco.configuration.Config;
import me.dan.pluginapi.file.gson.GsonUtil;
import me.dan.pluginapi.manager.Manager;
import org.bukkit.Bukkit;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

public class AccountManager extends Manager<UUID, Account> {

    private final Queue<Account> accountQueue;

    private static final File DIRECTORY = new File(EconomyPlugin.getInstance().getDataFolder(), "accounts");

    public AccountManager() {
        super();
        this.accountQueue = new PriorityQueue<>();
        long duration = Config.SAVE_INTERVAL.getInt() * 20L;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(EconomyPlugin.getInstance(), this::emptyQueue, duration, duration);
    }

    @Override
    public void init() {
        if (!DIRECTORY.exists()) {
            DIRECTORY.mkdirs();
            return;
        }

        if (DIRECTORY.listFiles() == null) {
            return;
        }

        for (File file : DIRECTORY.listFiles()) {
            Account account = GsonUtil.read(getDirectory(), file.getName(), Account.class);
            if (account == null) {
                continue;
            }

            insert(account.getUuid(), account);

        }

    }

    public static File getDirectory() {
        return DIRECTORY;
    }

    @Override
    public Account get(UUID uuid) {
        Account account = super.get(uuid);
        if (!accountQueue.contains(account)) {
            accountQueue.add(account);
        }
        return account;
    }

    public void emptyQueue() {
        while (accountQueue.peek() != null) {
            accountQueue.poll().save();
        }
    }

    public void create(UUID uuid) {
        Account account = new Account(uuid);
        account.save();
        insert(account.getUuid(), account);
    }

}
