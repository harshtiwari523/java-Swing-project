package com.gui;



	import Service.ServiceCadetAndTraining;
	import Model.ModelCadet;
	import Model.ModelTrainingRecord;
	import Model.ModelAchievement;

	import javax.swing.*;
	import javax.swing.table.DefaultTableModel;
	import java.awt.*;
	import java.awt.event.*;
	import java.time.LocalDate;
	import java.util.List;

	public class MainGUI extends JFrame {

	    private ServiceCadetAndTraining service;

	    private JTabbedPane tabbedPane;

	    // Cadet Components
	    private JTextField cadetIdField, cadetNameField, cadetAgeField, cadetRankField, cadetUnitField, cadetEventsField;
	    private JTable cadetTable;
	    private DefaultTableModel cadetTableModel;

	    // Training Components
	    private JTextField trainCadetIdField, trainCampField, trainLocationField, trainDateField;
	    private JTable trainTable;
	    private DefaultTableModel trainTableModel;

	    // Achievement Components
	    private JTextField achCadetIdField, achTitleField, achDescField, achDateField;
	    private JTable achTable;
	    private DefaultTableModel achTableModel;

	    public MainGUI() {
	        service = new ServiceCadetAndTraining();

	        // Admin Login
	        if (!adminLogin()) {
	            JOptionPane.showMessageDialog(this, "Login Failed! Exiting...");
	            System.exit(0);
	        }

	        setTitle("NCC Cadet Management");
	        setSize(900, 600);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        tabbedPane = new JTabbedPane();

	        createCadetTab();
	        createTrainingTab();
	        createAchievementTab();

	        add(tabbedPane);
	        setVisible(true);
	    }

	    private boolean adminLogin() {
	        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
	        JTextField usernameField = new JTextField();
	        JPasswordField passwordField = new JPasswordField();
	        panel.add(new JLabel("Username:"));
	        panel.add(usernameField);
	        panel.add(new JLabel("Password:"));
	        panel.add(passwordField);

	        int result = JOptionPane.showConfirmDialog(null, panel, "Admin Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	        if (result == JOptionPane.OK_OPTION) {
	            String username = usernameField.getText();
	            String password = new String(passwordField.getPassword());
	            return service.loginAdmin(username, password);
	        }
	        return false;
	    }

	    // ----------------- Cadet Tab --------------------
	    private void createCadetTab() {
	        JPanel panel = new JPanel(new BorderLayout());
	        panel.setBackground(new Color(240, 248, 255));

	        // Form Panel
	        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
	        cadetIdField = new JTextField();
	        cadetNameField = new JTextField();
	        cadetAgeField = new JTextField();
	        cadetRankField = new JTextField();
	        cadetUnitField = new JTextField();
	        cadetEventsField = new JTextField();

	        formPanel.add(new JLabel("ID:")); formPanel.add(cadetIdField);
	        formPanel.add(new JLabel("Name:")); formPanel.add(cadetNameField);
	        formPanel.add(new JLabel("Age:")); formPanel.add(cadetAgeField);
	        formPanel.add(new JLabel("Rank:")); formPanel.add(cadetRankField);
	        formPanel.add(new JLabel("Unit:")); formPanel.add(cadetUnitField);
	        formPanel.add(new JLabel("Events Attended:")); formPanel.add(cadetEventsField);

	        JButton addBtn = new JButton("Add Cadet");
	        JButton deleteBtn = new JButton("Delete Cadet");
	        formPanel.add(addBtn); formPanel.add(deleteBtn);

	        panel.add(formPanel, BorderLayout.NORTH);

	        // Table Panel
	        cadetTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Rank", "Unit", "Events"}, 0);
	        cadetTable = new JTable(cadetTableModel);
	        JScrollPane scrollPane = new JScrollPane(cadetTable);
	        panel.add(scrollPane, BorderLayout.CENTER);

	        tabbedPane.addTab("Cadets", panel);

	        refreshCadetTable();

	        // Button Actions
	        addBtn.addActionListener(e -> addCadet());
	        deleteBtn.addActionListener(e -> deleteCadet());
	    }

	    private void addCadet() {
	        try {
	            int id = Integer.parseInt(cadetIdField.getText());
	            String name = cadetNameField.getText();
	            int age = Integer.parseInt(cadetAgeField.getText());
	            String rank = cadetRankField.getText();
	            String unit = cadetUnitField.getText();
	            int events = Integer.parseInt(cadetEventsField.getText());

	            ModelCadet cadet = new ModelCadet(id, name, age, rank, unit, events);
	            service.addCadet(cadet);
	            JOptionPane.showMessageDialog(this, "Cadet added successfully.");
	            refreshCadetTable();
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
	        }
	    }

	    private void deleteCadet() {
	        try {
	            int row = cadetTable.getSelectedRow();
	            if (row >= 0) {
	                int id = (int) cadetTableModel.getValueAt(row, 0);
	                service.deleteCadetById(id);
	                JOptionPane.showMessageDialog(this, "Cadet deleted.");
	                refreshCadetTable();
	            } else {
	                JOptionPane.showMessageDialog(this, "Select a cadet to delete.");
	            }
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
	        }
	    }

	    private void refreshCadetTable() {
	        cadetTableModel.setRowCount(0);
	        List<ModelCadet> cadets = service.getAllCadets();
	        for (ModelCadet c : cadets) {
	            cadetTableModel.addRow(new Object[]{c.getId(), c.getName(), c.getAge(), c.getRank(), c.getUnit(), c.getEventsAttended()});
	        }
	    }

	    // ----------------- Training Tab --------------------
	    private void createTrainingTab() {
	        JPanel panel = new JPanel(new BorderLayout());
	        panel.setBackground(new Color(245, 245, 220));

	        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
	        trainCadetIdField = new JTextField();
	        trainCampField = new JTextField();
	        trainLocationField = new JTextField();
	        trainDateField = new JTextField();

	        formPanel.add(new JLabel("Cadet ID:")); formPanel.add(trainCadetIdField);
	        formPanel.add(new JLabel("Camp Name:")); formPanel.add(trainCampField);
	        formPanel.add(new JLabel("Location:")); formPanel.add(trainLocationField);
	        formPanel.add(new JLabel("Date (yyyy-mm-dd):")); formPanel.add(trainDateField);

	        JButton addBtn = new JButton("Add Training");
	        JButton deleteBtn = new JButton("Delete Training");
	        formPanel.add(addBtn); formPanel.add(deleteBtn);

	        panel.add(formPanel, BorderLayout.NORTH);

	        trainTableModel = new DefaultTableModel(new String[]{"ID", "CadetID", "Camp", "Location", "Date"}, 0);
	        trainTable = new JTable(trainTableModel);
	        panel.add(new JScrollPane(trainTable), BorderLayout.CENTER);

	        tabbedPane.addTab("Training", panel);
	        refreshTrainingTable();

	        addBtn.addActionListener(e -> addTraining());
	        deleteBtn.addActionListener(e -> deleteTraining());
	    }

	    private void addTraining() {
	        try {
	            int cadetId = Integer.parseInt(trainCadetIdField.getText());
	            String camp = trainCampField.getText();
	            String loc = trainLocationField.getText();
	            LocalDate date = LocalDate.parse(trainDateField.getText());

	            ModelTrainingRecord record = new ModelTrainingRecord(0, cadetId, camp, loc, date);
	            service.addTrainingRecord(record);
	            JOptionPane.showMessageDialog(this, "Training record added.");
	            refreshTrainingTable();
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
	        }
	    }

	    private void deleteTraining() {
	        try {
	            int row = trainTable.getSelectedRow();
	            if (row >= 0) {
	                int id = (int) trainTableModel.getValueAt(row, 0);
	                service.deleteTrainingRecordById(id);
	                JOptionPane.showMessageDialog(this, "Training record deleted.");
	                refreshTrainingTable();
	            } else {
	                JOptionPane.showMessageDialog(this, "Select a training record to delete.");
	            }
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
	        }
	    }

	    private void refreshTrainingTable() {
	        trainTableModel.setRowCount(0);
	        List<ModelTrainingRecord> records = service.getAllTrainingRecords();
	        for (ModelTrainingRecord r : records) {
	            trainTableModel.addRow(new Object[]{r.getId(), r.getCadetId(), r.getCampName(), r.getLocation(), r.getDate()});
	        }
	    }

	    // ----------------- Achievement Tab --------------------
	    private void createAchievementTab() {
	        JPanel panel = new JPanel(new BorderLayout());
	        panel.setBackground(new Color(224, 255, 255));

	        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
	        achCadetIdField = new JTextField();
	        achTitleField = new JTextField();
	        achDescField = new JTextField();
	        achDateField = new JTextField();

	        formPanel.add(new JLabel("Cadet ID:")); formPanel.add(achCadetIdField);
	        formPanel.add(new JLabel("Title:")); formPanel.add(achTitleField);
	        formPanel.add(new JLabel("Description:")); formPanel.add(achDescField);
	        formPanel.add(new JLabel("Date (yyyy-mm-dd):")); formPanel.add(achDateField);

	        JButton addBtn = new JButton("Add Achievement");
	        JButton deleteBtn = new JButton("Delete Achievement");
	        formPanel.add(addBtn); formPanel.add(deleteBtn);

	        panel.add(formPanel, BorderLayout.NORTH);

	        achTableModel = new DefaultTableModel(new String[]{"ID", "CadetID", "Title", "Description", "Date"}, 0);
	        achTable = new JTable(achTableModel);
	        panel.add(new JScrollPane(achTable), BorderLayout.CENTER);

	        tabbedPane.addTab("Achievements", panel);
	        refreshAchievementTable();

	        addBtn.addActionListener(e -> addAchievement());
	        deleteBtn.addActionListener(e -> deleteAchievement());
	    }

	    private void addAchievement() {
	        try {
	            int cadetId = Integer.parseInt(achCadetIdField.getText());
	            String title = achTitleField.getText();
	            String desc = achDescField.getText();
	            LocalDate date = LocalDate.parse(achDateField.getText());

	            ModelAchievement ach = new ModelAchievement(0, cadetId, title, desc, date);
	            service.addAchievement(ach);
	            JOptionPane.showMessageDialog(this, "Achievement added.");
	            refreshAchievementTable();
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
	        }
	    }

	    private void deleteAchievement() {
	        try {
	            int row = achTable.getSelectedRow();
	            if (row >= 0) {
	                int id = (int) achTableModel.getValueAt(row, 0);
	                service.deleteAchievementById(id);
	                JOptionPane.showMessageDialog(this, "Achievement deleted.");
	                refreshAchievementTable();
	            } else {
	                JOptionPane.showMessageDialog(this, "Select an achievement to delete.");
	            }
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
	        }
	    }

	    private void refreshAchievementTable() {
	        achTableModel.setRowCount(0);
	        List<ModelAchievement> achs = service.getAllAchievements();
	        for (ModelAchievement a : achs) {
	            achTableModel.addRow(new Object[]{a.getId(), a.getCadetId(), a.getTitle(), a.getDescription(), a.getDate()});
	        }
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> new MainGUI());
	    }


}
