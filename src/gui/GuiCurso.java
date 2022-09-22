package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import util.Conexao;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GuiCurso extends JFrame {

	private JPanel contentPane;
	private JTextField txfNome;
	private JTextField txfCategoria;
	private JTable table;
	private DefaultTableModel modelo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiCurso frame = new GuiCurso();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GuiCurso() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 438, 447);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setBounds(27, 35, 49, 14);
		contentPane.add(lblNewLabel);
		
		txfNome = new JTextField();
		txfNome.setBounds(100, 32, 261, 20);
		contentPane.add(txfNome);
		txfNome.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Categoria:");
		lblNewLabel_1.setBounds(27, 104, 66, 14);
		contentPane.add(lblNewLabel_1);
		
		txfCategoria = new JTextField();
		txfCategoria.setBounds(100, 101, 261, 20);
		contentPane.add(txfCategoria);
		txfCategoria.setColumns(10);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection con = Conexao.conectar();
					String sql = "insert into curso (nome, categoria) values (?,?)";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, txfNome.getText());
					ps.setString(2, txfCategoria.getText());
					ps.executeUpdate();
					con.close();
					JOptionPane.showMessageDialog(null, "Registro inserido");
					popularTabela();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro" + ex);
				}
			}
		});
		btnSalvar.setBounds(272, 155, 89, 23);
		contentPane.add(btnSalvar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 220, 404, 180);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
		});
		scrollPane.setViewportView(table);	
		
		popularTabela();
	}
	
	public void popularTabela() {		
		modelo = new DefaultTableModel();
		modelo.addColumn("Codigo");
		modelo.addColumn("Nome");
		modelo.addColumn("Categoria");
		
		try {
			Connection con = Conexao.conectar();
			String sql = "select * from curso";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				modelo.addRow(new String[] {rs.getString("codigo"), 
						rs.getString("nome"), rs.getString("categoria")});
			}
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro");
		}
		table.setModel(modelo);
	}
	
}
