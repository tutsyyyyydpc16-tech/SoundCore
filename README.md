# SoundCore

Player de música local, feito em Java (Swing + JavaFX), com biblioteca de músicas, playlist persistente, e interface pixel art.

## Funcionalidades

- Play/pause, avançar/retroceder música
- Barra de progresso com tempo atual/restante
- Controle de volume
- Capa do álbum e informações da música tocando
- Biblioteca de músicas com busca
- Playlist separada da biblioteca
- Repetir música e modo aleatório
- Tema visual pixel art / synthwave

## Instalação

1. Baixa o arquivo `SoundCore-1.0.exe` na aba [Releases](../../releases) deste repositório
2. Executa o instalador (duplo clique)
3. Segue o instalador (Next → Install)

### Se o Windows mostrar um aviso de segurança na instalação

Isso é normal para programas sem certificado de desenvolvedor pago. Clica em **"Mais informações"** → **"Executar assim mesmo"**.

Se aparecer o **"Auxiliar de compatibilidade de programas"** perguntando se o programa instalou certo, escolhe **"O programa foi instalado corretamente"**.

## Se o programa não abrir / ficar pedindo permissão toda vez

Se ao clicar no atalho (área de trabalho ou menu Iniciar) o Windows ficar pedindo permissão de administrador toda vez ("Deseja permitir que este aplicativo faça alterações no dispositivo?"), o atalho provavelmente está apontando para o instalador, não para o programa em si. Resolve assim:

1. Abre o Explorador de Arquivos
2. Navega até `C:\Program Files\SoundCore`
3. Confirma que existe o arquivo `SoundCore.exe` ali dentro
4. Clica com o botão direito nele → `Enviar para` → `Área de trabalho (criar atalho)`
5. Use esse novo atalho para abrir o programa

Isso cria um atalho direto para o executável, sem passar por instalador/desinstalador, e evita o pedido de permissão repetido.

## Como buildar a partir do código-fonte

Pré-requisitos:
- JDK 26 (ou compatível)
- JavaFX SDK
- JavaFX jmods (para gerar o instalador)
- Biblioteca jaudiotagger 3.0.1

1. Compila o projeto normalmente pela IDE
2. Empacota num `.jar` executável
3. Usa `jpackage` para gerar o instalador `.exe`

## Tecnologias

- Java Swing (interface)
- JavaFX (reprodução de áudio)
- jaudiotagger (leitura de metadados MP3)
