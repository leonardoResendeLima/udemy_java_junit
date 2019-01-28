package servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.servicos.LocacaoService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LocacaoServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void TesteLocacaoDeFilme() throws Exception {
        // Cenário
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Leonardo");
        Filme filme = new Filme("Resgate do Soldado Ryan", 1, 10.0);

        // Execução
        Locacao locacao = locacaoService.alugarFilme(usuario, filme);

        // Verificação
        error.checkThat(locacao.getValor(), is(equalTo(10.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    @Test(expected = Exception.class)
    public void testeLocacao_filmeSemEstoque_1() throws Exception {
        // Cenário
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Leonardo");
        Filme filme = new Filme("Resgate do Soldado Ryan", 0, 10.0);

        // Execução
        locacaoService.alugarFilme(usuario, filme);
    }

    @Test
    public void testeLocacao_filmeSemEstoque_2() {
        // Cenário
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Leonardo");
        Filme filme = new Filme("Resgate do Soldado Ryan", 2, 10.0);

        // Execução
        try {
            locacaoService.alugarFilme(usuario, filme);
            Assert.fail("Deveria ter lançado exceção");
        } catch (Exception e) {
            assertThat(e.getMessage(), is("Estoque não disponível"));
        }
    }

    @Test
    public void testeLocacao_filmeSemEstoque_3() throws Exception {
        // Cenário
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Leonardo");
        Filme filme = new Filme("Resgate do Soldado Ryan", 0, 10.0);

        exception.expect(Exception.class);
        exception.expectMessage("Estoque não disponível");

        // Execução
        locacaoService.alugarFilme(usuario, filme);

    }


}
