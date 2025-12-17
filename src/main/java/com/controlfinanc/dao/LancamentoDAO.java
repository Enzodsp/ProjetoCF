package com.controlfinanc.dao;

import com.controlfinanc.database.Conexao;
import com.controlfinanc.model.Lancamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LancamentoDAO {

    // CREATE
    public void salvar(Lancamento l) {
        String sql = "INSERT INTO lancamentos (descricao, valor, tipo, categoria, data_lancamento, usuario_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, l.getDescricao());
        stmt.setDouble(2, l.getValor());
        stmt.setString(3, l.getTipo());
        stmt.setString(4, l.getCategoria());
        stmt.setDate(5, java.sql.Date.valueOf(l.getData()));
        stmt.setInt(6, l.getUsuarioId());
        stmt.execute();
    }   catch (SQLException e) { throw new RuntimeException(e); }
    }

    // UPDATE 
    public void atualizar(Lancamento l) {
        String sql = "UPDATE lancamentos SET descricao=?, valor=?, tipo=?, categoria=?, data_lancamento=? WHERE id=? AND usuario_id=?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, l.getDescricao());
            stmt.setDouble(2, l.getValor());
            stmt.setString(3, l.getTipo());
            stmt.setString(4, l.getCategoria());
            stmt.setDate(5, java.sql.Date.valueOf(l.getData()));
            stmt.setInt(6, l.getId());
            stmt.setInt(7, l.getUsuarioId());            
            stmt.execute();           
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar: " + e.getMessage());
        }
    }
        
    // DELETE 
    public void excluir(int idLancamento, int idUsuario) {
        String sql = "DELETE FROM lancamentos WHERE id=? AND usuario_id=?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idLancamento);
            stmt.setInt(2, idUsuario); // Trava de segurança
            
            stmt.execute();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir: " + e.getMessage());
        }
    }

    // READ 
    public List<Lancamento> listarTodos(int idUsuario) {
        String sql = "SELECT * FROM lancamentos WHERE usuario_id = ? ORDER BY data_lancamento DESC";
        
        List<Lancamento> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, idUsuario); 
        
        ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Lancamento l = new Lancamento();
                l.setId(rs.getInt("id"));
                l.setDescricao(rs.getString("descricao"));
                l.setValor(rs.getDouble("valor"));
                l.setTipo(rs.getString("tipo"));     
                l.setCategoria(rs.getString("categoria"));
                l.setData(rs.getDate("data_lancamento").toLocalDate());
                
                lista.add(l);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tudo: " + e.getMessage());
        }
        return lista;
    }
    
    public double getSomaPorTipo(String tipo, int idUsuario) {
    double total = 0;
    String sql = "SELECT SUM(valor) as total FROM lancamentos WHERE tipo = ? AND usuario_id = ?";
    
    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, tipo);
        stmt.setInt(2, idUsuario);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            total = rs.getDouble("total");
        }        
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao somar valores: " + e.getMessage());
    }
    return total;
    
    }
}