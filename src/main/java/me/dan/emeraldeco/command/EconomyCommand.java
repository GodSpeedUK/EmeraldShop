package me.dan.emeraldeco.command;

import me.dan.emeraldeco.EconomyPlugin;
import me.dan.emeraldeco.configuration.Config;
import me.dan.emeraldeco.configuration.Message;
import me.dan.pluginapi.command.AbstractCommand;
import me.dan.pluginapi.command.CommandContext;
import me.dan.pluginapi.math.NumberUtil;
import me.dan.pluginapi.message.Placeholder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EconomyCommand extends AbstractCommand {

    public EconomyCommand() {
        super("economy");
        setAliases(Collections.singletonList("eco"));
        setDescription("Server Economy Information");
        setPermission("emeraldeconomy.economy");
    }

    @Override
    public void perform(CommandContext commandContext) {
        double interest = Config.INTEREST_RATE.getDouble();
        double exchange = Config.EXCHANGE_RATE.getDouble();
        String server = Config.SERVER_NAME.getString();
        int accounts = EconomyPlugin.getInstance().getAccountManager().getAll().size();
        List<Placeholder> placeholderList = new ArrayList<>();
        placeholderList.add(new Placeholder("{server}", server));
        placeholderList.add(new Placeholder("{interest_rate}", NumberUtil.formatBigDecimal(BigDecimal.valueOf(interest))));
        placeholderList.add(new Placeholder("{exchange_rate}", NumberUtil.formatBigDecimal(BigDecimal.valueOf(exchange))));
        placeholderList.add(new Placeholder("{accounts}", NumberUtil.formatBigDecimal(BigDecimal.valueOf(accounts))));

        Message.ECONOMY_INFORMATION.send(commandContext.getCommandSender(), placeholderList.toArray(new Placeholder[0]));
    }
}
