package controller;

import model.OperacoesBD;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Checkout {
    private int precoTotal;
    private int descontoTotal;
    private HashMap<Produto, Integer> produtoMap = new HashMap<Produto, Integer>();

    public Checkout() {
        try {
            produtoMap.put(OperacoesBD.obterProdutoPeloNome("A"),0);
            produtoMap.put(OperacoesBD.obterProdutoPeloNome("B"),0);
            produtoMap.put(OperacoesBD.obterProdutoPeloNome("C"),0);
            produtoMap.put(OperacoesBD.obterProdutoPeloNome("D"),0);
        }catch (SQLException e){
            e.getMessage();
        }

    }

    public void add(String produto) throws SQLException {
        AtomicReference<Produto> p = new AtomicReference<>();
        produtoMap.forEach((prod,key) ->{
            if(prod.getNome().equals(produto)) p.set(prod);
        });
        somaAoMap(p.get());
    }

    public void remove(String produto) {
        AtomicReference<Produto> p = new AtomicReference<>();
        produtoMap.forEach((prod,key) ->{
            if(prod.getNome().equals(produto)) p.set(prod);
        });
        subtraiDoMap(p.get());
    }

    public int getTotalPrice() {
        return precoTotal;
    }

    public int getTotalDiscount() {
        return descontoTotal;
    }

    private void somaAoMap(Produto p) {
        int quantidade = produtoMap.get(p);
        produtoMap.put(p, quantidade + 1);
        atualizaPrecoAtualEDesconto();
    }

    private void subtraiDoMap(Produto p) {
        int quantidade = produtoMap.get(p);
        produtoMap.put(p, quantidade - 1);
        atualizaPrecoAtualEDesconto();
    }

    public void atualizaPrecoAtualEDesconto() {
        AtomicInteger precoAnterior = new AtomicInteger();
        precoAnterior.set(0);
        descontoTotal = 0;
        produtoMap.forEach(((produto, quantidade) -> {
            precoAnterior.addAndGet( quantidade * produto.getPreco());
            descontoTotal += obterMelhorDesconto(produto.getPromocoes(),produto, quantidade);
        }));
        precoTotal = precoAnterior.get() - descontoTotal;
    }

    private int obterMelhorDesconto(ArrayList<TipoPromocao> promocoes, Produto produto, int quantidade){
        int desconto = 0;
        int multiplicador = 1;
        int descontoAtual = 0;
        Promocao promocao = new Promocao();
        for (TipoPromocao t: promocoes) {
            switch (t){
                case DOIS_POR_25:
                    if(quantidade >= 2){
                        multiplicador = quantidade/2;
                        descontoAtual = multiplicador * promocao.getDescontoP2Por25(produto);
                        if(descontoAtual >= desconto) desconto = descontoAtual;
                    }
                    break;
                case TRES_POR_130:
                    if (quantidade >= 3){
                        multiplicador = quantidade/3;
                        descontoAtual = multiplicador * promocao.getDescontoP3Por130(produto);
                        if (descontoAtual >= desconto) desconto = descontoAtual;
                    }
                    break;
                case DOIS_POR_45:
                    if ( quantidade >= 2) {
                        multiplicador = quantidade/2;
                        descontoAtual = multiplicador * promocao.getDescontoP2Por45(produto);
                        if (descontoAtual >= desconto) desconto = descontoAtual;
                    }
                    break;
                case LEVE_3_PAGUE_2:
                    if (quantidade >= 3) {
                        multiplicador = quantidade/3;
                        descontoAtual = multiplicador * promocao.getDescontoPLeve3Pague2(produto);
                        if ( descontoAtual >= desconto ) desconto = descontoAtual;
                    }
                    break;
                default:
                    break;
            }
        }

        return desconto;
    }
}
