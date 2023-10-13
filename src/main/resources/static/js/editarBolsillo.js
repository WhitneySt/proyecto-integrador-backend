const btnCancelar = document.getElementById('btnCancelar');
const colorInput = document.getElementById('color');

colorInput.style.backgroundColor = colorInput.value;

colorInput.addEventListener('input',(e)=>{
    colorInput.style.backgroundColor = e.target.value;
});

btnCancelar.addEventListener('click',()=>{
    window.location.href = "/bolsillos";
});