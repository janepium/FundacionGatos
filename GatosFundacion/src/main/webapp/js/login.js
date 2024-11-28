/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

// Capturar el formulario y los campos
const form = document.getElementById("login-form");
const usernameInput = document.getElementById("username");
const passwordInput = document.getElementById("password");
const errorMessage = document.getElementById("error-message");

// Escuchar el evento de envío del formulario
form.addEventListener("submit", (event) => {
    event.preventDefault(); // Evitar que el formulario se envíe por defecto

    // Obtener los valores de usuario y contraseña
    const username = usernameInput.value.trim();
    const password = passwordInput.value;

    // Verificar si los valores son correctos
    if (username === "admin" && password === "nilo123") {
        // Redirigir a la página del menú administrativo
        window.location.href = "http://localhost:8080/GatosFundacion/menuadmin.html";
    } else {
        // Mostrar mensaje de error
        errorMessage.textContent = "Usuario o contraseña incorrectos.";
        errorMessage.style.display = "block";
    }
});

