package enviando_email.enviando_email;

import enviando.email.ObjetoEnviaEmail;

public class AppTest {

	@org.junit.Test
	public void testeEmail() throws Exception {
		
		StringBuilder stringBilderCorpoEmail = new StringBuilder();
		stringBilderCorpoEmail.append(" <title></title><p><span style=font-size:28px><span style=\"font-family:courier new,courier,monospace\"><span style=color:#00f><strong>Voçe esta recebendo um e-mil enviado pelo Curso Java.. </strong></span></span></span><p><span style=font-size:18px><span style=color:grey><span style=\"font-family:courier new,courier,monospace\"><strong>clique abaixo para acessar o site da caixa</strong></span></span></span><p><a href=http://www.caixa.gov.br/Paginas/home-caixa.aspx>http://www.caixa.gov.br/Paginas/home-caixa.aspx</a><p><img alt=\"\"src=https://assets.blu365.com.br/uploads/sites/4/2019/06/curiosidades-sobre-a-caixa-economica-federal-98.jpg style=width:261px;height:178px>");

		ObjetoEnviaEmail objetoEmail = new ObjetoEnviaEmail("drogafarma.ce@gmail.com", "Jorge - Analista de Sistema",
				"Email enviado com Java", stringBilderCorpoEmail.toString());

		objetoEmail.enviarEmailAnexo(true);
		

		System.out.println("E-mail enviado com sucesso!");

	}
}
