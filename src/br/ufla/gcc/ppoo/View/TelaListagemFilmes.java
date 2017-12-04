package br.ufla.gcc.ppoo.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import br.ufla.gcc.ppoo.BancoDeDados.BancoDeDados;
import br.ufla.gcc.ppoo.Control.ControleDadosAvaliacao;
import br.ufla.gcc.ppoo.Control.ControleDadosComentarios;
import br.ufla.gcc.ppoo.Control.ControleDadosFilmes;
import br.ufla.gcc.ppoo.Control.ControleDadosUsuarios;
import br.ufla.gcc.ppoo.Dados.DadosLogin;
import br.ufla.gcc.ppoo.Dados.Filme;
import javax.swing.table.DefaultTableModel;

public class TelaListagemFilmes {
	
	private JFrame viewListagem;
	private JTable tableFilmes;
	private JScrollPane scrollPaneList;
	
	private static boolean status = false;
	
	
	private BancoDeDados bancoDDados = new BancoDeDados();
	private ArrayList<Filme> listFilms = new ArrayList<>();
	
	public static boolean getStatus() { 
		return status;
	}
	
	public static void setStatus(boolean bool) {
		status = bool;
	}
	
	public TelaListagemFilmes(DadosLogin dadosLogin){
		bancoDDados.Conecta();
		viewListagemDeFilmes(dadosLogin);
	}
	
	public ArrayList<Filme> atualizaLista(DadosLogin dl){
		return ControleDadosFilmes.BuscarFilmesUmUsuario(dl.getId());
	}
	
