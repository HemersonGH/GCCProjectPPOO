package br.ufla.gcc.ppoo.Control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.ufla.gcc.ppoo.BancoDeDados.BancoDeDados;
import br.ufla.gcc.ppoo.Dados.Comentarios;

public class ControleDadosComentarios {
	
private static BancoDeDados bancoDados = new BancoDeDados();
	
	public static boolean CadastrarComentario(Comentarios comentario){
		bancoDados.Conecta();
		boolean ok = false;
		
		try {
			PreparedStatement pst = bancoDados.getConnection().prepareStatement("insert into comentarios (coment, id_filme_commit, id_user_commit) values(?,?,?)");
			
			pst.setString(1, comentario.getCommit());
			pst.setLong(2, comentario.getId_filme_commit());		
			pst.setLong(3, comentario.getId_user_commit());
			pst.execute();
			ok = true;
			
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Falha ao salvar comentário:\n" + ex.getMessage() + 
					"\nEntre em contato com o administrador do sistema.", "Falha ao salvar", JOptionPane.ERROR_MESSAGE);
		} finally {
			bancoDados.Desconecta();
		}
		
		return ok;
	}	
	
	public static ArrayList<Comentarios> BuscarAvaliacao(Long id_filme){
		bancoDados.Conecta();
		
		ArrayList<Comentarios> listCommits = new ArrayList<>();
		String commit;
		Long id_user_commit, id_filme_commit;
		
		try {
			PreparedStatement pst = bancoDados.getConnection().prepareStatement("SELECT * FROM comentarios WHERE id_filme_commit = ?");
			pst.setLong(1, id_filme);
			ResultSet rs = pst.executeQuery();	
			
			while (rs.next()) {	
				commit = rs.getString("coment");
				id_filme_commit = rs.getLong("id_filme_commit");
				id_user_commit = rs.getLong("id_user_commit");
				
				Comentarios comentario = new Comentarios(commit, id_user_commit, id_filme_commit);
				
				listCommits.add(comentario);
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Falha ao buscar comentários do filme:\n" + ex.getMessage() + 
					"\nEntre em contato com o administrador do sistema.",  "Falha na busca de comentários", JOptionPane.ERROR_MESSAGE);
		} finally {
			bancoDados.Desconecta();
		}
				
		return listCommits;
	}
	
	public static boolean DeletaFilme(Long id_filme){
		boolean encontrou = false;
		
		bancoDados.Conecta();
		
		try {
			PreparedStatement pst = bancoDados.getConnection().prepareStatement("DELETE from comentarios where id_filme_commit = ?");
			
			pst.setLong(1, id_filme);
			pst.execute();
			pst.close();	
			
			encontrou = true;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Erro ao deletar comentários do filme selecionado:\n" + ex.getMessage() + 
					"\nEntre em contato com o administrador do sistema.", "Falha ao deletar comentários", JOptionPane.ERROR_MESSAGE);
		} finally {
			bancoDados.Desconecta();
		}
		
		return encontrou;
	}

}
