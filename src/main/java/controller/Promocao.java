package controller;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Promocao implements Promocoes{

    @Override
    public int getDescontoP3Por130(Produto p) {
        int precoProduto = p.getPreco();
        int desconto = 3*precoProduto - 130;
        return desconto;
    }

    @Override
    public int getDescontoPLeve3Pague2(Produto p) {
        int precoProduto = p.getPreco();
        int desconto = 3*precoProduto - 2*precoProduto;
        return desconto;
    }

    @Override
    public int getDescontoP2Por45(Produto p) {
        int precoProduto = p.getPreco();
        int desconto = 2*precoProduto - 45;
        return desconto;
    }

    @Override
    public int getDescontoP2Por25(Produto p) {
        int precoProduto = p.getPreco();
        int desconto = 2*precoProduto - 25;
        return desconto;
    }
}