	@SuppressWarnings("serial")
	public void constroiTabela(ArrayList<Filme> listFilms){
		
		int i = 0, n = listFilms.size();
		String[] titulosColunas = { "Filme", "G�nero", "Data de Lan�amento", "Dura��o", "Diretor", "#Pontos" };
		Object [][]filmes = new Object[n][6];
		
		for (Filme filme : listFilms) {
			filmes[i][0] = filme.getNome();
			filmes[i][1] = filme.getGenero();
			filmes[i][2] = filme.getData();
			filmes[i][3] = filme.getDuracaoFilme();
			filmes[i][4] = filme.getDiretor();
			filmes[i][5] = filme.getPontos();
			i++;
		}
		
		tableFilmes.setModel(new DefaultTableModel(filmes, titulosColunas) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
	}
	
	public ArrayList<Filme> ordenaLista(ArrayList<Filme> listFilmes) {
	    Filme aux;
	    
	    for (int i = 0; i < listFilmes.size()-1; i++) {
			for (int j = 0; j < listFilmes.size()-1-i; j++) {					
				if (compareTo(listFilmes.get(j), listFilmes.get(j+1)) >= 1) {
					aux = listFilmes.get(j+1);
					listFilmes.set(j+1,  listFilmes.get(j));
					listFilmes.set(j, aux);
				} 
			}
		}
	    
	    return listFilmes;
	}
	
	public static int compareTo(Filme filme1, Filme filme2) {
		return filme1.getNome().toUpperCase().compareToIgnoreCase(filme2.getNome().toUpperCase());
	}
	
	@SuppressWarnings("unused")
	public void viewListagemDeFilmes(DadosLogin dadosLogin) {
		
		setStatus(true);
		DadosLogin dl = ControleDadosUsuarios.BuscarDados(dadosLogin.getEmail());
		
		viewListagem = new JFrame();
		viewListagem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		viewListagem.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				setStatus(false);
			}
		});
		viewListagem.setBackground(new Color(0, 0, 255));
		viewListagem.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		viewListagem.setVisible(true);
		viewListagem.getContentPane().setLayout(null);
		viewListagem.getContentPane().setBackground(new Color(51, 102, 153));
		viewListagem.getContentPane().setForeground(new Color(255, 255, 255));
		viewListagem.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		viewListagem.setTitle("Meus Filme");
		viewListagem.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		
		scrollPaneList = new JScrollPane();
		scrollPaneList.setBounds(10, 125, 875, 400);
		viewListagem.getContentPane().add(scrollPaneList);
		
		tableFilmes = new JTable();
		listFilms = atualizaLista(dl);	
		constroiTabela(listFilms);
		
		tableFilmes.setFont(new Font("Microsoft JhengHei", Font.BOLD, 12));
		tableFilmes.clearSelection();
		tableFilmes.setFillsViewportHeight(true);
		tableFilmes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);		
		scrollPaneList.setViewportView(tableFilmes);
		
		JLabel lblMeusFilme = new JLabel("Meus Filmes");
		lblMeusFilme.setHorizontalAlignment(SwingConstants.CENTER);
		lblMeusFilme.setForeground(Color.WHITE);
		lblMeusFilme.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 40));
		lblMeusFilme.setBounds(337, 20, 230, 40);
		viewListagem.getContentPane().add(lblMeusFilme);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(TelaListagemFilmes.class.getResource("/br/ufla/gcc/ppoo/Imagens/lista.jpg")));
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBackground(new Color(51, 51, 255));
		label.setBounds(290, 20, 40, 40);
		viewListagem.getContentPane().add(label);
		
		JLabel lblSelecione = new JLabel("Selecione um filme para realizar alguma a��o:");
		lblSelecione.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelecione.setForeground(Color.WHITE);
		lblSelecione.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 20));
		lblSelecione.setBounds(250, 100, 405, 25);
		viewListagem.getContentPane().add(lblSelecione);
		
		JButton btnVisualizar = new JButton("Visualizar");
		btnVisualizar.setIcon(new ImageIcon(TelaListagemFilmes.class.getResource("/br/ufla/gcc/ppoo/Imagens/filmes.png")));
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (tableFilmes.getSelectedRow() != -1) {
					String filmeSelect = (String) tableFilmes.getModel().getValueAt(tableFilmes.getSelectedRow() , 0);
					Filme filme = new Filme (Filme.comparaFilme(listFilms, filmeSelect));
					
					setStatus(false);
					viewListagem.dispose();
					
					new TelaVisualizaFilme(dl, filme, "TelaListagem");
				} else {
					JOptionPane.showMessageDialog(null, "Para visuzalizar um filme selecione a linha dele.", "Sele��o inv�lida", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnVisualizar.setForeground(new Color(0, 0, 0));
		btnVisualizar.setToolTipText("Visualizar item");
		btnVisualizar.setFont(new Font("Arial", Font.PLAIN, 14));
		btnVisualizar.setBackground(new Color(255, 255, 255));
		btnVisualizar.setBounds(10, 535, 120, 25);
		viewListagem.getContentPane().add(btnVisualizar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(TelaListagemFilmes.class.getResource("/br/ufla/gcc/ppoo/Imagens/editar.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (tableFilmes.getSelectedRow() != -1) {
					String filmeSelect = (String) tableFilmes.getModel().getValueAt(tableFilmes.getSelectedRow() , 0);
					Filme filme = new Filme (Filme.comparaFilme(listFilms, filmeSelect));
					
					setStatus(false);
					viewListagem.setVisible(false);
					
					new TelaEditaFilme(dl, filme);
				} else {
					JOptionPane.showMessageDialog(null, "Para editar um filme selecione a linha dele.", "Sele��o inv�lida", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEditar.setForeground(new Color(0, 0, 0));
		btnEditar.setToolTipText("Editar item");
		btnEditar.setFont(new Font("Arial", Font.PLAIN, 14));
		btnEditar.setBackground(new Color(255, 255, 255));
		btnEditar.setBounds(260, 535, 120, 25);
		viewListagem.getContentPane().add(btnEditar);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.setIcon(new ImageIcon(TelaListagemFilmes.class.getResource("/br/ufla/gcc/ppoo/Imagens/deletar.png")));
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (tableFilmes.getSelectedRow() != -1) {
					int select = tableFilmes.getSelectionModel().getLeadSelectionIndex();
					String filmeSelect = (String) tableFilmes.getModel().getValueAt(tableFilmes.getSelectedRow() , 0);
					
					Filme filme = new Filme (Filme.comparaFilme(listFilms, filmeSelect));
					
					final int confirm = JOptionPane.showConfirmDialog(null, "Deseja excluir esse filme ?", "Excluir",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if (JOptionPane.YES_OPTION == confirm) {
						
						if (ControleDadosComentarios.DeletaFilme(filme.getId_filme())) {
							
							if (ControleDadosAvaliacao.DeletaFilme(filme.getId_filme())) {
								
								if (ControleDadosFilmes.DeletaFilme(filme)) {
									
									JOptionPane.showMessageDialog(null, "Filme deletado do banco de dados com sucesso.", 
											"Filme Deletado Com Sucesso", JOptionPane.WARNING_MESSAGE);
									
									listFilms = atualizaLista(dl);
									listFilms = ordenaLista(listFilms);
									
									constroiTabela(listFilms);
									
								} else {
									JOptionPane.showMessageDialog(null, "Erro ao deletar filme da banco de dados.",
											"Erro Ao Deletar Filme", JOptionPane.ERROR_MESSAGE);
								}
								
							} else {
								JOptionPane.showMessageDialog(null, "Erro ao deletar pontua��o do filme.",
										"Erro Ao Deletar", JOptionPane.ERROR_MESSAGE);
							}
							
						} else {
							JOptionPane.showMessageDialog(null, "Erro ao deletar coment�rios do filme.",
									"Erro Ao Deletar", JOptionPane.ERROR_MESSAGE);
						}
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Para remover um filme selecione a linha dele.",
							"Sele��o inv�lida", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnRemover.setForeground(new Color(0, 0, 0));
		btnRemover.setToolTipText("Remover item");
		btnRemover.setFont(new Font("Arial", Font.PLAIN, 14));
		btnRemover.setBackground(new Color(255, 255, 255));
		btnRemover.setBounds(520, 535, 120, 25);
		viewListagem.getContentPane().add(btnRemover);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setStatus(false);
				viewListagem.dispose();
			}
		});
		btnCancelar.setIcon(new ImageIcon(TelaListagemFilmes.class.getResource("/br/ufla/gcc/ppoo/Imagens/btn-cancelar.png")));
		btnCancelar.setForeground(new Color(0, 0, 0));
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCancelar.setBackground(new Color(255, 255, 255));
		btnCancelar.setBounds(765, 535, 120, 25);
		viewListagem.getContentPane().add(btnCancelar);
		
		viewListagem.setResizable(false);
		viewListagem.setSize(900, 600);
		viewListagem.setVisible(true);		
	}
}
