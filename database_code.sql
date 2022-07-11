-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Хост: std-mysql
-- Время создания: Июл 10 2022 г., 17:19
-- Версия сервера: 5.7.26-0ubuntu0.16.04.1
-- Версия PHP: 8.0.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `std_2021_teacher_quotes`
--

-- --------------------------------------------------------

--
-- Структура таблицы `teacher_quotes`
--

CREATE TABLE `teacher_quotes` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `quote` text NOT NULL,
  `last_name` text NOT NULL,
  `first_name` text NOT NULL,
  `second_name` text NOT NULL,
  `lesson` text NOT NULL,
  `publication_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `teacher_quotes`
--

INSERT INTO `teacher_quotes` (`id`, `user_id`, `quote`, `last_name`, `first_name`, `second_name`, `lesson`, `publication_date`) VALUES
(16, 19, '\"Для того, чтобы что-то сделать, надо что-то сделать\".', 'Иванов', 'Иван', 'Иванович', 'Математика', '2022-07-01'),
(17, 20, '\"Инженеру вообще кроме скоростей ничего не нужно\".', 'Петров', 'Петр', 'Петрович', 'Физика', '2022-07-07'),
(18, 17, '\"Микробы тоже люди!\".', 'Гришина', 'Юлия', 'Олеговна', 'Биология', '2022-07-02'),
(19, 16, '\"Вот так не делайте, это буква урод. Просто буква-урод\".', 'Туманов', 'Кирилл', 'Максимович', 'Физика', '2022-07-12'),
(20, 22, '\"Пряников не осталось, только кнуты. Чего вы боитесь больше всего, признавайтесь. Буду давить на эти точки\".', 'Максимов', 'Олег', 'Михайлович', 'Русский язык', '2022-07-01'),
(21, 18, '\"Ладно, я уж обращусь, пардон, к Библии...\".', 'Павлова', 'Алина', 'Сергеевна', 'Английский язык', '2022-07-03'),
(22, 21, '\"Я тебя сейчас прибью и правильно сделаю\".', 'Иванов', 'Илья', 'Иванович', 'Информатика', '2022-07-02'),
(23, 32, '\"Клетки надо зарисовывать в альбом, а не в тетрадь. И на листы, а не в скетчбуки!\".', 'Степанова', 'Надежда', 'Григорьевна', 'История', '2022-07-02'),
(25, 31, '\"Только ленивый сейчас грибы не пинает\".', 'Степанов', 'Сергей', 'Александрович', 'История', '2022-06-17'),
(26, 34, '\"Ваше лучшее время уже прошло.\".', 'Жукова', 'Наталья', 'Викторовна', 'Механика', '2022-06-08');

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` int(11) NOT NULL,
  `study_group` text NOT NULL,
  `rank` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id`, `login`, `password`, `study_group`, `rank`) VALUES
(7, 'admin', 123, 'all groups', 'admin'),
(15, 'Eugene', 123, '211-363', 'user'),
(16, 'Semen', 123, '211-363', 'headman'),
(17, 'Liza', 123, '211-363', 'user'),
(18, 'Petya', 123, '211-363', 'user'),
(19, 'Arslan', 123, '211-363', 'user'),
(20, 'Daniel', 123, '211-362', 'user'),
(21, 'Oleg', 123, '211-362', 'user'),
(22, 'Polina', 123, '211-362', 'user'),
(25, 'Ivan', 123, '211-362', 'headman'),
(26, 'Andrey', 123, '211-363', 'user'),
(30, 'Nikita', 123, '211-362', 'user'),
(31, 'Gleb', 123, '211-362', 'user'),
(32, 'Ira', 123, '211-362', 'user'),
(33, 'Stepa', 123, '211-362', 'user'),
(34, 'Katya', 123, '211-362', 'user'),
(35, 'Vika', 123, '211-363', 'user');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `teacher_quotes`
--
ALTER TABLE `teacher_quotes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`user_id`);

--
-- Индексы таблицы `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `login` (`login`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `teacher_quotes`
--
ALTER TABLE `teacher_quotes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT для таблицы `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `teacher_quotes`
--
ALTER TABLE `teacher_quotes`
  ADD CONSTRAINT `teacher_quotes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
