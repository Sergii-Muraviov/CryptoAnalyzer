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

public class BruteForce {public static void bruteForce() {
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
                inPath = Path.of(PathFinder.getRoot() + ConstantText.ENCRYPTNAMEFILE);
            }

            if (Files.exists(inPath)) {
                String[] keyWord = new String[]{"может", "нет", "как", "или", "все", "это", "вам", "вас", "что"};
                int key = 0;
                boolean flag = true;

                while (key < Alphabet.fullAlphabet.length) {
                    FileInputStream fis1 = new FileInputStream(String.valueOf(inPath));
                    Reader reader1 = new InputStreamReader(fis1);
                    BufferedReader bufferedReader1 = new BufferedReader(reader1);
                    Writer writer1 = new FileWriter(inPath.getParent() + File.separator + ConstantText.BRUTEFORCENAMEFILE);
                    while (bufferedReader1.ready()) {
                        String temp = bufferedReader1.readLine();
                        char[] value = temp.toCharArray();

                        for (char c : value) {
                            for (int i = 0; i < Alphabet.fullAlphabet.length; i++) {
                                if (Alphabet.fullAlphabet[i] == c) {
                                    int res = (i - key) % Alphabet.fullAlphabet.length;
                                    if (res > 0) {
                                        writer1.write(Alphabet.fullAlphabet[res]);
                                    } else {
                                        writer1.write(Alphabet.fullAlphabet[Alphabet.fullAlphabet.length - Math.abs(res) - 1]);
                                    }
                                }
                            }
                        }
                        writer1.write(Alphabet.JUMP);
                    }
                    fis1.close();
                    reader1.close();
                    bufferedReader1.close();
                    writer1.close();
                    FileInputStream fis2 = new FileInputStream(String.valueOf(Path.of(PathFinder.getRoot() + ConstantText.BRUTEFORCENAMEFILE)));
                    Reader reader2 = new InputStreamReader(fis2);
                    BufferedReader bufferedReader2 = new BufferedReader(reader2);
                    while (bufferedReader2.ready()) {
                        String[] str = bufferedReader2.readLine().split("\"");
                        for (String b : str) {
                            if (b.equalsIgnoreCase(keyWord[0]) ||
                                    b.equalsIgnoreCase(keyWord[1]) ||
                                    b.equalsIgnoreCase(keyWord[2]) ||
                                    b.equalsIgnoreCase(keyWord[3]) ||
                                    b.equalsIgnoreCase(keyWord[4]) ||
                                    b.equalsIgnoreCase(keyWord[5]) ||
                                    b.equalsIgnoreCase(keyWord[6]) ||
                                    b.equalsIgnoreCase(keyWord[7]) ||
                                    b.equalsIgnoreCase(keyWord[8])) {
                                flag = false;
                                break;
                            }
                        }
                    }
                    fis2.close();
                    reader2.close();
                    bufferedReader2.close();
                    if (!flag) {
                        break;
                    }
                    key++;
                }

                try {
                    System.err.println("Подобран ключ - " + key);
                    FileInputStream fis3 = new FileInputStream(String.valueOf(inPath));
                    Reader reader3 = new InputStreamReader(fis3);
                    BufferedReader bufferedReader3 = new BufferedReader(reader3);
                    Writer writer2 = new FileWriter(inPath.getParent() + File.separator + ConstantText.BRUTEFORCENAMEFILE);
                    while (bufferedReader3.ready()) {
                        char[] value = bufferedReader3.readLine().toCharArray();
                        for (char c : value) {
                            for (int i = 0; i < Alphabet.fullAlphabet.length; i++) {
                                if (Alphabet.fullAlphabet[i] == c) {
                                    int res = (i - key) % Alphabet.fullAlphabet.length;
                                    if (res > 0) {
                                        writer2.write(Alphabet.fullAlphabet[res]);
                                    } else {
                                        writer2.write(Alphabet.fullAlphabet[Alphabet.fullAlphabet.length - Math.abs(res)]);
                                    }
                                }
                            }
                        }
                        writer2.write(Alphabet.JUMP);
                    }
                    fis3.close();
                    reader3.close();
                    bufferedReader3.close();
                    writer2.close();
                    System.out.println("Выполнено! \nСоздан файл " + ConstantText.BRUTEFORCENAMEFILE + " по адресу " + inPath.getParent() + File.separator);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Возникла ошибка при выполнении операции. Ключ к зашифрованному тексту - " + (key + 1) + ". \nВоспользуйся функцией Расшифровка текста с помощью ключа");
                }

                QuestionExit.questionExit();
            } else if (Files.isDirectory(inPath) || !Files.exists(inPath)) {
                System.err.println(ConstantText.NOTENTEREDCORRECTY);
                BruteForce.bruteForce();
            }
            break;
        }
    } catch (IOException e) {
        throw new ApplicationException(e);
    }
}
}
