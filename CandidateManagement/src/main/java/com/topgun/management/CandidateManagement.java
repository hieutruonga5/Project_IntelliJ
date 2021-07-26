package com.topgun.management;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;

import com.topgun.model.Candidate;
import com.topgun.model.Certificate;
import com.topgun.model.Experience;
import com.topgun.model.Fresher;
import com.topgun.model.Intern;
import com.topgun.utils.DateTimeUtils;

/**
 * Hello world!
 *
 */
public class CandidateManagement {
    public static Scanner sc = new Scanner(System.in);
    public static List<Candidate> listInput = new ArrayList<>();
    public static Set<Candidate> candidateSet = new HashSet<>();
    private static Connection connection;
    private static Logger logger = Logger.getLogger(CandidateManagement.class);

    public static void main(String[] args) {
        boolean isStop = true;
        do {
            System.out.println("-------------- Menu -------------");
            System.out.println("1 : Nhap thong tin tung candidate !");
            System.out.println("2 : Import Candidate tu csv file !");
            System.out.println("3 : Get all candidate and sort !");
            System.out.println("4 : Update Candidate !");
            System.out.println("5 : Get all full name Candidate !");
            System.out.println("6 : Stop program !");
            String key = String.valueOf(sc.nextInt());
            switch (key) {
                case "1":
                    inputData();
                    break;
                case "2":
                    importCandidate();
                    break;
                case "3":
                    collectionCandidate();
                    break;
                case "4":
                    System.out.println("Nhap vao candidate ID : ");
                    updateCandidate(sc.nextLine());
                    break;
                case "5":
                    getAllNameCandidate();
                    break;
                case "6":
                    isStop = false;
                    break;
            }

        } while (isStop);
        System.out.println(" ----------- End Program ----------");

    }

