# Dashboard
Простая панель с прогнозом погоды и курсом валюты
Приложение Dashboard 
==============
Простое приложение, для отображения погоды, курса валют со счетчиком посещений страницы.


Требования
========
Для корректной работы приложения необходим доступ к MongoDB.
Параметры доступа к БД прописаны в конфигурационном файле dashboard.config.

Установка и запуск приложения
========
Сборка и запуск приложения.
1. поместите исходники в /opt/dashboard;
2. скомпилируйте приложение командой "mvn install";
3. скопируйте файл конфигурации ./src/main/resources/dashboard.config 
   в /opt/dashboard, измените параметры базы данных
4. для запуска приложения выполните команду "mvn jetty:run -Djetty.http.port=88" 
   и откройте страницу http://localhost:88
5. логи работы приложения в файле /opt/dashboard/dashboard.log

Режим отладки
========
Для получения большей информации о работе приложения необходимо отредактировать  файл
 ./src/main/resources/log4j.properties, заменив INFO на DEBUG.
 
Поддержка
========
Если у вас возникли вопросы по использованию приложения 
напишите на электронную почту <tot@ngs.ru>

