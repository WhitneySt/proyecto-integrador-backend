// Esta función se encarga de validar el formulario y retorna un valor booleano que indica si el formulario es válido o no para poder enviarlo
function validateForm(event) {

    // Obtiene los elementos de los inputs del formulario
    const nombreCompletoInput = document.getElementById("nombre-completo");
    const correoElectronicoInput = document.getElementById("correo-electronico");
    const numeroDocumentoInput = document.getElementById("numero-documento");
    const contrasenaInput = document.getElementById("contrasena");

    // Define las expresiones regulares para cada input
    const nombreCompletoRegex =  /^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+$/// Verifica si una cadena contiene solo constras mayúsculas, minúsculas y espacios en blanco,permite que tenga la ñ y tíldes
    const correoElectronicoRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // verifica si una cadena es una dirección de correo electrónico válida.
    const numeroDocumentoRegex = /^[0-9]+$/; // verifica si una cadena contiene solo números.
    const contrasenaRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/; // verifica si una cadena contiene al menos una letra minúscula, una letra mayúscula y un número, y tiene una longitud mínima de 8 caracteres

    let isValid = true;

    // Valida cada input y actualiza el valor de isValid
    // Hace la verificación para cada input y por cada uno que no cumpla con la validación cambia el valor de isValid a false
    /*Al utilizar && isValid, se asegura de que el valor de isValid se actualice correctamente después de cada validación es decir despues de llamar la función llamar a la función validateInput(),
    y que el formulario solo se envíe si todas las validaciones son verdaderas.*/
    isValid = validateInput(nombreCompletoInput, nombreCompletoRegex) && isValid;
    isValid = validateInput(correoElectronicoInput, correoElectronicoRegex) && isValid;
    isValid = validateInput(numeroDocumentoInput, numeroDocumentoRegex) && isValid;
    isValid = validateInput(contrasenaInput, contrasenaRegex) && isValid;

    console.log(isValid);

    // Si el formulario no es válido, evita que se envíe
    if (isValid) {
        return true;
    } else {
        event.preventDefault(); // Evita que el formulario se envíe automáticamente al hacer clic en el botón de envío porque se usa para prevenir el comportamiento prederteminado del formulario
        return false;
    }


}

// Esta función se encarga de validar un input y retorna un valor booleano que indica si el input es válido o no
function validateInput(inputField, regex) {

    const ERROR_MESSAGE_SELECTOR = "error";

      // Verifica si el valor del input cumple con la expresión regular
    if (!regex.test(inputField.value) || inputField.value == "") {

        setInputStyle(inputField, "red", "block",ERROR_MESSAGE_SELECTOR);

        if (inputField.id == 'contrasena') {
            // Hay que hacerle algunos cambios en el posicionamiento al icono del ojo para que se vea bien cuando aparezcan los mensajer de error  porque sino se descuadra su posicionamiento
            setPositionEyeIcon(ERROR_MESSAGE_SELECTOR);
        }
        return false;
    }
    else {
        // Si el valor del input cumple con la expresión regular, cambia el estilo del input y oculta el mensaje de error
        setInputStyle(inputField, "", "none",ERROR_MESSAGE_SELECTOR);
        return true;

    }

}

// Esta función se encarga de cambiar el estilo de un input y mostrar/ocultar el mensaje de error
function setInputStyle(inputField, borderColor,display,ERROR_MESSAGE_SELECTOR) {

    document.getElementById(inputField.id + "-" + ERROR_MESSAGE_SELECTOR).style.display = display; // contiene el mensaje de error del input
    inputField.style.borderColor = borderColor;
}

// Esta función se encarga de ajustar la posición del icono del ojo en la contraseña
function setPositionEyeIcon(ERROR_MESSAGE_SELECTOR) {
    // Obtiene los elementos de los mensajes de error de la contraseña y el número de documento, y el icono del ojo
    const errorMesaggeInputNumberDocument = document.getElementById("numero-documento" + "-" + ERROR_MESSAGE_SELECTOR);
    const errorMesaggeInputContrasena = document.getElementById("contrasena" +"-" + ERROR_MESSAGE_SELECTOR);
    const iconEye = document.getElementsByClassName("icon")[0];

    // Si ambos mensajes de error están visibles o solo el de la contraseña, ajusta la posición del icono del ojo
    if ((errorMesaggeInputNumberDocument.style.display == "block" && errorMesaggeInputContrasena.style.display == "block") || (errorMesaggeInputContrasena.style.display == "block")) {
        iconEye.style.top = "30px";
    }
}

// Esta función se encarga de alternar la visibilidad de la contraseña
function togglePassword() {
    const passwordInput = document.querySelector('.password-icon');
    const icon = document.querySelector('.icon');
    if (passwordInput.type === "password") {
            // Si la contraseña está oculta, la muestra cambiando el tipo de input y cambia el icono del ojo
        passwordInput.type = "text";
        icon.classList.remove("fa-eye");
        icon.classList.add("fa-eye-slash");
    } else {
            // Si la contraseña está visible, la oculta y cambia el icono del ojo
        passwordInput.type = "password";
        icon.classList.remove("fa-eye-slash");
        icon.classList.add("fa-eye");
    }
}