    public static void insertCandidate2(Candidate candidate) {

        try {
            String dbURL = "jdbc:sqlserver://localhost;databaseName=CANDIDATEMANAGEMENT;user=sa;password=fadn@2020";
            Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement preparedStmt = null;
            String query = "";
            String queryInsertExperience = " INSERT INTO Candidate (CandidateID, FullName,	BirthDay, Email, CandidateType,ExpInYear,ProSkill)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            String queryInsertFresher = " INSERT INTO Candidate (CandidateID, FullName,	BirthDay, Email, CandidateType,GraduationDate,GraduationRank,Education)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            String queryInsertIntern = " INSERT INTO Candidate (CandidateID, FullName,	BirthDay, Email, CandidateType,Majors,Semester,UniversityName)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            if (candidate instanceof Experience) {
                Experience experience = (Experience) candidate;
                query = queryInsertExperience;
                preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt(6, experience.getExpInYear());
                preparedStmt.setString(7, experience.getProSkill());
            } else if (candidate instanceof Fresher) {
                Fresher fresher = (Fresher) candidate;
                query = queryInsertFresher;
                preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(6, "");
                preparedStmt.setString(7, fresher.getGraduationRank());
                preparedStmt.setString(8, fresher.getEducation());
            } else if (candidate instanceof Intern) {
                Intern intern = (Intern) candidate;
                query = queryInsertIntern;
                preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(6, intern.getMajors());
                preparedStmt.setString(7, intern.getSemester());
                preparedStmt.setString(7, intern.getUniversityName());
            }
            preparedStmt.setString(1, candidate.getCandidateID());
            preparedStmt.setString(2, candidate.getFullName());
            preparedStmt.setString(3, DateTimeUtils.stringFormat(candidate.getBirthDay()));
            preparedStmt.setString(4, candidate.getEmail());
            preparedStmt.setInt(5, candidate.getCandidateType());

            // execute the preparedstatement
            preparedStmt.executeUpdate();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }

    }

    public static void insertCandidate(Candidate candidate) {

        try {
            Connection connection = getConnect();
            PreparedStatement stmt = null;
            String query = "SELECT * FROM Candidate";
            stmt = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();

            rs.moveToInsertRow();
            rs.updateString("CandidateID", candidate.getCandidateID());
            rs.updateString("FullName", candidate.getFullName());
            rs.updateString("BirthDay", DateTimeUtils.stringFormat(candidate.getBirthDay()));
            rs.updateString("Phone", candidate.getPhone());
            rs.updateString("Email", candidate.getEmail());
            rs.updateInt("CandidateType", candidate.getCandidateType());
            if (candidate instanceof Experience) {
                Experience experience = (Experience) candidate;
                rs.updateInt("ExpInYear", experience.getExpInYear());
                rs.updateString("ProSkill", experience.getProSkill());
            } else if (candidate instanceof Fresher) {
                Fresher fresher = (Fresher) candidate;
                rs.updateDate("GraduationDate", new Date(fresher.getGraduationDate().getTime()));
                rs.updateString("GraduationRank", fresher.getGraduationRank());
                rs.updateString("Education", fresher.getEducation());
            } else if (candidate instanceof Intern) {
                Intern intern = (Intern) candidate;
                rs.updateString("Majors", intern.getMajors());
                rs.updateString("Semester", intern.getSemester());
                rs.updateString("UniversityName", intern.getUniversityName());
            }

            rs.insertRow();

        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }

    }

    public static void insertCertificate(List<Certificate> list, String candidateId) {
        try {
            Connection conn = getConnect();
            PreparedStatement stmt = null;
            String query = "SELECT * FROM Certificate";
            stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            for (Certificate certificate : list) {
                rs.moveToInsertRow();
                rs.updateString("CertificatedID", certificate.getCertificatedID());
                rs.updateString("CertificateName", certificate.getCertificateName());
                rs.updateString("CertificateRank", certificate.getCertificateRank());
                rs.updateString("CertificatedDate", DateTimeUtils.stringFormat(certificate.getCertificatedDate()));
                rs.updateString("CandidateID", candidateId);
                rs.insertRow();
            }
            closeConnect();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }

    public static void insertCertificate2(List<Certificate> list, String candidateId) {
        try {

            String dbURL = "jdbc:sqlserver://localhost;databaseName=CANDIDATEMANAGEMENT;user=sa;password=fadn@2020";
            Connection conn = DriverManager.getConnection(dbURL);
            String query = "INSERT INTO Certificate (CertificatedID, CertificateName,	CertificateRank, CertificatedDate, CandidateId) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            for (Certificate certificate : list) {
                preparedStmt.setString(1, certificate.getCertificatedID());
                preparedStmt.setString(2, certificate.getCertificateName());
                preparedStmt.setString(3, certificate.getCertificateRank());
                preparedStmt.setString(4, DateTimeUtils.stringFormat(certificate.getCertificatedDate()));
                preparedStmt.setString(5, candidateId);
                preparedStmt.addBatch();
            }
            preparedStmt.executeBatch();
            // execute the preparedstatement
            conn.close();
        } catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
    }

    public static void getAllNameCandidate() {
        StringBuffer sb = new StringBuffer();
        try {
            Connection conn = getConnect();
            String query = "SELECT fullName FROM Candidate ";
            logger.info(query);
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();
            boolean isFirst = true;
            while (rs.next()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(",");
                }
                sb.append(rs.getString("FullName"));
            }
            closeConnect();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        System.out.println("Full name all candidate : " + sb.toString());
    }

    public static List<Candidate> inputData() {
        sc = new Scanner(System.in);
        Candidate cd = null;
        boolean cont = true;
        do {
            System.out.println("Nhap trinh do ung vien : 0 (Experience) 1(Fresher) 2(Intern) ");
            int x = Integer.parseInt(sc.nextLine());
            switch (x) {
                case 0:
                    cd = new Experience();
                    break;
                case 1:
                    cd = new Fresher();
                    break;
                case 2:
                    cd = new Intern();
                    break;
                default:
                    break;
            }
            cd.inputData();
            cd.inputCertificate();
            cd.showInfo();
            cd.showMe();
            listInput.add(cd);
            insertCandidate(cd);
            insertCertificate(cd.getCertificates(), cd.getCandidateID());
            System.out.println("Do you continute ? (Y/N) :D)");
            if ("N".equals(sc.nextLine())) {
                cont = false;
            }
        } while (cont);
        cd.showCount();
        return listInput;
    }

    public static void importCandidate() {
        List<Candidate> list = new ArrayList<>();
        try {
            FileInputStream fi = new FileInputStream("D:\\data.csv");
            InputStreamReader isr = new InputStreamReader(fi);
            BufferedReader br = new BufferedReader(isr);
            String currentLine;
            Candidate cd = null;
            String[] str;
            while ((currentLine = br.readLine()) != null) {
                str = currentLine.split(",");
                switch (str[5]) {
                    case "0":
                        cd = new Experience(str[0], str[1], DateTimeUtils.dateFormat1(str[2]), str[3], str[4],
                                Integer.valueOf(str[5]), Integer.valueOf(str[6]), str[7]);
                        break;
                    case "1":
                        cd = new Fresher(str[0], str[1], DateTimeUtils.dateFormat1(str[2]), str[3], str[4],
                                Integer.valueOf(str[5]), DateTimeUtils.dateFormat1(str[8]), str[9], str[10]);
                        break;
                    case "2":
                        cd = new Intern(str[0], str[1], DateTimeUtils.dateFormat1(str[2]), str[3], str[4],
                                Integer.valueOf(str[5]), str[9], str[10], str[10]);
                        break;
                    default:
                        break;
                }
                list.add(cd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }

        for (Candidate candidate : list) {
            insertCandidate(candidate);
        }
        closeConnect();
    }

    private static void updateCandidate(String candidateId) {
        if (checkExistCandidate(candidateId)) {
            try {
                Connection conn = getConnect();
                String query = "SELECT * FROM Candidate ";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                ResultSet rs = preparedStmt.executeQuery();
                Candidate cd = null;
                while (rs.next()) {
                    switch (rs.getString("candidateType")) {
                        case "0":
                            cd = new Experience(rs.getString(1), rs.getString(2),
                                    DateTimeUtils.dateFormat2(rs.getString(3)), rs.getString(4), rs.getString(5),
                                    rs.getInt(6), rs.getInt(7), rs.getString(8));
                            break;
                        case "1":
                            cd = new Fresher(rs.getString(1), rs.getString(2),
                                    DateTimeUtils.dateFormat2(rs.getString(3)), rs.getString(4), rs.getString(5),
                                    rs.getInt(6), DateTimeUtils.dateFormat2(rs.getString(9)), rs.getString(10),
                                    rs.getString(11));
                            break;
                        case "2":
                            cd = new Intern(rs.getString(1), rs.getString(2),
                                    DateTimeUtils.dateFormat2(rs.getString(3)), rs.getString(4), rs.getString(5),
                                    rs.getInt(6), rs.getString(12), rs.getString(13), rs.getString(14));
                            break;
                        default:
                            break;
                    }
                }
                switch (cd.getCandidateType()) {
                    case 0:
                        cd = new Experience();
                        break;
                    case 1:
                        cd = new Fresher();
                        break;
                    case 2:
                        cd = new Intern();
                        break;
                    default:
                        break;
                }
                cd.inputData();
            } catch (Exception e) {
            }

        } else {
            System.out.println("Candidate ID not exist ! ");
        }

    }

    private static boolean checkExistCandidate(String candidateId) {
        try {
            Connection conn = getConnect();
            String query = "SELECT * FROM Candidate WHERE ID = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, candidateId);
            ResultSet rs = preparedStmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return false;
    }

    public static void collectionCandidate() {
        try {
            Connection conn = getConnect();
            String query = "SELECT * FROM Candidate ";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();
            Candidate cd = null;
            while (rs.next()) {
                switch (rs.getString("candidateType")) {
                    case "0":
                        cd = new Experience(rs.getString(1), rs.getString(2),
                                DateTimeUtils.dateFormat2(rs.getString(3)), rs.getString(4), rs.getString(5),
                                rs.getInt(6), rs.getInt(7), rs.getString(8));
                        break;
                    case "1":
                        cd = new Fresher(rs.getString(1), rs.getString(2), DateTimeUtils.dateFormat2(rs.getString(3)),
                                rs.getString(4), rs.getString(5), rs.getInt(6),
                                DateTimeUtils.dateFormat2(rs.getString(9)), rs.getString(10), rs.getString(11));
                        break;
                    case "2":
                        cd = new Intern(rs.getString(1), rs.getString(2), DateTimeUtils.dateFormat2(rs.getString(3)),
                                rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(12), rs.getString(13),
                                rs.getString(14));
                        break;
                    default:
                        break;
                }
                candidateSet.add(cd);
            }
            System.out.println(candidateSet.size());
            List<Candidate> list = new ArrayList<>(candidateSet);
            Collections.sort(list);
            for (Candidate candidate : list) {
                candidate.showInfo();
                candidate.showMe();
            }

            closeConnect();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }

    public static Connection getConnect() {
        if (connection == null) {
            String url = "jdbc:sqlserver://localhost:1234;databaseName=CANDIDATEMANAGEMENT;user=sa;password=fadn@2020";
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                return DriverManager.getConnection(url);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
            return null;
        } else {
            return connection;
        }
    }

    public static void closeConnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
    }
}
