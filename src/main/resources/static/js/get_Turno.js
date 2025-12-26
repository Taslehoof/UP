window.addEventListener('load', function () {
    (function () {

        //con fetch invocamos a la API de turnos con el método GET
        //nos devolverá un JSON con una lista de turnos
        const url = '/turno';
        const settings = {
            method: 'GET'
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                //recorremos la lista de turnos del JSON
                for (turno of data) {
                    //por cada turno armaremos una fila de la tabla
                    //cada fila tendrá un id que luego nos permitirá borrar la fila si eliminamos el turno
                    var table = document.getElementById("turnoTableBody");
                    var turnoRow = table.insertRow();
                    let tr_id = 'tr_' + turno.id;
                    turnoRow.id = tr_id;

                    //por cada turno creamos un boton delete que agregaremos en cada fila para poder eliminar el mismo
                    //dicho boton invocara a la funcion de java script deleteById que se encargará
                    //de llamar a la API para eliminar un turno
                    let deleteButton = `<button
                      id="btn_delete_${turno.id}"
                      type="button"
                      class="btn btn-danger btn_delete"
                      data-bs-toggle="modal"
                      data-bs-target="#confirmDeleteModal"
                      data-turno-id="${turno.id}"
                      data-turno-nombre="${turno.pacienteNombre} ${turno.pacienteApellido}"
                      data-turno-fecha="${turno.fecha}">
                      <i class="bi bi-trash"></i>
                    </button>`;

                    //armamos cada columna de la fila
                    //como primer columna pondremos el ID del turno
                    //luego los datos del mismo
                    //como ultima columna el boton modificar y eliminar
                    turnoRow.innerHTML = '<td class=\"td_nombrePaciente\">' + turno.pacienteNombre + '</td>' +
                        '<td class=\"td_apellidoPaciente\">' + turno.pacienteApellido + '</td>' +
                        '<td class=\"td_fechaTurno\">' + turno.fecha + '</td>' +
                        '<td class=\"td_nombreDoctor\">' + turno.odontologoNombre + '</td>' +
                        '<td class=\"td_apellidoDoctor\">' + turno.odontologoApellido + '</td>' +
                        '<td>' + deleteButton + '</td>';
                }
            })
    })

    (function () {
        let pathname = window.location.pathname;
        if (pathname == "/turnoLista.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })
})