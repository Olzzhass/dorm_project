package com.example.project_inf201;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;

public class Database {
    static Database instance;
    private Database() {
        //Чтобы не создовались обьекты данного класса
    }
    // надо написать свой url, user, password (database)
    final String url = "jdbc:postgresql://localhost:5432/projectSoftware";
    final String user = "postgres";
    final String password = "o130805o";
    public static Database getInstance(){
        if(Database.instance == null){
            Database.instance = new Database();
        }
        return Database.instance;
    }
    Socket socket;
    public void getSocket(Socket socket){
        this.socket = socket;
    }
    public void addToUniversitiesTable(String universityEmail, String universityName, String universityPassword) throws IOException {
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            st.executeQuery("insert into universities values ('" + universityEmail + "', '" + universityName + "', '" + universityPassword + "');");
        } catch (SQLException e) {
            ServerConnection.sendMessage(socket, e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    public void createUniversityTable(String university_name) throws IOException {
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            st.executeQuery("create table " + university_name + "(" +
                    "room varchar primary key," +
                    "capacity int," +
                    "count_of_students int" +
                    ");");
        } catch (SQLException e) {
            ServerConnection.sendMessage(socket, e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    public void insertToStudentsTable(String email, String name, String surname, String university, String student_password) throws IOException {
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            st.executeQuery("insert into students (student_email, student_name, student_surname, student_university, student_password) values " +
                    "('" + email + "', '" + name + "', '" + surname + "', '" + university + "', '" + student_password + "');");
        } catch (SQLException e) {
            ServerConnection.sendMessage(socket, e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    public void insertToUniversityTable(String university_name, String room, int capacity) throws IOException {
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            st.executeQuery("insert into " + university_name + " values " +
                    "('" + room + "', " + capacity + ", 0);");
        } catch (SQLException e) {
            ServerConnection.sendMessage(socket, e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void updateStudentsTable(String email, String room){
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            st.executeQuery("update students set student_room = '" + room + "' where student_email = '" + email + "';");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateUniversityTable(String email, String room){
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            ResultSet rs1 = st.executeQuery("select student_university from students where student_email = '" + email + "';");
            String university_name = null;
            while (rs1.next()){
                university_name = rs1.getString(1);
            }
            st.executeQuery("update " + university_name + " students set count_of_students = count_of_students + 1 where room = '" + room + "';");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public String selectStudentRegistrationPage(){
        StringBuilder sb = new StringBuilder();
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select university_name from universities;");
            while (rs.next()){
                String universityName = rs.getString(1);
                sb.append(universityName).append(", ");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }
    public String selectStudentHomePage(String email){
        StringBuilder sb = new StringBuilder();
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            ResultSet rs1 = st.executeQuery("select student_name, student_surname, student_university, student_room student from students where student_email = '" + email + "';");
            String university_name = null;
            while (rs1.next()){
                String name = rs1.getString(1);
                String surname = rs1.getString(2);
                university_name = rs1.getString(3);
                String room = rs1.getString(4);
                sb.append(name).append(", ").append(surname).append(", ").append(university_name).append(", ").append(room).append(", ");
            }
            ResultSet rs2 = st.executeQuery("select room from " + university_name + ";");
            while (rs2.next()){
                sb.append(rs2.getString(1)).append(", ");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }
    public String selectStudentRoomPage(String email, String room){
        StringBuilder sb = new StringBuilder();
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            String university_name = null;
            ResultSet rs1 = st.executeQuery("select student_university from students where student_email = '" + email + "';");
            while (rs1.next()){
                university_name = rs1.getString(1);
            }
            ResultSet rs2 = st.executeQuery("select capacity, count_of_students from " + university_name + " where room = '" + room + "';");
            while (rs2.next()){
                int capacity = rs2.getInt(1);
                int count_of_students = rs2.getInt(2);
                sb.append(room).append(" - ").append(capacity).append(" - ").append(count_of_students);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }

    public String selectUniversityHomePage(String email) {
        StringBuilder sb = new StringBuilder();
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            ResultSet rs1 = st.executeQuery("select university_name from universities where university_email = '" + email + "';");
            String university_name = null;
            while (rs1.next()){
                university_name = rs1.getString(1);
            }
            ResultSet rs2 = st.executeQuery("select sum(count_of_students) as occupied, (sum(capacity) - sum(count_of_students)) as available from " + university_name);
            while (rs2.next()){
                String occupied = rs2.getString(1);
                String available = rs2.getString(2);
                sb.append(university_name).append(", ").append(occupied).append(", ").append(available).append(", ");
            }
            ResultSet rs3 = st.executeQuery("select student_name, student_surname from students where student_university = '" + university_name + "';");
            while (rs3.next()){
                String student_name = rs3.getString(1);
                String student_surname = rs3.getString(2);
                sb.append(student_name).append(" ").append(student_surname).append(", ");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }
    public boolean checkStudentEmail(String studEmail){
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select student_email from students where student_email = '" + studEmail + "';");
            while (rs.next()){
                String email = rs.getString(1);
                if (!email.isEmpty()){
                    return true;
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean checkStudentPassword(String email, String studPassword) throws IOException {
        String student_password = null;
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select student_password from students where student_email = '" + email + "';");
            while (rs.next()){
                student_password = rs.getString(1);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return studPassword.equals(student_password);
    }
    public boolean checkUniversityEmail(String univerEmail){
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select university_email from universities where university_email = '" + univerEmail + "';");
            while (rs.next()){
                String email = rs.getString(1);
                if (!email.isEmpty()){
                    return true;
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean checkUniversity(String university_name){
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select university_name from universities where university_name = '" + university_name + "';");
            while (rs.next()){
                String email = rs.getString(1);
                if (!email.isEmpty()){
                    return true;
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean checkUniversityPassword(String email, String univerPassword) throws IOException {
        String university_password = null;
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select university_password from universities where university_email = '" + email + "';");
            while (rs.next()){
                university_password = rs.getString(1);
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return univerPassword.equals(university_password);
    }
    public void studentForgotPassword(String email, String newPassword) throws IOException {
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            st.executeQuery("update students set student_password = '" + newPassword + "' where student_email = '" + email + "';");
        }
        catch (SQLException e) {
            ServerConnection.sendMessage(socket, e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    public void universityForgotPassword(String email, String newPassword) throws IOException {
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            st.executeQuery("update universities set university_password = '" + newPassword + "' where university_email = '" + email + "';");
        }
        catch (SQLException e) {
            ServerConnection.sendMessage(socket, e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    public String selectServiceHomePageComboBox(){
        StringBuilder sb = new StringBuilder();
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select university_name from universities;");
            while (rs.next()){
                String universityName = rs.getString(1);
                sb.append(universityName).append(", ");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }
    public String selectServiceHomePage(String university_name){
        StringBuilder sb = new StringBuilder();
        try {
            Connection conn  = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            ResultSet rs3 = st.executeQuery("select student_name, student_surname from students where student_university = '" + university_name + "';");
            while (rs3.next()){
                String student_name = rs3.getString(1);
                String student_surname = rs3.getString(2);
                sb.append(student_name).append(" ").append(student_surname).append("\n");
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }
}
