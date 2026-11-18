# FinControl

Sistema de controle de gastos residenciais com API Java/Spring Boot e interface Angular. O back-end segue uma organização DDD com separação CQRS: comandos alteram estado e queries consultam dados.

## Requisitos

- Java 21
- PostgreSQL
- Node.js 24 ou compatível com Angular 21

## Back-end

Configure a conexão PostgreSQL em `src/main/resources/application.properties`. As migrações Flyway criam/atualizam as tabelas automaticamente.

```powershell
.\mvnw.cmd spring-boot:run
```

### API

| Método | Endpoint | Operação |
|---|---|---|
| `POST` / `GET` | `/api/customers` | Cadastrar e listar pessoas |
| `PUT` / `DELETE` | `/api/customers/{id}` | Editar ou excluir pessoa e suas transações |
| `POST` / `GET` | `/api/categories` | Cadastrar e listar categorias |
| `POST` / `GET` | `/api/transactions` | Cadastrar e listar transações |
| `GET` | `/api/reports/people` | Totais por pessoa e total geral |
| `GET` | `/api/reports/categories` | Totais por categoria e total geral |

Categorias aceitam `Expenditure`, `Revenue` ou `Both`; transações aceitam `Expenditure` ou `Revenue`. IDs são UUID. Valores de transações devem ser positivos. Pessoas menores de 18 anos só podem ter despesas, e categoria e tipo de transação devem ser compatíveis.

## Front-end

```powershell
cd frontend
npm install
npm start
```

A interface fica disponível em `http://localhost:4200` e consome a API em `http://localhost:8080/api`.
