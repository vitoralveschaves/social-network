<h1 style="font-weight: bold;">Social Network 💻</h1>

<p>
Social Network é uma API que simula as principais funcionalidades de uma rede social. Entre as
funcionalidades implementadas estão a autenticação com níveis de acesso, utilizando Spring
Security e JWT, o sistema de postagens com persistência no banco de dados via JPA e a função
de feed, que inclui paginação para melhorar o desempenho de carregamento.
</p>

<h2 id="technologies">💻 Tecnologias Utilizadas</h2>
<div style="display:inline_block">
    <br />
    <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="java"/>
    <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="spring boot"/>
    <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white" alt="hibernate"/>
    <img src="https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white" alt="mysql" />
    <img src="https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white" alt="maven" />
    <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="docker" />
</div><br />

<h2 id="started">🚀 Iniciando</h2>

Tenha instalado em sua máquina as seguintes tecnologias:

- Java
- Apache Maven
- Docker
- Intellij ou Eclipse

<h3>Clonando o projeto</h3>

```bash
git clone https://github.com/vitoralveschaves/social-network
```

<h3>Gerando chaves públicas e privadas</h3>
<p>Abra o terminal do projeto, (Esteja com o Ubuntu ou WSL) e navegue até o diretório resources</p>

```bash
cd src/main/resources
```
<p>Após isso, digite no teminal o comando abaixo para gerar a chave privada</p>

```bash
openssl genrsa > app.key
```
<p>Agora digite o comando a seguir para gerar a chave pública a partir da chave privada gerada</p>

```bash
openssl rsa -in app.key -pubout -out app.pub
```

<h3>Iniciando o container com o banco de dados Mysql</h3>
<p>Com o terminal, navegue até o diretório do projeto clonado e digite o comando:</p>

```bash
docker compose up -d
```

<h3>Iniciando</h3>

- Inicie sua aplicação clicando no Run da sua IDE ou com as teclas Ctrl + Shift + F10 (Intellij)
- A aplicação estará executando em http://localhost:8080

<h2 id="routes">📍 Endpoints da API</h2>

​
| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /users</kbd>     | cadastra um novo usuário [detalhes de envio](#post-users-detail)
| <kbd>POST /login</kbd>     | realiza o login do usuário cadastrado [detalhes da resposta](#post-login-detail)
| <kbd>POST /tweets</kbd>     | realiza uma postagem [detalhes da resposta](#post-tweet-detail)
| <kbd>GET /feed</kbd>     | lista as postagens [detalhes da resposta](#get-feed-detail)
| <kbd>DELETE /tweets/{id}</kbd>     | deleta uma postagem pelo id de tal

<em>Obs: todos os endpoints acima com exceção dos dois primeiros necessitam do token de autorização, que deve ser passado na propriedade authorization do header</em>

<h3 id="post-users-detail">POST /users</h3>

**REQUEST BODY**
```json
{
  "username": "user",
  "password": "12345"
}
```

<h3 id="post-login-detail">POST /login</h3>

**REQUEST BODY**
```json
{
  "username": "user",
  "password": "12345"
}
```

**RESPONSE**
```json

{
  "accessToken": "...",
  "expiresIn": 300
}

```
<h3 id="post-tweet-detail">POST /tweets</h3>

**REQUEST BODY**
```json
{
  "content": "lorem ipsum"
}
```

<h3 id="get-feed-detail">GET /tweets</h3>

**RESPONSE**
```json

{
  "feedItems": [
  {
    "tweetId": 1,
    "content": "lorem ipsum",
    "username": "user"
  }
],
"page": 0,
"pageSize": 1,
"totalPages": 1,
"totalElements": 1
}

```
