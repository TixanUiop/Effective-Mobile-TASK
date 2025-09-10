# Effective-Mobile-TASK
Простое Spring Boot приложение с PostgreSQL, запускаемое через Docker.
## Требования
- Docker
- Docker Compose
## Запуск
1. Клонируем репозиторий:
```bash
git clone <URL_репозитория>
cd <имя_папки_проекта>
Запускаем всё через Docker Compose:
bash
docker-compose up --build
Флаг --build нужен при первом запуске или после изменения Dockerfile.
После успешного запуска:
Приложение доступно на http://localhost:8080/swagger-ui/index.html
База PostgreSQL доступна на localhost:5432
Пользователь: username
Пароль: password
Остановка
bash
docker-compose down
Если нужно удалить данные PostgreSQL, используйте:
bash
docker-compose down -v
Настройка Liquibase
Файлы миграций находятся в src/main/resources/db/changelog.

Чтобы включить Liquibase, выставьте в application.yaml:
yaml
spring:
  liquibase:
    enabled: true
