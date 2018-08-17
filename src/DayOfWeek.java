public enum DayOfWeek {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;

    public boolean isDayOff() {
        return this == SUNDAY || this == SATURDAY;
    }

    public DayOfWeek next() {
        int num = this.ordinal() + 1;

        return num == 7 ? get(1) : get(++num);
    }

    public DayOfWeek before() {
        int num = this.ordinal() + 1;

        return num == 1 ? get(7) : get(--num);
    }

    public static DayOfWeek get(int dowNumber) {
        DayOfWeek[] values = DayOfWeek.values();
        return values[dowNumber - 1];
    }
}