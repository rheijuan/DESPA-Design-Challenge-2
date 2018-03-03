package model;

public enum Color {

    Green, Red, Blue;

    public String toString() {
        switch(this) {
            case Green: return "green";
            case Red: return "red";
            case Blue: return "blue";
            default: return "black";
        }
    }

    public static Color toColor(String color) {
        switch(color.toLowerCase()) {
            case "green": return Green;
            case "red": return Red;
            case "blue": return Blue;
            default: return Green;
        }
    }
}
