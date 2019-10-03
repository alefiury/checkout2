import controller.Checkout;
import controller.Produto;
import controller.TipoPromocao;
import model.OperacoesBD;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;
//Classe responsável por realisar testes
public class MainTest {

    @Before
    public void init() throws SQLException {
        OperacoesBD.droparTodasTabelas();
    }

    @Test
    public void populate() throws SQLException {
        OperacoesBD.popularBD();
    }

    @Test
    public void teste1() throws SQLException {
        OperacoesBD.popularBD();
        Checkout checkout = new Checkout();
        checkout.add("A");
        assertEquals(50, checkout.getTotalPrice());
        assertEquals(0, checkout.getTotalDiscount());

        checkout.add("A");
        assertEquals(100, checkout.getTotalPrice());
        assertEquals(0, checkout.getTotalDiscount());

        checkout.add("A");
        assertEquals(100, checkout.getTotalPrice());
        assertEquals(50, checkout.getTotalDiscount());

        checkout.remove("A");
        assertEquals(100, checkout.getTotalPrice());
        assertEquals(0, checkout.getTotalDiscount());
    }

    @Test
    public void teste2() throws SQLException {
        OperacoesBD.popularBD();
        Checkout checkout = new Checkout();

        checkout.add("D");
        assertEquals(15, checkout.getTotalPrice());
        assertEquals(0, checkout.getTotalDiscount());

        checkout.add("A");
        assertEquals(65, checkout.getTotalPrice());
        assertEquals(0, checkout.getTotalDiscount());

        checkout.add("B");
        assertEquals(95, checkout.getTotalPrice());
        assertEquals(0, checkout.getTotalDiscount());

        checkout.add("A");
        assertEquals(145, checkout.getTotalPrice());
        assertEquals(0, checkout.getTotalDiscount());

        checkout.add("B");
        assertEquals(160, checkout.getTotalPrice());
        assertEquals(15, checkout.getTotalDiscount());

        checkout.add("A");
        assertEquals(160, checkout.getTotalPrice());
        assertEquals(65, checkout.getTotalDiscount());

        checkout.remove("A");
        assertEquals(160, checkout.getTotalPrice());
        assertEquals(15, checkout.getTotalDiscount());

        checkout.remove("B");
        assertEquals(145, checkout.getTotalPrice());
        assertEquals(0, checkout.getTotalDiscount());

    }

    @Test
    public void teste3() throws SQLException {
        OperacoesBD.popularBD();
        Checkout checkout = new Checkout();

        checkout.add("C");
        assertEquals(20, checkout.getTotalPrice());
        assertEquals(0, checkout.getTotalDiscount());

        checkout.add("C");
        assertEquals(25, checkout.getTotalPrice());
        assertEquals(15, checkout.getTotalDiscount());

        checkout.add("C");
        assertEquals(40, checkout.getTotalPrice());
        assertEquals(20, checkout.getTotalDiscount());

        checkout.add("C");
        assertEquals(50, checkout.getTotalPrice());
        assertEquals(30, checkout.getTotalDiscount());

        checkout.remove("C");
        assertEquals(40, checkout.getTotalPrice());
        assertEquals(20, checkout.getTotalDiscount());

        checkout.remove("C");
        assertEquals(25, checkout.getTotalPrice());
        assertEquals(15, checkout.getTotalDiscount());
    }

    @Test
    public void teste4() throws SQLException {
        OperacoesBD.popularBD();
        Checkout checkout = new Checkout();

        checkout.add("C");
        assertEquals(20, checkout.getTotalPrice());
        assertEquals(0, checkout.getTotalDiscount());

        checkout.add("B");
        assertEquals(50, checkout.getTotalPrice());
        assertEquals(0, checkout.getTotalDiscount());

        checkout.add("B");
        assertEquals(65, checkout.getTotalPrice());
        assertEquals(15, checkout.getTotalDiscount());

        checkout.remove("B");
        assertEquals(50, checkout.getTotalPrice());
        assertEquals(0, checkout.getTotalDiscount());

    }
}
