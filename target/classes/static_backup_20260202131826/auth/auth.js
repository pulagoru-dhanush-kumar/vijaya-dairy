const BASE_URL = "http://localhost:8080/users";

function openModal(view) {
  document.getElementById("authModal").classList.remove("hidden");
  switchView(view);
}

function closeModal() {
  document.getElementById("authModal").classList.add("hidden");
}

function switchView(view) {
  document.querySelectorAll(".view").forEach(v => v.classList.add("hidden"));

  if (view === "login") document.getElementById("loginView").classList.remove("hidden");
  if (view === "register") document.getElementById("registerView").classList.remove("hidden");
  if (view === "forgot") document.getElementById("forgotView").classList.remove("hidden");
}

/* ---------------- LOGIN ---------------- */
function login() {
  fetch(`${BASE_URL}/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      email: loginEmail.value,
      password: loginPassword.value
    })
  })
  .then(res => res.json())
  .then(data => alert(data.message))
  .catch(err => alert("Login error"));
}

/* ---------------- REGISTER ---------------- */
function register() {
  fetch(`${BASE_URL}/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      email: regEmail.value,
      password: regPassword.value,
      confirmpassword: regConfirm.value,
      mobilenumber: regMobile.value
    })
  })
  .then(res => res.json())
  .then(data => alert(data.message))
  .catch(err => alert("Registration error"));
}

/* ---------------- FORGOT PASSWORD ---------------- */
function sendForgotOtp() {
  const email = fpEmail.value;
  fetch(`${BASE_URL}/forgotpassword?email=${encodeURIComponent(email)}`, {
    method: "POST"
  })
  .then(res => res.json())
  .then(data => alert(data.message))
  .catch(err => alert("OTP send failed"));
}

function resetPassword() {
  const params = new URLSearchParams({
    otp: fpOtp.value,
    password: fpPassword.value,
    confirmpassword: fpConfirm.value
  });

  fetch(`${BASE_URL}/resetpassword?${params.toString()}`, {
    method: "POST"
  })
  .then(res => res.json())
  .then(data => {
    alert(data.message);
    if (data.message.includes("successfully")) {
      switchView("login");
    }
  })
  .catch(err => alert("Reset failed"));
}
