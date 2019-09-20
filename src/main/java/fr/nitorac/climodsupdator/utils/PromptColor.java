package fr.nitorac.climodsupdator.utils;

public enum PromptColor {
    RESET(-1, "\u001B[0m"),
    BLACK(0, "\u001B[30m"),
    RED(1, "\u001B[31m"),
    GREEN(2, "\u001B[32m"),
    YELLOW(3, "\u001B[33m"),
    BLUE(4, "\u001B[34m"),
    MAGENTA(5, "\u001B[35m"),
    CYAN(6, "\u001B[36m"),
    WHITE(7, "\u001B[37;1m"),
    BRIGHT(8, "\u001B[37m");

    private final int value;
    private final String ansi;

    PromptColor(int value, String ansi) {
        this.value = value;
        this.ansi = ansi;
    }

    public String toAnsi() {
        return this.ansi;
    }

    @Override
    public String toString() {
        return toAnsi();
    }

    public int toJlineAttributedStyle() {
        return this.value;
    }
}