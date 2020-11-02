package com.qyai.beaconlib.bean;


import com.qyai.beaconlib.Interface.CommandResult;

public class OperatingController {
    public int value;
    public String msg;

    public CommandResult commandResult;

    public OperatingController(int value) {
        this.value = value;
    }

    public OperatingController(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public OperatingController(int value, String msg, CommandResult commandResult) {
        this.value = value;
        this.msg = msg;
        this.commandResult = commandResult;
    }

    public OperatingController(int value, CommandResult commandResult) {
        this.value = value;
        this.commandResult = commandResult;
    }
}
