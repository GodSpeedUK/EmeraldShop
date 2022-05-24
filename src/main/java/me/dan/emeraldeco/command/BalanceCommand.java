package me.dan.emeraldeco.command;

import me.dan.emeraldeco.EconomyPlugin;
import me.dan.emeraldeco.account.Account;
import me.dan.emeraldeco.configuration.Message;
import me.dan.pluginapi.command.AbstractCommand;
import me.dan.pluginapi.command.CommandContext;
import me.dan.pluginapi.message.Placeholder;
import org.bukkit.entity.Player;

import java.util.Collections;

public class BalanceCommand extends AbstractCommand {

    public BalanceCommand() {
        super("balance");
        setRequiresPlayer(true);
        setPermission("emeraldeconomy.balance");
        setUsage("/balance");
        setAliases(Collections.singletonList("bal"));
    }

    @Override
    public void perform(CommandContext commandContext) {

        Player player = (Player) commandContext.getCommandSender();

        Account account = EconomyPlugin.getInstance().getAccountManager().get(player.getUniqueId());

        double balance = account.getBalance();

        String balanceFormat = EconomyPlugin.getInstance().getEconomyHolder().format(balance);

        Message.BALANCE.send(player, new Placeholder("{balance}", balanceFormat));

    }
}
