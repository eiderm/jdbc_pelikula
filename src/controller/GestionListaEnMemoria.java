/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import model.Pelikula;

/**
 *
 * @author Eider
 */
public class GestionListaEnMemoria {

    static FileReader frPeli = null;
    static FileReader frIkus = null;
    static BufferedReader br = null;

    public static Connection connect() {

        String url = "jdbc:sqlite:pelikuladb.db";
        String user = "root";
        String pass = "";
        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            Connection con = DriverManager.getConnection(url, user, pass);
            return con;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Pelikula> cargarDatos() {
        ArrayList<Pelikula> pelis = new ArrayList<>();
        try {

            Connection con = connect();
            PreparedStatement ps = con.prepareStatement("select * from pelikula");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String iz = rs.getString("izena");
                String dir = rs.getString("direktorea");
                int ur = rs.getInt("urtea");
                String ge = rs.getString("generoa");
                String ik = rs.getString("ikusita");

                pelis.add(new Pelikula(iz, dir, String.valueOf(ur), ge, ik));
            }
        } catch (Exception e) {
        }

        return pelis;
    }

    public static ArrayList<String> cargarDatosIkusita() {

        try {
            //Strima irekitzen dugu.
            frIkus = new FileReader("Ikusita.txt");
            br = new BufferedReader(frIkus);
            String strAux;
            String[] arrAux;
            ArrayList<String> arrIkus = new ArrayList();
            while ((strAux = br.readLine()) != null) {
                arrAux = strAux.split(",");
                for (int i = 0; i < arrAux.length; i++) {
                    arrIkus.add(arrAux[i]);
                }
            }
            br.close();
            return arrIkus;
        } catch (FileNotFoundException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Artxiboa ez da aurkitu.");
            error.showAndWait();
            return null;
        } catch (IOException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Errorea egon da irakurketan.");
            error.showAndWait();
            return null;
        }
    }

    public static void guardarDatos(Pelikula peli) {
        Connection con = connect();

        PreparedStatement ps;
        try {
            ps = con.prepareStatement("update pelikula set izena=?,direktorea=?,urtea=?,generoa=?,ikusita=?");

            ps.setString(1, peli.getIzena());
            ps.setString(2, peli.getDirektorea());
            ps.setInt(3, Integer.parseInt(peli.getUrtea()));
            ps.setString(4, peli.getGeneroa());
            ps.setString(5, peli.getIkusita());

            ps.execute();
        } catch (SQLException ex) {
        }
    }

    public static void escribirDatos(TableView<Pelikula> table) {
        Pelikula peli = table.getItems().get(table.getItems().size() - 1);
        Connection con = connect();

        PreparedStatement ps;
        try {
            ps = con.prepareStatement("insert into pelikula values (?,?,?,?,?)");

            ps.setString(1, peli.getIzena());
            ps.setString(2, peli.getDirektorea());
            ps.setInt(3, Integer.parseInt(peli.getUrtea()));
            ps.setString(4, peli.getGeneroa());
            ps.setString(5, peli.getIkusita());

            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(GestionListaEnMemoria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void borrarDatos(Pelikula peli) {
        try {
            Connection con = connect();
            PreparedStatement ps = con.prepareStatement("delete from pelikula where izena=?");
            ps.setString(1, peli.getIzena());
            ps.execute();

        } catch (Exception e) {
        }
    }

    public static void guardarDatosCompletos(TableView<Pelikula> table) {
        Connection con = connect();

        PreparedStatement ps;
        try {
            for (int i = 0; i < table.getItems().size(); i++) {
                Pelikula peli = table.getItems().get(i);
                ps = con.prepareStatement("update pelikula set izena=?,direktorea=?,urtea=?,puntosalud=?,generoa=?,ikusita=?");

                ps.setString(1, peli.getIzena());
                ps.setString(2, peli.getDirektorea());
                ps.setInt(3, Integer.parseInt(peli.getUrtea()));
                ps.setString(4, peli.getGeneroa());
                ps.setString(5, peli.getIkusita());

                ps.execute();
            }
        } catch (SQLException ex) {
        }

    }
}
