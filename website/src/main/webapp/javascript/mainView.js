let name = localStorage.getItem("name");
let token = localStorage.getItem("token");

if (name === null || token === null) {
    window.location.href = "login.jsp";
} else {
    document.getElementById("avatar-name").textContent = name
}



