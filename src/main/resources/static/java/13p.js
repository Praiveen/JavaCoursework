document.addEventListener('DOMContentLoaded', function() {
  var imgContainers = document.querySelectorAll('.img-container');

  imgContainers.forEach(function(container) {
    var decrementButton = container.querySelector('[id^="decrement-"]');
    var incrementButton = container.querySelector('[id^="increment-"]');
    var quantityInput = container.querySelector('input[type="number"]');

    if (decrementButton && incrementButton && quantityInput) {
      var componentId = decrementButton.id.replace('decrement-', '');

      decrementButton.addEventListener('click', function() {
        decrementQuantity(componentId, quantityInput);
      });

      incrementButton.addEventListener('click', function() {
        incrementQuantity(componentId, quantityInput);
      });
    }
  });
});

function incrementQuantity(componentId, inputField) {
  var currentValue = parseInt(inputField.value);
  var maxValue = parseInt(inputField.max);

  if (currentValue < maxValue) {
    inputField.value = currentValue + 1;
  }
}

function decrementQuantity(componentId, inputField) {
  var currentValue = parseInt(inputField.value);
  var minValue = parseInt(inputField.min);

  if (currentValue > minValue) {
    inputField.value = currentValue - 1;
  }
}

document.addEventListener("DOMContentLoaded", function() {
    const scrollPosition = sessionStorage.getItem('scrollPosition');
    if (scrollPosition) {
        window.scrollTo(0, parseInt(scrollPosition));
    }

    window.addEventListener('beforeunload', function() {
        sessionStorage.setItem('scrollPosition', window.scrollY);
    });
});
