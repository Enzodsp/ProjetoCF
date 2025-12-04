package com.controlfinanc.dao;

import com.controlfinanc.database.Conexao;
import com.controlfinanc.model.Usuario;
import java.sql.*;

public class UsuarioDAO {
    
    public boolean autenticar(String login, String senha) {
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, login);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            return rs.next(); 
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro na autenticação: " + e.getMessage());
        }
    }

    // Método para Cadastrar novo usuário
    public void cadastrar(Usuario u) {
        String sql = "INSERT INTO usuarios (login, senha) VALUES (?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, u.getLogin());
            stmt.setString(2, u.getSenha());
            stmt.execute();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar: " + e.getMessage());
        }
    }
}

