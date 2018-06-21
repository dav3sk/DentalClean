# DentalClean

## Para alterar codigo:
 1. git pull origin master (atualizar repositório local)
 2. git checkout -b issue#xxx , onde xxx é a proxima issue na lista de issues
 2. Efetuar as modificações do código fonte.
 3. Executar: git add [ARQUIVO INDIVIDUAL ou LISTA DE ARQUIVOS].
 4. Executar: git commit -m "MENSAGEM EXPLICATIVA" após cada 'git add [ARQUIVO INDIVIDUAL ou LISTA DE ARQUIVOS]'
 5. git push origin issue#xxx
 6. git checkout master
 7. git pull origin master
 
 
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
 10. git pull origin master
