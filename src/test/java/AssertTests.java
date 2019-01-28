import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;

public class AssertTests {
    @Test
    public void AssertBooleanDelta() {
        Assert.assertEquals("Erro de Comparação: ", 1, 2);
        Assert.assertEquals(0.25, 0.25, 0.01);
        Assert.assertEquals(Math.PI, 3.1415, 0.0001);
    }

    @Test
    public void AssertIntAndIntegerValues() {
        int i = 5;
        Integer i2 = 5;

        Assert.assertEquals(Integer.valueOf(i), i2);
        Assert.assertEquals(i, i2.intValue());
    }

    @Test
    public void AssertUsuario() {
        Usuario user1 = new Usuario("Usuario 1");
        Usuario user2 = new Usuario("Usuario 1");
        Usuario user3 = user2;
        Usuario user4 = null;

        Assert.assertEquals(user1, user2);

        Assert.assertSame(user2, user3);

        Assert.assertNotSame(user1, user2);

        Assert.assertNull(user4);

        Assert.assertNotNull(user1);
    }
}
