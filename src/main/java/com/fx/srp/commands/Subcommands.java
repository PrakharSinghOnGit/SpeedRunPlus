package com.fx.srp.commands;

import lombok.Getter;


@Getter
public enum Subcommands {
    START("start"),
    RESET("reset"),
    STOP("stop"),
    COOP("coop"),
    BATTLE("battle");

    private final String subcommand;

    Subcommands(String subcommand) {
        this.subcommand = subcommand;
    }

    public static Subcommands fromString(String input) {
        for (Subcommands cmd : values()) {
            if (cmd.subcommand.equalsIgnoreCase(input)) {
                return cmd;
            }
        }
        return null; // invalid subcommand
    }
}


