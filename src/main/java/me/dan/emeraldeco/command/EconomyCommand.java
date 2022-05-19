package me.dan.emeraldeco.command;

import me.dan.pluginapi.command.AbstractCommand;
import me.dan.pluginapi.command.CommandContext;

import java.util.Collections;

public class EconomyCommand extends AbstractCommand {

    public EconomyCommand() {
        super("economy");
        setAliases(Collections.singletonList("eco"));
        setDescription("Server Economy Information");
        setPermission("emeraldeconomy.economy");
    }

    @Override
    public void perform(CommandContext commandContext) {

    }
}
