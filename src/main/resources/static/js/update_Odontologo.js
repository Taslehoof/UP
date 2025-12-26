(() => {
    let editMode = false;
    let modalInstance = null;

    // Campos "bloqueados" permanentemente
    const LOCKED_IDS = new Set(['ver_odontologo_id']);

    // Selectors helpers
    const $ = (sel, root = document) => root.querySelector(sel);
    const $$ = (sel, root = document) => Array.from(root.querySelectorAll(sel));

    function updateOdontologoRow(odontologo) {
        const row = document.getElementById(`tr_${odontologo.id}`);
        if (!row) return;

        const setText = (sel, val) => {
            const td = row.querySelector(sel);
            if (td) td.textContent = val ?? '';
        };

        setText('.td_id', odontologo.id);
        setText('.td_nombre', odontologo.nombre);
        setText('.td_apellido', odontologo.apellido);
        setText('.td_matricula', odontologo.matricula);
    }

    function setReadOnly(form, readonly) {
        $$('#ver_odontologo_form input', document).forEach(inp => {
            if (LOCKED_IDS.has(inp.id)) {
                inp.readOnly = true;
                inp.classList.toggle('bg-light', true);
            } else {
                inp.readOnly = readonly;
                inp.classList.toggle('bg-light', readonly);
            }
        });
    }

    function fillForm(odontologo) {
        $('#ver_odontologo_id').value     = odontologo.id ?? '';
        $('#ver_nombre').value          = odontologo.nombre ?? '';
        $('#ver_apellido').value        = odontologo.apellido ?? '';
        $('#ver_matricula').value     = odontologo.matricula ?? '';
    }

    function toPayload() {
        return {
            id: $('#ver_odontologo_id').value,
            nombre: $('#ver_nombre').value,
            apellido: $('#ver_apellido').value,
            matricula: $('#ver_matricula').value,
            }
    };


    function showModal() {
        const modalEl = document.getElementById('verOdontologo');
        modalInstance = modalInstance || new bootstrap.Modal(modalEl);
        modalInstance.show();
    }

    function resetViewMode() {
        editMode = false;
        setReadOnly($('#ver_odontologo_form'), true);
        const btn = $('#btnToggleEdit');
        btn.textContent = 'Modificar';
        btn.classList.remove('btn-success');
        btn.classList.add('btn-danger'); // rojo en modo "Modificar"
    }

    // Submit del formulario (Guardar Cambios)
    $('#ver_odontologo_form')?.addEventListener('submit', (event) => {
        event.preventDefault();
        const odontologoId = $('#ver_odontologo_id').value;
        const formData = toPayload();

        const url = `/odontologo/${odontologoId}`;
        const settings = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        };

        fetch(url, settings)
            .then(res => {
                if (res.status === 204) return null;         // No Content
                if (!res.ok) return res.json().then(e => Promise.reject(e));
                return res.json();                            // Devuelve el odontologo actualizado
            })
            .then((maybeUpdated) => {
                const updated = maybeUpdated ?? formData;     // Si no llega JSON, usamos lo que enviamos
                updateOdontologoRow(updated);                   // Actualización de la fila en tiempo real en el html principal
                resetViewMode();                              // Volver a modo readonly todos los elementos del modal

                showToast('Odontologo actualizado', 'success');

            })
            .catch(err => {
                const msg = err?.message || err?.error || 'Error al actualizar el odontologo';
                showToast(msg, 'danger');

            });
    });

    // Botón Modificar / Guardar Cambios
    $('#btnToggleEdit')?.addEventListener('click', () => {
        const btn = $('#btnToggleEdit');
        if (!editMode) {
            // 👉 Pasar a modo edición
            editMode = true;
            setReadOnly($('#ver_odontologo_form'), false);
            btn.textContent = 'Guardar Cambios';
            btn.classList.remove('btn-danger');
            btn.classList.add('btn-success'); // Ponemos el botón verde en modo "Guardar Cambios"
        } else {
            // 💾 Guardar
            $('#ver_odontologo_form').requestSubmit();
        }
    });

    // Exponer findBy en la ventana actual (para que el botón de "ver" lo pueda ver)
    window.findBy = function (id) {
        const url = '/odontologo/BuscarPorId/' + id;
        fetch(url, { method: 'GET' })
            .then(r => r.json())
            .then(data => {
                fillForm(data);
                resetViewMode();
                showModal();
            })
            .catch(error => {
                const msg = "Error: " + (error?.message || error);
                showToast(msg, 'danger');
            });
    };

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

})();
