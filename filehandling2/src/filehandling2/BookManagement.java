package filehandling2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BookManagement {

    static File file = new File("Book.text");
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        boolean flag = true;

        while (flag) {

            System.out.println("---- Main Menu ----");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Find Book");
            System.out.println("4. Display Book");
            System.out.println("5. Update Book");
            System.out.println("6. Exit");
            System.out.println("-------------------");

            System.out.print("Enter Your Choice: ");
            int ch = sc.nextInt();

            switch (ch) {

                case 1:
                    addBook();
                    break;

                case 2:
                    removeBook();
                    break;

                case 3:
                    findBookById();
                    break;

                case 4:
                    findAllBooks();
                    break;

                case 5:
                    updateBook();
                    break;

                case 6:
                    flag = false;
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice");
            }
        }

        sc.close();
    }

    // ================= ADD BOOK =================

    private static void addBook() throws IOException {

        System.out.print("Enter Book Id: ");
        int id = sc.nextInt();

        System.out.print("Enter Book Name: ");
        String name = sc.next();

        System.out.print("Enter Book Author: ");
        String author = sc.next();

        System.out.print("Enter Book Price: ");
        double price = sc.nextDouble();

        Book book = new Book(id, name, author, price);

        FileWriter writer = new FileWriter(file, true);

        writer.write(book.toString());
        writer.write(System.lineSeparator());

        writer.close();

        System.out.println("Book Added Successfully!");
    }

    // ================= DISPLAY ALL BOOKS =================

    private static void findAllBooks() throws FileNotFoundException {

        Scanner scanner = new Scanner(file);

        if (!scanner.hasNextLine()) {
            System.out.println("No Books Available.");
            scanner.close();
            return;
        }

        System.out.println("----- All Books -----");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
        }

        System.out.println("---------------------");

        scanner.close();
    }

    // ================= FIND BOOK BY ID =================

    private static void findBookById() throws FileNotFoundException {

        System.out.print("Enter Book Id: ");
        int id = sc.nextInt();

        Scanner scanner = new Scanner(file);

        boolean found = false;

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            String data[] = line.split(",");

            String idData[] = data[0].split("=");

            int bookId = Integer.parseInt(idData[1]);

            if (bookId == id) {

                System.out.println("Book Found!");
                System.out.println(line);

                found = true;
                break;
            }
        }

        scanner.close();

        if (!found) {
            System.out.println("Book Not Found!");
        }
    }

    // ================= REMOVE BOOK =================

    private static void removeBook() throws IOException {

        System.out.print("Enter Book Id to Remove: ");
        int id = sc.nextInt();

        Scanner scanner = new Scanner(file);

        File tempFile = new File("tempdata.text");

        FileWriter writer = new FileWriter(tempFile);

        boolean found = false;

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            String data[] = line.split(",");

            String idData[] = data[0].split("=");

            int bookId = Integer.parseInt(idData[1]);

            if (bookId == id) {
                found = true;
                continue;
            }

            writer.write(line);
            writer.write(System.lineSeparator());
        }

        scanner.close();
        writer.close();

        if (found) {

            file.delete();

            if (tempFile.renameTo(file)) {
                System.out.println("Book Removed Successfully!");
            } else {
                System.out.println("Unable to update file.");
            }

        } else {

            tempFile.delete();

            System.out.println("Book Not Found!");
        }
    }

    // ================= UPDATE BOOK =================

    private static void updateBook() throws IOException {

        System.out.print("Enter Book Id to Update: ");
        int id = sc.nextInt();

        Scanner scanner = new Scanner(file);

        File tempFile = new File("tempdata.text");

        FileWriter writer = new FileWriter(tempFile);

        boolean found = false;

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            String data[] = line.split(",");

            String idData[] = data[0].split("=");

            int bookId = Integer.parseInt(idData[1]);

            if (bookId == id) {

                found = true;

                System.out.println("Enter New Book Details:");

                System.out.print("Enter Book Name: ");
                String name = sc.next();

                System.out.print("Enter Book Author: ");
                String author = sc.next();

                System.out.print("Enter Book Price: ");
                double price = sc.nextDouble();

                Book updatedBook =
                        new Book(id, name, author, price);

                writer.write(updatedBook.toString());
                writer.write(System.lineSeparator());

            } else {

                writer.write(line);
                writer.write(System.lineSeparator());
            }
        }

        scanner.close();
        writer.close();

        if (found) {

            file.delete();

            if (tempFile.renameTo(file)) {
                System.out.println("Book Updated Successfully!");
            } else {
                System.out.println("Unable to update file.");
            }

        } else {

            tempFile.delete();

            System.out.println("Book Not Found!");
        }
    }
}