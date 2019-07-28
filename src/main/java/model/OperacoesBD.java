package model;

import controller.Produto;
import controller.TipoPromocao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OperacoesBD {

    public static void popularBD() throws SQLException {
        popularTabelaProdutos();
        popularTabelaPromocoes();
    }

    private static void popularTabelaProdutos() throws SQLException {
        inserirProdutoBD("A", 50);
        inserirProdutoBD("B", 30);
        inserirProdutoBD("C", 20);
        inserirProdutoBD("D", 15);
    }

    private static void popularTabelaPromocoes() throws SQLException {
        inserirPromocaoBD(TipoPromocao.TRES_POR_130.getIdPromocao(), obterIdProdutoPeloNome("A"));
        inserirPromocaoBD(TipoPromocao.LEVE_3_PAGUE_2.getIdPromocao(), obterIdProdutoPeloNome("A"));
        inserirPromocaoBD(TipoPromocao.DOIS_POR_45.getIdPromocao(), obterIdProdutoPeloNome("B"));
        inserirPromocaoBD(TipoPromocao.LEVE_3_PAGUE_2.getIdPromocao(), obterIdProdutoPeloNome("C"));
        inserirPromocaoBD(TipoPromocao.DOIS_POR_25.getIdPromocao(), obterIdProdutoPeloNome("C"));
    }

    private static void inserirPromocaoBD(int id_promocao, int id_produto_respectivo) throws SQLException {
        //Insere a promocão no banco de dados
        PreparedStatement st = ConexaoBD.getConnection().prepareStatement(
                "INSERT INTO promocoes(id_promocao) VALUES (?) ON CONFLICT DO NOTHING"
        );
        st.setInt(1, id_promocao);
        st.executeUpdate();

        //Faz a ligação do produto com a promocao
        st = ConexaoBD.getConnection().prepareStatement(
                "INSERT INTO produto_promocao(id_produto, id_promocao) VALUES (?,?)"
        );
        st.setInt(1,id_produto_respectivo);
        st.setInt(2,id_promocao);
        st.executeUpdate();

        ConexaoBD.desconectar();
    }

    public static Produto obterProdutoPeloNome(String nomeProduto) throws SQLException {
        Produto p = new Produto();
        //Statement para realizar a consulta no banco de dados
        PreparedStatement st = ConexaoBD.getConnection().prepareStatement(
                "SELECT * FROM produtos WHERE nome_item=" + "'" + nomeProduto + "'"
        );

        ResultSet resultSet = st.executeQuery();
        resultSet.next();

        String nome = resultSet.getString("nome_item");
        int preco = resultSet.getInt("preco");

        p.setPreco(preco);
        p.setNome(nome);
        p.setPromocoes(obterPromocoesAssociadasAoProduto(nomeProduto));

        return p;
    }

    private static void inserirProdutoBD(String nome_item, int preco) throws SQLException {
        PreparedStatement st = ConexaoBD.getConnection().prepareStatement(
                "INSERT INTO produtos(nome_item, preco) VALUES (?,?)"
        );
        st.setString(1, nome_item);
        st.setInt(2, preco);
        st.executeUpdate();
        ConexaoBD.desconectar();

    }

    public static void droparTodasTabelas() throws SQLException {
        deletarTabelaProdutos();
        deletarTabelaPromocoes();
        deletarTabelaProdutosPromocoes();
    }

    private static void deletarTabelaProdutosPromocoes() throws SQLException {
        PreparedStatement st = ConexaoBD.getConnection().prepareStatement(
                "DELETE FROM produto_promocao"
        );
        st.executeUpdate();
        st.close();
        ConexaoBD.desconectar();
    }

    private static void deletarTabelaProdutos() throws SQLException {
        PreparedStatement st = ConexaoBD.getConnection().prepareStatement(
                "DELETE FROM produtos"
        );
        st.executeUpdate();
        st.close();
        ConexaoBD.desconectar();
    }

    private static void deletarTabelaPromocoes() throws SQLException {
        PreparedStatement st = ConexaoBD.getConnection().prepareStatement(
                "DELETE FROM promocoes"
        );
        st.executeUpdate();
        st.close();
        ConexaoBD.desconectar();
    }

    private static int obterIdProdutoPeloNome(String nome_produto) throws SQLException {
        int id = 0;
        PreparedStatement st = ConexaoBD.getConnection().prepareStatement(
                "SELECT id_produto FROM produtos WHERE nome_item=" + "'" + nome_produto + "'"
        );
        ResultSet resultSet = st.executeQuery();
        resultSet.next();
        id = resultSet.getInt("id_produto");
        ConexaoBD.desconectar();
        return id;
    }

    public static ArrayList<TipoPromocao> obterPromocoesAssociadasAoProduto(String nomeProduto) throws SQLException {
        ArrayList<TipoPromocao> promocoes = new ArrayList<>();
        //Obtem o id do produto
        PreparedStatement st = ConexaoBD.getConnection().prepareStatement(
                "SELECT * FROM produtos WHERE nome_item=" + "'" + nomeProduto + "'"
        );
        ResultSet resultadoProduto = st.executeQuery();
        resultadoProduto.next();
        int idProduto = resultadoProduto.getInt("id_produto");

        // Obtem as promocoes associadas aquele produto
        st = ConexaoBD.getConnection().prepareStatement(
                "SELECT * FROM produto_promocao WHERE id_produto=" + idProduto
        );
        ResultSet resultSetPromocoes = st.executeQuery();
        while(resultSetPromocoes.next()){
            int idPromocao = resultSetPromocoes.getInt("id_promocao");
            TipoPromocao t = obterPromocaoPeloId(idPromocao);
            promocoes.add(t);
        }

        ConexaoBD.desconectar();

        return promocoes;
    }

    private static TipoPromocao obterPromocaoPeloId(int id){
        for (TipoPromocao t: TipoPromocao.values()) {
            if(id == t.getIdPromocao()) return t;
        }
        return null;
    }
}
