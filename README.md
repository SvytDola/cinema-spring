![](https://media.tenor.com/Vo44NSr2xn8AAAAd/chainsaw-man-anime.gif)

# Rest приложение подборки кино.
_⚠️ Данный проект ещё находится на стадии разработки, но в любом случае я буду рад любым вопросам и комментариям._


***Цель проекта:*** Реализовать полноценный сервис по поиску фильмов, возможность их оценивать и комментировать.

# Оглавление
* [Список задач](#tasks)
* [Конфигурация](#configuration) 
* [Сборка и запуск](#build) 


## Список задач: <a id=tasks>
- [ ] CRUD 💢
  - [ ] Рецензий критиков на кино.
  - [ ] Критики.
  - [x] Жанры кино.
  - [ ] Кино.
- [ ] Функции 🛠️
  - [ ] Получние кино по жанрам (учесть, что их может быть несколько).
  - [ ] Получение рецензий критиков по кино.
  - [ ] JWT авторизация.
  - [ ] I18N (Было бы неплохо добавить его поддержку в swagger).
  - [ ] Swagger.

![](https://i.pinimg.com/originals/b0/59/35/b05935c02fb5442005023af78ebdc67b.gif)

***NOTE:*** _Отдельная благодарность этому [человеку](https://github.com/DavidRezcov) за помощь._

## Конфигурация <a id=configuration>
Базовая настройка проекта включает в себя добавления файла `env.properties` в корень проекта.
```properties
# Конфигурация базы данных
POSTGRES_DATABASE_URL=jdbc:postgresql://localhost:5432/cinema
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres

TEST_POSTGRES_DATABASE_URL=jdbc:postgresql://localhost:5432/test-cinema

# Приватный ключ для jwt авторизации.
JWT_KEY=3677397A24432646294A404E635266556A586E5A7234753778214125442A472D
```

***NOTE:*** _Ключи `POSTGRES_DATABASE_URL` и `TEST_POSTGRES_DATABASE_URL` не обязательные, если вы будете запускать всё это в докере, они необходими для разработки и тестирования._

## Сборка и запуск проекта <a id=build>
![](https://i.pinimg.com/originals/16/4c/74/164c74b35af563f686c50815841ebc33.gif)

В начале необходимо образ докера.

```console
docker build -t cinema-server .
```

Затем поднять приложение.
```console
docker-compose up
```

***NOTE:*** _Да благославит вас господь, если оно запуститься. 😀_ 
