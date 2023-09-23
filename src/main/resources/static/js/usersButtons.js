function redirigirDetalleUsuario() {

    let checkboxes = document.querySelectorAll('input[type="checkbox"]'); // Selecciona todos los checkboxes dentro del contenedor
    let checkboxSeleccionado = null; // Variable para almacenar el checkbox seleccionado

    // Recorre todos los checkboxes y verifica cuál está marcado utilizando la propiedad  checked
    checkboxes.forEach(function(checkbox) {
    if (checkbox.checked) {
      checkboxSeleccionado = checkbox;
    }
    });

    //Si encuentra un checkbox seleccionado,
    if (checkboxSeleccionado) {
        const idUsuario = checkboxSeleccionado.value; // obtiene el valor del checkbox que se asume como el ID del usuario
        window.location.href = "detalleUsuario/" + idUsuario; // redirige a la URL "detalleUsuario/{id}"
        }
    // Si no hay checkbox seleccionado
    else {
        // muestra una alerta utilizando Swal.fire indicando que no hay un usuario seleccionado.
        Swal.fire({
              icon: 'warning',
              title: 'Alerta',
              text: 'No hay un usuario seleccionado',
              confirmButtonColor: '#3085d6',
              confirmButtonText: 'Aceptar'
            });
    }
}

function redirigirEditarUsuario() {
}

function redirigirEliminarUsuario() {}