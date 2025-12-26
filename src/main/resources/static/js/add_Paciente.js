window.addEventListener('load', () => {
    const form = document.querySelector('#agregar_paciente_form');
    const modalEl = document.getElementById('staticBackdrop');
    const modal = modalEl ? new bootstrap.Modal(modalEl) : null;

    // Enganchar el botón "Save changes" del modal para que envíe el form
    const saveBtn = modalEl?.querySelector('.modal-footer .btn.btn-primary');
    if (saveBtn && form) {
        saveBtn.addEventListener('click', () => form.requestSubmit());
    }

    if (!form) return;

    // Definimos la fecha de hoy para el payload, en formato YYYY-MM-DD
    function todayLocalYYYYMMDD() {
        const now = new Date();
        const tzOffset = now.getTimezoneOffset(); // en minutos
        const local = new Date(now.getTime() - tzOffset * 60000);
        return local.toISOString().slice(0, 10); // "YYYY-MM-DD"
    }

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        // Armar payload (Para el PacienteController)
        const payload = {
            nombre: document.querySelector('#add_nombre')?.value.trim() || '',
            apellido: document.querySelector('#add_apellido')?.value.trim() || '',
            numeroContacto: document.querySelector('#add_numContacto')?.value.trim() || '',
            email: document.querySelector('#add_email')?.value.trim() || '',
            fechaIngreso: todayLocalYYYYMMDD(),
            domicilio: {
                calle: document.querySelector('#add_domi_calle')?.value.trim() || '',
                numero: Number(document.querySelector('#add_domi_numero')?.value.trim() || 0),
                localidad: document.querySelector('#add_domi_localidad')?.value.trim() || '',
                provincia: document.querySelector('#add_domi_provincia')?.value.trim() || ''
            }
        };

        try {
            const resp = await fetch('/paciente', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (!resp.ok) {
                let errorMsg = 'No se pudo agregar el paciente. Intentá nuevamente.';
                try {
                    // Intentar parsear JSON (caso del GlobalExceptionHandler, para mostrar mensaje de error retornado en el Toast)
                    const errorBody = await resp.json();

                    if (errorBody) {
                        if (typeof errorBody === 'string') {
                            errorMsg = errorBody;
                        } else if (errorBody.message) {
                            errorMsg = errorBody.message;
                        } else if (errorBody.error) {
                            errorMsg = errorBody.error;
                        }
                    }
                } catch (e) {
                    // Si no viene JSON, intento leer texto plano
                    try {
                        const text = await resp.text();
                        if (text) errorMsg = text;
                    } catch (e2) {
                        // si tampoco puedo leer texto, me quedo con el mensaje por defecto
                    }
                }
                throw new Error(errorMsg);
            }

            // Intentar leer el JSON creado del Paciente (puede venir 201 con body)
            let data = null;
            try { data = await resp.json(); } catch (_) {}

            // Cerrar modal y limpiar formulario
            form.reset();
            if (modal) modal.hide();

            // Insertar fila en la tabla si vino el objeto creado; si no, refrescar
            if (data && data.id) {
                const table = document.getElementById('pacienteTableBody');
                if (table) {
                    const row = table.insertRow();
                    const tr_id = 'tr_' + data.id;
                    row.id = tr_id;

                    // Creamos los mismos botones del get_paciente
                    let deleteButton = `<button
                      id="btn_delete_${data.id}"
                      type="button"
                      class="btn btn-danger btn_delete"
                      data-bs-toggle="modal"
                      data-bs-target="#confirmDeleteModal"
                      data-paciente-id="${data.id}"
                      data-paciente-nombre="${data.nombre} ${data.apellido}">
                      <i class="bi bi-trash"></i>
                    </button>`;

                    //por cada paciente creamos un boton que al hacerle clic invocará
                    //a la función de java script findBy que se encargará de buscar el paciente que queremos
                    //modificar y mostrar los datos del mismo en un formulario.
                    let updateButton = `<button
                      id="btn_id_${data.id}"
                      type="button"
                      onclick="findBy(${data.id})" 
                      class="btn btn-secondary btn_id">
                      <i class="bi bi-eye-fill"></i>
                    </button>`;

                    //por cada paciente creamos un boton que al hacerle clic invocará
                    //el modal de Administrar Turnos para ese paciente.
                    let turnosButton = `<button
                      id="btn_pacienteTurno_${data.id}"
                      type="button"
                      class="btn btn-primary btn_turno"
                      data-bs-toggle="modal"
                      data-bs-target="#turnosModal"
                      data-paciente-id="${data.id}"
                      data-paciente-nombre="${data.nombre} ${data.apellido}">
                      <i class="bi bi-calendar3"></i>
                    </button>`;


                    row.innerHTML =
                        '<td class="td_id">' + data.id + '</td>' +
                        '<td class="td_nombre">' + (data.nombre ?? payload.nombre) + '</td>' +
                        '<td class="td_apellido">' + (data.apellido ?? payload.apellido) + '</td>' +
                        '<td class="td_contacto">' + (data.numeroContacto ?? payload.numeroContacto) + '</td>' +
                        '<td class="td_fechaIngreso">' + (data.fechaIngreso ?? '') + '</td>' +
                        '<td class="td_email">' + (data.email ?? payload.email) + '</td>' +
                        '<td>' + turnosButton + '</td>' +
                        '<td>' + updateButton + '</td>' +
                        '<td>' + deleteButton + '</td>';
                } else {
                    window.location.reload();
                }
            } else {
                window.location.reload();
            }

            showToast('Paciente agregado', 'success');
        } catch (err) {
            console.log(err);
            showToast(err.message || 'No se pudo agregar el paciente. Intentá nuevamente.', 'danger');
        }
    });

    // Toast Bootstrap 5 (auto-contenido, sin tocar tu HTML)
    function showToast(message, type = 'success') {
        let container = document.getElementById('toastContainer');
        if (!container) {
            container = document.createElement('div');
            container.id = 'toastContainer';
            container.className = 'toast-container position-fixed top-0 end-0 p-3';
            document.body.appendChild(container);
        }
        const toastEl = document.createElement('div');
        toastEl.className = `toast align-items-center text-bg-${type} border-0`;
        toastEl.setAttribute('role', 'alert');
        toastEl.setAttribute('aria-live', 'assertive');
        toastEl.setAttribute('aria-atomic', 'true');
        toastEl.innerHTML = `
      <div class="d-flex">
        <div class="toast-body">${message}</div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto"
                data-bs-dismiss="toast" aria-label="Close"></button>
      </div>`;
        container.appendChild(toastEl);
        const toast = new bootstrap.Toast(toastEl);
        toast.show();
        toastEl.addEventListener('hidden.bs.toast', () => toastEl.remove());
    }
});
