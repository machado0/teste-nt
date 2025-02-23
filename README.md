

**README**

**Aplicação de Pautas e Votos**

Essa aplicação é um sistema de votação que permite aos usuários criar, visualizar e votar em pautas.

**Endpoints**

A aplicação oferece os seguintes endpoints:

### **GET /pautas**

* Retorna uma lista de pautas com paginação.
* Parâmetros:
	+ `page`: número da página (opcional, padrão: 0)
	+ `size`: tamanho da página (opcional, padrão: 10)
* Exemplo: `GET /pautas?page=1&size=20`

### **POST /pautas**

* Cria uma nova pauta.
* Corpo da requisição:
	+ `titulo`: título da pauta (obrigatório)
	+ `descricao`: descrição da pauta (obrigatório)
* Exemplo: `POST /pautas { "titulo": "Pauta 1", "descricao": "Descrição da pauta 1" }`

### **GET /pautas/{pautaId)**

* Retorna uma pauta específica.
* Parâmetros:
	+ `pautaId`: ID da pauta (obrigatório)
* Exemplo: `GET /pautas/1`

### **PUT /pautas/{pautaId)**

* Atualiza uma pauta existente.
* Corpo da requisição:
	+ `titulo`: título da pauta (obrigatório)
	+ `descricao`: descrição da pauta (obrigatório)
* Exemplo: `PUT /pautas/1 { "titulo": "Pauta 1 atualizada", "descricao": "Descrição da pauta 1 atualizada" }`

### **DELETE /pautas/{pautaId)**

* Exclui uma pauta existente.
* Parâmetros:
	+ `pautaId`: ID da pauta (obrigatório)
* Exemplo: `DELETE /pautas/1`

### **GET /pautas/abrir-sessao**

* Abre a sessão de uma pauta.
* Parâmetros:
	+ `pautaId`: ID da pauta (obrigatório)
	+ `tempoEncerramento`: Data e hora do encerramento da pauta (obrigatório)
* Exemplo: `GET /pautas/abrir-sessao?tempoEncerramento=2025-02-23T13:45:45.123-03:00&pautaId=5`

### **GET /votos**

* Retorna uma lista de votos com paginação.
* Parâmetros:
	+ `page`: número da página (opcional, padrão: 0)
	+ `size`: tamanho da página (opcional, padrão: 10)
* Exemplo: `GET /votos?page=1&size=20`

### **POST /votos**

* Cria um novo voto.
* Corpo da requisição:
	+ `pautaId`: ID da pauta (obrigatório)
	+ `associadoId`: ID do usuário (obrigatório)
	+ `voto`: voto (obrigatório, true ou false)
* Exemplo: `POST /votos { "pautaId": 1, "associadoId": 1, "voto": true }`

### **GET /votos/{pautaId)**

* Retorna uma lista de votos para uma pauta específica.
* Parâmetros:
	+ `pautaId`: ID da pauta (obrigatório)
* Exemplo: `GET /votos/1`

### **PUT /votos/{votoId)**

* Atualiza um voto existente.
* Corpo da requisição:
	+ `pautaId`: ID da pauta (obrigatório)
	+ `associadoId`: ID do usuário (obrigatório)
	+ `voto`: voto (obrigatório, true ou false)
* Exemplo: `PUT /votos/1 { "pautaId": 1, "associadoId": 1, "voto": false }`

### **DELETE /votos/{votoId)**

* Exclui um voto existente.
* Parâmetros:
	+ `votoId`: ID do voto (obrigatório)
* Exemplo: `DELETE /votos/1`

### **GET /votos/resultados/{pautaId}**

* Pega os resultados .
* Parâmetros:
	+ `pautaId`: ID da pauta (obrigatório)
  + `page`: número da página (opcional, padrão: 0)
	+ `size`: tamanho da página (opcional, padrão: 10)
* Exemplo: `GET /votos/resultados/5`  

### **GET /associados**

* Retorna uma lista de associados com paginação.
* Parâmetros:
	+ `page`: número da página (opcional, padrão: 0)
	+ `size`: tamanho da página (opcional, padrão: 10)
* Exemplo: `GET /associados?page=1&size=20`

### **POST /associados**

* Cria um novo associado.
* Corpo da requisição:
	+ `cpf`: CPF do associado (obrigatório)
* Exemplo: `POST /associados { "cpf": "000.000.000-00" }'

### **GET /associados/{associadoId)**

* Retorna um associado específico.
* Parâmetros:
	+ `associadoId`: ID do associado (obrigatório)
* Exemplo: `GET /associados/1`

### **PUT /associados/{associadoId)**

* Atualiza um associado existente.
* Corpo da requisição:
	+ `nome`: nome do associado (obrigatório)
	+ `email`: email do associado (obrigatório)
* Exemplo: `PUT /associados/1 { "cpf": "000.000.000-00" }`

### **DELETE /associados/{associadoId)**

* Exclui um associado existente.
* Parâmetros:
	+ `associadoId`: ID do associado (obrigatório)
* Exemplo: `DELETE /associados/1`
