#language: ru

@ConstellationFeature
Функция: Constellation Controller
  @first-in-constellation
  Сценарий: [200] GET /constellations
    Когда Отправим GET запрос для метода "/constellations"
    Тогда Проверим, что в ответе пришел статус-код = 200

  @constellation-create
  Сценарий: [201] POST /constellations
    Дано Группировка спутников с name="Test-Const"
    Когда Отправим POST запрос для метода "/constellations"
    Тогда Проверим, что в ответе пришел статус-код = 201
    И Проверим данные группировки в ответе
  
  Сценарий: [200] GET /constellations/{id}
    Когда Отправим GET запрос с id последнего ответа для метода "/constellations"
    Тогда Проверим, что в ответе пришел статус-код = 200
    И Проверим данные группировки в ответе

  Сценарий: [200] GET /constellations/name/{name}
    Когда Отправим GET запрос с именем группировки для метода "/constellations"
    Тогда Проверим, что в ответе пришел статус-код = 200
    И Проверим данные группировки в ответе

  Сценарий: [200] POST /constellations/{id}
    Когда Добавим спутник в группировку для метода "/constellations"
    Тогда Проверим, что в ответе пришел статус-код = 200
    И Проверим, что группировка содержит спутник

  @last-in-constellation
  Сценарий: [204] DELETE /constellations/{id}
    Когда Отправим DELETE запрос с id последнего ответа для метода "/constellations"
    Тогда Проверим, что в ответе пришел статус-код = 204
