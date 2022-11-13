package com.javarush.cryptoanalyzer.muraviov.commands;

import com.javarush.cryptoanalyzer.muraviov.constants.Alphabet;
import com.javarush.cryptoanalyzer.muraviov.constants.ConstantText;
import com.javarush.cryptoanalyzer.muraviov.controller.QuestionExit;
import com.javarush.cryptoanalyzer.muraviov.controller.QuestionStart;
import com.javarush.cryptoanalyzer.muraviov.exception.ApplicationException;
import com.javarush.cryptoanalyzer.muraviov.scan.Scan;
import com.javarush.cryptoanalyzer.muraviov.util.PathFinder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Encrypt {
    public static void encrypt() {
        try {
            while (true) {
                System.out.println(ConstantText.ENTERTHEADDRESS);
                String scanRes = Scan.scan().nextLine();
                Path inPath;
                if (!scanRes.equals("")) {
                    if (scanRes.equalsIgnoreCase(ConstantText.EXIT)) {
                        System.out.println(ConstantText.GOODBYE);
                        break;
                    } else if (scanRes.equalsIgnoreCase(ConstantText.MENU)) {
                        QuestionStart.questionStart();
                        break;
                    }
                    inPath = Path.of(scanRes);
                } else {
                    inPath = Path.of(PathFinder.getRoot() + ConstantText.TEXT);
                }

                if (Files.exists(inPath)) {
                    FileInputStream fis = new FileInputStream(String.valueOf(inPath));
                    Reader reader = new InputStreamReader(fis);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    Writer writer = new FileWriter(inPath.getParent() + File.separator + ConstantText.ENCRYPTNAMEFILE);

                    System.out.println(ConstantText.NUMBERKEYE);
                    String numKey = Scan.scan().nextLine();
                    int key;
                    if (numKey.equalsIgnoreCase(ConstantText.EXIT)) {
                        System.out.println(ConstantText.GOODBYE);
                        break;
                    } else if (numKey.equalsIgnoreCase(ConstantText.MENU)) {
                        QuestionStart.questionStart();
                        break;
                    } else if (numKey.equalsIgnoreCase("")) {
                        key = (int) (Math.random() * 48) + 1;
                        System.err.println("Ваш ключ - " + key + ". Запомните его!");
                    } else {
                        key = Integer.parseInt(numKey);
                    }

                    while (bufferedReader.ready()) {
                        char[] value = bufferedReader.readLine().toCharArray();
                        for (char c : value) {
                            for (int i = 0; i < Alphabet.fullAlphabet.length; i++) {
                                if (Alphabet.fullAlphabet[i] == c) {
                                    int res = (i + key) % Alphabet.fullAlphabet.length;
                                    if (res < Alphabet.fullAlphabet.length) {
                                        writer.write(Alphabet.fullAlphabet[res]);
                                    } else {
                                        writer.write(Alphabet.fullAlphabet[res - Alphabet.fullAlphabet.length]);
                                    }
                                }
                            }
                        }
                        writer.write(Alphabet.JUMP);
                    }
                    fis.close();
                    reader.close();
                    bufferedReader.close();
                    writer.close();
                    System.out.println("Выполнено! \nСоздан файл " + ConstantText.ENCRYPTNAMEFILE + " по адресу " + inPath.getParent() + File.separator);

                    QuestionExit.questionExit();
                } else if (Files.isDirectory(inPath) || !Files.exists(inPath)) {
                    System.err.println(ConstantText.NOTENTEREDCORRECTY);
                    Encrypt.encrypt();
                }
                break;
            }
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
