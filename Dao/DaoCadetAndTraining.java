package Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.ModelCadet;
import Model.ModelTrainingRecord;
import Model.ModelAchievement;

public class DaoCadetAndTraining {
    private static final DaoCadetAndTraining DB = null;
	private final String URL = "jdbc:mysql://localhost:3306/ncc_cadets";
    private final String USER = "root";
    private final String PASS = "TIGER";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // ==================== Cadet Methods =========================

    public void addCadet(ModelCadet cadet) {
    	String sql = "INSERT INTO cadet (id, name, age, `rank`, unit, events_attended) VALUES (?, ?, ?, ?, ?, ?)";


        try {
            Connection conn = DB.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, cadet.getId());
            pstmt.setString(2, cadet.getName());
            pstmt.setInt(3, cadet.getAge());
            pstmt.setString(4, cadet.getRank());
            pstmt.setString(5, cadet.getUnit());
            pstmt.setInt(6, cadet.getEventsAttended());
            System.out.println("Trying to insert cadet: " + cadet.getId() + ", " + cadet.getName() + ", " + cadet.getAge());

            int rows = pstmt.executeUpdate();
            System.out.println("Rows inserted: " + rows);

            System.out.println("✅ Cadet added successfully! Rows affected: " + rows);

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("b n❌ Error adding cadet: " + e.getMessage());
            e.printStackTrace(); // print full error
        }
    }
 

               
   

    public List<ModelCadet> getAllCadets() throws SQLException {
        List<ModelCadet> cadets = new ArrayList<>();
        String sql = "SELECT * FROM cadet";
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

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

        conn.close();
        return cadets;
    }

    // ==================== Training Record Methods =========================

    public void addTrainingRecord(ModelTrainingRecord record) throws SQLException {
        String sql = "INSERT INTO training_records (cadet_id, camp_name, location, date) VALUES (?, ?, ?, ?)";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, record.getCadetId());
        ps.setString(2, record.getCampName());
        ps.setString(3, record.getLocation());
        ps.setDate(4, Date.valueOf(record.getDate()));

        ps.executeUpdate();
        conn.close();
    }

    public List<ModelTrainingRecord> getAllTrainingRecords() throws SQLException {
        List<ModelTrainingRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM training_records";
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

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

        conn.close();
        return records;
    }

    // ==================== Achievement Methods =========================

    public void addAchievement(ModelAchievement achievement) throws SQLException {
        String sql = "INSERT INTO achievements (cadet_id, title, description, date) VALUES (?, ?, ?, ?)";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, achievement.getCadetId());
        ps.setString(2, achievement.getTitle());
        ps.setString(3, achievement.getDescription());
        ps.setDate(4, Date.valueOf(achievement.getDate()));

        ps.executeUpdate();
        conn.close();
    }

    public List<ModelAchievement> getAllAchievements() throws SQLException {
        List<ModelAchievement> achievements = new ArrayList<>();
        String sql = "SELECT * FROM achievements";
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            ModelAchievement achievement = new ModelAchievement(
                rs.getInt("id"),
                rs.getInt("cadet_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getDate("date").toLocalDate()
            );
            achievements.add(achievement);
        }

        conn.close();
        return achievements;
    }

    // ==================== Admin Delete Methods =========================

    public boolean deleteCadetById(int cadetId) throws SQLException {
        String sql = "DELETE FROM cadet WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cadetId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteTrainingRecordById(int recordId) throws SQLException {
        String sql = "DELETE FROM traning_records WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteAchievementById(int achievementId) throws SQLException {
        String sql = "DELETE FROM achievements WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, achievementId);
            return ps.executeUpdate() > 0;
        }
    }

    // ==================== Admin Login Method =========================

    public boolean validateAdmin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // If result found, login successful
        }
    }
}
