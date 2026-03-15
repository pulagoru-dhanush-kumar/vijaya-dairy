/*<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Messiah Vijaya Dairy | Premium Milk Products</title>

<link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;600;800&display=swap" rel="stylesheet">

<style>

 ---------- CART PANEL ---------- 
#cartPanel {
    position: fixed;
    right: 0;
    top: 0;
    width: 400px;
    max-width: 90%;
    height: 100%;
    background: #fff;
    box-shadow: -6px 0 20px rgba(0, 0, 0, 0.2);
    padding: 20px;
    display: none;
    overflow-y: auto;
    z-index: 3000;
    transition: transform 0.3s ease;
    transform: translateX(100%);
}

#cartPanel.show {
    transform: translateX(0);
}

#cartPanel h3 {
    margin-top: 0;
    font-size: 1.4rem;
    color: var(--primary);
    border-bottom: 1px solid #ddd;
    padding-bottom: 10px;
}

#cartItems p {
    margin: 10px 0;
    font-size: 0.95rem;
    line-height: 1.4;
}

#cartItems hr {
    border: none;
    border-top: 1px solid #eee;
    margin: 8px 0;
}

#cartItems button {
    margin: 0 4px;
    padding: 4px 8px;
    border-radius: 6px;
    border: 1px solid #ccc;
    cursor: pointer;
    font-size: 0.85rem;
    transition: background 0.2s;
}

#cartItems button:hover {
    background: #f3f4f6;
}

#cartTotal {
    font-weight: 700;
    color: var(--primary);
    font-size: 1.2rem;
}

#cartPanel button.close-btn {
    display: block;
    width: 100%;
    padding: 10px 0;
    margin-top: 10px;
    border: none;
    background: var(--primary);
    color: white;
    border-radius: 12px;
    cursor: pointer;
    font-size: 1rem;
    transition: background 0.3s;
}

#cartPanel button.close-btn:hover {
    background: #15803d;
}

 ---------- PRODUCT GRID ---------- 
.grid {
    max-width: 1200px;
    margin: 2rem auto;
    padding: 20px;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
    gap: 1.2rem;
}

.product-card {
    background: #fff;
    border-radius: 16px;
    padding: 12px;
    border: 1px solid #e5e7eb;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    transition: transform 0.3s, box-shadow 0.3s;
}

.product-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 12px 20px rgba(0,0,0,0.12);
}

.category-tag {
    font-size: 0.65rem;
    font-weight: 700;
    color: #16a34a;
    background: #dcfce7;
    padding: 4px 10px;
    border-radius: 12px;
    width: fit-content;
    margin: 0 auto 6px;
    text-transform: uppercase;
}

.product-card img {
    width: 100%;
    height: 120px;
    object-fit: contain;
    border-radius: 12px;
    margin-bottom: 8px;
}

.price-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 6px;
}

.price-tag {
    font-size: 1.1rem;
    font-weight: 700;
    color: var(--primary);
}

.btn-group {
    display: flex;
    gap: 6px;
    margin-top: 0.5rem;
}

.btn-primary {
    flex: 1;
    padding: 8px 0;
    border-radius: 12px;
    background: var(--primary);
    color: white;
    font-weight: 600;
    cursor: pointer;
    transition: background 0.3s;
}

.btn-primary:hover {
    background: #15803d;
}

.icon-btn {
    border: 1px solid #d1d5db;
    padding: 8px;
    border-radius: 12px;
    cursor: pointer;
    color: var(--text);
    background: #f9fafb;
    transition: background 0.2s, transform 0.2s;
}

.icon-btn:hover {
    background: #e5e7eb;
    transform: scale(1.05);
}

 ---------- RESPONSIVE ---------- 
@media(max-width: 768px){
    .grid {
        grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
    }
    #cartPanel {
        width: 300px;
    }
}

@media(max-width: 480px){
    .grid {
        grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
    }
    #cartPanel {
        width: 90%;
        padding: 16px;
    }
}

:root {
    --primary: #16a34a;  Vibrant Green 
    --primary-light: #dcfce7;
    --bg-light: #f5f5f5;
    --bg-dark: #fefefe;
    --card: #ffffff;
    --text: #1f2937;
    --text-muted: #4b5563;
    --border: #d1d5db;
    --shadow: 0 8px 18px rgba(0,0,0,0.08);
}

body {
    margin: 0;
    font-family: 'Plus Jakarta Sans', sans-serif;
    background: var(--bg-light);
    color: var(--text);
}

 ---------- HEADER ---------- 
header {
    position: sticky;
    top: 0;
    z-index: 1000;
    background: var(--card);
    padding: 1rem 5%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: var(--shadow);
    border-bottom: 1px solid var(--border);
}

.logo { font-size: 1.5rem; font-weight: 800; color: var(--primary); }

.nav-actions { display: flex; gap: 1rem; align-items: center; }

.icon-btn {
    background: none;
    border: 1px solid var(--border);
    padding: 8px;
    border-radius: 12px;
    cursor: pointer;
    color: var(--text);
    position: relative;
}

.cart-badge {
    position: absolute;
    top: -6px;
    right: -6px;
    background: #ef4444;
    color: white;
    font-size: 11px;
    width: 18px;
    height: 18px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
}

.btn-primary {
    background: var(--primary);
    color: white;
    border: none;
    padding: 8px 16px;
    border-radius: 12px;
    font-weight: 600;
    cursor: pointer;
}

 ---------- FILTER BAR ---------- 
.filter-container {
    max-width: 1200px;
    margin: 2rem auto;
    padding: 0 20px;
    display: flex;
    gap: 1rem;
    align-items: center;
    background: var(--bg-dark);
    border-radius: 12px;
    padding: 16px;
    box-shadow: var(--shadow);
}

#search {
    flex: 1;
    padding: 16px 24px;
    border-radius: 18px;
    border: 1px solid var(--border);
    background: var(--card);
    color: var(--text);
    font-size: 1.1rem;
    box-shadow: var(--shadow);
}

#sort {
    padding: 14px;
    border-radius: 14px;
    border: 1px solid var(--border);
    background: var(--card);
    color: var(--text);
    font-weight: 600;
}

 ---------- PRODUCT GRID ---------- 
.grid {
    max-width: 1200px;
    margin: 2rem auto;
    padding: 20px;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
    gap: 1rem;
}

.product-card {
    background: var(--card);
    border-radius: 14px;
    padding: 0.6rem;
    border: 1px solid var(--border);
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    transition: transform 0.3s, box-shadow 0.3s;
}

.product-card:hover {
    transform: translateY(-4px);
    box-shadow: 0 10px 20px rgba(0,0,0,0.12);
}

.category-tag {
    font-size: 0.65rem;
    font-weight: 700;
    color: var(--primary);
    background: var(--primary-light);
    padding: 3px 8px;
    border-radius: 12px;
    width: fit-content;
    margin: 0 auto 4px;
    text-transform: capitalize;
}

.product-card img {
    width: 100%;
    height: 100px;
    object-fit: contain;
    border-radius: 10px;
    margin-bottom: 4px;
}

.price-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.price-tag {
    font-size: 1.05rem;
    font-weight: 800;
    color: var(--primary);
}

.btn-group {
    display: grid;
    grid-template-columns: 1fr 36px;
    gap: 6px;
    margin-top: 0.5rem;
}

 ---------- FOOTER ---------- 
footer {
    background: #fefefe;
    color: var(--text);
    padding: 4rem 5%;
    border-top: 2px solid var(--primary-light);
}

.footer-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 2rem;
    border-bottom: 2px solid var(--primary-light);
    padding-bottom: 2rem;
}

.footer-section {
    margin-top: 2rem;
    padding-top: 2rem;
    border-top: 1px solid var(--primary-light);
}

.footer-section h2 {
    color: var(--primary);
    margin-bottom: 1rem;
}

.footer-section ul, .footer-section p {
    font-size: 0.95rem;
    line-height: 1.6;
}

 ---------- AUTH MODAL ---------- 
.modal-overlay {
    position: fixed;
    top: 0; left: 0; width: 100%; height: 100%;
    background: rgba(0,0,0,0.5);
    display: none;  Hidden by default 
    justify-content: center;
    align-items: center;
    z-index: 2000;
}

.auth-modal {
    background: var(--card);
    padding: 2rem;
    border-radius: 20px;
    width: 90%;
    max-width: 400px;
    position: relative;
    box-shadow: var(--shadow);
}

.auth-modal h2 { color: var(--primary); margin-top: 0; }

.auth-form { display: flex; flex-direction: column; gap: 1rem; }

.auth-form input {
    padding: 12px;
    border-radius: 10px;
    border: 1px solid var(--border);
    font-family: inherit;
}

.modal-close {
    position: absolute;
    top: 15px; right: 15px;
    cursor: pointer;
    font-size: 1.5rem;
}

.auth-link {
    font-size: 0.85rem;
    color: var(--primary);
    cursor: pointer;
    text-align: center;
    margin-top: 10px;
}

 User Profile Dropdown 
.user-menu { position: relative; display: inline-block; }
.dropdown-content {
    display: none;
    position: absolute;
    right: 0;
    background: var(--card);
    min-width: 200px;
    box-shadow: var(--shadow);
    border-radius: 12px;
    padding: 1rem;
    z-index: 1001;
    border: 1px solid var(--border);
}
.user-menu:hover .dropdown-content { display: block; }
.logout-btn { color: #ef4444; border: 1px solid #ef4444; background: none; width: 100%; margin-top: 10px; padding: 8px; border-radius: 8px; cursor: pointer; }
</style>
</head>

<body>

<header>
    <div class="logo">🕊️ Messiah Vijaya Dairy</div>
    <div class="nav-actions">
        <button class="icon-btn" onclick="toggleTheme()">🌓</button>
        <button class="icon-btn" onclick="toggleCart()">🛒 <span id="cart-count" class="cart-badge">0</span></button>
        <button class="btn-primary" onclick="toggleAuth()">Sign In</button>
        
    </div>
</header>

<div id="cartPanel" style="
position:fixed;
right:0;
top:0;
width:350px;
height:100%;
background:white;
box-shadow:-4px 0 10px rgba(0,0,0,0.2);
padding:20px;
display:none;
overflow:auto;
z-index:3000;
">

<h3>Your Cart</h3>

<div id="cartItems"></div>

<h4>Total: ₹<span id="cartTotal">0</span></h4>

<button onclick="toggleCart()">Close</button>

</div>


<!-- FILTER BAR -->
<div class="filter-container">
    <input id="search" placeholder="Search dairy products...">
    <select id="sort" onchange="applySort()">
        <option value="">Sort by Name / Price</option>
        <option value="priceAsc">Price ↑</option>
        <option value="priceDesc">Price ↓</option>
        <option value="nameAsc">Name A-Z</option>
    </select>
</div>
<div id="authModal" class="modal-overlay">
    <div class="auth-modal">
        <span class="modal-close" onclick="toggleAuth()">&times;</span>
        
        <div id="loginSection">
            <h2>Login</h2>
            <div class="auth-form">
                <input type="email" id="loginEmail" placeholder="Email">
                <input type="password" id="loginPass" placeholder="Password">
                <button class="btn-primary" onclick="handleLogin()">Sign In</button>
            </div>
            <p class="auth-link" onclick="switchAuth('register')">Don't have an account? Register</p>
            <p class="auth-link" onclick="switchAuth('forgot')">Forgot Password?</p>
        </div>

        <div id="registerSection" style="display:none;">
            <h2>Register</h2>
            <div class="auth-form">
                <input type="email" id="regEmail" placeholder="Email">
                <input type="password" id="regPass" placeholder="Password">
                <input type="password" id="regConfirm" placeholder="Confirm Password">
                <input type="text" id="regMobile" placeholder="Mobile Number">
                <button class="btn-primary" onclick="handleRegister()">Send OTP</button>
            </div>
            <p class="auth-link" onclick="switchAuth('login')">Back to Login</p>
        </div>

        <div id="otpSection" style="display:none;">
            <h2>Verify OTP</h2>
            <div class="auth-form">
                <input type="text" id="otpValue" placeholder="Enter OTP">
                <button class="btn-primary" onclick="handleVerifyOtp()">Verify & Register</button>
            </div>
        </div>

        <div id="forgotSection" style="display:none;">
            <h2>Reset Password</h2>
            <div class="auth-form">
                <input type="email" id="forgotEmail" placeholder="Enter registered email">
                <button class="btn-primary" onclick="handleForgot()">Send Reset OTP</button>
                <div id="resetFields" style="display:none; flex-direction: column; gap: 10px;">
                    <input type="text" id="resetOtp" placeholder="OTP">
                    <input type="password" id="newPass" placeholder="New Password">
                    <input type="password" id="newPassConfirm" placeholder="Confirm New Password">
                    <button class="btn-primary" onclick="handleReset()">Update Password</button>
                </div>
            </div>
            <p class="auth-link" onclick="switchAuth('login')">Back to Login</p>
        </div>
    </div>
</div>

<!-- PRODUCTS GRID -->
<div class="grid" id="products-container"></div>

<!-- FOOTER -->
<footer>
<div class="footer-grid">
    <div>
        <h3>Messiah Vijaya Dairy</h3>
        <p>Freshness & quality you can trust.</p>
    </div>
    <div>
        <h4>Contact</h4>
        <p>📞 +91 6304773400</p>
        <p>📧 mesiahdairy@gmail.com</p>
        <p>📍 Kotha Majeru</p>
    </div>
    <div>
        <h4>Delivery Policy</h4>
        <p>✔ Free delivery within 5 km</p>
        <p>✔ Delivery charges applicable beyond 5 km</p>
    </div>
</div>

<!-- Services & About -->
<div class="footer-section">
<h2>Our Services</h2>
<ul>
<li>Daily fresh Vijaya Dairy products</li>
<li>Bulk orders for marriages & family functions</li>
<li>Doorstep delivery to your location</li>
<li>Immediate and flexible payment options</li>
<li>Free delivery within 5 km</li>
<li>Delivery charges applicable beyond 5 km</li>
</ul>

<h2>About Messiah Vijaya Dairy</h2>
<p>
Messiah Vijaya Dairy is an authorized dairy outlet affiliated with Vijaya Dairy. Established in 2024 by Mr. Pulagoru Uday Bhaskhar.
</p>
<p>
We deliver high-quality, standardized, and hygienic dairy products directly from trusted sources. Our mission is to provide freshness, purity, and reliability to every household we serve.
</p>
</div>
</footer>

<script>
let allProducts = [];

async function loadProducts() {
    try {
        const res = await fetch("http://localhost:8080/products/all");
        const json = await res.json();
        allProducts = json.data;
        renderProducts(allProducts);
    } catch(e){console.error("Backend connection failed.");}
}

function formatCategory(cat){
    return cat.replace(/_/g," ").replace(/\b\w/g,c=>c.toUpperCase());
}

function renderProducts(products){
    const container = document.getElementById("products-container");
    container.innerHTML = "";
    products.forEach(p=>{
        const card = document.createElement("div");
        card.className = "product-card";
        card.innerHTML = `
        <div>
            <div class="category-tag">${formatCategory(p.category)}</div>
            <img src="${p.imageurl}" alt="${p.name}">
            <h3>${p.name}</h3>
            <p style="color:var(--text-muted)">${p.quantity}</p>
        </div>
        <div>
            <div class="price-row">
                <span class="price-tag">₹${p.price}</span>
            </div>
            <div class="btn-group">
                <button class="btn-primary" onclick="buyNow('${p.name}', ${p.price})">Buy</button>
                <button class="icon-btn" onclick="addToCart(${p.pid})">🛒</button>
            </div>
        </div>`;
        container.appendChild(card);
    });
}

function toggleTheme(){document.body.classList.toggle("dark");}
document.getElementById("search").addEventListener("input", e=>{
    const q = e.target.value.toLowerCase();
    renderProducts(allProducts.filter(p=>p.name.toLowerCase().includes(q)));
});
function applySort(){
    const val=document.getElementById("sort").value;
    let data=[...allProducts];
    if(val==="priceAsc") data.sort((a,b)=>a.price-b.price);
    if(val==="priceDesc") data.sort((a,b)=>b.price-a.price);
    if(val==="nameAsc") data.sort((a,b)=>a.name.localeCompare(b.name));
    renderProducts(data);
}
function buyNow(name, price){alert(`Redirecting to payment for ${name} (₹${price})...`);}

let currentUser = JSON.parse(localStorage.getItem('user')) || null;
const API_BASE = "http://localhost:8080/users";

// Initialize UI on Load
window.onload = () => {
    loadProducts();
    updateAuthUI();
    loadCart();
};

function updateAuthUI() {
    const authContainer = document.querySelector('.nav-actions');
    const existingAuthBtn = authContainer.querySelector('.btn-primary, .user-menu');
    
    if (currentUser) {
        // Logged In State
        const userHtml = `
            <div class="user-menu">
                <button class="btn-primary">👤 Account</button>
                <div class="dropdown-content">
                    <p><strong>${currentUser.name}</strong></p>
                    <p style="font-size:0.8rem">${currentUser.mobile}</p>
                    <hr>
                    <button class="logout-btn" onclick="logout()">Logout</button>
                </div>
            </div>`;
        if (existingAuthBtn) existingAuthBtn.outerHTML = userHtml;
    } else {
        // Logged Out State
        const loginBtn = `<button class="btn-primary" onclick="toggleAuth()">Sign In</button>`;
        if (existingAuthBtn) existingAuthBtn.outerHTML = loginBtn;
    }
}

// Modal Toggle Logic
function toggleAuth() {
    const modal = document.getElementById('authModal');
    modal.style.display = (modal.style.display === 'flex') ? 'none' : 'flex';
}

function switchAuth(section) {
    document.getElementById('loginSection').style.display = section === 'login' ? 'block' : 'none';
    document.getElementById('registerSection').style.display = section === 'register' ? 'block' : 'none';
    document.getElementById('otpSection').style.display = section === 'otp' ? 'block' : 'none';
    document.getElementById('forgotSection').style.display = section === 'forgot' ? 'block' : 'none';
}

// API HANDLERS
async function handleLogin() {
    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPass').value;

    const res = await fetch(`${API_BASE}/login`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ email, password })
    });

    const data = await res.json();

    if (res.ok) {

     
        localStorage.setItem("token", data.token);

        // Fetch user details with token
        const userRes = await fetch(`${API_BASE}/me`, {
            headers:{
                "Authorization": "Bearer " + data.token
            }
        });

        const userData = await userRes.json();

        currentUser = {
            email,
            name: userData.name,
            mobile: userData.mobile
        };

        localStorage.setItem('user', JSON.stringify(currentUser));

        location.reload();

    } else {
        alert(data.message);
    }
}
async function handleRegister() {
    const email = document.getElementById('regEmail').value;
    const password = document.getElementById('regPass').value;
    const confirmpassword = document.getElementById('regConfirm').value;
    const mobilenumber = document.getElementById('regMobile').value;

    const res = await fetch(`${API_BASE}/register`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ email, password, confirmpassword, mobilenumber })
    });
    const data = await res.json();
    if(res.ok) switchAuth('otp'); else alert(data.message);
}

async function handleVerifyOtp() {
    const otp = document.getElementById('otpValue').value;
    const res = await fetch(`${API_BASE}/verifyotp?otp=${otp}`, { method: 'POST' });
    const data = await res.json();
    if(res.ok) { alert("Success! Please Login."); switchAuth('login'); } else alert(data.message);
}

async function handleForgot() {
    const email = document.getElementById('forgotEmail').value;
    const res = await fetch(`${API_BASE}/forgotpassword?email=${email}`, { method: 'POST' });
    if(res.ok) document.getElementById('resetFields').style.display = 'flex';
}

async function handleReset() {
    const otp = document.getElementById('resetOtp').value;
    const password = document.getElementById('newPass').value;
    const confirmpassword = document.getElementById('newPassConfirm').value;
    
    const res = await fetch(`${API_BASE}/resetpassword?otp=${otp}&password=${password}&confirmpassword=${confirmpassword}`, { method: 'POST' });
    if(res.ok) { alert("Password updated!"); switchAuth('login'); } else alert("Failed to update.");
}

function logout() {
    localStorage.removeItem('user');
    location.reload();
}



const CART_API = "http://localhost:8080/cart";

async function addToCart(productId) {

    const token = localStorage.getItem("token");

    if(!token){
        alert("Please login first");
        toggleAuth();
        return;
    }

    try{

        const res = await fetch(`${CART_API}/addtocart?productid=${productId}&quantity=1`,{
            method:"POST",
            headers:{
                "Authorization":"Bearer " + token
            }
        });

        const data = await res.json();

        if(res.ok){
            updateCartBadge(data.cart.items.length);
            alert("Product added to cart");
        }else{
            alert("Failed to add product");
        }

    }catch(err){
        console.error(err);
    }
}


async function loadCart(){

    const token = localStorage.getItem("token");

    if(!token) return;

    try{

        const res = await fetch(`${CART_API}/mycart`,{
            headers:{
                "Authorization":"Bearer " + token
            }
        });

        const data = await res.json();

        if(res.ok){
            updateCartBadge(data.cart.items.length);
        }

    }catch(err){
        console.error(err);
    }
}

function updateCartBadge(count){
    const badge = document.getElementById("cart-count");
    if(badge){
        badge.innerText = count;
    }
}

function toggleCart(){

    const panel = document.getElementById("cartPanel");

    if(panel.style.display === "block"){
        panel.style.display = "none";
    }else{
        panel.style.display = "block";
        loadCartDetails();
    }
}

async function loadCartDetails(){

    const token = localStorage.getItem("token");

    const res = await fetch(`${CART_API}/mycart`,{
        headers:{
            "Authorization":"Bearer " + token
        }
    });

    const data = await res.json();

    const itemsContainer = document.getElementById("cartItems");

    itemsContainer.innerHTML = "";

    data.cart.items.forEach(item=>{

        const div = document.createElement("div");

        div.innerHTML = `
        <p>
        ${item.product.name} - ₹${item.product.price}
        <br>
        Qty: ${item.quantity}

        <button onclick="increaseItem(${item.product.pid})">+</button>
        <button onclick="decreaseItem(${item.product.pid})">-</button>
        <button onclick="removeItem(${item.product.pid})">Remove</button>

        </p>
        <hr>
        `;

        itemsContainer.appendChild(div);

    });

    document.getElementById("cartTotal").innerText = data.cart.carttotal;

}async function increaseItem(productId){

    const token = localStorage.getItem("token");

    await fetch(`${CART_API}/increase?productid=${productId}`,{
        method:"POST",
        headers:{
            "Authorization":"Bearer " + token
        }
    });

    loadCartDetails();
    loadCart();
}async function decreaseItem(productId){

    const token = localStorage.getItem("token");

    await fetch(`${CART_API}/decrease?productid=${productId}`,{
        method:"POST",
        headers:{
            "Authorization":"Bearer " + token
        }
    });

    loadCartDetails();
    loadCart();
}async function removeItem(productId){

    const token = localStorage.getItem("token");

    await fetch(`${CART_API}/removeproduct?productid=${productId}`,{
        method:"POST",
        headers:{
            "Authorization":"Bearer " + token
        }
    });

    loadCartDetails();
    loadCart();
}

</script>
</body>
</html>
*/