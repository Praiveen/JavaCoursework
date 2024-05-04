document.addEventListener("DOMContentLoaded", function() {
    // Получаем все кнопки "плюс" и "минус" по их id, добавленным к названию компонента
    const incrementButtons = document.querySelectorAll('[id^="increment-"]');
    const decrementButtons = document.querySelectorAll('[id^="decrement-"]');

    // Для каждой кнопки "плюс" добавляем обработчик события клика
    incrementButtons.forEach(button => {
        button.addEventListener("click", function() {
            // Получаем id компонента из id кнопки
            const componentId = button.id.split("-")[1];
            // Находим соответствующее поле ввода по id компонента
            const inputField = document.getElementById(`quantity${componentId}`);
            // Получаем текущее значение поля ввода
            let value = parseInt(inputField.value);
            // Увеличиваем значение на 1, если не превышено максимальное значение
            if (value < parseInt(inputField.max)) {
                value++;
                inputField.value = value;
            }
        });
    });

    // Для каждой кнопки "минус" добавляем обработчик события клика
    decrementButtons.forEach(button => {
        button.addEventListener("click", function() {
            // Получаем id компонента из id кнопки
            const componentId = button.id.split("-")[1];
            // Находим соответствующее поле ввода по id компонента
            const inputField = document.getElementById(`quantity${componentId}`);
            // Получаем текущее значение поля ввода
            let value = parseInt(inputField.value);
            // Уменьшаем значение на 1, если не превышено минимальное значение
            if (value > parseInt(inputField.min)) {
                value--;
                inputField.value = value;
            }
        });
    });
});
