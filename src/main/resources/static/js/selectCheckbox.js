/*Este código selecciona todos los elementos de tipo checkbox que se encuentran dentro del contenedor.
Luego, agrega un event listener a cada checkbox para que cuando se haga clic en uno de ellos,
se deseleccionen los demás checkboxes.
Esto se logra recorriendo todos los checkboxes y verificando si son diferentes al checkbox actual.
 Si son diferentes, se establece su propiedad checked en falso para deseleccionarlos.
*/

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