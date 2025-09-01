package Controller;

import Model.ModelCadet;
import Model.ModelTrainingRecord;
import Model.ModelAchievement;
import Service.ServiceCadetAndTraining;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ControllerCadetAndTraining {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ServiceCadetAndTraining service = new ServiceCadetAndTraining();

        boolean isAdmin = false;

        // ✅ Admin Login using database
        System.out.println("===== ADMIN LOGIN =====");
        System.out.print("Enter Admin Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = sc.nextLine();

        if (service.loginAdmin(username, password)) {
            isAdmin = true;
            System.out.println("✅ Login successful!");
        } else {
            System.out.println("❌ Invalid credentials. Exiting...");
            return;
        }

        while (isAdmin) {
            System.out.println("\n====== NCC Cadet Management ======");
            System.out.println("1. Add Cadet");
            System.out.println("2. View All Cadets");
            System.out.println("3. Add Training Record");
            System.out.println("4. View All Training Records");
            System.out.println("5. Add Achievement");
            System.out.println("6. View All Achievements");
            System.out.println("7. Delete Cadet");
            System.out.println("8. Delete Training Record");
            System.out.println("9. Delete Achievement");
            System.out.println("10. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Rank: ");
                    String rank = sc.nextLine();
                    System.out.print("Enter Unit: ");
                    String unit = sc.nextLine();
                    System.out.print("Enter Events Attended: ");
                    int events = sc.nextInt();
                    ModelCadet cadet = new ModelCadet(id, name, age, rank, unit, events);
                    service.addCadet(cadet);
                    break;

                case 2:
                    List<ModelCadet> cadets = service.getAllCadets();
                    for (ModelCadet c : cadets) {
                        System.out.println(c);
                    }
                    break;

                case 3:
                    System.out.print("Enter Cadet ID: ");
                    int cadetId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Camp Name: ");
                    String camp = sc.nextLine();
                    System.out.print("Enter Location: ");
                    String loc = sc.nextLine();
                    System.out.print("Enter Date (yyyy-mm-dd): ");
                    LocalDate date1 = LocalDate.parse(sc.nextLine());
                    ModelTrainingRecord record = new ModelTrainingRecord(0, cadetId, camp, loc, date1);
                    service.addTrainingRecord(record);
                    break;

                case 4:
                    List<ModelTrainingRecord> records = service.getAllTrainingRecords();
                    for (ModelTrainingRecord r : records) {
                        System.out.println(r);
                    }
                    break;

                case 5:
                    System.out.print("Enter Cadet ID: ");
                    int achCadetId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Description: ");
                    String desc = sc.nextLine();
                    System.out.print("Enter Date (yyyy-mm-dd): ");
                    LocalDate date2 = LocalDate.parse(sc.nextLine());
                    ModelAchievement achievement = new ModelAchievement(0, achCadetId, title, desc, date2);
                    service.addAchievement(achievement);
                    break;

                case 6:
                    List<ModelAchievement> achievements = service.getAllAchievements();
                    for (ModelAchievement a : achievements) {
                        System.out.println(a);
                    }
                    break;

                case 7:
                    System.out.print("Enter Cadet ID to Delete: ");
                    int delCadetId = sc.nextInt();
                    service.deleteCadetById(delCadetId);
                    break;

                case 8:
                    System.out.print("Enter Training Record ID to Delete: ");
                    int delTrainingId = sc.nextInt();
                    service.deleteTrainingRecordById(delTrainingId);
                    break;

                case 9:
                    System.out.print("Enter Achievement ID to Delete: ");
                    int delAchId = sc.nextInt();
                    service.deleteAchievementById(delAchId);
                    break;

                case 10:
                    System.out.println("Exiting...");
                    isAdmin = false;
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }
}
