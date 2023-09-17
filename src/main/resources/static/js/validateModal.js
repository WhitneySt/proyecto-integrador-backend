
        // Obtener elementos necesarios
        const crearBtn = document.querySelector('.crear-cuenta');
        const modal = document.getElementById('modal');
        const cancelarBtn = document.querySelector('.cancelar');
        const form = document.getElementById('crear-cuenta-form');

        // Función para abrir el modal
        function openModal() {
            modal.style.display = 'block';
        }

        // Función para cerrar el modal
        function closeModal() {
            modal.style.display = 'none';
        }

        // Event listeners para abrir y cerrar el modal
        crearBtn.addEventListener('click',(e)=>{
            e.preventDefault();
            openModal();
        });

        // crearBtn.addEventListener('click', openModal);
        cancelarBtn.addEventListener('click', closeModal);