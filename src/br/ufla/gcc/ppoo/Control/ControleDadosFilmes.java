package br.ufla.gcc.ppoo.Control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.ufla.gcc.ppoo.BancoDeDados.BancoDeDados;
import br.ufla.gcc.ppoo.Dados.Filme;

public class ControleDadosFilmes {
	
	BancoDeDados bancoDados = new BancoDeDados();
	
	public void CadastrarFilme(Filme filme, int id_user){
		bancoDados.Conecta();
		
		try {
			PreparedStatement pst = bancoDados.connection.prepareStatement("insert into filmes(id_user,nome_filme,ano_lancamento,"
					+ "descricao,palavras_chaves,genero,duracao_filme, diretor) values(?,?,?,?,?,?,?,?)");
			
			pst.setInt(1, id_user);
			pst.setString(2, filme.getNome());		
			pst.setString(3, filme.getData());
			pst.setString(4, filme.getDescricao());	
			pst.setString(5, filme.getWordKeys());	
			pst.setString(6, filme.getGenero());	
			pst.setString(7, filme.getDuracaoFilme());	
			pst.setString(8, filme.getDiretor());	
			pst.execute();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		bancoDados.Desconecta();
	}	
	
	public ArrayList<Filme> buscarFilmesUmUsuario(int id){
		bancoDados.Conecta();
		
		ArrayList<Filme> listFilm = new ArrayList<>();
		
		try {
//			 PreparedStatement pst = bancoDados.connection.prepareStatement("SELECT * FROM filmes Where id_user = '" + id +"'");
//			PreparedStatement pst = bancoDados.connection.prepareStatement("SELECT id_user = '" + id + "' FROM filmes ORDER BY nome_filme ASC");
			PreparedStatement pst = bancoDados.connection.prepareStatement("SELECT * FROM filmes WHERE id_user = '" + id + "' ORDER BY nome_filme ASC");
			ResultSet rs = pst.executeQuery();	
			
			while (rs.next()) {	
				Filme filme = new Filme();
				
				filme.setNome(rs.getString("nome_filme"));
				filme.setData(rs.getString("ano_lancamento"));
				filme.setDescricao(rs.getString("descricao"));
				filme.setWordKeys(rs.getString("palavras_chaves"));
				filme.setGenero(rs.getString("genero"));
				filme.setDuracaoFilme(rs.getString("duracao_filme"));
				filme.setDiretor(rs.getString("diretor"));
				filme.setPontos(rs.getLong("pontos_filme"));
				filme.setId_user(rs.getLong("id_user"));
				filme.setId_filme(rs.getLong("id_filme"));
				
				listFilm.add(filme);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		bancoDados.Desconecta();
		return listFilm;
	}
	
	public ArrayList<Filme> buscarFilmesTodosUsuarios(int id){
		bancoDados.Conecta();
		
		ArrayList<Filme> listFilm = new ArrayList<>();
		
		try {
			PreparedStatement pst = bancoDados.connection.prepareStatement("SELECT * FROM filmes ORDER BY nome_filme ASC");
			ResultSet rs = pst.executeQuery();	
			
			while (rs.next()) {	
				Filme filme = new Filme();
				
				filme.setNome(rs.getString("nome_filme"));
				filme.setData(rs.getString("ano_lancamento"));
				filme.setDescricao(rs.getString("descricao"));
				filme.setWordKeys(rs.getString("palavras_chaves"));
				filme.setGenero(rs.getString("genero"));
				filme.setDuracaoFilme(rs.getString("duracao_filme"));
				filme.setDiretor(rs.getString("diretor"));
				filme.setPontos(rs.getLong("pontos_filme"));
				filme.setId_user(rs.getLong("id_user"));
				filme.setId_filme(rs.getLong("id_filme"));
				
				listFilm.add(filme);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		bancoDados.Desconecta();
		return listFilm;
	}
	
	public boolean confereFilme(String filme){
		bancoDados.Conecta();	
		boolean encontrei = false;
		
		try {
			PreparedStatement pst = bancoDados.connection.prepareStatement("SELECT * FROM filmes Where nome_filme = '" + filme +"'");
			ResultSet rs = pst.executeQuery();	
			
			while (rs.next()) {	
				encontrei = true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		bancoDados.Desconecta();
		return encontrei;
	}
	
	public boolean deletaFilme(Filme filme){
		boolean encontrou = false;
		
		bancoDados.Conecta();
		
		try {
			PreparedStatement pst = bancoDados.connection.prepareStatement("DELETE from filmes where nome_filme = ?");
			
			pst.setString(1, filme.getNome());
			pst.execute();
			pst.close();	
			encontrou = true;
	    } catch (SQLException ex) {
				ex.printStackTrace();
		}
		
		bancoDados.Desconecta();
		return encontrou;
	}
	
	public boolean alteraFilme(Filme filme){
		boolean encontrou = false;
		
		bancoDados.Conecta();
		
		try {
			 PreparedStatement pst = bancoDados.connection.prepareStatement("UPDATE filmes set nome_filme=?, ano_lancamento=?, "
			 		+ "descricao=?, palavras_chaves=?, genero=?, duracao_filme=?, diretor=? WHERE id_filme=?");

			 pst.setString(1, filme.getNome());
			 pst.setString(2, filme.getData());
			 pst.setString(3, filme.getDescricao());
			 pst.setString(4, filme.getWordKeys());
			 pst.setString(5, filme.getGenero());
			 pst.setString(6, filme.getDuracaoFilme());
			 pst.setString(7, filme.getDiretor());
			 pst.setLong(8, filme.getId_filme());
	         pst.execute();
	         
	         encontrou = true;
	    } catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		bancoDados.Desconecta();
		return encontrou;
	}
}
