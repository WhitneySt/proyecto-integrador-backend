function redirigirDetalleUsuario() {

    let checkboxSeleccionado = checkboxSelect(); // obtiene el checkbox seleccionado

    //Si encuentra un checkbox seleccionado, es decir es diferente a null,
    if (checkboxSeleccionado) {
        const idUsuario = checkboxSeleccionado.value; // obtiene el valor del checkbox que se asume como el ID del usuario
        window.location.href = "getInformationUser/verUsuario/" + idUsuario; // redirige a la URL "detalleUsuario/{id}"
        }

}

function redirigirEditarUsuario() {

    let checkboxSeleccionado = checkboxSelect(); // obtiene el checkbox seleccionado

    //Si encuentra un checkbox seleccionado, es decir es diferente a null,
    if (checkboxSeleccionado) {
        const idUsuario = checkboxSeleccionado.value; // obtiene el valor del checkbox que se asume como el ID del usuario
        window.location.href = "getInformationUser/editarUsuario/" + idUsuario; // redirige a la URL "editarUsuario/{id}"
        }

}

function redirigirCrearUsuario() {
    window.location.href = "/crearUsuario"; // redirige a la URL "crearUsuario"
    }


function checkboxSelect(){

    // Selecciona todos los checkboxes dentro del contenedor
    let checkboxes = document.querySelectorAll('input[type="checkbox"]'); // Selecciona todos los checkboxes dentro del contenedor
    let checkboxSeleccionado = null; // Variable para almacenar el checkbox seleccionado

    // Recorre todos los checkboxes y verifica cuál está marcado utilizando la propiedad  checked
    checkboxes.forEach(function(checkbox) {
        if (checkbox.checked) {
            checkboxSeleccionado = checkbox;
        }
    });

    if(checkboxSeleccionado == null){
         // muestra una alerta utilizando Swal.fire indicando que no hay un usuario seleccionado.
        Swal.fire({
              icon: 'warning',
              title: 'Alerta',
              text: 'No hay un usuario seleccionado',
              confirmButtonColor: '#3085d6',
              confirmButtonText: 'Aceptar'
        });
    }
    else{
        return checkboxSeleccionado;
    }
}