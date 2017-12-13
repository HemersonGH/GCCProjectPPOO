package br.ufla.gcc.ppoo.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.ufla.gcc.ppoo.BancoDeDados.BancoDeDados;
import br.ufla.gcc.ppoo.Control.ControleDadosUsuarios;
import br.ufla.gcc.ppoo.Dados.DadosLogin;
import br.ufla.gcc.ppoo.Exceptions.BancoDadosException;
import br.ufla.gcc.ppoo.Exceptions.BuscasException;
import br.ufla.gcc.ppoo.Exceptions.ConverteSenhaException;
import br.ufla.gcc.ppoo.Exceptions.UsuarioException;

public class TelaLogin {
	
	private JFrame myViewLogin;
	private JTextField txtNovoUsuario;
	private JTextField textAreaUser;
	private JPasswordField passwordField;
	private BancoDeDados bancoDDados = new BancoDeDados();

	public TelaLogin() {
		try {
			bancoDDados.Conecta();
		} catch (BancoDadosException dbe) {
			JOptionPane.showMessageDialog(null, dbe.getMessage(), dbe.getTitulo(), JOptionPane.ERROR_MESSAGE);
		}
		
		View();
	}
	
	public void confereSenhaUsuario(String senhaInserida, String senhaUsuario) throws BuscasException, ConverteSenhaException {
		if (!ControleDadosUsuarios.ConvertMD5(senhaInserida).equals(senhaUsuario)) {
			throw new BuscasException(" Usu�rio e/ou senha errada...", "Usu�rio invalido");
		}
	}

	public void View(){

		myViewLogin = new JFrame();
		myViewLogin.getContentPane().setBackground(new Color(51, 102, 153));
		myViewLogin.getContentPane().setForeground(new Color(255, 255, 255));
		myViewLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myViewLogin.setResizable(false);
		myViewLogin.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		myViewLogin.setTitle("Login");
		myViewLogin.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		myViewLogin.getContentPane().setLayout(null);
		
		textAreaUser = new JTextField();
		textAreaUser.setToolTipText("Digite seu email...");
		textAreaUser.setBackground(new Color(255, 255, 255));
		textAreaUser.setBounds(90, 57, 340, 25);
		myViewLogin.getContentPane().add(textAreaUser);
		textAreaUser.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("Digite sua senha...");
		passwordField.setBackground(new Color(255, 255, 255));
		passwordField.setBounds(90, 111, 220, 25);
		myViewLogin.getContentPane().add(passwordField);
		
		JButton btnEnter = new JButton("Entrar");
		btnEnter.setForeground(new Color(0, 0, 0));
		btnEnter.setToolTipText("Entrar");
		btnEnter.setFont(new Font("Arial", Font.PLAIN, 14));
		btnEnter.setBackground(new Color(255, 255, 255));
		btnEnter.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				DadosLogin dadosLogin;
				
				try {
					dadosLogin = new DadosLogin(ControleDadosUsuarios.BuscarDados(textAreaUser.getText()));
					confereSenhaUsuario(passwordField.getText().trim(), dadosLogin.getSenha());					
					
					myViewLogin.dispose();
					new TelaPrincipal(dadosLogin);
				}  catch (BancoDadosException dbe) {
					JOptionPane.showMessageDialog(null, dbe.getMessage(), dbe.getTitulo(), JOptionPane.ERROR_MESSAGE);
				} catch (UsuarioException ee) {
					JOptionPane.showMessageDialog(null, ee.getMessage(), ee.getTitulo(), JOptionPane.ERROR_MESSAGE);
				} catch (ConverteSenhaException cse) {
					JOptionPane.showMessageDialog(null, cse.getMessage(), cse.getTitulo(), JOptionPane.ERROR_MESSAGE);
				} catch (BuscasException be) {
					JOptionPane.showMessageDialog(null, be.getMessage(), be.getTitulo(), JOptionPane.ERROR_MESSAGE);
				} 					
			}
		});
		btnEnter.setBounds(90, 160, 90, 25);
		myViewLogin.getContentPane().add(btnEnter);
		
		JButton btnCancel = new JButton("Sair");
		btnCancel.setToolTipText("Sair");
		btnCancel.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCancel.setBackground(new Color(255, 255, 255));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				myViewLogin.dispose();
				
				try {
					bancoDDados.Desconecta();
				}  catch (BancoDadosException dbe) {
					JOptionPane.showMessageDialog(null, dbe.getMessage(), dbe.getTitulo(), JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCancel.setBounds(320, 160, 90, 25);
		myViewLogin.getContentPane().add(btnCancel);
		
		txtNovoUsuario = new JTextField();
		txtNovoUsuario.setForeground(new Color(255, 255, 255));
		txtNovoUsuario.setFont(new Font("Arial", Font.ITALIC, 14));
		txtNovoUsuario.setBackground(new Color(0, 128, 128));
		txtNovoUsuario.setToolTipText("Clique aqui para cadastrar um novo usu�rio");
		txtNovoUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				myViewLogin.dispose();
				new TelaCadastroUsuario();
			}
		});
		txtNovoUsuario.setEditable(false);
		txtNovoUsuario.setText("Novo usu�rio");
		txtNovoUsuario.setBounds(335, 113, 95, 20);
		myViewLogin.getContentPane().add(txtNovoUsuario);
		txtNovoUsuario.setOpaque(false);
		txtNovoUsuario.setBorder(null);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setForeground(new Color(255, 255, 255));
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSenha.setBounds(40, 111, 50, 20);
		myViewLogin.getContentPane().add(lblSenha);
		
		JLabel lblLogin = new JLabel("Email:");
		lblLogin.setForeground(new Color(255, 255, 255));
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLogin.setBounds(48, 56, 45, 25);
		myViewLogin.getContentPane().add(lblLogin);
		
		JLabel lblAutenticao = new JLabel("Autenticar usu�rio ");
		lblAutenticao.setBackground(new Color(51, 204, 102));
		lblAutenticao.setFont(new Font("Arial", Font.BOLD, 18));
		lblAutenticao.setForeground(new Color(255, 255, 255));
		lblAutenticao.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutenticao.setBounds(125, 10, 225, 35);
		myViewLogin.getContentPane().add(lblAutenticao);
		
		myViewLogin.setSize(485, 240);
		myViewLogin.setVisible(true);
		myViewLogin.setResizable(false);
	}
}	

