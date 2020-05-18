const express = require('express') // подключаем Express
const app = express() // создадим объект приложения
const path = require('path')
app.listen(3000,() => console.log("Сервер запущен, порт 3000"))// запуск сервера
app.use(express.static(path.resolve(__dirname, 'client')))
app.get('*', (req,res) => {
    res.sendFile(path.resolve(__dirname,'client','index.html'))
}) //использование пути по всем (*) get запросам (запрос, ответ)
