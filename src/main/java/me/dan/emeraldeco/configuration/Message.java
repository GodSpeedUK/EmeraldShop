package me.dan.emeraldeco.configuration;

import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.configuration.Configuration;

import java.util.Arrays;
import java.util.List;

public enum Message implements Configuration, me.dan.pluginapi.message.Message {

    PREFIX("prefix", "&8[&aEmeraldEco&8] &2"),
    PLAYER_NOT_FOUND("player-not-found", "{prefix}Could not find player {player}!"),
    NOT_ENOUGH_MONEY("not-enough-money", "{prefix}You do not have enough money to do this!"),
    PAY_SENT("pay.sent", "{prefix}You have paid {player} {amount}!"),
    PAY_RECEIVED("pay.received", "{prefix}You were paid {amount} from {player}!"),
    NOT_LARGE_ENOUGH_NUMBER("not-large-enough-number", "{prefix}You must use an amount larger or equal to 0.01!"),
    NOT_A_NUMBER("not-a-number", "{prefix}{arg} is not a number!"),
    ECONOMY_INFORMATION("economy-information", Arrays.asList(
            "&8&l=========================",
            " ",
            " &aServer Name &8- &3{server}",
            " &aAccounts &8- &3{accounts}",
            " &aInterest Rates &8- &3{interest_rate}%",
            " &aExchange Rate &8- &3{exchange_rate}:1",
            " ",
            "&8&l========================="
    )),
    BALANCE("balance", "{prefix}Your Balance: &b{balance}");

    @Getter
    private final String path;

    @Getter
    @Setter
    private Object value;

    Message(String path, Object value) {
        this.path = path;
        this.value = value;
    }


    @Override
    public String getPrefix() {
        return PREFIX.getString();
    }

    @Override
    public List<String> getStringList() {
        return Configuration.super.getStringList();
    }

    @Override
    public String getString() {
        return Configuration.super.getString();
    }
}
