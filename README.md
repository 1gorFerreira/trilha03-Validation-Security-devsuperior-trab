**Desafio para entregar**

TAREFA: Validação e Segurança

Implemente as funcionalidades necessárias para que os testes do projeto abaixo passem:
https://github.com/devsuperior/bds04

Collection do Postman:
https://www.getpostman.com/collections/e1f59c905aeca84c1ebc

Este é um sistema de eventos e cidades com uma relação N-1 entre eles:
![image](https://user-images.githubusercontent.com/85773707/175787252-871d1187-a949-4676-99a2-e52eaff08dd7.png)

Neste sistema, somente as rotas de leitura (GET) de eventos e cidades são **públicas** (não precisa de login). Usuários **CLIENT** podem também inserir (POST) novos eventos. Os demais acessos são permitidos apenas a usuários **ADMIN**.

**Validações de City**:
- Nome não pode ser vazio

**Validações de Event**:
- Nome não pode ser vazio
- Data não pode ser passada
- Cidade não pode ser nula
