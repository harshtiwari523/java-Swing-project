package Service;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Model.ModelCadet;
import Model.ModelTrainingRecord;
import Model.ModelAchievement;
import Model.ModelAdmin;

public class ServiceCadetAndTraining {

    private final String URL = "jdbc:mysql://localhost:3306/ncc_cadets";
    private final String USER = "root";
    private final String PASS = "TIGER";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // ===================== Cadet =====================
    public void addCadet(ModelCadet cadet) {
        String sql = "INSERT INTO cadet(id, name, age, `rank`, unit, events_attended) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cadet.getId());
            ps.setString(2, cadet.getName());
            ps.setInt(3, cadet.getAge());
            ps.setString(4, cadet.getRank());
            ps.setString(5, cadet.getUnit());
            ps.setInt(6, cadet.getEventsAttended());
            ps.executeUpdate();
            System.out.println("Cadet added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding cadet: " + e.getMessage());
        }
    }

    public List<ModelCadet> getAllCadets() {
        List<ModelCadet> cadets = new ArrayList<>();
        String sql = "SELECT * FROM cadet";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ModelCadet cadet = new ModelCadet(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("rank"),
                    rs.getString("unit"),
                    rs.getInt("events_attended")
                );
                cadets.add(cadet);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving cadets: " + e.getMessage());
        }
        return cadets;
    }

    public void deleteCadetById(int cadetId) {  // <-- NAME CHANGED
        String sql = "DELETE FROM cadet WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cadetId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cadet deleted successfully.");
            } else {
                System.out.println("Cadet not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting cadet: " + e.getMessage());
        }
    }

    // ===================== Training Records =====================
    public void addTrainingRecord(ModelTrainingRecord record) {
        String sql = "INSERT INTO training_records (cadet_id, camp_name, location, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, record.getCadetId());
            ps.setString(2, record.getCampName());
            ps.setString(3, record.getLocation());
            ps.setDate(4, Date.valueOf(record.getDate()));
            ps.executeUpdate();
            System.out.println("Training record added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding training record: " + e.getMessage());
        }
    }

    public List<ModelTrainingRecord> getAllTrainingRecords() {
        List<ModelTrainingRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM training_records";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ModelTrainingRecord record = new ModelTrainingRecord(
                    rs.getInt("id"),
                    rs.getInt("cadet_id"),
                    rs.getString("camp_name"),
                    rs.getString("location"),
                    rs.getDate("date").toLocalDate()
                );
                records.add(record);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving training records: " + e.getMessage());
        }
        return records;
    }

    public void deleteTrainingRecordById(int recordId) {  // <-- NAME CHANGED
        String sql = "DELETE FROM training_records WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Training record deleted.");
            } else {
                System.out.println("Record not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting record: " + e.getMessage());
        }
    }

    // ===================== Achievements =====================
    public void addAchievement(ModelAchievement achievement) {
        String sql = "INSERT INTO achievements (cadet_id, title, description, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, achievement.getCadetId());
            ps.setString(2, achievement.getTitle());
            ps.setString(3, achievement.getDescription());
            ps.setDate(4, Date.valueOf(achievement.getDate()));
            ps.executeUpdate();
            System.out.println("Achievement added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding achievement: " + e.getMessage());
        }
    }

    public List<ModelAchievement> getAllAchievements() {
        List<ModelAchievement> achievements = new ArrayList<>();
        String sql = "SELECT * FROM achievements";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ModelAchievement ach = new ModelAchievement(
                    rs.getInt("id"),
                    rs.getInt("cadet_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getDate("date").toLocalDate()
                );
                achievements.add(ach);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving achievements: " + e.getMessage());
        }
        return achievements;
    }

    public void deleteAchievementById(int achievementId) {  // <-- NAME CHANGED
        String sql = "DELETE FROM achievements WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, achievementId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Achievement deleted.");
            } else {
                System.out.println("Achievement not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting achievement: " + e.getMessage());
        }
    }

    // ===================== Admin =====================
    public boolean loginAdmin(String username, String password) {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error during admin login: " + e.getMessage());
            return false;
        }
    }

    public void deleteAdmin(String username) {
        String sql = "DELETE FROM admin WHERE username = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Admin deleted.");
            } else {
                System.out.println("Admin not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting admin: " + e.getMessage());
        }
    }

    public List<ModelAdmin> getAllAdmins() {
        List<ModelAdmin> admins = new ArrayList<>();
        String sql = "SELECT * FROM admin";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ModelAdmin admin = new ModelAdmin(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                admins.add(admin);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching admins: " + e.getMessage());
        }
        return admins;
    }
}
