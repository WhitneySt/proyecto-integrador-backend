const crearBolsilloBtn = document.getElementById('crear-bolsillo');
const modalBolsillo = document.getElementById('modal-bolsillo');
const cancelarBolsilloBtn = document.querySelector('.cancelar-bolsillo-modal');
const colorInput = document.getElementById('color');
const saldos = document.querySelectorAll('.saldo-bolsillo');



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

colorInput.style.backgroundColor = colorInput.value;

for (let index = 0; index < saldos.length; index++) {
    const element = saldos[index];
    saldos[index].innerText = numberToMoney(element.innerText);
}

colorInput.addEventListener('input',(e)=>{
    colorInput.style.backgroundColor = e.target.value;
});


crearBolsilloBtn.addEventListener('click', (e) => {
    e.preventDefault();
    openBolsilloModal();
})

cancelarBolsilloBtn.addEventListener('click', closeBolsilloModal);

document.addEventListener('click', (e) => {

    if(e.target.id == "btnEditar") {
        const btnEditar = e.target;
        const idBolsilloSeleccionado = btnEditar.getAttribute("name");
        window.location.href = "bolsillos/" + idBolsilloSeleccionado;
    }

    if(e.target.id == "btnEliminar") {
        const btnEliminar = e.target;
        const idBolsilloSeleccionado = btnEliminar.getAttribute("name");
        window.location.href = "bolsillos/remove/" + idBolsilloSeleccionado;
    }

});



