package controller;

public enum TipoPromocao {
    TRES_POR_130(1),
    DOIS_POR_45(2),
    LEVE_3_PAGUE_2(3),
    DOIS_POR_25(4),
    NENHUMA(5);

    private final int idPromocao;
    TipoPromocao(int id_promocao){
        idPromocao = id_promocao;
    }

    public int getIdPromocao(){
        return idPromocao;
    }
}
