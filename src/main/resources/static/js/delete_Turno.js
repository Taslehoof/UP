async function deleteBy(id) {
    const url = '/turno/' + id;
    try {
        const response = await fetch(url, { method: 'DELETE' });

        if (!response.ok) {
            throw new Error(`Error al eliminar (HTTP ${response.status})`);
        }

        // Borrar la fila del turno eliminado
        const row = document.querySelector('#tr_' + id);
        if (row) row.remove();

    } catch (err) {
        console.error('Error eliminando turno:', err);
        alert('No se pudo eliminar el turno. Intentá nuevamente.');
    }
}
let deleteTargetId = null;

document.addEventListener('DOMContentLoaded', () => {
    const confirmModalEl = document.getElementById('confirmDeleteModal');
    if (!confirmModalEl) return;

    // Al abrir el modal, obtenemos los data-* del botón que lo disparó
    confirmModalEl.addEventListener('show.bs.modal', (event) => {
        const button = event.relatedTarget;
        if (!button) return;

        deleteTargetId = button.getAttribute('data-turno-id');
        const nombre = button.getAttribute('data-turno-nombre') || '';
        const fecha = button.getAttribute('data-turno-fecha') || '';

        // Pintamos los datos en el modal
        const nameEl = document.getElementById('confirmTurnoNombre');
        const fechaEl = document.getElementById('confirmTurnoFecha');
        const idEl = document.getElementById('confirmTurnoId');
        if (nameEl) nameEl.textContent = nombre;
        if (fechaEl) fechaEl.textContent = fecha;
        if (idEl) idEl.textContent = `id: ${deleteTargetId}`;
    });

    // Al confirmar, borramos y cerramos el modal
    const btnConfirm = document.getElementById('btnConfirmDelete');
    if (btnConfirm) {
        btnConfirm.addEventListener('click', async () => {
            if (!deleteTargetId) return;
            await deleteBy(deleteTargetId);

            const modal = bootstrap.Modal.getInstance(confirmModalEl);
            if (modal) modal.hide();

            deleteTargetId = null;
        });
    }
});