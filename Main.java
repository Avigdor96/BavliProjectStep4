package Level4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Bavli bavli = new Bavli();
        // יצירת תיקיות של השס ועדכון נתיב הבבלי
        bavli.setPath(Methods4.createBavliDirctory());
        // יצירת אינדקס ספרים, שמות ספרים וע"י פונקציה פנימית אינדקס דפים ושמות דפים
        Methods4.createBookAndNames(bavli);
        // יצירת אינדקס פרקים
        String[] chapterIndex = Methods4.indexChapters(bavli);
        bavli.setChapterIndex(chapterIndex);
        Arrays.sort(chapterIndex);
        boolean flag = true;
        while (flag) {
            System.out.println("הקש 1 כדי להדפיס פרק מלא:");
            System.out.println("הקש 2 כדי להדפיס רק משניות של פרק מסויים:");
            System.out.println("הקש 3 כדי להדפיס רק גמרא של פרק מסויים:");
            System.out.println("הקש 4 כדי להדפיס רק משניות של מסכת:");
            System.out.println("הקש 5 כדי להדפיס רק גמרא של מסכת:");
            System.out.println("הקש 6 כדי לצאת מהתכנית:");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    // בקשת שם מסכת עד לקבלת שם תקין וקבלת המיקום
                    int numBook = Methods4.checkValidBook(bavli);
                    // בקשת שם פרק עד לקבלת פרק תקין במסכת זו
                    String chapterName = Methods4.checkValidChapter(numBook, chapterIndex);
                    // יצירת מערך של דפי הפרק הספציפי בלבד
                    String[] pagesOfChapter = Methods4.catchChapter(numBook, chapterName, chapterIndex);
                    // חילוץ המיקום של הדף הראשון בפרק ע"י חיפוש באינדקס המסכת
                    int startChapter = Methods4.exportLocationPage(pagesOfChapter[0].substring(pagesOfChapter[0].indexOf("דף")), bavli.getBooks()[numBook].getPagesName());
                    // חילוץ המיקום של הדף האחרון בפרק ע"י חיפוש באינדקס המסכת
                    int endChapter = Methods4.exportLocationPage(pagesOfChapter[pagesOfChapter.length - 1].substring(pagesOfChapter[pagesOfChapter.length - 1].indexOf("דף")), bavli.getBooks()[numBook].getPagesName());
                    // הדפסת הפרק המבוקש ע"י גישה לאינדקסים מדוייקים
                    Methods4.printAll(bavli, numBook, startChapter, endChapter);
                    break;
                case 2:
                    // בקשת שם מסכת עד לקבלת שם תקין וקבלת המיקום
                    int numBook2 = Methods4.checkValidBook(bavli);
                    // בקשת שם פרק עד לקבלת פרק תקין במסכת זו
                    String chapterName2 = Methods4.checkValidChapter(numBook2, chapterIndex);
                    // יצירת מערך של דפי הפרק הספציפי בלבד
                    String[] pagesOfChapter2 = Methods4.catchChapter(numBook2, chapterName2, chapterIndex);
                    // חילוץ המיקום של הדף הראשון בפרק ע"י חיפוש באינדקס המסכת
                    int startChapter2 = Methods4.exportLocationPage(pagesOfChapter2[0].substring(pagesOfChapter2[0].indexOf("דף")), bavli.getBooks()[numBook2].getPagesName());
                    // חילוץ המיקום של הדף האחרון בפרק ע"י חיפוש באינדקס המסכת
                    int endChapter2 = Methods4.exportLocationPage(pagesOfChapter2[pagesOfChapter2.length - 1].substring(pagesOfChapter2[pagesOfChapter2.length - 1].indexOf("דף")), bavli.getBooks()[numBook2].getPagesName());
                    // הדפסת משניות בלבד של הפרק המבוקש
                    Methods4.printOnlyMishnha(bavli, numBook2, startChapter2, endChapter2 - 1);
                    break;
                case 3:
                    int numBook3 = Methods4.checkValidBook(bavli);
                    String chapterName3 = Methods4.checkValidChapter(numBook3, chapterIndex);
                    String[] pagesOfChapter3 = Methods4.catchChapter(numBook3, chapterName3, chapterIndex);
                    int startChapter3 = Methods4.exportLocationPage(pagesOfChapter3[0].substring(pagesOfChapter3[0].indexOf("דף")), bavli.getBooks()[numBook3].getPagesName());
                    int endChapter3 = Methods4.exportLocationPage(pagesOfChapter3[pagesOfChapter3.length - 1].substring(pagesOfChapter3[pagesOfChapter3.length - 1].indexOf("דף")), bavli.getBooks()[numBook3].getPagesName());
                    Methods4.printOnlyGmara(bavli, numBook3, startChapter3, endChapter3 - 1);
                    break;
                case 4:
                    Methods4.printAllMishnhaOfBook(bavli);
                    break;
                case 5:
                    Methods4.printAllGmaraOfBook(bavli);
                    break;
                    // יציאה
                case 6:
                    flag = false;
                    break;
            }
        }


    }
}
