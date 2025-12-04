package com.controlfinanc.view;

import com.controlfinanc.dao.LancamentoDAO;
import com.controlfinanc.model.Lancamento;
import java.time.LocalDate;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;


public class TelaPrincipal extends javax.swing.JFrame {

// Variável global para controlar qual ID está sendo editado 
    private int idEmEdicaoDespesa = 0; 
    private int idEmEdicaoReceita = 0;
    LancamentoDAO dao = new LancamentoDAO();
    
    public TelaPrincipal() {
        initComponents();
        atualizarTabelaDespesas();
        atualizarTabelaReceitas();
        atualizarDashboard();
        
        java.time.format.DateTimeFormatter formatoBr = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataHojeFormatada = java.time.LocalDate.now().format(formatoBr);
        txtDataDespesa.setText(dataHojeFormatada);
        txtDataReceita.setText(dataHojeFormatada);
        
        // PARA ABRIR MAXIMIZADO:
        this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

        estilizarTabela(tabelaDespesas);
        estilizarTabela(tabelaReceita);
    }

    // --- MÉTODOS AUXILIARES ---
    
    private void atualizarTabelaDespesas() {
        LancamentoDAO dao = new LancamentoDAO();
        List<Lancamento> lista = dao.listarPorTipo("DESPESA");

        DefaultTableModel modelo = (DefaultTableModel) tabelaDespesas.getModel();
        modelo.setNumRows(0); 

        java.time.format.DateTimeFormatter formatadorData = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        java.text.NumberFormat formatadorMoeda = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));

        for (Lancamento l : lista) {
            modelo.addRow(new Object[]{
                l.getId(),
                l.getData().format(formatadorData),   
                l.getDescricao(),
                l.getCategoria(),
                formatadorMoeda.format(l.getValor())  
            });
        }
    }

    private void atualizarTabelaReceitas() {
        LancamentoDAO dao = new LancamentoDAO();
        List<Lancamento> lista = dao.listarPorTipo("RECEITA");

        DefaultTableModel modelo = (DefaultTableModel) tabelaReceita.getModel();
        modelo.setNumRows(0); 

        // Formatadores
        java.time.format.DateTimeFormatter formatadorData = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        java.text.NumberFormat formatadorMoeda = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));

        for (Lancamento l : lista) {

            modelo.addRow(new Object[]{
                l.getId(),
                l.getData().format(formatadorData),    
                l.getDescricao(),
                l.getCategoria(),
                formatadorMoeda.format(l.getValor())   
            });
        }
    }
    
    private void atualizarDashboard() {
    LancamentoDAO dao = new LancamentoDAO();
    
    //Busca os valores no banco
    double totalReceita = dao.getSomaPorTipo("RECEITA");
    double totalDespesa = dao.getSomaPorTipo("DESPESA");
    double saldo = totalReceita - totalDespesa;
    
    //Formata para dinheiro brasileiro
    NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    
    //Atualiza os textos
    lblTotalReceita.setText(formatoMoeda.format(totalReceita));
    lblTotalDespesa.setText(formatoMoeda.format(totalDespesa));
    lblSaldo.setText(formatoMoeda.format(saldo));
    
    //Mudar cor do saldo
    if (saldo < 0) {
        lblSaldo.setForeground(Color.RED);
    } else if (saldo == 0) {
        lblSaldo.setForeground(new Color(240, 237, 204));
    } else {
        lblSaldo.setForeground(new Color(0, 100, 0)); // Verde escuro
    }
}
    
    private void estilizarTabela(javax.swing.JTable tabela) {
    // renderizador que centraliza
    DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
    centro.setHorizontalAlignment(JLabel.CENTER);
    // Aplica o renderizador em todas as colunas da tabela
    for (int i = 0; i < tabela.getColumnCount(); i++) {
        tabela.getColumnModel().getColumn(i).setCellRenderer(centro);
    }   
    // Aumenta a altura da linha 
    tabela.setRowHeight(30);
}
    
    private void limparCamposDespesa() {
    txtDescDespesa.setText("");
    txtValorDespesa.setText("");
    cbCatDespesa.setSelectedIndex(0);
    
    java.time.format.DateTimeFormatter formatoBr = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
    txtDataDespesa.setText(java.time.LocalDate.now().format(formatoBr));
    
    idEmEdicaoDespesa = 0;
    tabelaDespesas.clearSelection();
}
    
    private void limparCamposReceita() {
    txtDescReceita.setText("");
    txtValorReceita.setText("");
    cbCatReceita.setSelectedIndex(0);
    
    java.time.format.DateTimeFormatter formatoBr = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
    txtDataReceita.setText(java.time.LocalDate.now().format(formatoBr));
    
    idEmEdicaoReceita = 0; 
    tabelaReceita.clearSelection();
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
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lblSaldo = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblTotalReceita = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblTotalDespesa = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        btnIrReceitas = new javax.swing.JButton();
        btnIrDespesas = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtDescDespesa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtValorDespesa = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDataDespesa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbCatDespesa = new javax.swing.JComboBox<>();
        btnSalvarDespesa = new javax.swing.JButton();
        btnExcluirDespesa = new javax.swing.JButton();
        btnLimparDespesa = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaDespesas = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtDescReceita = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtValorReceita = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDataReceita = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbCatReceita = new javax.swing.JComboBox<>();
        btnSalvarReceita = new javax.swing.JButton();
        btnExcluirReceita = new javax.swing.JButton();
        btnLimparReceita = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaReceita = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(2, 52, 63));

        jTabbedPane.setBackground(new java.awt.Color(2, 52, 63));
        jTabbedPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 237, 204)));
        jTabbedPane.setForeground(new java.awt.Color(240, 237, 204));
        jTabbedPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneStateChanged(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(2, 52, 63));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(240, 237, 204));
        jLabel11.setText("Saldo Atual:");

        lblSaldo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblSaldo.setForeground(new java.awt.Color(240, 237, 204));
        lblSaldo.setText("R$ 0,00");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 204, 0));
        jLabel13.setText("Total Receitas:");

        lblTotalReceita.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblTotalReceita.setForeground(new java.awt.Color(0, 204, 0));
        lblTotalReceita.setText("R$ 0,00");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setText("Total Despesas:");

        lblTotalDespesa.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
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
                .addGap(76, 76, 76))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel14)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        btnIrReceitas.setBackground(new java.awt.Color(240, 237, 204));
        btnIrReceitas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnIrReceitas.setForeground(new java.awt.Color(2, 52, 63));
        btnIrReceitas.setText("Nova Receita");
        btnIrReceitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIrReceitasActionPerformed(evt);
            }
        });

        btnIrDespesas.setBackground(new java.awt.Color(240, 237, 204));
        btnIrDespesas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnIrDespesas.setForeground(new java.awt.Color(2, 52, 63));
        btnIrDespesas.setText("Nova Despesa");
        btnIrDespesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIrDespesasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSaldo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(lblTotalReceita)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalDespesa)))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(btnIrReceitas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnIrDespesas)
                .addGap(59, 59, 59))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSaldo)
                    .addComponent(jLabel11))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblTotalDespesa)
                    .addComponent(jLabel13)
                    .addComponent(lblTotalReceita))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIrReceitas)
                    .addComponent(btnIrDespesas))
                .addContainerGap(310, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("INICIO", jPanel4);

        jPanel2.setBackground(new java.awt.Color(2, 52, 63));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(240, 237, 204));
        jLabel2.setText("Descrição:");

        txtDescDespesa.setBackground(new java.awt.Color(240, 237, 204));
        txtDescDespesa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDescDespesa.setForeground(new java.awt.Color(2, 52, 63));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(240, 237, 204));
        jLabel3.setText("Valor:");

        txtValorDespesa.setBackground(new java.awt.Color(240, 237, 204));
        txtValorDespesa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtValorDespesa.setForeground(new java.awt.Color(2, 52, 63));
        txtValorDespesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorDespesaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(240, 237, 204));
        jLabel4.setText("Data:");

        txtDataDespesa.setBackground(new java.awt.Color(240, 237, 204));
        txtDataDespesa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDataDespesa.setForeground(new java.awt.Color(2, 52, 63));
        txtDataDespesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataDespesaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(240, 237, 204));
        jLabel5.setText("Categoria:");

        cbCatDespesa.setBackground(new java.awt.Color(240, 237, 204));
        cbCatDespesa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbCatDespesa.setForeground(new java.awt.Color(2, 52, 63));
        cbCatDespesa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CASA", "EDUCAÇÃO", "ELETRONICOS", "LAZER", "OUTROS", "TRANSPORTE", "MERCADO", "VESTUARIO", "SAUDE" }));
        cbCatDespesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCatDespesaActionPerformed(evt);
            }
        });

        btnSalvarDespesa.setBackground(new java.awt.Color(240, 237, 204));
        btnSalvarDespesa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSalvarDespesa.setForeground(new java.awt.Color(2, 52, 63));
        btnSalvarDespesa.setText("SALVAR");
        btnSalvarDespesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarDespesaActionPerformed(evt);
            }
        });

        btnExcluirDespesa.setBackground(new java.awt.Color(240, 237, 204));
        btnExcluirDespesa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExcluirDespesa.setForeground(new java.awt.Color(2, 52, 63));
        btnExcluirDespesa.setText("EXCLUIR");
        btnExcluirDespesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirDespesaActionPerformed(evt);
            }
        });

        btnLimparDespesa.setBackground(new java.awt.Color(240, 237, 204));
        btnLimparDespesa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLimparDespesa.setForeground(new java.awt.Color(2, 52, 63));
        btnLimparDespesa.setText("LIMPAR");
        btnLimparDespesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparDespesaActionPerformed(evt);
            }
        });

        tabelaDespesas.setBackground(new java.awt.Color(240, 237, 204));
        tabelaDespesas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tabelaDespesas.setForeground(new java.awt.Color(2, 52, 63));
        tabelaDespesas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Data", "Descrição", "Categoria", "Valor (R$)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaDespesas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaDespesasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaDespesas);

        jPanel7.setBackground(new java.awt.Color(240, 237, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 52, 63), 3));
        jPanel7.setPreferredSize(new java.awt.Dimension(331, 59));

        jLabel1.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(2, 52, 63));
        jLabel1.setText("DESPESAS");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(219, 219, 219)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDataDespesa, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                                    .addComponent(txtValorDespesa)
                                    .addComponent(txtDescDespesa)
                                    .addComponent(cbCatDespesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btnSalvarDespesa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnExcluirDespesa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLimparDespesa))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValorDespesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescDespesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDataDespesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCatDespesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvarDespesa)
                    .addComponent(btnExcluirDespesa)
                    .addComponent(btnLimparDespesa))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("DESPESAS", jPanel2);

        jPanel3.setBackground(new java.awt.Color(240, 237, 204));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(2, 52, 63));
        jLabel7.setText("Descrição:");

        txtDescReceita.setBackground(new java.awt.Color(2, 52, 63));
        txtDescReceita.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDescReceita.setForeground(new java.awt.Color(240, 237, 204));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(2, 52, 63));
        jLabel8.setText("Valor:");

        txtValorReceita.setBackground(new java.awt.Color(2, 52, 63));
        txtValorReceita.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtValorReceita.setForeground(new java.awt.Color(240, 237, 204));
        txtValorReceita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorReceitaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(2, 52, 63));
        jLabel9.setText("Data:");

        txtDataReceita.setBackground(new java.awt.Color(2, 52, 63));
        txtDataReceita.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDataReceita.setForeground(new java.awt.Color(240, 237, 204));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(2, 52, 63));
        jLabel10.setText("Categoria:");

        cbCatReceita.setBackground(new java.awt.Color(2, 52, 63));
        cbCatReceita.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbCatReceita.setForeground(new java.awt.Color(240, 237, 204));
        cbCatReceita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "INVESTIMENTOS", "OUTROS", "PRESENTE", "PRÊMIOS", "SALÁRIO" }));
        cbCatReceita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCatReceitaActionPerformed(evt);
            }
        });

        btnSalvarReceita.setBackground(new java.awt.Color(2, 52, 63));
        btnSalvarReceita.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSalvarReceita.setForeground(new java.awt.Color(240, 237, 204));
        btnSalvarReceita.setText("SALVAR");
        btnSalvarReceita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarReceitaActionPerformed(evt);
            }
        });

        btnExcluirReceita.setBackground(new java.awt.Color(2, 52, 63));
        btnExcluirReceita.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExcluirReceita.setForeground(new java.awt.Color(240, 237, 204));
        btnExcluirReceita.setText("EXCLUIR");
        btnExcluirReceita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirReceitaActionPerformed(evt);
            }
        });

        btnLimparReceita.setBackground(new java.awt.Color(2, 52, 63));
        btnLimparReceita.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLimparReceita.setForeground(new java.awt.Color(240, 237, 204));
        btnLimparReceita.setText("LIMPAR");
        btnLimparReceita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparReceitaActionPerformed(evt);
            }
        });

        tabelaReceita.setBackground(new java.awt.Color(2, 52, 63));
        tabelaReceita.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tabelaReceita.setForeground(new java.awt.Color(240, 237, 204));
        tabelaReceita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Data", "Descrição", "categoria", "Valor (R$)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaReceita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaReceitaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaReceita);

        jPanel6.setBackground(new java.awt.Color(2, 52, 63));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 237, 204), 3));
        jPanel6.setForeground(new java.awt.Color(240, 237, 204));

        jLabel6.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(240, 237, 204));
        jLabel6.setText("RECEITAS");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(227, 227, 227))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbCatReceita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescReceita)
                    .addComponent(txtValorReceita)
                    .addComponent(txtDataReceita, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnSalvarReceita)
                        .addGap(18, 18, 18)
                        .addComponent(btnExcluirReceita)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimparReceita)
                        .addGap(113, 113, 113))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtValorReceita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDescReceita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtDataReceita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cbCatReceita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvarReceita)
                    .addComponent(btnExcluirReceita)
                    .addComponent(btnLimparReceita))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("RECEITAS", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane)
        );

        getContentPane().add(jPanel1, new java.awt.GridBagConstraints());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtValorDespesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorDespesaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorDespesaActionPerformed

    private void cbCatDespesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCatDespesaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCatDespesaActionPerformed

    private void txtValorReceitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorReceitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorReceitaActionPerformed

    private void cbCatReceitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCatReceitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCatReceitaActionPerformed

    private void btnSalvarDespesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarDespesaActionPerformed
        try {
        Lancamento l = new Lancamento();
        l.setDescricao(txtDescDespesa.getText());
        l.setCategoria(cbCatDespesa.getSelectedItem().toString());
        l.setTipo("DESPESA");

        String valorTexto = txtValorDespesa.getText();
        valorTexto = valorTexto.replace("R$", "").replace(" ", "").replace(".", "");
        valorTexto = valorTexto.replace(",", ".");
        l.setValor(Double.parseDouble(valorTexto));

        java.time.format.DateTimeFormatter formatador = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        l.setData(LocalDate.parse(txtDataDespesa.getText(), formatador));

        if (idEmEdicaoDespesa == 0) {
            dao.salvar(l);
            JOptionPane.showMessageDialog(this, "Despesa Salva!");
        } else {
            l.setId(idEmEdicaoDespesa);
            dao.atualizar(l);
            JOptionPane.showMessageDialog(this, "Despesa Atualizada!");
            idEmEdicaoDespesa = 0;
        }

        atualizarTabelaDespesas();
        atualizarDashboard(); 
        limparCamposDespesa();

    } catch (Exception e) {
        e.printStackTrace(); 
        JOptionPane.showMessageDialog(this, "Erro: Verifique se a data está dia/mês/ano (ex: 02/12/2025) e o valor está correto.");
    }
    }//GEN-LAST:event_btnSalvarDespesaActionPerformed

    private void btnExcluirDespesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirDespesaActionPerformed
       if (tabelaDespesas.getSelectedRow() != -1) {
            int id = (int) tabelaDespesas.getValueAt(tabelaDespesas.getSelectedRow(), 0);
            
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Atenção", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                dao.excluir(id);
                atualizarTabelaDespesas();
                limparCamposDespesa();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para excluir.");
        }
    }//GEN-LAST:event_btnExcluirDespesaActionPerformed

    private void tabelaDespesasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaDespesasMouseClicked
        if (tabelaDespesas.getSelectedRow() != -1) {
            idEmEdicaoDespesa = (int) tabelaDespesas.getValueAt(tabelaDespesas.getSelectedRow(), 0);
            txtDataDespesa.setText(tabelaDespesas.getValueAt(tabelaDespesas.getSelectedRow(), 1).toString());
            txtDescDespesa.setText(tabelaDespesas.getValueAt(tabelaDespesas.getSelectedRow(), 2).toString());
            cbCatDespesa.setSelectedItem(tabelaDespesas.getValueAt(tabelaDespesas.getSelectedRow(), 3).toString());
            txtValorDespesa.setText(tabelaDespesas.getValueAt(tabelaDespesas.getSelectedRow(), 4).toString());
        }
    }//GEN-LAST:event_tabelaDespesasMouseClicked

    private void btnSalvarReceitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarReceitaActionPerformed
        try {
        Lancamento l = new Lancamento();
        l.setDescricao(txtDescReceita.getText());
        l.setCategoria(cbCatReceita.getSelectedItem().toString());
        l.setTipo("RECEITA");
        
        String valorTexto = txtValorReceita.getText();
        valorTexto = valorTexto.replace("R$", "").replace(" ", "").replace(".", "");
        valorTexto = valorTexto.replace(",", ".");
        l.setValor(Double.parseDouble(valorTexto));

        java.time.format.DateTimeFormatter formatador = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        l.setData(LocalDate.parse(txtDataReceita.getText(), formatador));

        if (idEmEdicaoReceita == 0) {
            dao.salvar(l);
            JOptionPane.showMessageDialog(this, "Receita Salva!");
        } else {
            l.setId(idEmEdicaoReceita);
            dao.atualizar(l);
            JOptionPane.showMessageDialog(this, "Receita Atualizada!");
            idEmEdicaoReceita = 0;
        }

        atualizarTabelaReceitas();
        atualizarDashboard(); 
        limparCamposReceita();

    } catch (Exception e) {
        e.printStackTrace(); 
        JOptionPane.showMessageDialog(this, "Erro: Verifique se a data está dia/mês/ano (ex: 02/12/2025) e o valor está correto.");
    }
    }//GEN-LAST:event_btnSalvarReceitaActionPerformed

    private void btnExcluirReceitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirReceitaActionPerformed
        if (tabelaReceita.getSelectedRow() != -1) {
            int id = (int) tabelaReceita.getValueAt(tabelaReceita.getSelectedRow(), 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Atenção", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                dao.excluir(id);
                atualizarTabelaReceitas();
                limparCamposReceita();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para excluir.");
        }
    }//GEN-LAST:event_btnExcluirReceitaActionPerformed

    private void tabelaReceitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaReceitaMouseClicked
       if (tabelaReceita.getSelectedRow() != -1) {
            idEmEdicaoReceita = (int) tabelaReceita.getValueAt(tabelaReceita.getSelectedRow(), 0);
            txtDataReceita.setText(tabelaReceita.getValueAt(tabelaReceita.getSelectedRow(), 1).toString());
            txtDescReceita.setText(tabelaReceita.getValueAt(tabelaReceita.getSelectedRow(), 2).toString());
            cbCatReceita.setSelectedItem(tabelaReceita.getValueAt(tabelaReceita.getSelectedRow(), 3).toString());
            txtValorReceita.setText(tabelaReceita.getValueAt(tabelaReceita.getSelectedRow(), 4).toString());
        }
    }//GEN-LAST:event_tabelaReceitaMouseClicked

    private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneStateChanged
        if (jTabbedPane.getSelectedIndex() == 0) { 
        atualizarDashboard();
    }
    }//GEN-LAST:event_jTabbedPaneStateChanged

    private void txtDataDespesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataDespesaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataDespesaActionPerformed

    private void btnIrDespesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIrDespesasActionPerformed
       jTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_btnIrDespesasActionPerformed

    private void btnIrReceitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIrReceitasActionPerformed
        jTabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_btnIrReceitasActionPerformed

    private void btnLimparDespesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparDespesaActionPerformed
        limparCamposDespesa();
    }//GEN-LAST:event_btnLimparDespesaActionPerformed

    private void btnLimparReceitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparReceitaActionPerformed
        limparCamposReceita();
    }//GEN-LAST:event_btnLimparReceitaActionPerformed

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
    private javax.swing.JButton btnExcluirDespesa;
    private javax.swing.JButton btnExcluirReceita;
    private javax.swing.JButton btnIrDespesas;
    private javax.swing.JButton btnIrReceitas;
    private javax.swing.JButton btnLimparDespesa;
    private javax.swing.JButton btnLimparReceita;
    private javax.swing.JButton btnSalvarDespesa;
    private javax.swing.JButton btnSalvarReceita;
    private javax.swing.JComboBox<String> cbCatDespesa;
    private javax.swing.JComboBox<String> cbCatReceita;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JLabel lblTotalDespesa;
    private javax.swing.JLabel lblTotalReceita;
    private javax.swing.JTable tabelaDespesas;
    private javax.swing.JTable tabelaReceita;
    private javax.swing.JTextField txtDataDespesa;
    private javax.swing.JTextField txtDataReceita;
    private javax.swing.JTextField txtDescDespesa;
    private javax.swing.JTextField txtDescReceita;
    private javax.swing.JTextField txtValorDespesa;
    private javax.swing.JTextField txtValorReceita;
    // End of variables declaration//GEN-END:variables
}
