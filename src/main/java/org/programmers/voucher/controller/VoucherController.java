package org.programmers.voucher.controller;

import org.programmers.voucher.io.Input;
import org.programmers.voucher.io.Output;
import org.programmers.voucher.service.VoucherService;
import org.programmers.voucher.util.Command;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController implements Runnable {

    private final VoucherService voucherService;
    private final Input input;
    private final Output output;

    public VoucherController(VoucherService voucherService, Input input, Output output) {
        this.voucherService = voucherService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        output.startProgram();
        Command cmd = null;
        do {
            output.listCommand();
            try {
                cmd = parseCommand();
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal Argument");
            }
        } while (!Command.EXIT.equals(cmd));
    }

    private Command parseCommand() throws IllegalArgumentException {
        Command command = input.inputCommand();
        switch (command) {
            case EXIT:
                break;
            case CREATE:
                output.listVoucherType();
                voucherService.makeVoucher(input.inputVoucherType(), input.inputValue());
                break;
            case LIST:
                output.listVoucher(voucherService.listVoucher());
                break;
        }
        return command;
    }
}
