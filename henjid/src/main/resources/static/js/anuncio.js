document.addEventListener("DOMContentLoaded", () => {
    const video = document.getElementById("adVideo");
    const btnMute = document.getElementById("btnMute");
    const btnContinuar = document.getElementById("btnContinuar");
    const btnCerrar = document.getElementById("btnCerrar");
    const timerSpan = document.getElementById("adTimer");
    const layout = document.querySelector(".ad-layout");
    const moduloId = layout.getAttribute("data-modulo-id");

    // ====== CONTADOR DEL ANUNCIO ======
    const DURACION = 30; // segundos
    let restante = DURACION;

    timerSpan.textContent = `${restante}s`;

    const intervalId = setInterval(() => {
        restante--;
        if (restante <= 0) {
            clearInterval(intervalId);
            timerSpan.textContent = "Listo";

            btnContinuar.disabled = false;
            btnCerrar.disabled = false;
            btnCerrar.classList.add("enabled");
        } else {
            timerSpan.textContent = `${restante}s`;
        }
    }, 1000);

    // Evitar que el usuario pause el video
    video.addEventListener("pause", () => {
        if (restante > 0) {
            video.play().catch(() => {});
        }
    });

    // ====== BOTÓN MUTE ======
    btnMute.addEventListener("click", () => {
        video.muted = !video.muted;
        btnMute.textContent = video.muted ? "Activar sonido" : "Silenciar";
    });

    // ====== CONTINUAR ACTIVIDAD (después del anuncio) ======
    btnContinuar.addEventListener("click", () => {
        if (moduloId) {
            window.location.href = `/curso/${moduloId}`;
        } else {
            window.location.href = "/modulos";
        }
    });

    // ====== CERRAR ANUNCIO (ir a módulos) ======
    btnCerrar.addEventListener("click", () => {
        if (btnCerrar.disabled) return;
        window.location.href = "/modulos";
    });

    // ======================================================
    //   ACTIVIDAD INTERACTIVA (mini quiz de vocabulario)
    // ======================================================
    const banco = [
        { en: "Apple", es: "Manzana" },
        { en: "Dog", es: "Perro" },
        { en: "House", es: "Casa" },
        { en: "Book", es: "Libro" },
        { en: "Water", es: "Agua" },
        { en: "School", es: "Escuela" },
        { en: "Sun", es: "Sol" },
        { en: "Car", es: "Coche" },
        { en: "Chair", es: "Silla" },
        { en: "Milk", es: "Leche" }
    ];

    const preguntaEl = document.getElementById("actividadPregunta");
    const opcionesEl = document.getElementById("actividadOpciones");
    const feedbackEl = document.getElementById("actividadFeedback");

    function nuevaPregunta() {
        feedbackEl.textContent = "";

        // palabra correcta
        const correcta = banco[Math.floor(Math.random() * banco.length)];

        // dos incorrectas distintas
        let opciones = [correcta];
        while (opciones.length < 3) {
            const cand = banco[Math.floor(Math.random() * banco.length)];
            if (!opciones.some(o => o.es === cand.es)) {
                opciones.push(cand);
            }
        }

        // mezclar
        opciones = opciones.sort(() => Math.random() - 0.5);

        // pintar
        preguntaEl.textContent = `¿Cuál es la traducción de “${correcta.en}”?`;
        opcionesEl.innerHTML = "";

        opciones.forEach(op => {
            const btn = document.createElement("button");
            btn.textContent = op.es;
            btn.addEventListener("click", () => {
                if (op.es === correcta.es) {
                    btn.classList.add("correct");
                    feedbackEl.textContent = "¡Muy bien! 🎉";
                    setTimeout(nuevaPregunta, 900);
                } else {
                    btn.classList.add("incorrect");
                    feedbackEl.textContent = "Intenta de nuevo.";
                }
            });
            opcionesEl.appendChild(btn);
        });
    }

    nuevaPregunta();
});
