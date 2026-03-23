package pages;

import java.util.Scanner;

class ResetPasswordScreen {
    void show() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username: ");
        String user = sc.nextLine();

        System.out.print("Enter new password: ");
        String pass = sc.nextLine();

       ;
    }
}
