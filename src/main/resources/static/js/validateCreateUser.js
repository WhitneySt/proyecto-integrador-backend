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
      const inputs = document.querySelectorAll('input');

      let isFormValid = true;

      for (let i = 0; i < inputs.length; i++) {
        // Se valida que los campos obligatorios no estén vacios
        if (inputs[i].value.trim() === '') {
          console.log('El campo ' + inputs[i].name + ' está vacio')
          isFormValid = false;
          inputs[i].classList.toggle('errorInput', true); // Agrega la clase ' al input
          mostrarAlerta('error', 'Error', 'Por favor, complete todos los campos obligatorios.');
          break;
        }
        // Se valida que la structura del algunos inputs sea correcta
        else if (inputs[i].type === 'email') {
          let correoValido = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(inputs[i].value);
          if (!correoValido) {
            inputs[i].classList.toggle('errorInput', true); // Agrega la clase 'red' al input
            isFormValid = false;
            mostrarAlerta('error', 'Error', 'Por favor, ingrese un correo electrónico válido.');
            break;
          }
        }
        // Se valida la contraseña que si no está vacia debe tener al algunas condiciones para que sea correcta
        else if (inputs[i].type === 'password') {
            const passwordValido = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(inputs[i].value);
            if (!passwordValido) {
                isFormValid = false;
                passwordInput.classList.add('errorInput');
                mostrarAlerta('error', 'Error', 'La contraseña debe tener al menos 8 caracteres, una letra mayúscula y un número.');
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