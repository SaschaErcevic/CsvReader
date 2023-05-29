package com.app.util;

import java.util.List;

public class StringFormatter {

    public String buildLine(List<String> line, String component, List<Integer> columnWidths) {
        StringBuilder sb = new StringBuilder();

        switch (component) {
            case "row", "header":
                for (int i = 0; i < line.size(); i++) {
                    sb.append(line.get(i));
                    sb.append(" ".repeat(Math.max(0, columnWidths.get(i) - line.get(i).length())));
                    sb.append("|");
                }
                break;
            case "seperator":
                for (Integer columnWidth : columnWidths) {
                    sb.append("-".repeat(Math.max(0, columnWidth)));
                    sb.append("|");
                }
                break;
            default:
                return "";
        }
        return sb.toString();
    }
}
