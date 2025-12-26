document.addEventListener('DOMContentLoaded', function () {
    const turnosModal = document.getElementById('turnosModal');
    const odontologoSelect = document.getElementById('selectOdontologo');
    const btnAgendar = document.getElementById('btnToggleTurnos');
    if (!turnosModal) return;

    function cargarTurnos(pacienteId, nombreCompleto) {
        const nombreEl = document.getElementById('turnosPacienteNombre');
        const proximosDiv = document.getElementById('turnosProximos');
        const previosDiv = document.getElementById('turnosPrevios');

        if (nombreEl) nombreEl.textContent = nombreCompleto || '';

        if (proximosDiv) proximosDiv.innerHTML = '<h5>Turnos próximos:</h5>';
        if (previosDiv) previosDiv.innerHTML = '<h5>Turnos previos:</h5>';

        if (!pacienteId) return;

        fetch(`/turno/TurnoPorPaciente/${pacienteId}`)
            .then(resp => {
                if (!resp.ok) throw new Error('Error HTTP ' + resp.status);
                return resp.json();
            })
            .then(turnos => {
                const hoyStr = new Date().toISOString().slice(0, 10); // YYYY-MM-DD
                const ulProximos = document.createElement('ul');
                const ulPrevios = document.createElement('ul');

                turnos.forEach(t => {
                    const li = document.createElement('li');
                    const fecha = document.createElement('strong');
                    fecha.textContent = t.fecha;
                    const doctor = document.createElement('strong');
                    doctor.textContent = `${t.odontologoNombre} ${t.odontologoApellido}`;
                    li.append(fecha, ': Con el Dr. ', doctor);

                    if (t.fecha >= hoyStr) {
                        ulProximos.appendChild(li);
                    } else {
                        ulPrevios.appendChild(li);
                    }
                });

                if (proximosDiv) {
                    if (ulProximos.childElementCount > 0) {
                        proximosDiv.appendChild(ulProximos);
                    } else {
                        proximosDiv.innerHTML += '<p>No tiene turnos futuros.</p>';
                    }
                }

                if (previosDiv) {
                    if (ulPrevios.childElementCount > 0) {
                        previosDiv.appendChild(ulPrevios);
                    } else {
                        previosDiv.innerHTML += '<p>No tiene turnos previos.</p>';
                    }
                }
            })
            .catch(err => {
                console.error(err);
                if (proximosDiv) proximosDiv.innerHTML +=
                    '<p class="text-danger">No se pudieron cargar los turnos.</p>';
            });
    }

    function cargarOdontologos() {
        if (!odontologoSelect) return;

        odontologoSelect.innerHTML = '<option value="">Seleccione un Odontólogo</option>';

        fetch('/odontologo')
            .then(resp => {
                if (!resp.ok) throw new Error('Error HTTP ' + resp.status);
                return resp.json();
            })
            .then(odontologos => {
                odontologos.forEach(o => {
                    const opt = document.createElement('option');
                    opt.value = o.id;
                    opt.textContent = `Dr. ${o.nombre} ${o.apellido}`;
                    odontologoSelect.appendChild(opt);
                });
            })
            .catch(err => {
                console.error(err);
                const opt = document.createElement('option');
                opt.value = '';
                opt.textContent = 'No se pudieron cargar los odontólogos';
                odontologoSelect.appendChild(opt);
            });
    }

    // Cuando se abre el modal
    turnosModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        if (!button) return;

        const pacienteId = button.getAttribute('data-paciente-id');
        const nombre = button.getAttribute('data-paciente-nombre');

        // Guardamos el id en el modal para usarlo al agendar
        turnosModal.dataset.pacienteId = pacienteId || '';

        cargarTurnos(pacienteId, nombre);
        cargarOdontologos();
    });

    // Click en "Agendar turno"
    if (btnAgendar) {
        btnAgendar.addEventListener('click', function () {
            const pacienteId   = turnosModal.dataset.pacienteId;
            const fechaInput   = document.getElementById('datepicker');
            const fecha        = fechaInput ? fechaInput.value : '';
            const odontologoId = odontologoSelect ? odontologoSelect.value : '';

            if (!pacienteId || !fecha || !odontologoId) {
                showToast('Seleccioná una fecha y un odontólogo e intentá nuevamente.', 'danger');
                return;
            }

            const payload = {
                pacienteId: Number(pacienteId),
                odontologoId: Number(odontologoId),
                fecha: fecha              // formato YYYY-MM-DD
            };

            fetch('/turno', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            })
                .then(resp => {
                    if (!resp.ok) throw new Error('Error al crear turno: HTTP ' + resp.status);
                    return resp.json();
                })
                .then(data => {
                    //alert('Turno agendado correctamente.');
                    cargarTurnos(pacienteId, document.getElementById('turnosPacienteNombre')?.textContent || '');
                    showToast('Turno agendado exitosamente', 'success');
                })
                .catch(err => {
                    console.error(err);
                    showToast(err.message || 'No se pudo agendar el turno. Intentá nuevamente.', 'danger');
                });
        });
    }

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
