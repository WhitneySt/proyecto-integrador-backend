function confirmLogout() {
        Swal.fire({
            title: '¿Estás seguro de que quieres cerrar sesión?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Sí',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                // se realiza la acción de cierre de sesión
                window.location.href = "/logout";
            }
        });
    }