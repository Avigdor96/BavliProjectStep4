package Level4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Methods4 {
    //מקבלת מחרוזת ומחזירה איזה סוג המחרוזת(ריקה, מסכת, פרק, דף, או תוכן
    public static String typeLine(String line) {
        if (line.isEmpty()) {
            return "Empty";
        }
        if (line.startsWith("מסכת ") && line.contains("פרק א")) {
            return "Book";
        }
        if (line.startsWith("מסכת ") && line.contains("פרק ")) {
            return "Chapter";
        }
        if (line.startsWith("דף ") && (line.endsWith(" א") || line.endsWith(" ב")) && (line.split(" ").length < 4)) {
            return "Page";
        }
        if (line.startsWith("גמרא") || line.startsWith("משנה")) {
            return "Content";
        }
        return "";
    }

    // מקבלת מחרוזת שורה שלימה ומחזירה רק את שם המסכת
    public static String correctBookName(String line) {
        return line.substring(0, line.indexOf(" פרק"));
    }

    //מקבלת מחרוזת עם שם הדף ומחזירה את שם הדף בצורה תקינה
    public static String correctNamePage(String line) {
        return line.split(" ")[0] + " " + line.split(" ")[1];
    }

    //יוצרת תיקייה של שס ממויין לפי מסכתות, דפים, ועמודים וכותבת להם את התוכן
    public static String createBavliDirctory() {
        Bavli bavli = new Bavli();
        String path = "C:\\Users\\אביגדור\\OneDrive\\שולחן העבודה\\בבלי ממויין";
        File base = new File("C:\\Users\\אביגדור\\OneDrive\\שולחן העבודה\\בבלי ממויין");
        if (!base.exists()) {
            base.mkdir();

            String textPath = "C:\\Users\\אביגדור\\OneDrive\\שולחן העבודה\\__תלמוד בבלי טקסט_.txt";
            File fileOfText = new File(textPath);
            String currentBook = "";
            String currentPage = "";
            try {
                Scanner scanner = new Scanner(fileOfText);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    switch (Methods4.typeLine(line)) {
                        case "Book":
                            String correctBookName = correctBookName(line);
                            File newBook = new File(base, correctBookName);
                            if (!newBook.exists()) {
                                newBook.mkdir();
                            }
                            currentBook = newBook.getAbsolutePath().trim();
                            break;
                        case "Chapter":
                            //currentChapter = line.substring(line.indexOf("פרק"));
                        case "Page":
                            File fatherPage = new File(currentBook, correctNamePage(line));
                            if (!fatherPage.exists()) {
                                fatherPage.mkdir();
                            }

                            currentPage = currentBook + "/" + fatherPage.getName();
//
                            File file = new File(currentPage, line);
                            if (!file.exists()) {
                                file.createNewFile();
                            }

                            currentPage = file.getAbsolutePath().trim();
                            break;


                        case "Content":
                            FileWriter fw = new FileWriter(currentPage, true);
                            fw.write(line + "\n");
                            fw.close();
                            break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return path;
    }

    //מקבלת "בבלי" ויוצרת לו מערך של ספרים מסוג "ספר" וכן מערך של מחרוזות של שמות הספרים
    public static void createBookAndNames(Bavli bavli) {
        File file = new File(bavli.getPath());
        File[] files = file.listFiles();
        assert files != null;
        String[] namesBooks = new String[files.length];
        Book[] books = new Book[files.length];
        for (int i = 0; i < files.length; i++) {
            namesBooks[i] = files[i].getName();
            books[i] = new Book(files[i].getName());
            books[i].setPath(files[i].getPath());
        }
        bavli.setBooksNames(namesBooks);
        bavli.setBooks(books);
        createFatherPages(books);
    }

    //מקבלת מערך של ספרים ויוצרת לכל ספר מערך של דפים ומערך מחרוזות של שמות הדפים
    public static void createFatherPages(Book[] books) {
        for (int i = 0; i < books.length; i++) {
            File file = new File(books[i].getPath());
            File[] files = file.listFiles();
            String[] pageFathersNames = new String[files.length];
            Page[] pageFathers = new Page[files.length];
            for (int j = 0; j < pageFathersNames.length; j++) {
                pageFathersNames[j] = (files[j].getName());
                pageFathers[j] = new Page(files[j].getName());
                pageFathers[j].setPath(files[j].getPath());
            }
            books[i].setPages(pageFathers);
            books[i].setPagesName(pageFathersNames);
            createAmuds(pageFathers);
        }
    }

    // מקבלת מערך של דפים ויוצרת לכל דף מערך של עמודים ומערך מחרוזות של שמות העמודים
    // ועדכון האם יש משנה בעמוד
    public static void createAmuds(Page[] pages) {
        for (int i = 0; i < pages.length; i++) {
            File file = new File(pages[i].getPath());
            File[] files = file.listFiles();
            String[] amudsNames = new String[files.length];
            Amud[] amuds = new Amud[files.length];
            for (int j = 0; j < amudsNames.length; j++) {
                amudsNames[j] = files[j].getName();
                amuds[j] = new Amud(files[j].getName());
                amuds[j].setPath(files[j].getPath());
                File file1 = new File(amuds[j].getPath());
                try {
                    Scanner scanner = new Scanner(file1);
                    // עדכון כל עמוד האם יש משנה
                    while (scanner.hasNextLine()) {
                        if (scanner.nextLine().contains("משנה")) {
                            amuds[j].setIfMishnha(true);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            pages[i].setAmuds(amuds);
            pages[i].setAmudsNames(amudsNames);
        }
    }

    //מקבלת שם של ספר ומערך של שמות הספרים ומחזירה את מיקום הספר במערך ע"י חיפוש בינארי
    public static int exportLocationBook(String bookName, String[] booksNames) {
        int location = binarySearch(booksNames, bookName);
        return location;
    }

    //מקבלת "בבלי" מבקשת מהמשתמש שם של מסכת עד שהמסכת תקינה ומחזירה את מיקומו במערך
    public static int checkValidBook(Bavli bavli) {
        System.out.println("Enter name of book: ");
        Scanner scanner = new Scanner(System.in);
        String name = "מסכת " + scanner.nextLine();
        int i = exportLocationBook(name, bavli.getBooksNames());
        if (i == -1) {
            System.out.println("Book not found enter again.");
            return checkValidBook(bavli);
        }
        return i;
    }

    //מקבלת שם של דף ומערך של שמות הדפים של המסכת ומחזירה את מיקום הדף במערך ע"י חיפוש בינארי
    public static int exportLocationPage(String pageName, String[] pagesNames) {
        int location = binarySearch(pagesNames, pageName);
        return location;
    }

    //בדיקת תקינות דף והחזרת מיקומו על ידי פונקציה
    public static int checkValidPage(Bavli bavli, int bookLocation) {
        System.out.println("Enter name of page: ");
        Scanner scanner = new Scanner(System.in);
        String page = "דף " + scanner.nextLine();
        int i = exportLocationPage(page, bavli.getBooks()[bookLocation].getPagesName());
        if (i == -1) {
            System.out.println("Page not found enter again.");
            return checkValidPage(bavli, bookLocation);
        }
        return i;
    }

    //הדפסת תוכן דף
    public static void printPage(File file) {
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file1 = files[i];
            try {
                Scanner scanner1 = new Scanner(file1);
                while (scanner1.hasNextLine()) {
                    String line = scanner1.nextLine();
                    System.out.println(line);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    //חיפוש בינארי של מחרוזת בתוך מערך של מחרוזות והחזרת מיקומו במערך
    public static int binarySearch(String[] strings, String string) {
        Arrays.sort(strings);
        int mid = 0;
        int low = 0;
        int high = strings.length - 1;
        while (high >= low) {
            mid = (low + high) / 2;
            int x = strings[mid].compareTo(string);
            if (x == 0) {
                return mid;
            } else if (x < 0) {
                low = mid + 1;
            } else if (x > 0) {
                high = mid - 1;
            }
        }
        return -1;
    }

    // מקבלת דף ובודקת האם הוא מכיל משנה ומעדכנת את הדף בהתאם
    public static void catchMishnha(Page page) {
        File file1 = new File(page.getName());
        try {
            Scanner scanner = new Scanner(file1);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("משנה ")) {
                    page.setIfMishna(true);
                } else {
                    page.setIfMishna(false);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //יצירת מערך אינדקס של פרקים של כל השס
    public static String[] indexChapters(Bavli bavli) {
        String[] chapterIndex = new String[bavli.getBooksNames().length]; // מערך בגודל מספר המסכתות
        File file = new File("C:\\Users\\אביגדור\\OneDrive\\שולחן העבודה\\__תלמוד בבלי טקסט_.txt");
        try {
            int i = -1;        // יאותחל לאפס כשימצא מסכת ראשונה ויעלה כל פעם באחד שיש מסכת חדשה
            String currentBook = "";          // יאותחל כל מסכת חדשה
            String currentChapter = "";      // יאותחל כל פרק חדש
            String currentPage = "";        // יאותחל כל דף חדש
            String res = "";               // יאותחל בכל דף לשם המסכת + שם פרק + שם דף
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("מסכת ") && line.contains("פרק א")) {
                    currentBook = line.substring(line.indexOf(" "), line.indexOf("פרק")).trim();
                    i += 1;
                    chapterIndex[i] = ""; // אתחול תא במערך למחרוזת
                }
                if (line.contains("פרק ") && line.split(" ").length <= 5) {
                    currentChapter = line.substring(line.indexOf("פרק ")).trim();
                }
                if (line.startsWith("דף ") && (line.split(" ").length < 4) && line.endsWith(" א")) {
                    currentPage = line.split(" ")[0] + " " + line.split(" ")[1].trim();
                    res = currentBook + " " + currentChapter + " " + currentPage + " ,"; // נדרס כל דף חדש
                    if (!chapterIndex[i].contains(res)) {
                        chapterIndex[i] += res; // נקבל בסוף בכל תא: מחרוזת אחת עם פרטים על כל דף לאיזה פרק הוא
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return chapterIndex;
    }


    // מקבלת מיקום ספר, שם של פרק, ומערך אינדקס פרקים כולל ומחזירה מערך של דפי הפרק בלבד
    public static String[] catchChapter(int bookLocation, String chapterName, String[] strings) {
        // יצירת מערך בגודל הפרקים של המסכת בכל תא מופיעים כל דפי הפרק
        String[] allChapter = strings[bookLocation].split(" ,");
        String res = "";
        // מוסיף לכל דף בפרק המבוקש סימן "," כדי להפוך אותו למערך של דפים בספליט
        for (int i = 0; i < allChapter.length; i++) {
            if (allChapter[i].contains(chapterName)) {
                res += allChapter[i] + ",";
            }
        }
        String[] res2 = res.split(",");
        //System.out.println(Arrays.toString(res2));
        return res2;
    }

    // בדיקת תקינות פרק על ידי חיפוש באינדקס הפרקים של המסכת המבוקשת
    public static String checkValidChapter(int bookNum, String[] indexOfChapter) {
        System.out.println("Enter name of chapter:");
        Scanner scanner = new Scanner(System.in);
        String chapterName = scanner.nextLine();
        // יצירת אינדקס פרקים ספיצפי למסכת המבוקשת
        String[] allChapter = indexOfChapter[bookNum].split(" ,");
        String chapterRes = null;
        for (int i = 0; i < allChapter.length; i++) {
            if (allChapter[i].contains(chapterName)) {
                chapterRes = chapterName;
            }
        }
        // אם לא קיים, חזרה לתחילת הפונקציה
        if (chapterRes == null) {
            System.out.println("Chapter is not valid enter again:");
            return checkValidChapter(bookNum, indexOfChapter);
        }
        return chapterRes;
    }

    //מקבלת "בבלי", מיקום ספר, מיקום תחילת פרק, מיקום סוף פרק, ומדפיסה את כל הפרק
    public static void printAll(Bavli bavli, int numBook, int startChapter, int endChapter) throws FileNotFoundException {
        for (int j = startChapter; j < endChapter + 1; j++) {
            for (int i = 0; i < bavli.getBooks()[numBook].getPages()[j].getAmuds().length; i++) {
                File file = new File(bavli.getBooks()[numBook].getPages()[j].getAmuds()[i].getPath());
                Scanner scanner1 = new Scanner(file);
                while (scanner1.hasNextLine()) {
                    System.out.println(scanner1.nextLine());
                }
            }
        }

    }

    //הדפסת משניות בלבד של פרק מסויים על ידי אינדקסים
    public static void printOnlyMishnha(Bavli bavli, int numBook, int startChapter, int endChapter) throws FileNotFoundException {
        for (int j = startChapter; j < endChapter + 1; j++) { // עובר רק על דפי הפרק
            for (int i = 0; i < bavli.getBooks()[numBook].getPages()[j].getAmuds().length; i++) {
                File file = new File(bavli.getBooks()[numBook].getPages()[j].getAmuds()[i].getPath());
                Scanner scanner1 = new Scanner(file);
                while (scanner1.hasNextLine()) {
                    String line = scanner1.nextLine();
                    if (line.startsWith("משנה")) {
                        System.out.println(line);
                    }
                }
            }
        }
    }

    //הדפסת גמרא בלבד של פרק מסויים על ידי אינדקסים
    public static void printOnlyGmara(Bavli bavli, int numBook, int startChapter, int endChapter) throws FileNotFoundException {
        for (int j = startChapter; j < endChapter + 1; j++) {
            for (int i = 0; i < bavli.getBooks()[numBook].getPages()[j].getAmuds().length; i++) {
                File file = new File(bavli.getBooks()[numBook].getPages()[j].getAmuds()[i].getPath());
                Scanner scanner1 = new Scanner(file);
                while (scanner1.hasNextLine()) {
                    String line = scanner1.nextLine();
                    if (line.startsWith("גמרא")) {
                        System.out.println(line);
                    }

                }
            }
        }
    }

    //הדפסת משניות של מסכת שלימה על ידי אינדקסים ובדיקה האם העמוד מכיל משנה
    public static void printAllMishnhaOfBook(Bavli bavli) throws FileNotFoundException {
        int numBook4 = Methods4.checkValidBook(bavli);
        for (int j = 0; j < bavli.getBooks()[numBook4].getPages().length; j++) {
            for (int k = 0; k < bavli.getBooks()[numBook4].getPages()[j].getAmuds().length; k++) {
                if (bavli.getBooks()[numBook4].getPages()[j].getAmuds()[k].existMishnha()) {
                    File file = new File(bavli.getBooks()[numBook4].getPages()[j].getAmuds()[k].getPath());
                    Scanner scanner1 = new Scanner(file);
                    while (scanner1.hasNextLine()) {
                        String line = scanner1.nextLine();
                        if (line.startsWith("משנה")) {
                            System.out.println(line);
                        }
                    }
                }
            }
        }
    }

    //הדפסת גמרא בלבד של מסכת מסויימת
    public static void printAllGmaraOfBook(Bavli bavli) throws FileNotFoundException {
        int numBook4 = Methods4.checkValidBook(bavli);
        for (int j = 0; j < bavli.getBooks()[numBook4].getPages().length; j++) {
            for (int k = 0; k < bavli.getBooks()[numBook4].getPages()[j].getAmuds().length; k++) {
//                if (bavli.getBooks()[numBook4].getPages()[j].getAmuds()[k].existMishnha()) {
                File file = new File(bavli.getBooks()[numBook4].getPages()[j].getAmuds()[k].getPath());
                Scanner scanner1 = new Scanner(file);
                while (scanner1.hasNextLine()) {
                    String line = scanner1.nextLine();
                    if (line.startsWith("גמרא")) {
                        System.out.println(line);
                    }
                }
//                }
            }
        }
    }
}
