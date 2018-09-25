package office;

public enum RoomSize {
    LARGE,
    SMALL;

    public static RoomSize get(String str) {
        return str.equalsIgnoreCase("large") ? LARGE :
               str.equalsIgnoreCase("small") ? SMALL : null;
    }
}
