package org.prgrms.kdtspringdemo.application;

import org.prgrms.kdtspringdemo.CommandType;
import org.prgrms.kdtspringdemo.console.Console;
import org.prgrms.kdtspringdemo.console.CustomerOperator;
import org.prgrms.kdtspringdemo.console.VoucherOperator;
import org.prgrms.kdtspringdemo.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApp {
    private final VoucherOperator voucherOperator;
    private final CustomerOperator customerOperator;

    private Console console;

    public ConsoleApp(VoucherOperator voucherOperator, CustomerOperator customerOperator) {
        this.voucherOperator = voucherOperator;
        this.customerOperator = customerOperator;

        console = new Console();
    }

    public void run() {
        console.printStartAppInfo();

        while (true) {
            CommandType command = console.getInputCommand();
            switch (command) {
                case CREATE -> {
                    console.printCreateSelect();
                    String num = console.getCreateLine();
                    switch (num) {
                        case "1" -> {
                            boolean commandSuccess = false;
                            while (!commandSuccess) {
                                console.printCreateCustomerByTypes();
                                String[] createCommand = console.getCreateLine().split(" ");
                                commandSuccess = customerOperator.create(createCommand);
                            }
                        }
                        case "2" -> {
                            boolean commandSuccess = false;
                            while (!commandSuccess) {
                                console.printCreateVoucherByTypes();
                                String[] createCommand = console.getCreateLine().split(" ");
                                commandSuccess = voucherOperator.create(createCommand);
                            }
                        }
                    }
                }
                case LIST -> {
                    console.printListSelect();
                    String num = console.getCreateLine();
                    switch (num) {
                        case "1" -> console.printList(customerOperator.getAllitems());
                        case "2" -> console.printList(voucherOperator.getAllitems());
                    }
                }
                case BLACKS -> console.printList(customerOperator.getAllBlacklist());
                case DELETE -> {
                    console.printDeleteSelect();
                    String num = console.getCreateLine();
                    switch (num) {
                        case "1" -> {}
                        case "2" -> {
                            console.printDeleteVoucher();
                            String[] deleteCommand = console.getCreateLine().split(" ");
                            voucherOperator.delete(deleteCommand);
                        }
                    }
                }
                case FIND -> {
                    console.printFindSelect();
                    String num = console.getCreateLine();
                    switch (num) {
                        case "1" -> {
                            console.printFindCustomer();
                            String[] findCommand = console.getCreateLine().split(" ");
                            Customer customer = customerOperator.findCustomer(findCommand);
                            if (customer != null) console.printObject(customer);
                            else System.out.println("None!!!");
                        }
                        case "2" -> {}
                    }
                }
                case EXIT -> System.exit(0);
            }
        }
    }
}