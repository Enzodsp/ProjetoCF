package com.controlfinanc.view;

import com.controlfinanc.dao.LancamentoDAO;
import com.controlfinanc.database.Conexao;
import com.controlfinanc.model.Lancamento;
import com.controlfinanc.util.RelatorioUtil;
import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TelaPrincipal extends javax.swing.JFrame {

    private int idEmEdicao = 0;

    private final String[] CAT_RECEITAS = {"SALÁRIO", "INVESTIMENTO", "PRESENTE", "PRÊMIO", "OUTROS"};
    private final String[] CAT_DESPESAS = {"CASA", "MERCADO", "TRANSPORTE", "LAZER", "SAÚDE", "EDUCAÇÃO", "VESTUÁRIO", "ELETRÔNICOS", "OUTROS"};

    public TelaPrincipal() {
        initComponents();

        this.setExtendedState(MAXIMIZED_BOTH); // Tela Cheia

        DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtData.setText(LocalDate.now().format(formatoBr));

        configurarCategorias();
        configurarRenderizadorDeCores();
        atualizarTabelaGeral();
        atualizarDashboard();
    }

    // --- MÉTODOS AUXILIARES ---
    // Muda as opções do combobox de acordo com o Tipo 
    private void configurarCategorias() {
        cbCategoria.removeAllItems();
        String tipo = cbTipo.getSelectedItem().toString();

        if (tipo.equals("RECEITA")) {
            for (String c : CAT_RECEITAS) {
                cbCategoria.addItem(c);
            }
        } else {
            for (String c : CAT_DESPESAS) {
                cbCategoria.addItem(c);
            }
        }
    }

    // Busca TUDO do banco e preenche a tabela
    private void atualizarTabelaGeral() {
        LancamentoDAO dao = new LancamentoDAO();

        int idLogado = com.controlfinanc.model.Sessao.usuarioLogado.getId();

        List<Lancamento> lista = dao.listarTodos(idLogado);

        DefaultTableModel modelo = (DefaultTableModel) tabelaExtrato.getModel();
        modelo.setNumRows(0);

        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        for (Lancamento l : lista) {
            modelo.addRow(new Object[]{
                l.getId(),
                l.getData().format(formatadorData),
                l.getDescricao(),
                l.getTipo(),
                l.getCategoria(),
                formatadorMoeda.format(l.getValor())
            });
        }

        ajustarLarguraColunas(tabelaExtrato);
    }

    // Calcula Totais e Atualiza os Cards
    private void atualizarDashboard() {
        LancamentoDAO dao = new LancamentoDAO();
        int idLogado = com.controlfinanc.model.Sessao.usuarioLogado.getId();

        double receita = dao.getSomaPorTipo("RECEITA", idLogado);
        double despesa = dao.getSomaPorTipo("DESPESA", idLogado);
        double saldo = receita - despesa;

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        lblTotalReceita.setText(nf.format(receita));
        lblTotalDespesa.setText(nf.format(despesa));
        lblSaldo.setText(nf.format(saldo));

        // Cor do Saldo
        if (saldo < 0) {
            lblSaldo.setForeground(Color.RED);
        } else if (saldo == 0) {
            lblSaldo.setForeground(new Color(240, 237, 204)); // Bege
        } else {
            lblSaldo.setForeground(new Color(46, 204, 113)); // Verde
        }
    }

    private void limparCampos() {
        txtDescricao.setText("");
        txtValor.setText("");
        idEmEdicao = 0;

        // Reseta data para hoje
        DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtData.setText(LocalDate.now().format(formatoBr));

        tabelaExtrato.clearSelection();
    }

    // --- CORES NA TABELA ---
    private void configurarRenderizadorDeCores() {
        tabelaExtrato.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String tipo = table.getModel().getValueAt(row, 3).toString();

                if (!isSelected) {
                    if ("RECEITA".equals(tipo)) {
                        c.setForeground(new Color(0, 204, 0));
                    } else {
                        c.setForeground(Color.RED);
                    }
                }
                setHorizontalAlignment(CENTER);
                return c;
            }
        });
        tabelaExtrato.setRowHeight(30);
    }

    public void ajustarLarguraColunas(javax.swing.JTable tabela) {
        for (int coluna = 0; coluna < tabela.getColumnCount(); coluna++) {

            int largura = tabela.getColumnModel().getColumn(coluna).getHeaderValue().toString().length() * 10;

            for (int linha = 0; linha < tabela.getRowCount(); linha++) {
                Object valor = tabela.getValueAt(linha, coluna);
                if (valor != null) {
                    int tamanhoTexto = valor.toString().length() * 10; // *10 é um fator de pixels por caractere
                    if (tamanhoTexto > largura) {
                        largura = tamanhoTexto;
                    }
                }
            }

            if (largura > 400) {
                largura = 400;
            }

            tabela.getColumnModel().getColumn(coluna).setPreferredWidth(largura + 10); // +10 de margem
        }
    }
    
    private void gerarRelatorio(String tipo) {
    try {
        String caminho = "src/main/resources/relatorios/" + tipo + ".jasper";

        Connection con = Conexao.getConexao();

        Map<String, Object> parametros = new HashMap<>();

        RelatorioUtil.abrirPDF(caminho, parametros, con);

    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        cbTipo = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        cbCategoria = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtData = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        lblSaldo = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblTotalReceita = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblTotalDespesa = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        BtnSalvar = new javax.swing.JButton();
        BtnExcluir = new javax.swing.JButton();
        BtnLimpar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaExtrato = new javax.swing.JTable();
        BtnImprimir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(2, 52, 63));

        jPanel4.setBackground(new java.awt.Color(2, 52, 63));

        cbTipo.setBackground(new java.awt.Color(240, 237, 204));
        cbTipo.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        cbTipo.setForeground(new java.awt.Color(2, 52, 63));
        cbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RECEITA", "DESPESA" }));
        cbTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(240, 237, 204));
        jLabel15.setText("Categoria:");

        cbCategoria.setBackground(new java.awt.Color(240, 237, 204));
        cbCategoria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbCategoria.setForeground(new java.awt.Color(2, 52, 63));
        cbCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoriaActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(240, 237, 204));
        jLabel16.setText("Descrição:");

        txtDescricao.setBackground(new java.awt.Color(240, 237, 204));
        txtDescricao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDescricao.setForeground(new java.awt.Color(2, 52, 63));
        txtDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescricaoActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(240, 237, 204));
        jLabel17.setText("Valor:");

        txtValor.setBackground(new java.awt.Color(240, 237, 204));
        txtValor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtValor.setForeground(new java.awt.Color(2, 52, 63));
        txtValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(240, 237, 204));
        jLabel18.setText("Data:");

        txtData.setBackground(new java.awt.Color(240, 237, 204));
        txtData.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtData.setForeground(new java.awt.Color(2, 52, 63));
        txtData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(240, 237, 204));
        jLabel11.setText("Saldo Atual:");

        lblSaldo.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblSaldo.setForeground(new java.awt.Color(240, 237, 204));
        lblSaldo.setText("R$ 0,00");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 204, 0));
        jLabel13.setText("Total Receitas:");

        lblTotalReceita.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTotalReceita.setForeground(new java.awt.Color(0, 204, 0));
        lblTotalReceita.setText("R$ 0,00");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setText("Total Despesas:");

        lblTotalDespesa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTotalDespesa.setForeground(new java.awt.Color(255, 0, 0));
        lblTotalDespesa.setText("R$ 0,00");

        jPanel5.setBackground(new java.awt.Color(240, 237, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 52, 63), 3));

        jLabel14.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(2, 52, 63));
        jLabel14.setText("CONTROLE FINANCEIRO");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(72, 72, 72))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14)
        );

        BtnSalvar.setBackground(new java.awt.Color(240, 237, 204));
        BtnSalvar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        BtnSalvar.setForeground(new java.awt.Color(2, 52, 63));
        BtnSalvar.setText("SALVAR");
        BtnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalvarActionPerformed(evt);
            }
        });

        BtnExcluir.setBackground(new java.awt.Color(240, 237, 204));
        BtnExcluir.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        BtnExcluir.setForeground(new java.awt.Color(2, 52, 63));
        BtnExcluir.setText("EXCLUIR");
        BtnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnExcluirActionPerformed(evt);
            }
        });

        BtnLimpar.setBackground(new java.awt.Color(240, 237, 204));
        BtnLimpar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        BtnLimpar.setForeground(new java.awt.Color(2, 52, 63));
        BtnLimpar.setText("LIMPAR");
        BtnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLimparActionPerformed(evt);
            }
        });

        tabelaExtrato.setBackground(new java.awt.Color(240, 237, 204));
        tabelaExtrato.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tabelaExtrato.setForeground(new java.awt.Color(2, 52, 63));
        tabelaExtrato.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Data", "Descrição", "Tipo", "Categoria", "Valor (R$)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaExtrato.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaExtratoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaExtrato);

        BtnImprimir.setBackground(new java.awt.Color(240, 237, 204));
        BtnImprimir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BtnImprimir.setForeground(new java.awt.Color(2, 52, 47));
        BtnImprimir.setText("IMPRIMIR");
        BtnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel18)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtData)
                        .addComponent(txtValor)
                        .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblTotalReceita)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotalDespesa))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSaldo)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(BtnSalvar)
                        .addGap(18, 18, 18)
                        .addComponent(BtnExcluir)
                        .addGap(18, 18, 18)
                        .addComponent(BtnLimpar)
                        .addGap(105, 105, 105))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(BtnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSaldo)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblTotalDespesa)
                    .addComponent(jLabel13)
                    .addComponent(lblTotalReceita))
                .addGap(18, 18, 18)
                .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnSalvar)
                    .addComponent(BtnExcluir)
                    .addComponent(BtnLimpar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnImprimir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 37, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new java.awt.GridBagConstraints());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCategoriaActionPerformed

    private void txtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorActionPerformed

    private void txtDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataActionPerformed

    private void txtDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescricaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescricaoActionPerformed

    private void BtnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalvarActionPerformed
        try {
            Lancamento l = new Lancamento();
            l.setDescricao(txtDescricao.getText());
            l.setTipo(cbTipo.getSelectedItem().toString());
            l.setCategoria(cbCategoria.getSelectedItem().toString());

            // Tratamento de Valor 
            String valorLimpo = txtValor.getText().replaceAll("[^0-9,]", "").replace(",", ".");
            l.setValor(Double.parseDouble(valorLimpo));

            // Tratamento de Data
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            l.setData(LocalDate.parse(txtData.getText(), fmt));

            l.setUsuarioId(com.controlfinanc.model.Sessao.usuarioLogado.getId());

            LancamentoDAO dao = new LancamentoDAO();

            if (idEmEdicao == 0) {
                dao.salvar(l);
                JOptionPane.showMessageDialog(this, "Lançamento Salvo!");
            } else {
                l.setId(idEmEdicao);
                dao.atualizar(l);
                JOptionPane.showMessageDialog(this, "Lançamento Atualizado!");
            }

            atualizarTabelaGeral();
            atualizarDashboard();
            limparCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique valor e data (dd/mm/aaaa). \n" + e.getMessage());
        }
    }//GEN-LAST:event_BtnSalvarActionPerformed

    private void BtnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnExcluirActionPerformed
        if (tabelaExtrato.getSelectedRow() != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza?", "Excluir", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                int idLancamento = (int) tabelaExtrato.getValueAt(tabelaExtrato.getSelectedRow(), 0);
                int idUsuario = com.controlfinanc.model.Sessao.usuarioLogado.getId();
                new LancamentoDAO().excluir(idLancamento, idUsuario);

                atualizarTabelaGeral();
                atualizarDashboard();
                limparCampos();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para excluir.");
        }
    }//GEN-LAST:event_BtnExcluirActionPerformed

    private void BtnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLimparActionPerformed
        limparCampos();
    }//GEN-LAST:event_BtnLimparActionPerformed

    private void cbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoActionPerformed
        configurarCategorias();
    }//GEN-LAST:event_cbTipoActionPerformed

    private void tabelaExtratoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaExtratoMouseClicked
        if (tabelaExtrato.getSelectedRow() != -1) {
            int linha = tabelaExtrato.getSelectedRow();

            // Pega dados da tabela e joga nos campos
            idEmEdicao = (int) tabelaExtrato.getValueAt(linha, 0);
            txtData.setText(tabelaExtrato.getValueAt(linha, 1).toString());
            txtDescricao.setText(tabelaExtrato.getValueAt(linha, 2).toString());

            String tipo = tabelaExtrato.getValueAt(linha, 3).toString();
            cbTipo.setSelectedItem(tipo);
            configurarCategorias(); // Atualiza a lista de categorias antes de selecionar

            String categoria = tabelaExtrato.getValueAt(linha, 4).toString();
            cbCategoria.setSelectedItem(categoria);

            txtValor.setText(tabelaExtrato.getValueAt(linha, 5).toString());
        }
    }//GEN-LAST:event_tabelaExtratoMouseClicked

    private void BtnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnImprimirActionPerformed
        gerarRelatorio("relatorio");
    }//GEN-LAST:event_BtnImprimirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Erro ao carregar tema.");
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnExcluir;
    private javax.swing.JButton BtnImprimir;
    private javax.swing.JButton BtnLimpar;
    private javax.swing.JButton BtnSalvar;
    private javax.swing.JComboBox<String> cbCategoria;
    private javax.swing.JComboBox<String> cbTipo;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JLabel lblTotalDespesa;
    private javax.swing.JLabel lblTotalReceita;
    private javax.swing.JTable tabelaExtrato;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
