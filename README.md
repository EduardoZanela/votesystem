# votesystem

Projeto criado usando Java JDK 11 e SpringBoot, RabbitMQ, Docker, Lombok e Junit.
Para documentação foi utilizado biblioteca de swaggerui que pode ser acessada atraves do link http://localhost:8080/swagger-ui.html.

- Para acessar o projeto é preciso subir o serviços utilizando o docker atraves do comando  docker-compose up -d.

- Apos importar o projeto na IDE e subir a aplicaçao.


O projeto consiste em uma API utilizando framework Spring boot, o qual foi escolhido por ser muito adotado e aceito no mercado. As requisições chegam através de um controler chamado PautaController que direciona a requisição para o Service que ira executar a logica do negocio, para as requisições foram utilizados metodos REST POST e GET. O service PautaService é responsavel pelas operações da Pauta. Qual é recebido uma requisição para a criação da pauta, o service salva a entidade no banco e inicia um scheduler que fica responsavel por ao enceramento da votação disparar uma mensagem através do broker RabbitMq com o resultado para serviços interessados. No serviço de votação é recebido o voto do associado o qual faz a verificação se o mesmo ja votou ou se a votação ainda esta em andamento, alem disto é disparado uma consulta a um serviço externo que verifica se o associado esta habilitado a votar, caso passo nas validações o associado é criado no banco caso não exista e seu voto contabilizado.
