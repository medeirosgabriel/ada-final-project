# Projeto Final Ada Avanade

## O projeto se resume a um jogo RPG (Role Playing Game). Como todo bom RPG, será duelado em turnos. Ao iniciar uma batalha você pode escolher o seu personagem favorito (herói ou monstro) e o seu nome.

## Algumas regras:
- Ao iniciar, será necessário escolher um personagem (herói ou monstro);
- Cada personagem tem seus atributos únicos. Escolha com calma o seu personagem;
- Os personagens possuem Pontos de Vida (PV);
- Se um personagem ficar com PV igual ou abaixo de zero então o oponente será o vencedor;
- O dano causado por um ataque depende da força do atacante e da defesa do defensor, enquanto o dano
recebido depende da força do atacante e da resistência do defensor e da eficácia de sua defesa;

## Personagens:
- Guerreiro
- Bárbaro
- Cavaleiro
- Orc
- Gigante
- Lobisomen

## Tecnologias:
- Spring Boot
- PostgreSQL
- Docker
- Swagger

## Executando o projeto
- Clone o repositório com o seguinte comando: `git clone https://github.com/medeirosgabriel/ada-final-project.git`
- Navegue até o diretório raiz do projeto com o comando: `cd ada-final-project/`
- Rode o comando `docker-compose up` para subirmos a aplicação
- A aplicação estará disponível na porta 8080 -> `http://localhost:8080`
- Endpoint do Swagger: `http://localhost:8080/swagger-ui/index.html`
