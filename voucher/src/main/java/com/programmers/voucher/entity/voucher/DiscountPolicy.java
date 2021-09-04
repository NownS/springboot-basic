package com.programmers.voucher.entity.voucher;

import java.io.Serializable;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class DiscountPolicy implements Serializable {

    public enum Type {
        FIXED(
                (price, discount) -> Math.max(price - discount, 0),
                input -> Math.max(input, 0)),
        PERCENTAGE(
                (price, discount) -> Math.min(price * (100 - discount) / 100, price),
                input -> Math.min(Math.max(input, 0), 100)),
        UNKNOWN(
                (price, discount) -> {throw new UnsupportedOperationException("Unknown discount policy type.");},
                input -> {throw new UnsupportedOperationException("Unknown discount policy type.");});

        BinaryOperator<Integer> operation;
        UnaryOperator<Integer> constraint;

        public BinaryOperator<Integer> getOperation() {
            return operation;
        }

        public UnaryOperator<Integer> getConstraint() {
            return constraint;
        }

        Type(BinaryOperator<Integer> operation, UnaryOperator<Integer> constraint) {
            this.operation = operation;
            this.constraint = constraint;
        }

        public static Type of(String input) {
            try {
                return Type.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException ex) {
                return Type.UNKNOWN;
            }
        }
    }

    private int amount;
    private Type type;

    public DiscountPolicy(int amount, Type type) {
        this.amount = amount;
        this.type = type;
    }

    public int discount(int price) {
        return type.operation.apply(price, amount);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        amount = this.type.constraint.apply(amount);
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}