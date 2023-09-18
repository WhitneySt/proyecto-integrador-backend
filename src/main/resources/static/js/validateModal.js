 // Obtener elementos necesarios
const crearCuentaBtn = document.querySelector('.crear-cuenta');
const modal = document.getElementById('modal');
const cancelarBtn = document.querySelector('.cancelar');
const form = document.getElementById('crear-cuenta-form');
const montoInicialInput = document.querySelector('#monto-inicial');
const buttonSubmit = document.querySelector('button[type="submit"]');

// Función para abrir el modal
function openModal() {
    modal.style.display = 'block';
}

// Función para cerrar el modal
function closeModal() {
    modal.style.display = 'none';
}

// Event listeners para abrir y cerrar el modal
crearCuentaBtn.addEventListener('click', (e) => {
    e.preventDefault();
    openModal();
});

cancelarBtn.addEventListener('click', closeModal);


// Validar que el campo de monto inicial no esté vacío
form.addEventListener('submit', (event) => {

    if (montoInicialInput.value.trim() === '') {

        event.preventDefault();

        const errorMensaje = document.createElement('span');
        errorMensaje.classList.add('error-mensaje');
        errorMensaje.textContent = 'El campo de monto inicial no puede estar vacío';

        montoInicialInput.classList.add('errorInput');
        montoInicialInput.parentNode.insertBefore(errorMensaje, montoInicialInput.nextSibling);

        // Deshabilitar el botón de enviar el formulario
        buttonSubmit.disabled = true;

    }
}
);

// si el campo de monto inicial no está vacío y se está ingresando valores  , se remueve el mensaje de error
montoInicialInput.addEventListener('input', () => {
    if (montoInicialInput.value.trim() !== '') {
        montoInicialInput.classList.remove('error');
        buttonSubmit.disabled = false;
    }
})

