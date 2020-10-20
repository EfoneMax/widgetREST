# widgetREST
[![CircleCI](https://circleci.com/gh/EfoneMax/widgetREST.svg?style=svg)](https://app.circleci.com/pipelines/github/EfoneMax/widgetREST)

Особенности реализации:
Считается, что у виджетов X и Y - координаты левого нижнего угла.
Для фильтрации по области создано ограниченное поле 1000х1000х1000. 
Отрицательные координаты виджетов обрабатываются корректно.

requestBody
[
        {
            "x": "0",
            "y": "0"
        },
         {
            "x": "10",
            "y": "10"
        }
]
-для фильтрации по области

{
    "width": 3,
    "height": 3,
    "zindex": 1,
    "xindex": 3,
    "yindex": 3
}
- для put и post
