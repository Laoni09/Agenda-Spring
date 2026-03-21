# Documentação da API

A aplicação expõe uma API RESTful consumindo e produzindo dados no formato JSON. Todos os erros da API seguem um formato padronizado contendo `timestamp`, `status`, `error`, `message` e `path`.

## 1. Domínio: Usuário (`/api/usuarios`)
Responsável pela autenticação e registro de usuários no sistema.

### Endpoint: Registrar Usuário
* **Método:** `POST`
* **URL:** `/api/usuarios/registrar`
* **Descrição:** Cria uma nova conta de usuário no banco de dados.

**Exemplo de JSON (Request Body):**
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123"
}
```

**Retornos Possíveis:**

| Código HTTP | Situação | Corpo da Resposta |
| :--- | :--- | :--- |
| **201 Created** | Registro efetuado com sucesso. | *Vazio* |
| **400 Bad Request** | Erro de validação (campos vazios/nulos). | JSON de Erro Padrão (Ex: "Nome é obrigatório") |
| **400 Bad Request** | Regra de negócio violada. | JSON de Erro Padrão ("Email já registrado") |

---

### Endpoint: Login
* **Método:** `POST`
* **URL:** `/api/usuarios/login`
* **Descrição:** Autentica um usuário existente.

**Exemplo de JSON (Request Body):**
```json
{
  "email": "joao@email.com",
  "senha": "123"
}
```

**Retornos Possíveis:**

| Código HTTP | Situação | Corpo da Resposta |
| :--- | :--- | :--- |
| **200 OK** | Autenticação bem sucedida. | `Integer` (ID do usuário logado) |
| **400 Bad Request** | Erro de validação (campos vazios/nulos). | JSON de Erro Padrão (Ex: "Email é obrigatório") |
| **400 Bad Request** | Credenciais inválidas. | JSON de Erro Padrão ("Usuário não encontrado") |

---

## 2. Domínio: Agenda / Contatos (`/api/agenda`)
Gerencia os contatos (Pessoais e Profissionais) vinculados a um usuário específico.

### Endpoint: Listar Contatos
* **Método:** `GET`
* **URL:** `/api/agenda/{usuarioId}`
* **Descrição:** Retorna a lista completa de contatos de um usuário.

**Retornos Possíveis:**

| Código HTTP | Situação | Corpo da Resposta |
| :--- | :--- | :--- |
| **200 OK** | Busca realizada com sucesso. | Lista em JSON (`[ { "id": 1, "nome": "...", ... } ]`) |

---

### Endpoint: Adicionar Contato Profissional
* **Método:** `POST`
* **URL:** `/api/agenda/{usuarioId}/profissional`
* **Descrição:** Salva um novo contato corporativo vinculado ao usuário informado na URL.

**Exemplo de JSON (Request Body):**
```json
{
  "nome": "Carlos TI",
  "telefone": "99999-2222",
  "empresa": "Tech Solutions"
}
```

**Retornos Possíveis:**

| Código HTTP | Situação | Corpo da Resposta |
| :--- | :--- | :--- |
| **201 Created** | Contato adicionado com sucesso. | *Vazio* |
| **400 Bad Request** | Erro de validação nos dados enviados. | JSON de Erro Padrão (Ex: "A empresa é obrigatória") |
| **404 Not Found** | O ID do usuário informado na URL não existe. | JSON de Erro Padrão ("Entidade não encontrada") |

---

### Endpoint: Adicionar Contato Pessoal
* **Método:** `POST`
* **URL:** `/api/agenda/{usuarioId}/pessoal`
* **Descrição:** Salva um novo contato pessoal vinculado ao usuário informado na URL.

**Exemplo de JSON (Request Body):**
```json
{
  "nome": "Maria Souza",
  "telefone": "99999-1111",
  "cpf": "111.222.333-44"
}
```

**Retornos Possíveis:**

| Código HTTP | Situação | Corpo da Resposta |
| :--- | :--- | :--- |
| **201 Created** | Contato adicionado com sucesso. | *Vazio* |
| **400 Bad Request** | Erro de validação nos dados enviados. | JSON de Erro Padrão (Ex: "CPF é obrigatório") |
| **404 Not Found** | O ID do usuário informado na URL não existe. | JSON de Erro Padrão ("Entidade não encontrada") |

---

### Endpoint: Remover Contato
* **Método:** `DELETE`
* **URL:** `/api/agenda/{usuarioId}/remover/{idContato}`
* **Descrição:** Exclui permanentemente um contato e suas tarefas vinculadas (Exclusão em Cascata).

**Retornos Possíveis:**

| Código HTTP | Situação | Corpo da Resposta |
| :--- | :--- | :--- |
| **204 No Content** | Contato removido com sucesso. | *Vazio* |
| **400 Bad Request** | Contato não existe no banco de dados. | JSON de Erro Padrão ("Contato não encontrado.") |
| **400 Bad Request** | Contato pertence a outro usuário. | JSON de Erro Padrão ("Acesso negado.") |

---

## 3. Domínio: Tarefas (`/api/tasks`)
Gerencia a lista de afazeres vinculada a um contato específico.

### Endpoint: Listar Tarefas
* **Método:** `GET`
* **URL:** `/api/tasks/{contatoId}`
* **Descrição:** Retorna todas as tarefas associadas a um contato.

**Retornos Possíveis:**

| Código HTTP | Situação | Corpo da Resposta |
| :--- | :--- | :--- |
| **200 OK** | Busca realizada com sucesso. | Lista em JSON de `TaskDTO` |

---

### Endpoint: Adicionar Tarefa
* **Método:** `POST`
* **URL:** `/api/tasks/{contatoId}`
* **Descrição:** Cria uma nova tarefa associada ao ID do contato informado.

**Exemplo de JSON (Request Body):**
```json
{
  "nome": "Enviar orçamento",
  "description": "Mandar o PDF em anexo.",
  "completed": false
}
```

**Retornos Possíveis:**

| Código HTTP | Situação | Corpo da Resposta |
| :--- | :--- | :--- |
| **201 Created** | Tarefa criada com sucesso. | *Vazio* |
| **400 Bad Request** | Erro de validação no formulário. | JSON de Erro Padrão (Ex: "Descrição é obrigatória") |
| **404 Not Found** | O ID do contato informado na URL não existe. | JSON de Erro Padrão ("Entidade não encontrada") |

---

### Endpoint: Atualizar Tarefa
* **Método:** `PUT`
* **URL:** `/api/tasks/{contatoId}/{taskId}`
* **Descrição:** Modifica os dados de uma tarefa existente (ex: marcar como concluída).

**Exemplo de JSON (Request Body):**
```json
{
  "nome": "Enviar orçamento",
  "description": "Mandar o PDF em anexo.",
  "completed": true
}
```

**Retornos Possíveis:**

| Código HTTP | Situação | Corpo da Resposta |
| :--- | :--- | :--- |
| **200 OK** | Tarefa atualizada com sucesso. | *Vazio* |
| **400 Bad Request** | Erro de validação (dados faltando). | JSON de Erro Padrão |
| **400 Bad Request** | O ID da tarefa não existe. | JSON de Erro Padrão ("Task não encontrada.") |
| **400 Bad Request** | A tarefa não pertence a este contato. | JSON de Erro Padrão ("Acesso negado.") |

---

### Endpoint: Remover Tarefa
* **Método:** `DELETE`
* **URL:** `/api/tasks/{contatoId}/{taskId}`
* **Descrição:** Exclui uma tarefa específica.

**Retornos Possíveis:**

| Código HTTP | Situação | Corpo da Resposta |
| :--- | :--- | :--- |
| **204 No Content** | Tarefa removida com sucesso. | *Vazio* |
| **400 Bad Request** | O ID da tarefa não existe no sistema. | JSON de Erro Padrão ("Task não encontrada.") |
| **400 Bad Request** | A tarefa não pertence ao contato informado. | JSON de Erro Padrão ("Acesso negado.") |

---

Posso auxiliar com algum outro ajuste no projeto ou já podemos prosseguir para o planejamento da arquitetura do projeto Javazon?