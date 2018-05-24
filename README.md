# Апи для публикатора

<details><summary>Аутентификация</summary>
<p>

Аутентификация выполняется через oauth: Resource Owner Password http://oauthbible.com/#oauth-2-two-legged

```sh
curl -X POST "https://${client_id}:${client_secret}@oauth.evotor.ru/oauth/token?username=${publisher_email}&password=${publisher_password}&grant_type=password"
```

Пример ответа:
```json
{
"access_token":"eyJhbGci...",
"token_type":"bearer",
"refresh_token":"eyJhbGci...",
"expires_in":1799,
"scope": "push-notification:write event:read app:read"
}
```
Также допустимо использование персональных токенов разработчика, которые являются частным случаем oauth.

### Замечания
1) Срок жизни токена может быть изменен на стороне сервера

</p>
</details>

<details><summary>Events Api</summary>
<p>

Все прикладные события, помимо уведомления через _webhook'и_, доступны через _rest апи_.
Более того, использование _Event streaming_ позволяет отказаться от _webhook'ов_.

### Получение за заданный интервал времени
```sh
 curl -X GET  -H 'Authorization: bearer {OAUTH_TOKEN}' -H 'Accept: application/vnd.evotor.v2+json' https://dev.evotor.ru/apps/{application_id}/events?since={timestamp}&until={until}&types={optional_event_types}'
 ```
Поле _since_ не является обязательным. Дата, с которой получить последующие события - UNIX timestamp.
Полу _until_ не является обязательным. По-умолчанию равна _now()_
Поле _types_ не является обязательным. Фильтр для типов забытий - конкатенация названий типов через запятую. По-умолчанию возвращаются все события заданного приложения.

Запрос возвращает первую страницу.
Пример ответа:
```json
{
"items": [
{
            "id": "d8f8ad3e-4d3a-4064-9fc0-a81f49098f02", 
            "payload": {
            "_comment": "<payload body>"
            }, 
            "timestamp": "2018-03-26T16:59:11.048+0000", 
            "type": "document",
            "action": "created"
        },
],
"paging": {
  "next_cursor": "<cursor string value>"
}
}
```

Для получения последующих страниц требуется указать значение курсора:
```sh
 curl -X GET  -H 'Authorization: bearer {OAUTH_TOKEN}' https://dev.evotor.ru/apps/{application_id}/events?cursor={cursor string value}'
```
Пустой объект _paging_ означает достижение последней страницы

### Event streaming 
```sh
 curl -X GET  -H 'Authorization: bearer {OAUTH_TOKEN}' -H 'Accept: application/stream+json' https://dev.evotor.ru/apps/{application_id}/events?since={timestamp}'
 ```
 При указании _Accept_Type_ равным _application/stream+json_ будет использоваться https://en.wikipedia.org/wiki/JSON_streaming с разделителем '\n'. 
 Содержимое каждого отдельного события не отличается от описанных выше.
 С целью поддержания постоянного соединения, каждые 30 секунд сервер отправляет техническое событие вида:
```json
{
  "id":"139ab11a-d917-4ad9-b36e-5049f7393aad",
  "type": "system",
  "action": "ping"
}
```
 
## Поддерживаемые типы
* document
* store
* employee
* device

</p>
</details>

<details><summary>Push notifications</summary>
<p>

Возможность отправки прикладных пушей на устройства, где установлено приложение разработчика.

```sh
curl -X POST 'https://dev.evotor.ru/apps/{application_id}/push-notifications' -d '{"devices":["device-guid-1", "device-guid-2"],"payload":{"magic_field":123456,"magicString":"STR"}, "active_till": {timestamp}}' -H 'Content-Type:application/json' -H "Authorization: bearer {OAUTH_TOKEN}"
```
Обязательное поле _devices_ является списком идентификаторов устройств, которым будет отправлен пуш.
Опциональное поле _active_till_ указывает дату, до которой пуш считается активным. Если при достижении указанной даты, пуш не будет принят устройством, то пуш становится неактивным. По-умолчанию равно _now() + 2 days_
Обязательное поле _payload_ - сообщение, которое будет передано устройствам.

Пример ответа:
```json
{
  "id": "<task identifier>",
  "status": "task status: [ACCEPTED, RUNNING, FAILED, COMPLETED]", 
  "details": [
    {
      "device_id": "device-guid-1",
      "status": "delivery status: [ACCEPTED, DELIVERED, EXPIRED, REJECTED]"
    },
    {
      "device_id": "device-guid-2",
      "status": "delivery status: [ACCEPTED, DELIVERED, EXPIRED, REJECTED]"
    }
  ]
}
```
Допустимые статусы:
* ACCEPTED - принято
* DELIVERED - доставлено
* EXPIRED - достигнуто _active_till_
* REJECTED - отклонена сервером
* UNKNOWN - (по-умолчанию) неизвестная ошибка

Для получения актуального статуса требуется выполнить запрос:
```sh
curl -X GET 'https://dev.evotor.ru/apps/{application_id}/push-notifications/{task identifier}' -H 'Accept: application/vnd.evotor.v2+json' -H "Authorization: bearer {OAUTH_TOKEN}"
```
</p>
</details>
