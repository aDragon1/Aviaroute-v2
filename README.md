<!-- Table of Contents -->

# :notebook_with_decorative_cover: Содержание

- [О проекте](#star2-about-the-project)
    * [Скриншоты](#camera-screenshots)
    * [Технологический стек](#space_invader-tech-stack)
    * [Фичи](#dart-features)
- [Лицензия](#warning-license)
- [Значимые ссылки](#gem-acknowledgements)

<!-- About the Project -->

# Aviaroute

## :star2: О Проекте

Aviaroute-v2 — Android-приложение разработанное в рамках курсовой работы по предмету Базы данных и в связке с [web-сервером](https://github.com/adragon1/aviaroute-web) является идейным продолжением [Aviaroute](http://github.com/adragon1/aviaroute), развивая его идеи.

Главное отличие - сложность системы базы данных. Связка этого приложения с сервером и базой данных является сложной полновестной системой о управлениях авиарейсами.

Данное приложение предназначенно для поиска авиабилетов. Это позволяет пользователям просматривать доступные
маршруты полетов, включая такую информация, как аэропорты отправления и назначения, цены на билеты и время полета.

Так же, данное приложение позволяет получить выходные отчеты, генерируемые базой данных.


<!-- Screenshots -->

## :camera: Скриншоты

|                                             |                                          |
| ------------------------------------------- | ---------------------------------------- |
| ![](screenshots/1.jpg)                      | ![](screenshots/2.jpg)                   |
| Экран ввода поисковых параметров            | Экран выбора аэропорта                   |
| ![](screenshots/4.jpg)                      | ![](screenshots/3.jpg)                   |
| Экран выбора даты                           | Экран с заполненными параметрами запроса |
| ![](screenshots/5.jpg)                      | ![](screenshots/6.jpg)                   |
| Результаты поскового запроса                | Экран получения выходных отчетов         |
| ![](screenshots/7.jpg)                      | ![](screenshots/8.jpg)                   |
| Экран выбора периода                        | Экран с заполненым периодов              |
| ![](screenshots/9.jpg)                      | ![](screenshots/10.jpg)                  |
| PDF-файл c всеми рейсами на заданный период | Справка о ВС в системе                   |
---------------------------------------------------------------------------------------------

<!-- Features -->

### :dart: Фичи

- **Flight Search:** Ищите доступные маршруты, выбирая между аэропортом отправления, назначения и датой вылета
- **Flight Information:** Смотрите полную информацию о рейсе
- **Route Details:** Ищите рейсы, сортируйте поисковые результаты по цене, дате отправления, а так же кастомизируйте
  поисковую выдачу, двигая слайдеры на цену и время в пути

<!-- TechStack -->

### :space_invader: Технологический стак
**[Android SDK](https://developer.android.com/tools/releases/platform-tools)**: Предоставляет необходимые инструменты и API для создания Android-приложений  
**[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)**: Компонент архитектуры Jetpack, предназначенный для хранения и управления данными пользовательского интерфейса с учетом жизненного цикла приложения
**[LiveData](https://developer.android.com/topic/libraries/architecture/livedata)**: Компонент Jetpack, обеспечивающий реактивное программирование с учетом жизненного цикла приложения, используемый для работы с данными, которые могут изменяться
**[Kotlin](https://kotlinlang.org/)**: Основной язык программирования для создания Android-приложений  
**[Flow](https://kotlinlang.org/docs/flow.html)**: Асинхронный поток данных, встроенный в Kotlin, для управления последовательностью событий

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/aDragon1/Aviaroute-v2.git
   ```
2. Have fun with it

## :warning: License

Поставляется без лицензии. Смотри LICENSE для дополнительной информации

<!-- Acknowledgments -->

## :gem: Значимые ссылки

- [Readme Template](https://github.com/Louis3797/awesome-readme-template)