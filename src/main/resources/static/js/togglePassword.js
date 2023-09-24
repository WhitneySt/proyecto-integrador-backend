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