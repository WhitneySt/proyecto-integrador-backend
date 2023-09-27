 $(document).ready(function () {
    // selecciana el button submit y le agrega un evento click
    $('button[type="submit"]').click(function (event) {
        event.preventDefault();
        // se llama a la funcion validarFormulario y si retorna true se envia el formulario
        const isValidForm=validarFormulario();
        if(isValidForm){
            $('form').submit();
        }
        });
});
    // verifica si los datos del formulario son correctos
    function validarFormulario() {
      const inputs = document.querySelectorAll('input:not([type="password"])');

      let isFormValid = true;

      for (let i = 0; i < inputs.length; i++) {
        if (inputs[i].name === 'urlImage') {
            continue;
        }
        if (inputs[i].value.trim() === '') {
          console.log('El campo ' + inputs[i].name + ' está vacio')
          isFormValid = false;
          inputs[i].classList.toggle('errorInput', true); // Agrega la clase 'rec' al input
          mostrarAlerta('error', 'Error', 'Por favor, complete todos los campos obligatorios.');
          break;
        }

        else if (inputs[i].type === 'email') {
          let correoValido = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(inputs[i].value);
          if (!correoValido) {
            inputs[i].classList.toggle('errorInput', true); // Agrega la clase 'red' al input
            isFormValid = false;
            mostrarAlerta('error', 'Error', 'Por favor, ingrese un correo electrónico válido.');
            break;
          }
        }
        else {
          inputs[i].classList.toggle('errorInput', false); // Elimina la clase 'red' al input
        }
      }


      return isFormValid;

    }

    function mostrarAlerta(icon, title, mensaje) {
      Swal.fire({
        icon: icon,
        title: title,
        text: mensaje,
      });
    }