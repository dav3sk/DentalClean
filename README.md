# DentalClin

## Para alterar codigo:
 1. git pull origin master (atualizar repositório local)
 2. Efetuar as modificações do código fonte.
 3. Executar: git add [ARQUIVO INDIVIDUAL ou LISTA DE ARQUIVOS].
 4. Executar: git commit -m "MENSAGEM EXPLICATIVA" após cada 'git add [ARQUIVO INDIVIDUAL ou LISTA DE ARQUIVOS]'
 5. git push origin master
 
 
## Alteração simultanea de arquivos:
 1. git pull origin master (atualizar repositório local)
 2. git checkout -b issue#xxx , onde xxx é a proxima issue na lista de issues
 3. Efetuar as modificações do código fonte.
 4. Executar: git add [ARQUIVO INDIVIDUAL ou LISTA DE ARQUIVOS].
 5. Executar: git commit -m "MENSAGEM EXPLICATIVA" após cada 'git add [ARQUIVO INDIVIDUAL ou LISTA DE ARQUIVOS]'
 6. git push origin issue#xxx
 7. Ir na página do repositório no GitHub na parte de branches e criar a PR (Pull Request).
 8. Na página da Pull Request no GitHub: realizar o merge.
 9. git checkout master
 10. Executar: git pull origin master


## Iniciar novo projeto
 1. Criar Dynamic Web Project no Eclipse
 2. Abrir Git Bash na area de trabalho e executar: git clone https://github.com/dav3sk/DentalClin.git
 3. Abrir pasta DentalClin e copiar a pasta oculta .git
 4. Colar .git dentro da pasta do Dynamic Web Project criado no passo 1
 5. Abrir Git Bash dentro da pasta do projeto e executar: git pull origin master
