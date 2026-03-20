# 🏨 API REST - Reservas de Hotel

API desenvolvida com **Java + Spring Boot** para gerenciar reservas de hotel, incluindo criação, consulta, atualização, cancelamento, check-in, check-out e controle de detalhes da estadia.

## Visão geral

O projeto foi criado para praticar conceitos importantes de desenvolvimento backend, como:

- criação de API REST
- organização em camadas
- regras de negócio
- persistência com JPA/Hibernate
- validações
- relacionamento `@OneToOne`

## Principais funcionalidades

### Reservas
- Criar reserva
- Listar todas as reservas
- Buscar reserva por ID
- Atualizar reserva
- Excluir reserva
- Buscar por status
- Buscar por tipo de quarto
- Buscar reservas do dia
- Buscar próximas reservas
- Buscar por nome ou email do hóspede

### Fluxo da hospedagem
- Confirmar reserva
- Cancelar reserva
- Realizar check-in
- Realizar check-out
- Alterar tipo de quarto

### Detalhes da estadia
- Adicionar detalhes à reserva
- Buscar detalhes
- Atualizar detalhes
- Remover detalhes
- Listar reservas com detalhes
- Listar reservas sem detalhes

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Jakarta Validation
- Jackson
- Maven

## Estrutura do projeto

```text
src/main/java/com/senai/pablineeandre/hotel
│
├── controler
│   └── ReservaController.java
├── entity
│   ├── Reserva.java
│   └── Detalhe.java
├── enums
│   ├── Status.java
│   └── TipoQuarto.java
├── repository
│   ├── ReservaRepository.java
│   └── DetalheRepository.java
├── services
│   └── ReservaService.java
└── HotelApplication.java
```

## Entidades principais

### Reserva
Representa os dados principais da reserva do hóspede.

**Campos principais:**
- `id`
- `nomeHospede`
- `emailHospede`
- `telefoneHospede`
- `dataEntrada`
- `dataSaida`
- `dataCheckIn`
- `dataCheckOut`
- `dataCriacao`
- `observacoes`
- `tipoQuarto`
- `status`
- `detalhe`

### Detalhe
Representa as informações complementares da estadia e do quarto.

**Campos principais:**
- `id`
- `numeroQuarto`
- `andar`
- `possuiFrigobar`
- `possuiVaranda`
- `acessibilidade`
- `observacoesQuarto`

## Enums

### Status
- `PENDENTE`
- `CONFIRMADA`
- `EM_HOSPEDAGEM`
- `CONCLUIDA`
- `CANCELADA`

### TipoQuarto
- `SOLTEIRO`
- `DUPLO`
- `SUITE`
- `SUITE_PRESIDENCIAL`

## Regras de negócio mais importantes

- A **data de entrada** deve ser hoje ou futura
- A **data de saída** deve ser posterior à data de entrada
- Apenas reservas **PENDENTE** podem ser confirmadas
- O **check-in** só pode ser feito em reservas **CONFIRMADAS**
- O **check-out** só pode ser feito em reservas **EM_HOSPEDAGEM**
- Reservas em hospedagem não podem ser excluídas
- O cancelamento só é permitido para reservas **PENDENTE** ou **CONFIRMADA**
- Os detalhes só podem ser adicionados em reservas **PENDENTE** ou **CONFIRMADA**
- Cada reserva pode possuir apenas **um** conjunto de detalhes

## Relacionamento entre entidades

O sistema utiliza um relacionamento **`@OneToOne`** entre `Reserva` e `Detalhe`:

- uma reserva possui um detalhe de estadia
- um detalhe pertence a uma única reserva

## Endpoints principais

### Reservas
- `POST /api/reservas`
- `GET /api/reservas`
- `GET /api/reservas/{id}`
- `PUT /api/reservas/{id}`
- `DELETE /api/reservas/{id}`
- `GET /api/reservas/status/{status}`
- `GET /api/reservas/tipo/{tipoQuarto}`
- `GET /api/reservas/hoje`
- `GET /api/reservas/proximas?dias=7`
- `GET /api/reservas/buscar?termo=ana`
- `GET /api/reservas/em-hospedagem`

### Ações da reserva
- `PATCH /api/reservas/{id}/confirmar`
- `PATCH /api/reservas/{id}/cancelar`
- `PATCH /api/reservas/{id}/checkin`
- `PATCH /api/reservas/{id}/checkout`
- `PATCH /api/reservas/{id}/tipo/{tipoQuarto}`

### Detalhes
- `POST /api/reservas/{id}/detalhes`
- `GET /api/reservas/{id}/detalhes`
- `PUT /api/reservas/{id}/detalhes`
- `DELETE /api/reservas/{id}/detalhes`
- `GET /api/reservas/com-detalhes`
- `GET /api/reservas/sem-detalhes`

## Exemplos de requisição

### Criar reserva
```json
{
  "nomeHospede": "Maria Souza",
  "emailHospede": "maria@email.com",
  "telefoneHospede": "62999999999",
  "dataEntrada": "2026-03-25",
  "dataSaida": "2026-03-30",
  "observacoes": "Quarto silencioso, se possível",
  "tipoQuarto": "SUITE"
}
```

### Adicionar detalhes
```json
{
  "numeroQuarto": "305",
  "andar": 3,
  "possuiFrigobar": true,
  "possuiVaranda": false,
  "acessibilidade": false,
  "observacoesQuarto": "Quarto próximo ao elevador"
}
```

## Respostas esperadas

A API pode retornar:

- `200 OK`
- `201 Created`
- `204 No Content`
- `400 Bad Request`
- `404 Not Found`
- `409 Conflict`

## Como executar

### Pré-requisitos
- Java 17+
- Maven
- Banco de dados configurado no `application.properties`

### Executando
```bash
git clone URL_DO_SEU_REPOSITORIO
cd hotel
mvn spring-boot:run
```

## Configuração do banco

Exemplo no `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hotel
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Aprendizados

Com este projeto, foi possível praticar:

- desenvolvimento de API REST com Spring Boot
- uso de JPA e Hibernate
- aplicação de regras de negócio
- modelagem de entidades
- relacionamento `OneToOne`
- tratamento de erros com `ResponseStatusException`

## Autor

Projeto desenvolvido por **Pabline Pereira** com foco acadêmico e de aprendizado em backend com Java e Spring Boot.
