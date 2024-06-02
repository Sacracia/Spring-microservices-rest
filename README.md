# Spring-microservices-rest
## Микросервис авторизации пользователей
http://localhost:9000/auth

### Сущности
- AppUser ( пользователь )
```
{
    id: Integer,
    nickname: String,
    email: String,
    password: String,
    created: Date
}
```

- Session ( сессия авторизации )
```
{
    id: Integer,
    userId: Integer,
    token: String,
    expires: Timestamp
}
```

### Эндпоинты
---
- ***/users***

    **Метод:** GET

    **Тело:** Нет

    **Возврат:** List\<AppUser>

    Получение всех пользователей в БД

    **Статусы:**
    - Успешное завершение операции (200)
---
- ***/register***

    **Метод:** POST

    **Тело:**
    ```
    {
        nickname: String,
        email: String,
        password: String
    }
    ```
    Пример:
    ```yaml
    {
        "nickname": "Anna",
        "email": "anna@gmail.com",
        "password": "#somepASSword123"
    }

    ```
> [!WARNING]  
> Почта и никнейм не должны быть зарегистрированы ранее

    Ограничения на формат почты:

    - It allows numeric values from 0 to 9.
    - Both uppercase and lowercase letters from a to z are allowed.
    - Allowed are underscore “_”, hyphen “-“, and dot “.”
    - Dot isn’t allowed at the start and end of the local part.
    - Consecutive dots aren’t allowed.
    - For the local part, a maximum of 64 characters are allowed.

    Ограничения на домен почты:

    - It allows numeric values from 0 to 9.
    - We allow both uppercase and lowercase letters from a to z.
    - Hyphen “-” and dot “.” aren’t allowed at the start and end of the domain part.
    - No consecutive dots.

    Требования к паролю:
    - Have eight characters or more
    - Include a capital letter
    - Use at least one lowercase letter
    - Consists of at least one digit
    - Need to have one special symbol (i.e., @, #, $, %, etc.)
    - Doesn’t contain space, tab, etc.

    **Возврат:** String ( сообщение об успехе / текст ошибки )

    Регистрация нового пользователя

    **Статусы:**
    - Успешное завершение операции (200)
    - Пустые входные данные (400)
    - Некорректный формат почты / пароля (400)
    - Почта или никнейм уже существуют (400)
---
- ***/login***

    **Метод:** POST

    **Тело:**
    ```
    {
        email: String,
        password: String
    }
    ```
    Пример:
    ```yaml
    {
        "email": "anna@gmail.com",
        "password": "#somepASSword123"
    }
    ```

    **Возврат:** String ( токен сессии )

    Получение информации о пользователе по токену авторизации

    **Статусы:**
    - Успешное завершение операции (200)
    - Нет пользователя с такой почтой (400)
    - Неверный пароль (400)
    
---
- ***/token/info***

    **Метод:** POST

    **Тело:**
    ```
    {
        token: String
    }
    ```
    Пример:
    ```yaml
    {
        "token": "sometoken"
    }
    ```

    **Возврат:** 
    ```
    UserInfoResponse
    {
        id: Integer,
        nickname: String,
        email: String,
        created: Date
    }
    ```

    Получение информации о пользователе по токену авторизации

    **Статусы:**
    - Успешное завершение операции (200)
    - Невалидный / истекший токен (400)
    - Не найден пользователь по токену (400)
---
- ***/token/valid***

    **Метод:** POST

    **Тело:**
    ```
    {
        token: String
    }
    ```
    Пример:
    ```yaml
    {
        "token": "sometoken"
    }
    ```

    **Возврат:** Boolean ( валидный ли токен )

    Проверка токена на валидность

    **Статусы:**
    - Успешное завершение операции (200)
---

## Микросервис заказов на покупку билетов
http://localhost:9001/order

### Сущности
```
Order ( заказ )
{
    id: Integer,
    user_id: Integer,
    from_station_id: Integer,
    pto_station_id: Integer,
    status: Integer (default = 1),
    created: Date (default = current time)
}
```

```
Session ( сессия авторизации )
{
    id: Integer,
    userId: Integer,
    token: String,
    expires: Timestamp
}
```

### Эндпоинты
---
- ***/create***

    **Метод:** POST

    **Тело:**
    ```
    {
        token: String,
        from: String,
        to: String
    }
    ```
    Пример:
    ```yaml
    {
        "token" : "AuthorizationSessionToken",
        "from": "Moscow",
        "to": "Tokio"
    }
    ```

    **Возврат:** String ( сообщение об успехе / текст ошибки )

    Создание заказа на покупку билета

    **Статусы:**
    - Успешное завершение операции (200)
    - Место отправления совпадает с местом назначения (400)
    - Невалидный токен (400)
    - Некорректная точка отправления / назначения (400)
    - Не удается определить пользователя по токену (400)
    - Не удается установить соединение с сервисом авторизации (500)
---
- ***/info/{order_id}***

    **Метод:** POST

    **Тело:** Нет

    **Возврат:** Order ( сущность заказа )

    Получение информации о заказе по id

    **Статусы:**
    - Успешное завершение операции (200)
    - Не удается определить заказ по id (400)
---
