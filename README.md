# WaveAccessTest

## Задача ##
Создать приложение, отображающее список людей с возможностью просмотра информации о каждом конкретном человеке, включая список его друзей с возможностью перехода между ними. Полученные данные должны кешироваться при получении и НЕ перезапрашиваться при последующем запуске приложения. На экране списка должна быть обеспечена возможность перезагрузки закешированных данных.

## Решение ##
**Описание:**  
- Для перезагрузки закешированных данных необходимо потянуть вниз список кандидатов
(на экране появится иконка загрузки)
- Приложение поддерживает свтелую и темную темы
- Для возвращения назад можно использовать иконку navigate up
- Статус неактивного кандидата помечен красным цветом (активного - зеленым). У карточек неактивных кандидатов отключен модификатор clickable

**Использованный стек:** Kotlin, Hilt, Room, Jetpack Navigation, Jsoup, Retrofit, Kotlin Coroutines

## Tребования к решению ##

Адрес запроса:
https://firebasestorage.googleapis.com/v0/b/candidates--questionnaire.appspot.com/o/users.json?alt=media&token=e3672c23-b1a5-4ca7-bb77-b6580d75810c

Общие требования:
- язык разработки - kotlin
- версия Android - 7.0+
- требования к дизайну - соответствие гайдлайнам Material Design
- требования к архитектуре - MVP, MVVM или MVI; Clean Architecture
- желательно использовать DI (Dagger 2, Kodein, Koin, на усмотрение кандидата), Android Architecture Components, AndroidX

Требования к содержанию экранов:
1. Экран списка пользователей
- представлен в виде списка
- каждый элемент списка содержит текст name, текст email и отображение состояния isActive
- переход на детали пользователя доступен только для активных пользователей (isActive == true)

2. Экран деталей пользователя:
- содержит текстовые поля name, age, company, email, phone, address и about
- нажатие на поле email должно открывать внешний почтовый клиент и добавлять значение поля в качестве адресата письма
- нажатие на поле phone должно открывать приложение для звонков и вставлять номер в поле набора
- значение поля eyeColor должно быть представлено в виде точки соответствующего цвета. Возможные варианты: brown, green, blue
- значение поля favoriteFruit должно быть представлено в виде иконки, соответствующей одному из трех вариантов: apple, banana, strawberry
- значение поля registered должно быть отформатированно в строковый формат даты HH:mm dd.MM.yy
- значение полей latitude и longitude должно выводиться в одну строку по стандартному формату координат и, при нажатии, открывать внешнее приложение карт с отображением данной точки
- список friends должен выводиться в виде вертикального списка, аналогичного по наполнению и поведению списку, описанному в пункте 1. Экран списка пользователей
- переход по кнопке back должен обеспечивать переход по всей иерархии открытых экранов, а не вести на экран списка
