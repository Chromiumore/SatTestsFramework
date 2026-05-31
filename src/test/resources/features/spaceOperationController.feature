#language: ru

Функция: Space Operation Controller
  @first-in-operation
  Сценарий: [200] GET /space-operation/overview
    Когда Отправим GET запрос для метода "/space-operation/overview"
    Тогда Проверим отчет на содержание группировки
    И Проверим, что в ответе пришел статус-код = 200

  Сценарий: [200] POST /space-operation/missions
    Дано Запрос на миссию
    Когда Отправим POST запрос для метода "/space-operation/missions"
    Тогда Проверим, что в ответе пришел статус-код = 200

  @last-in-operation
  Сценарий: [200] DELETE /space-operation/constellations/{constellationName}/satellites/{satelliteName}
    Когда Отправим DELETE-запрос на удаление спутника из группировки
    Тогда Проверим, что в ответе пришел статус-код = 200