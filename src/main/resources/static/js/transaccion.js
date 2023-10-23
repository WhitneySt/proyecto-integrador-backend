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
const inputMiCuentaDestino = document.getElementById('miCuentaDestinoDiv');
const miCuentaDestino = document.getElementById('miCuentaDestino')
const bolsillosOrigen = document.getElementById('bolsilloOrigen');
const bolsillosDestino = document.getElementById('bolsillosDestino');
const MontoTransacciones = document.querySelectorAll('.monto-transaccion');
const tipoTransacciones = document.querySelectorAll('.tipo-transferencia');


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

const handleChangeSelect = (selectOne, selectTwo)=>{
    const optionList = Array.from(selectTwo.children);
    const bolsillosFiltered = optionList.map(item=>{
        if(item.value === selectOne.value){
            item.style.display='none';
        }else{
            item.style.display='block';
        }

        return item
    });
    selectTwo.innerHTML ='';
    bolsillosFiltered.forEach(item=>{
        selectTwo.appendChild(item);
    })

}

const numberToMoney = (number) => {
    return new Intl.NumberFormat('es-CO', {
        style: 'currency',
        currency: 'COP',
        minimumFractionDigits: 0,
    }).format(number);
};

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
otrasCuentas.checked=true;
miCuentaDestino.checked = false;
misBolsillosDestinos.checked=false;
listaBolsilloOrigen.style.display='none';
listaBolsillosDestino.style.display='none';
inputMiCuentaDestino.style.display='none';

for (let index = 0; index < MontoTransacciones.length; index++) {
    const element = MontoTransacciones[index];
    MontoTransacciones[index].innerText = numberToMoney(element.innerText);
}
for (let index = 0; index < tipoTransacciones.length; index++) {
    const element = tipoTransacciones[index];
    element.style.fontWeight = '600';
    const tipoTransaccion = [{type:'deposito', color:'#00B894'},{type: 'transferencia', color: '#5F65F6'},{type:'retiro', color:'#FF2222'}];
    tipoTransaccion.forEach(item=>{
        if(element.innerText.toLowerCase().includes(item.type)){
            element.style.color = item.color;
        }

    })
}

miCuentaOrigen.addEventListener('input', (e)=>{
    if (e.target.checked) {
        misBolsillosOrigen.checked=false;
        document.getElementById('bolsilloOrigen').value = '';
        listaBolsilloOrigen.style.display='none';
        inputMiCuentaDestino.style.display = 'none';
    }else{
        misBolsillosOrigen.checked=true;
        listaBolsilloOrigen.style.display='block';
        inputMiCuentaDestino.style.display = 'block';
    }
})

misBolsillosOrigen.addEventListener('input', (e)=>{

    if (e.target.checked) {
        miCuentaOrigen.checked=false;
        /*if(bolsillosDestino.value){
            bolsillosOrigen.value='';
            const optionList = Array.from(bolsillosOrigen.children);
            const bolsillosFiltered = optionList.filter(item=> item.value !== bolsillosDestino.value);
            bolsillosOrigen.innerHTML ='';
            bolsillosFiltered.forEach(item=>{
                bolsillosOrigen.appendChild(item);
            })
        }*/
        listaBolsilloOrigen.style.display='block';
        inputMiCuentaDestino.style.display = 'block';
    }else{
        miCuentaOrigen.checked=true;
        listaBolsilloOrigen.style.display='none';
        inputMiCuentaDestino.style.display = 'none';
    }
})

otrasCuentas.addEventListener('input', (e)=>{
    if (e.target.checked) {
        misBolsillosDestinos.checked=false;
        miCuentaDestino.checked=false;
        document.getElementById('bolsillosDestino').value = '';
        listaBolsillosDestino.style.display='none';
        inputCuentaDestino.style.display='block';

    }else{
        misBolsillosDestinos.checked=true;
        miCuentaDestino.checked=false;
        listaBolsillosDestino.style.display='block';
        inputCuentaDestino.style.display='none';
    }
});

misBolsillosDestinos.addEventListener('input', (e)=>{
    if (e.target.checked) {
        otrasCuentas.checked=false;
        miCuentaDestino.checked=false;

        /*if(bolsillosOrigen.value){
            bolsillosDestino.value='';
            const optionList = Array.from(bolsillosDestino.children);
            const bolsillosFiltered = optionList.filter(item=> item.value !== bolsillosOrigen.value);
            bolsillosDestino.innerHTML ='';
            bolsillosFiltered.forEach(item=>{
                bolsillosDestino.appendChild(item);
            })
        }*/

        listaBolsillosDestino.style.display='block';
        inputCuentaDestino.style.display='none';

    }else{
        otrasCuentas.checked=true;
        miCuentaDestino.checked=false;
        document.getElementById('bolsillosDestino').value = '';
        listaBolsillosDestino.style.display='none';
        inputCuentaDestino.style.display='block';
    }
});

miCuentaDestino.addEventListener('input',(e)=>{
    if(e.target.checked){
        misBolsillosDestinos.checked=false;
        otrasCuentas.checked = false;
        document.getElementById('bolsillosDestino').value = '';
        listaBolsillosDestino.style.display='none';
        inputCuentaDestino.style.display='none';
    }else{
        misBolsillosDestinos.checked=true;
        otrasCuentas.checked = false;
        listaBolsillosDestino.style.display='block';
        inputCuentaDestino.style.display='none';
    }
})

bolsillosOrigen.addEventListener('input',(e)=>{
    if(e.target.value){
        handleChangeSelect(bolsillosOrigen,bolsillosDestino);
    }
})

bolsillosDestino.addEventListener('input',(e)=>{
    if(e.target.value){
        handleChangeSelect(bolsillosDestino,bolsillosOrigen);
    }
})


