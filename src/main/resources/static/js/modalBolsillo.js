const crearBolsilloBtn = document.getElementById('crear-bolsillo');
const modalBolsillo = document.getElementById('modal-bolsillo');
const cancelarBolsilloBtn = document.querySelector('.cancelar-bolsillo-modal');
const formBolsillo = document.getElementById('crear-bolsillo-form');

function openBolsilloModal() {
    modalBolsillo.style.display = 'block';
}

function closeBolsilloModal() {
    modalBolsillo.style.display = 'none';
}

const numberToMoney = (number) => {
    return new Intl.NumberFormat('es-CO', {
        style: 'currency',
        currency: 'COP',
        minimumFractionDigits: 0,
    }).format(number);
};

crearBolsilloBtn.addEventListener('click', (e) => {
    e.preventDefault();
    openBolsilloModal();
});

crearBolsilloBtn.addEventListener('click', (e) => {
    e.preventDefault();
    openBolsilloModal();
});

cancelarBolsilloBtn.addEventListener('click', closeBolsilloModal);

formBolsillo.addEventListener('submit', (event) => {
    //event.preventDefault();
    //alert('bolsillo submitted!');
});