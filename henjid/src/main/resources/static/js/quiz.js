// Obtener moduloId desde la URL
const moduloId = window.location.pathname.split("/")[2];

/* ===========================================
   BANCO DE PREGUNTAS — A1
   =========================================== */
const preguntasA1 = [
    {
        pregunta: "¿Cuál es un saludo formal?",
        opciones: ["Hey", "Good morning", "What's up?", "Hi"],
        correcta: 1
    },
    {
        pregunta: "¿Qué significa 'How are you?'",
        opciones: ["¿De dónde eres?", "¿Cómo estás?", "¿Qué hora es?", "¿Cómo te llamas?"],
        correcta: 1
    },
    {
        pregunta: "¿Cuál es la respuesta adecuada a 'How are you?'",
        opciones: ["My name is Ana", "Blue", "I'm fine, thank you", "Ten"],
        correcta: 2
    },
    {
        pregunta: "¿Cómo se dice ‘Soy de Perú’ en inglés?",
        opciones: ["I am from Peru", "I study Peru", "Peru me", "I'm Peru"],
        correcta: 0
    },
    {
        pregunta: "¿Cuál es el color 'Yellow'?",
        opciones: ["Rojo", "Amarillo", "Azul", "Morado"],
        correcta: 1
    },
    {
        pregunta: "¿Cuál es el número 'Five'?",
        opciones: ["3", "5", "9", "7"],
        correcta: 1
    },
    {
        pregunta: "¿Cuál es la traducción correcta de 'Parents'?",
        opciones: ["Abuelos", "Hijos", "Padres", "Tíos"],
        correcta: 2
    },
    {
        pregunta: "¿Cómo se dice 'Hermana' en inglés?",
        opciones: ["Mother", "Sister", "Daughter", "Son"],
        correcta: 1
    },
    {
        pregunta: "¿Cuál es un saludo casual?",
        opciones: ["Good evening", "Good afternoon", "Hey", "Nice to meet you"],
        correcta: 2
    },
    {
        pregunta: "¿Qué significa 'What's your favorite color?'",
        opciones: ["¿Cómo estás?", "¿Cuál es tu color favorito?", "¿Qué hora es?", "¿Dónde vives?"],
        correcta: 1
    }
];


/* ===========================================
   BANCO DE PREGUNTAS — A2
   =========================================== */
const preguntasA2 = [
    {
        pregunta: "¿Cuál es la estructura correcta del presente simple?",
        opciones: ["Verbo + sujeto", "Sujeto + verbo", "Verbo + ing", "To + verbo"],
        correcta: 1
    },
    {
        pregunta: "¿Cuál es la traducción de 'She goes to school'?",
        opciones: ["Ella está yendo a la escuela", "Ella iba a la escuela", "Ella va a la escuela", "Ella fue a la escuela"],
        correcta: 2
    },
    {
        pregunta: "¿Cuál es el significado de 'near'?",
        opciones: ["Lejos de", "Cerca de", "Detrás de", "Entre"],
        correcta: 1
    },
    {
        pregunta: "¿Qué significa 'I love reading'?",
        opciones: ["No me gusta leer", "Me encanta leer", "Odio leer", "Estoy leyendo"],
        correcta: 1
    },
    {
        pregunta: "¿Cómo se dice 'Está lloviendo' en inglés?",
        opciones: ["It rains", "It is rainy", "It is raining", "It rain"],
        correcta: 2
    },
    {
        pregunta: "¿Cuál es la correcta? 'El banco está al lado del parque'",
        opciones: [
            "The bank is next to the park",
            "The bank is between the park",
            "The park is next the bank",
            "The bank is in the park"
        ],
        correcta: 0
    },
    {
        pregunta: "¿Cuál es un pasatiempo?",
        opciones: ["Studying math", "Cooking", "Working", "Sleeping"],
        correcta: 1
    },
    {
        pregunta: "¿Qué significa 'big'?",
        opciones: ["Pequeño", "Viejo", "Grande", "Caro"],
        correcta: 2
    },
    {
        pregunta: "¿Qué pregunta se usa para pedir direcciones?",
        opciones: [
            "Where is the...?",
            "How old are you?",
            "What time is it?",
            "Do you like music?"
        ],
        correcta: 0
    },
    {
        pregunta: "¿Cuál oración describe el clima?",
        opciones: [
            "The car is blue",
            "She is tall",
            "It is sunny today",
            "I like pizza"
        ],
        correcta: 2
    }
];


