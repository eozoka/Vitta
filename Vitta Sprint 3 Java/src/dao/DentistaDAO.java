package dao;

import entities.Dentista;
import infra.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DentistaDAO {

    // create
    public void create(Dentista dentista) {
        var sql = "INSERT INTO dentista (nome, cpf, especialidade) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dentista.getNome());
            stmt.setString(2, dentista.getCpf());
            stmt.setString(3, dentista.getEspecialidade());
            stmt.execute();
            System.out.println("Dentista salvo com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // read ler tudo
    public List<Dentista> listarTodos() {
        var sql = "SELECT * FROM dentista";
        List<Dentista> dentistas = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Dentista d = new Dentista(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("especialidade")
                );
                dentistas.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dentistas;
    }

    // update
    public void update(int id, Dentista dentista) {
        var sql = "UPDATE dentista SET nome=?, cpf=?, especialidade=? WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dentista.getNome());
            stmt.setString(2, dentista.getCpf());
            stmt.setString(3, dentista.getEspecialidade());
            stmt.setInt(4, id);
            stmt.execute();
            System.out.println("Dentista atualizado!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // delete
    public void delete(int id) {
        var sql = "DELETE FROM dentista WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.execute();
            System.out.println("Dentista deletado!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}