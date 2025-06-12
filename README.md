API desenvolvida para fazer o registro de ocorrências que fazendas solicitam suporte.

- funções -
Usuario CRUD
- A ideia foi fazer uma classe usuário para cadastrar usuarios que não necessitam de login.
- A role fiz com essa logica, por que estava treinando umas herança entre classes no spring usando o One-To-One.
- 
Login CRUD + Spring Security/JTW
- O login foi feito para herdar um usuario que necessita de fazer login na plataforma.
- Ta gerando um token com as informações que achei necessario para utilizar no CRUD e para segurança.
- 
Fazenda CRUD (modificável)
- Essa classe seria o cadastro da entidade que solicitaria o suporte para a empresa, por exemplo, a fazenda X solicitou suporte através do usuário Y e um usuario com login, geralmente um suporte iria fazer o registro dessa ocorrência.
