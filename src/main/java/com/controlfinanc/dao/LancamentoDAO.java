package com.controlfinanc.dao;

import com.controlfinanc.database.Conexao;
import com.controlfinanc.model.Lancamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LancamentoDAO {

    // CREATE
    public void salvar(Lancamento l) {
        String sql = "INSERT INTO lancamentos (descricao, valor, tipo, categoria, data_lancamento) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, l.getDescricao());
            stmt.setDouble(2, l.getValor());
            stmt.setString(3, l.getTipo());
            stmt.setString(4, l.getCategoria());
            stmt.setDate(5, Date.valueOf(l.getData()));
            stmt.execute();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    // UPDATE 
    public void atualizar(Lancamento l) {
        String sql = "UPDATE lancamentos SET descricao=?, valor=?, categoria=?, data_lancamento=? WHERE id=?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, l.getDescricao());
            stmt.setDouble(2, l.getValor());
            stmt.setString(3, l.getCategoria());
            stmt.setDate(4, Date.valueOf(l.getData()));
            stmt.setInt(5, l.getId()); // Importante: O ID é usado para achar o registro
            stmt.execute();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    // DELETE 
    public void excluir(int id) {
        String sql = "DELETE FROM lancamentos WHERE id=?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    // READ 
    public List<Lancamento> listarPorTipo(String tipoFiltro) {
        String sql = "SELECT * FROM lancamentos WHERE tipo = ? ORDER BY data_lancamento DESC";
        List<Lancamento> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipoFiltro);
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
        } catch (SQLException e) { throw new RuntimeException(e); }
        return lista;
    }
    
    public double getSomaPorTipo(String tipo) {
    double total = 0;
    String sql = "SELECT SUM(valor) as total FROM lancamentos WHERE tipo = ?";
    
    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, tipo);
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