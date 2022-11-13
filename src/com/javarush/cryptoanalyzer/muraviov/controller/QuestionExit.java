package com.javarush.cryptoanalyzer.muraviov.controller;

import com.javarush.cryptoanalyzer.muraviov.constants.ConstantText;
import com.javarush.cryptoanalyzer.muraviov.scan.Scan;

public class QuestionExit {
    public static void questionExit() {
        System.out.print("-".repeat(80));
        String textQuestion = """
                \nВыберете команду которую Вы хотите выполнить?\s
                menu - что бы Продолжить\s
                exit - что бы Выйти\s
                \nДля выбора введите соответствующую команду""";

        System.out.println(textQuestion);
        System.out.println("-".repeat(80));
        while (true) {
            String numberFunction = Scan.scan().next();
            if (numberFunction.equalsIgnoreCase(ConstantText.MENU)) {
                QuestionStart.questionStart();
                break;
            } else if (numberFunction.equalsIgnoreCase(ConstantText.EXIT)) {
                System.out.println(ConstantText.GOODBYE);
                break;
            } else {
                System.err.println(ConstantText.NOTECORRECTY);
            }
        }
    }
}
