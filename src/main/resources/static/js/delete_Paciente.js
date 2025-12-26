async function deleteBy(id) {
    const url = '/paciente/' + id;
    try {
        const response = await fetch(url, { method: 'DELETE' });

        if (!response.ok) {
            throw new Error(`Error al eliminar (HTTP ${response.status})`);
        }

        // Borrar la fila del paciente eliminado
        const row = document.querySelector('#tr_' + id);
        if (row) row.remove();

    } catch (err) {
        console.error('Error eliminando paciente:', err);
        alert('No se pudo eliminar el paciente. Intentá nuevamente.');
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

        deleteTargetId = button.getAttribute('data-paciente-id');
        const nombre = button.getAttribute('data-paciente-nombre') || '';

        // Pintamos los datos en el modal
        const nameEl = document.getElementById('confirmPacienteNombre');
        const idEl = document.getElementById('confirmPacienteId');
        if (nameEl) nameEl.textContent = nombre;
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