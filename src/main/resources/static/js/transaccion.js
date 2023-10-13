const btnDeposito =  document.getElementById('btnDepositar');
const btnTransferencia = document.getElementById('btnTransferir');
const btnRetiro = document.getElementById('btnRetirar');
const modalDeposito = document.getElementById('modal-deposito');
const modalTransferencia = document.getElementById('modal-transferencia');
const modalRetiro= document.getElementById('modal-Retiro');
const btnCancelar = document.getElementById('cancelar');
const btnCancelarTransferencia = document.getElementById('cancelarTransferencia');
const btnCancelarRetiro = document.getElementById('cancelarRetiro');
const miCuentaOrigen = document.getElementById('miCuentaOrigen');
const misBolsillosOrigen = document.getElementById('misBolsillosOrigen');
const listaBolsilloOrigen = document.getElementById('listaBolsillosOrigen');
const otrasCuentas = document.getElementById('otrasCuentas');
const misBolsillosDestinos = document.getElementById('misBolsillosDestinos');
const listaBolsillosDestino = document.getElementById('listaBolsillosDestino');
const inputCuentaDestino = document.getElementById('inputCuentaDestino');


function openBolsilloModalDeposito() {
    modalDeposito.style.display = 'block';
}

function openBolsilloModalTransferencia() {
    modalTransferencia.style.display = 'block';
}

function openBolsilloModalRetiro() {
    modalRetiro.style.display = 'block';
}

function closeBolsilloModal() {
    modalDeposito.style.display = 'none';
    modalTransferencia.style.display = 'none';
    modalRetiro.style.display = 'none';
}

btnDeposito.addEventListener('click', ()=>{
    openBolsilloModalDeposito()
});

btnTransferencia.addEventListener('click', ()=>{
    openBolsilloModalTransferencia()
})

btnRetiro.addEventListener('click', ()=>{
    openBolsilloModalRetiro()
})

btnCancelar.addEventListener('click',()=>{
    closeBolsilloModal();
})

btnCancelarTransferencia.addEventListener('click',()=>{
    closeBolsilloModal();
})

btnCancelarRetiro.addEventListener('click',()=>{
    closeBolsilloModal();
})


miCuentaOrigen.checked = true;
misBolsillosOrigen.checked=false;
listaBolsilloOrigen.style.display='none'; 


miCuentaOrigen.addEventListener('input', (e)=>{
    if (e.target.checked) {
        misBolsillosOrigen.checked=false;
        listaBolsilloOrigen.style.display='none'; 
    }else{
        misBolsillosOrigen.checked=true;
        listaBolsilloOrigen.style.display='block'; 
    }
})

misBolsillosOrigen.addEventListener('input', (e)=>{
    if (e.target.checked) {
        miCuentaOrigen.checked=false;
        listaBolsilloOrigen.style.display='block'; 
    }else{
        miCuentaOrigen.checked=true;
        listaBolsilloOrigen.style.display='none'; 
    }
})

otrasCuentas.checked = true;
misBolsillosDestinos.checked=false;
listaBolsillosDestino.style.display='none'; 

otrasCuentas.addEventListener('input', (e)=>{
    if (e.target.checked) {
        misBolsillosDestinos.checked=false;
        listaBolsillosDestino.style.display='none';
        inputCuentaDestino.style.display='block'; 
    }else{
        misBolsillosDestinos.checked=true;
        listaBolsillosDestino.style.display='block';
        inputCuentaDestino.style.display='none'; 
    }
});

misBolsillosDestinos.addEventListener('input', (e)=>{
    if (e.target.checked) {
        otrasCuentas.checked=false;
        inputCuentaDestino.style.display='none';
        listaBolsillosDestino.style.display='block'; 
    }else{
        otrasCuentas.checked=true;
        inputCuentaDestino.style.display='block';
        listaBolsillosDestino.style.display='none'; 
    }
});


