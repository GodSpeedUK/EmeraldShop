package me.dan.emeraldeco.account;

import me.dan.emeraldeco.EconomyPlugin;
import me.dan.emeraldeco.account.interest.Interest;
import me.dan.emeraldeco.configuration.Config;
import me.dan.pluginapi.file.gson.GsonUtil;
import me.dan.pluginapi.manager.Manager;
import org.bukkit.Bukkit;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AccountManager extends Manager<UUID, Account> {

    private final List<Account> accountQueue;

    private static final File DIRECTORY = new File(EconomyPlugin.getInstance().getDataFolder(), "accounts");

    public AccountManager() {
        super();
        this.accountQueue = new ArrayList<>();
        long duration = Config.SAVE_INTERVAL.getInt() * 20L;
        Interest interest = Config.INTEREST.getInterest();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(EconomyPlugin.getInstance(), this::emptyQueue, duration, duration);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime then = LocalDateTime.now();
        double interestMultiplier = (interest.getRate() / 100) + 1;
        then = then.with(TemporalAdjusters.nextOrSame(interest.getDayOfWeek())).withHour(interest.getHour());
        ScheduledExecutorService scheduledActivity = Executors.newScheduledThreadPool(1);
        scheduledActivity.scheduleAtFixedRate(() -> {
            for (Account account : getAll()) {
                account.setBalance(account.getBalance() * interestMultiplier);
            }
        }, Duration.between(now, then).getSeconds(), TimeUnit.DAYS.toSeconds(7), TimeUnit.SECONDS);
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
        for (Account account : accountQueue) {
            account.save();
        }

        accountQueue.clear();

    }

    public void create(UUID uuid) {
        Account account = new Account(uuid);
        account.save();
        insert(account.getUuid(), account);
        EconomyPlugin.getInstance().sendConsoleMessage("Account created!");
    }

}
