package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UtilsFonction {

    public static <T> void displayDataInTable(List<T> dataList, JTable jTable) {
        displayDataInTable(dataList, jTable, new ArrayList<>());
    }

    public static <T> void displayDataInTable(List<T> dataList, JTable jTable, List<String> columnsToOmit) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }

        Class<?> itemClass = dataList.get(0).getClass();
        Field[] allFields = itemClass.getDeclaredFields();
        List<Field> fields = new ArrayList<>();

        for (Field field : allFields) {
            if (!Modifier.isStatic(field.getModifiers()) && !columnsToOmit.contains(field.getName())) {
                fields.add(field);
            }
        }

        String[] columnNames = new String[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            columnNames[i] = camelCaseToText(fields.get(i).getName());
        }
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (T item : dataList) {
            Object[] rowData = new Object[fields.size()];
            for (int i = 0; i < fields.size(); i++) {
                try {
                    Field field = fields.get(i);
                    field.setAccessible(true);
                    Object fieldValue = field.get(item);

                    if (fieldValue instanceof Date) {
                        rowData[i] = dateFormat.format((Date) fieldValue);
                    } else {
                        rowData[i] = fieldValue;
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            model.addRow(rowData);
        }
        jTable.setModel(model);
    }

    public static String camelCaseToText(String camelCaseString) {
        if (camelCaseString == null || camelCaseString.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append(Character.toUpperCase(camelCaseString.charAt(0)));

        for (int i = 1; i < camelCaseString.length(); i++) {
            char currentChar = camelCaseString.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                result.append(' ');
                result.append(Character.toLowerCase(currentChar));
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }

    public static String formatNumber(String text) {
        // Enlever les espaces éventuels du numéro de téléphone
        String cleanText = text.replaceAll("[^\\d]", "");

        // Ajouter des zéros au début si nécessaire pour obtenir une chaîne de 9 caractères
        while (cleanText.length() < 9) {
            cleanText = "0" + cleanText;
        }

        // Ajouter les espaces selon le format demandé
        String formattedText = cleanText.substring(0, 2) + " "
                + cleanText.substring(2, 5) + " "
                + cleanText.substring(5, 7) + " "
                + cleanText.substring(7, 9);

        return formattedText;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Enlever les espaces éventuels du numéro de téléphone
        String cleanPhoneNumber = phoneNumber.replaceAll("[^\\d]", "");

        if (cleanPhoneNumber.length() != 9) {
            return false;
        }

        String prefix = cleanPhoneNumber.substring(0, 2);
        return Arrays.asList("77", "78", "76", "75", "72", "70").contains(prefix);
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }

       public static String encrypt(String str) {
        int code;
        var result = new StringBuilder();
        final String SEPARATOR = ";";
        for (int i = 0; i < str.length(); i++) {
            code = Math.round((float) Math.random() * 8 + 1);
            result.append(code).append(Integer.toHexString(((int) str.charAt(i)) ^ code)).append(SEPARATOR);
        }
        return result.substring(0, result.length() - SEPARATOR.length());
    }

    public static String decrypt(String str) {
        final String SEPARATOR = ";";
        String[] tokens = str.split(SEPARATOR);
        var result = new StringBuilder();
        for (String token : tokens) {
            var hex = token.substring(1);
            result.append((char) (Integer.parseInt(hex, 16) ^ (Integer.parseInt(String.valueOf(token.charAt(0))))));
        }
        return result.toString();
    }

}
