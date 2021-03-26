# work-date [![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21) [![Build Status](https://travis-ci.com/NewtonCesarRoncari/work-date.svg?branch=develop)](https://travis-ci.com/NewtonCesarRoncari/work-date) [![codebeat badge](https://codebeat.co/badges/6616053d-28fb-45e4-8ae7-3fef895cebc2)](https://codebeat.co/projects/github-com-newtoncesarroncari-work-date-develop)

Este projeto consiste na criação de uma ferramenta para agendar um serviço a ser prestado e consequentemente gerar lançamentos financeiros

## Introdução

<p>A ideia principal se concentra em entregar para o usuário uma ferramenta para que o mesmo possa fazer um planejamento de forma agendada
sobre tarefas ou trabalhos que devem gerar lançamentos financeiros, assim facilitando o controle financeiro de um usuário que trabalhe de modo
agendado e receba pelo serviço realizado, como trabalhos free lancers ou agendamentos autônomos</p>

### Layouts
Splash             |  Agendamentos    | Lançamentos 
:----------------------------:|:----------------------------:|:----------------------------:
<img src="https://github.com/NewtonCesarRoncari/work-date/blob/develop/img/Screenshot_2021-03-25-21-53-16-221_com.newton.workdate.jpg"/> | <img src="https://github.com/NewtonCesarRoncari/work-date/blob/develop/img/Screenshot_20200908_214236.jpg"/> | <img src="https://github.com/NewtonCesarRoncari/work-date/blob/develop/img/Screenshot_2021-03-25-21-57-46-875_com.newton.workdate.jpg"/>

### Ações

Ao entrar na aplicação o usuário pode cadastrar seus clientes e igualmente, cadastrar os serviços prestados pelo próprio usuário, cadastrando seus serviços 
e incluindo os preços, é possível realizar um agendamento, informando o cliente para o qual vai ser realizado o trabalho e informando qual é o serviço prestado
o aplicativo irá gerar um lançamento financeiro 'Em Aberto' assim, após o trabalho realizado, basta marcar o agendamento como concluído, o lançamento
financeiro gerado será marcado como 'Pago', ao final do mês auxiliando o usuário a ter controle de seus trabahos realizados e consequentemente sua gestão financeira


Detalhes do agendamento     |  Lista de clientes |  Filtro financeiro
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/NewtonCesarRoncari/work-date/blob/master/img/Screenshot_20200908_214445.jpg"/> | <img src="https://github.com/NewtonCesarRoncari/work-date/blob/master/img/Screenshot_20200908_214302.jpg"/> | <img src="https://github.com/NewtonCesarRoncari/work-date/blob/master/img/Screenshot_20200908_214413.jpg"/>
- Caso se interesse é porssível fazer o <a href="https://github.com/NewtonCesarRoncari/work-date/raw/develop/apk/work-date.apk">download da apk<a/>

## Linguagem
O idioma alterna conforme a linguagem configurada no dispositivo do usuário.
O aplicativo atualmente se encontra nos idiomas: 
- Português
- Inglês 
- Francês

## Rodando a aplicação

Clone ou realize o download do projeto em formato Zip, más antes certifique se que contem os pré requisitos para as comunicações com o
banco de dados

### Pré requisitos

Para garantir o bom funcionamento da aplicação rode com: 
- Target JVM 1.8 
- Android Gradle Plugin Version 4.0.1 
- Gradle Version 6.1.1

### Instalando 

Após clonar o projeto, importe no seu Android Studio, aceitando as susjestões da Ide, os pré requisitos serão importados automaticamente,

- Rode a aplicação normalmente

## Tecnologias utilizadas

- <a href="https://developer.android.com/guide/topics/ui/look-and-feel?hl=pt-br">Material Design<a/> 
- <a href="https://developer.android.com/guide/navigation?gclid=Cj0KCQiAvJXxBRCeARIsAMSkAppbYUXuaVm-tnHPOV9rH5RlVVScLrsUnhHxK-tbmHkYdTBeCDqU6aoaAphrEALw_wcB">Android Navigation</a>
- <a href="https://github.com/airbnb/lottie-android">Lotties</a>
- <a href="https://developer.android.com/topic/libraries/architecture/livedata">Live Data with ViewModel<a/>
- <a href="https://insert-koin.io/">Koin dependency injection<a/>
- <a href="https://developer.android.com/topic/libraries/architecture/room">Room Persistence Library<a/>
- <a href="https://kotlinlang.org/docs/reference/coroutines-overview.html">Coroutines<a/>
- <a href="https://developer.android.com/reference/android/content/SharedPreferences">Shared Preferences<a/>
- <a href="https://developer.android.com/topic/libraries/data-binding">Data Biniding<a/>
