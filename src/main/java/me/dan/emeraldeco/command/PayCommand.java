package me.dan.emeraldeco.command;

import me.dan.emeraldeco.EconomyPlugin;
import me.dan.emeraldeco.configuration.Message;
import me.dan.pluginapi.command.AbstractCommand;
import me.dan.pluginapi.command.CommandContext;
import me.dan.pluginapi.configurable.Messages;
import me.dan.pluginapi.message.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PayCommand extends AbstractCommand {

    public PayCommand() {
        super("pay");
        setRequiresPlayer(true);
        setDescription("Pay users Money");
        setUsage("/pay {player} {amount}");
        setPermission("emeraldeconomy.pay");

    }

    @Override
    public void perform(CommandContext commandContext) {

        Player player = (Player) commandContext.getCommandSender();

        if (commandContext.getArgs().length < 2) {
            Messages.USAGE.send(commandContext.getCommandSender(), new Placeholder("{usage}", getUsage()));
            return;
        }


        double amount;

        try {
            amount = Double.parseDouble(commandContext.getArgs()[1]);
        } catch (NumberFormatException e) {
            Message.NOT_A_NUMBER.send(commandContext.getCommandSender(), new Placeholder("{arg}", commandContext.getArgs()[1]));
            return;
        }

        if (amount <= 0.01) {
            Message.NOT_LARGE_ENOUGH_NUMBER.send(player);
            return;
        }

        String target = commandContext.getArgs()[0];

        if (!EconomyPlugin.getInstance().getEcon().hasAccount(player)) {
            Message.PLAYER_NOT_FOUND.send(commandContext.getCommandSender(), new Placeholder("{player}", target));
            return;
        }

        if (EconomyPlugin.getInstance().getEcon().getBalance(player) < amount) {
            Message.NOT_ENOUGH_MONEY.send(player);
            return;
        }

        OfflinePlayer targetP = Bukkit.getOfflinePlayer(target);

        String moneyFormat = EconomyPlugin.getInstance().getEcon().format(amount);

        EconomyPlugin.getInstance().getEcon().withdrawPlayer(player, amount);
        EconomyPlugin.getInstance().getEcon().depositPlayer(targetP, amount);

        Message.PAY_SENT.send(player, new Placeholder("{player}", targetP.getName()), new Placeholder("{amount}", moneyFormat));
        if (targetP.isOnline()) {
            Message.PAY_RECEIVED.send(targetP.getPlayer(), new Placeholder("{amount}", moneyFormat), new Placeholder("{player}", player.getName()));
        }

    }
}
