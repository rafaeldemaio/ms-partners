# ze-challenge-partners

---
### Propósito:

Microserviço criado para controlar os dados de parceiros, contendo as seguintes funcionalidades:
- Criar um novo parceiro.
- Recuperar um parceiro pelo seu id.
- Recuperar o parceiro mais próximo a determinada localização que também esteja dentro da sua área de cobertura.

---
### Executando projeto local

Para executarmos o projeto local precisamos do [Docker](https://www.docker.com/), o Docker foi escolhido pois nos garante que o projeto seja multi-plataforma, basta apenas termos o Docker instalado.<br>
Na pasta raiz do projeto execute o seguinte comando:
```shell script
docker-compose -f docker-local/docker-compose.yml build --no-cache && docker-compose -f docker-local/docker-compose.yml up -d
```
O docker-compose criar o banco local na port 27017, criar um container para de migrations que garanti a criação dos índices, e sobe o serviço na porta 8080.<br>
Para a execução do projeto é impressindivél que as portas supracitadas não estejam em uso. 

---
###Executando o projeto em produção

O Docker nos facilita também na subida do projeto em produção, pensando num mundo onde as transações são feitas em grandes escalas, e escalar a aplicação de forma automática é essencial. Em muitos ambientes as aplicações rodam em orquestradores de containers (Kubernetes, Openshift ou similares). Para realizar a subida para a produção o ideal é termos um pipeline na qual faz o deploy em algum desses orquestradores, assim que o merge na master for feito. 

---

### Como testar a aplicação

Utilizamos o conceito de [OpenApi](https://swagger.io/specification/) para documentação da aplicação, sua implementação  é feito pelo [Swaggeer](https://swagger.io/), e os testes podem ser feitos por lá, basta acessar o endpoint (/swagger-ui.html):
- http://localhost:8080/swagger-ui.html

---
### Tecnologias utilizadas.

- Java 11
- MongoDB
- Spring Boot
- Spring Validation
- Spring Actuator
- Open Api 3 + Swagger 3

A escolha por Java foi devido ao meu background onde possuo mais conhecimento e consequentemente consiguia desenvolver com maior agilidade.
A escolha pelo MongoDB foi feita levando em considerão 2 pointos, primeiro por os dados não serem normalizados e por ele ter suporte a Geospatial.
