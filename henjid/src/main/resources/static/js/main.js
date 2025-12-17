document.addEventListener("DOMContentLoaded", function () {

  /* ============================
     MENÚ DE USUARIO (avatar)
  ============================ */
  const btn = document.getElementById("userMenuBtn");
  const menu = document.getElementById("userMenuDropdown");

  if (btn && menu) {
    btn.addEventListener("click", function (e) {
      e.preventDefault();
      menu.classList.toggle("show");
    });

    document.addEventListener("click", function (e) {
      if (!btn.contains(e.target) && !menu.contains(e.target)) {
        menu.classList.remove("show");
      }
    });
  }


  /* ============================
     LOGOUT CON TOAST (FUNCIONA EN TODO EL SITIO)
  ============================ */

  const logoutHeaderBtn = document.getElementById("logoutBtn");        // menú superior
  const logoutPerfilBtn = document.getElementById("logoutPerfilBtn");  // botón del perfil
  const toast = document.getElementById("toastLogout");

  function ejecutarLogout(e) {
    e.preventDefault();

    toast.classList.add("show");

    setTimeout(() => {
      toast.classList.remove("show");
      window.location.href = "/logout";
    }, 1800);
  }

  if (logoutHeaderBtn) logoutHeaderBtn.addEventListener("click", ejecutarLogout);
  if (logoutPerfilBtn) logoutPerfilBtn.addEventListener("click", ejecutarLogout);

});
/* ============================
   PREVISUALIZAR FOTO DE PERFIL
=============================== */

document.addEventListener("DOMContentLoaded", function () {

  const inputFoto = document.getElementById("inputFoto");
  const previewFoto = document.getElementById("previewFoto");
  const previewAvatar = document.getElementById("previewAvatar");

  if (!inputFoto) return; // Solo funciona en configuración de perfil

  inputFoto.addEventListener("change", function () {

    const archivo = this.files[0];
    if (!archivo) return;

    const lector = new FileReader();

    lector.onload = function (e) {
      const url = e.target.result;

      // Ocultar avatar si existe
      if (previewAvatar) {
        previewAvatar.style.display = "none";
      }

      // Mostrar foto previa
      if (previewFoto) {
        previewFoto.src = url;
        previewFoto.style.display = "block";
      }
    };

    lector.readAsDataURL(archivo);
  });

});
