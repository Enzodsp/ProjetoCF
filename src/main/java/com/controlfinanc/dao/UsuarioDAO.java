package com.controlfinanc.dao;

import com.controlfinanc.database.Conexao;
import com.controlfinanc.model.Usuario;
import java.sql.*;

public class UsuarioDAO {
    
    public Usuario autenticar(String login, String senha) {
    String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";
    
    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, login);
        stmt.setString(2, senha);
        
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            Usuario u = new Usuario();
            u.setId(rs.getInt("id"));
            u.setNome(rs.getString("nome"));
            u.setLogin(rs.getString("login"));
            return u; 
        }
        
    } catch (SQLException e) {
        throw new RuntimeException("Erro na autenticação: " + e.getMessage());
    }
    return null; 
    }

    public void cadastrar(Usuario u) {
        String sql = "INSERT INTO usuarios (nome, email, login, senha) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getLogin());
            stmt.setString(4, u.getSenha());
            stmt.execute();

        } catch (SQLException e) { throw new RuntimeException(e); }
    }
};