/* ===========================================
   SELECCIONAR QUIZ SEGÚN EL MÓDULO
   =========================================== */
let preguntas;

if (moduloId === "1") {
    preguntas = preguntasA1;
} else if (moduloId === "2") {
    preguntas = preguntasA2;
} else {
    preguntas = []; // Por si se abre un módulo sin quiz
}


/* ===========================================
   Render de preguntas
   =========================================== */
const contenedor = document.getElementById("preguntasContainer");

preguntas.forEach((p, index) => {
    const div = document.createElement("div");
    div.classList.add("quiz-question");

    let html = `<h3>${index + 1}. ${p.pregunta}</h3><div class="quiz-options">`;

    p.opciones.forEach((op, i) => {
        html += `
        <label id="preg${index}_op${i}">
            <input type="radio" name="preg${index}" value="${i}">
            ${op}
        </label>`;
    });

    html += `</div>`;
    div.innerHTML = html;
    contenedor.appendChild(div);
});


// ====================================
// Procesar Quiz
// ====================================
document.getElementById("enviarQuiz").addEventListener("click", () => {
    let correctas = 0;

    preguntas.forEach((p, index) => {
        const seleccionada = document.querySelector(`input[name="preg${index}"]:checked`);
        if (!seleccionada) return;

        const valor = parseInt(seleccionada.value);

        if (valor === p.correcta) {
            correctas++;
            pintarCorrecto(index, valor);
        } else {
            pintarIncorrecto(index, valor);
        }
    });

    // Enviar al backend
    fetch(`/quiz/${moduloId}/resultado`, {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: `puntuacion=${correctas}`
    })
    .then(r => r.text())
    .then(res => {
        mostrarModal(correctas, res === "APROBADO");
    });
});


// ====================================
// Pintar respuestas
// ====================================
function pintarCorrecto(idx, val) {
    const op = document.getElementById(`preg${idx}_op${val}`);
    op.style.background = "#d4ffd6";
    op.style.borderColor = "#27ae60";
}

function pintarIncorrecto(idx, val) {
    const op = document.getElementById(`preg${idx}_op${val}`);
    op.style.background = "#ffd4d4";
    op.style.borderColor = "#e74c3c";
}


// ====================================
// Modal de resultado (restaurado)
// ====================================
function mostrarModal(correctas, aprobado) {
    const modal = document.getElementById("resultadoModal");
    const titulo = document.getElementById("resultadoTitulo");
    const nota = document.getElementById("resultadoNota");

    const btnRevisar = document.getElementById("btnRevisar");
    const btnReintentar = document.getElementById("btnReintentar");
    const btnTerminar = document.getElementById("btnTerminar");
    const btnVolver = document.getElementById("btnVolver");

    modal.style.display = "flex";
    nota.textContent = `Tu nota: ${correctas}/10`;

    btnRevisar.style.display = "block";
    btnReintentar.style.display = "none";
    btnTerminar.style.display = "none";
    btnVolver.style.display = "none";

    if (aprobado) {
        titulo.textContent = "🎉 ¡Aprobaste!";
        titulo.style.color = "#27ae60";
        btnTerminar.style.display = "block";
    } else {
        titulo.textContent = "❌ Reprobaste";
        titulo.style.color = "#e74c3c";
        btnReintentar.style.display = "block";
    }

    btnRevisar.onclick = () => {
        modal.style.display = "none";
        activarModoRevisar();
        btnVolver.style.display = "block";
    };

    btnVolver.onclick = () => {
        modal.style.display = "flex";
        ocultarModoRevisar();
    };

    btnReintentar.onclick = () => window.location.reload();
    btnTerminar.onclick = () => window.location.href = "/anuncio";
}


// ====================================
// Activar / Desactivar modo revisar
// ====================================
function activarModoRevisar() {
    document.querySelectorAll("input[type='radio']").forEach(r => r.disabled = true);
}

function ocultarModoRevisar() {
    document.querySelectorAll("input[type='radio']").forEach(r => r.disabled = false);
}

