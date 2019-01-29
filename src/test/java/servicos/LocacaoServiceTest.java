package servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.servicos.LocacaoService;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LocacaoServiceTest {
    private LocacaoService locacaoService;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void tearDown() {
        locacaoService = new LocacaoService();
    }

    @After
    public void setup() {

    }

    @BeforeClass
    public static void tearDownBeforeClass() {

    }

    @AfterClass
    public static void setupAfterClass() {

    }

    @Test
    public void TesteLocacaoDeFilme() throws Exception {
        Usuario usuario = new Usuario("Leonardo");
        List<Filme> filmes = Arrays.asList(
                new Filme("Resgate do Soldado Ryan", 1, 10.0)
        );

        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        error.checkThat(locacao.getValor(), is(equalTo(10.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    // Forma Elegante
    @Test(expected = FilmeSemEstoqueException.class)
    public void TesteLocacaoFilmeSemEstoque() throws Exception {
        Usuario usuario = new Usuario("Leonardo");
        List<Filme> filmes = Arrays.asList(
                new Filme("Resgate do Soldado Ryan", 0, 10.0)
        );

        locacaoService.alugarFilme(usuario, filmes);
    }

    // Forma Robusta
    @Test
    public void TesteLocacaoUsuarioVazio() throws FilmeSemEstoqueException {
        LocacaoService locacaoService = new LocacaoService();
        List<Filme> filmes = Arrays.asList(
                new Filme("Resgate do Soldado Ryan", 1, 10.0)
        );

        try {
            locacaoService.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuário vazio"));
        }
    }

    // Nova
    @Test
    public void TesteLocacaoFilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Leonardo");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        locacaoService.alugarFilme(usuario, null);
    }
}
