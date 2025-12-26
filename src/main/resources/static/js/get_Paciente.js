window.addEventListener('load', function () {
    (function () {

        //con fetch invocamos a la API de pacientes con el método GET
        //nos devolverá un JSON con una lista de pacientes
        const url = '/paciente';
        const settings = {
            method: 'GET'
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                //recorremos la lista de pacientes del JSON
                for (paciente of data) {
                    //por cada paciente armaremos una fila de la tabla
                    //cada fila tendrá un id que luego nos permitirá borrar la fila si eliminamos el paciente
                    var table = document.getElementById("pacienteTableBody");
                    var pacienteRow = table.insertRow();
                    let tr_id = 'tr_' + paciente.id;
                    pacienteRow.id = tr_id;

                    //por cada paciente creamos un boton delete que agregaremos en cada fila para poder eliminar el mismo
                    //dicho boton invocara a la funcion de java script deleteById que se encargará
                    //de llamar a la API para eliminar un paciente
                    let deleteButton = `<button
                      id="btn_delete_${paciente.id}"
                      type="button"
                      class="btn btn-danger btn_delete"
                      data-bs-toggle="modal"
                      data-bs-target="#confirmDeleteModal"
                      data-paciente-id="${paciente.id}"
                      data-paciente-nombre="${paciente.nombre} ${paciente.apellido}">
                      <i class="bi bi-trash"></i>
                    </button>`;

                    //por cada paciente creamos un boton que al hacerle clic invocará
                    //a la función de java script findBy que se encargará de buscar el paciente que queremos
                    //modificar y mostrar los datos del mismo en un formulario.
                    let updateButton = `<button
                      id="btn_id_${paciente.id}"
                      type="button"
                      onclick="findBy(${paciente.id})" 
                      class="btn btn-secondary btn_id">
                      <i class="bi bi-eye-fill"></i>
                    </button>`;

                    //por cada paciente creamos un boton que al hacerle clic invocará
                    //el modal de Administrar Turnos para ese paciente.
                    let turnosButton = `<button
                      id="btn_pacienteTurno_${paciente.id}"
                      type="button"
                      class="btn btn-primary btn_turno"
                      data-bs-toggle="modal"
                      data-bs-target="#turnosModal"
                      data-paciente-id="${paciente.id}"
                      data-paciente-nombre="${paciente.nombre} ${paciente.apellido}" >
                      <i class="bi bi-calendar3"></i>
                    </button>`;

                    //armamos cada columna de la fila
                    //como primer columna pondremos el ID del paciente
                    //luego los datos del mismo
                    //como ultima columna el boton modificar y eliminar
                    pacienteRow.innerHTML = '<td class=\"td_id\">' + paciente.id + '</td>' +
                        '<td class=\"td_nombre\">' + paciente.nombre + '</td>' +
                        '<td class=\"td_apellido\">' + paciente.apellido + '</td>' +
                        '<td class=\"td_contacto\">' + paciente.numeroContacto + '</td>' +
                        '<td class=\"td_fechaIngreso\">' + paciente.fechaIngreso + '</td>' +
                        '<td class=\"td_email\">' + paciente.email + '</td>' +
                        '<td>' + turnosButton + '</td>' +
                        '<td>' + updateButton + '</td>' +
                        '<td>' + deleteButton + '</td>';
                }
            })
    })

    (function () {
        let pathname = window.location.pathname;
        if (pathname == "/pacienteLista.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })
})