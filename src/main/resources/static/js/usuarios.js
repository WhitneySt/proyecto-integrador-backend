// Selecciona todos los checkboxes dentro del contenedor
const checkboxes = document.querySelectorAll('input[type="checkbox"]');

// Agrega un event listener a cada checkbox
checkboxes.forEach((checkbox) => {
  checkbox.addEventListener('click', () => {
    // Cuando un checkbox es seleccionado, deselecciona los otros
    checkboxes.forEach((otherCheckbox) => {
      if (otherCheckbox !== checkbox) {
        otherCheckbox.checked = false;
      }
    });
  });
});