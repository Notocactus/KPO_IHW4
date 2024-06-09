Для запуска нужно в терминале переместиться в папку, содержащую сервисы и docker-compose.yml с помощью cd "{путь к папке проекта}"

Postman Workplace: https://www.postman.com/bold-astronaut-773079/workspace/kpo-ihw4

Инструкция к использованию/ реализованные функции:

port: 8080
* Регистрация нового пользователя: /user/register
* Вход пользователя в систему (авторизация): /user/login
* Предоставление информации о пользователе: /user/userinfo

port: 8081
* Создание заказа на покупку билета: /orders/add
* Обработка заказов на покупку билетов: реализован внутри класса OrderController
* Предоставление информации о заказе на покупку билета: /orders/get/{id} or /orders with userId in parameters or /orders (gives the full list of orders)

Для создания заказа необходимо сначала создать объекты станций с помощью /stations/add

