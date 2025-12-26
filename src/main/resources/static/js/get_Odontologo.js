window.addEventListener('load', function () {
    (function(){

      //con fetch invocamos a la API de odontologos con el método GET
      //nos devolverá un JSON con una lista de odontologos
      const url = '/odontologo';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {
      //recorremos la lista de odontologos del JSON
         for(odontologo of data){
            //por cada odontologo armaremos una fila de la tabla
            //cada fila tendrá un id que luego nos permitirá borrar la fila si eliminamos el odontologo
            var table = document.getElementById("odontologoTableBody");
            var odontologoRow =table.insertRow();
            let tr_id = 'tr_' + odontologo.id;
            odontologoRow.id = tr_id;

            //por cada odontologo creamos un boton delete que agregaremos en cada fila para poder eliminar el mismo
            //dicho boton invocara a la funcion de java script deleteById que se encargará
            //de llamar a la API para eliminar un odontologo
            let deleteButton = `<button
              id="btn_delete_${odontologo.id}"
              type="button"
              class="btn btn-danger btn_delete"
              data-bs-toggle="modal"
              data-bs-target="#confirmDeleteModal"
              data-odontologo-id="${odontologo.id}"
              data-odontologo-nombre="${odontologo.nombre} ${odontologo.apellido}">
              <i class="bi bi-trash"></i>
            </button>`;

            //por cada odontologo creamos un boton que al hacerle clic invocará
            //a la función de java script findBy que se encargará de buscar el odontologo que queremos
            //modificar y mostrar los datos del mismo en un formulario.
             let updateButton = `<button
                      id="btn_id_${odontologo.id}"
                      type="button"
                      onclick="findBy(${odontologo.id})" 
                      class="btn btn-secondary btn_id">
                      <i class="bi bi-eye-fill"></i>
                    </button>`;

            //armamos cada columna de la fila
            //como primer columna pondremos el ID del odontologo
            //luego los datos del mismo
            //como ultima columna el boton modificar y eliminar
            odontologoRow.innerHTML = '<td class=\"td_id\">' + odontologo.id + '</td>' +
                    '<td class=\"td_nombre\">' + odontologo.nombre + '</td>' +
                    '<td class=\"td_apellido\">' + odontologo.apellido + '</td>' +
                    '<td class=\"td_matricula\">' + odontologo.matricula + '</td>' +
                    '<td>' + updateButton + '</td>' +
                    '<td>' + deleteButton + '</td>';

        };

    })
    })

    (function(){
      let pathname = window.location.pathname;
      if (pathname == "/odontologoLista.html") {
          document.querySelector(".nav .nav-item a:last").addClass("active");
      }
    })
})