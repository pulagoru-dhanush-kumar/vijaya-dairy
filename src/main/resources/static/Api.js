/**
 * API INTEGRATION
 * Centralized API calls for all endpoints
 */

// ════════════════════════════════════════════════════════
// API CONFIGURATION
// ════════════════════════════════════════════════════════

const API_BASE = 'http://localhost:8080';

const API_ENDPOINTS = {
  // Auth
  LOGIN: `${API_BASE}/users/login`,
  REGISTER: `${API_BASE}/users/register`,
  VERIFY_OTP: `${API_BASE}/users/verifyotp`,
  FORGOT_PASSWORD: `${API_BASE}/users/forgotpassword`,
  RESET_PASSWORD: `${API_BASE}/users/resetpassword`,
  GET_USER: `${API_BASE}/users/me`,
  
  // Products
  GET_PRODUCTS: `${API_BASE}/products/all`,
  GET_PRODUCT: `${API_BASE}/products/:id`,
  
  // Cart
  CART_API: `${API_BASE}/cart`,
  GET_CART: `${API_BASE}/cart/mycart`,
  ADD_TO_CART: `${API_BASE}/cart/addtocart`,
  REMOVE_FROM_CART: `${API_BASE}/cart/removeproduct`,
  INCREASE_QTY: `${API_BASE}/cart/increase`,
  DECREASE_QTY: `${API_BASE}/cart/decrease`,
  
  // Payment
  CHECKOUT: `${API_BASE}/payment/checkout`,
  VERIFY_PAYMENT: `${API_BASE}/payment/verify`,
  TEST_SIGNATURE: `${API_BASE}/test/test-signature`,
  
  // Orders
  GET_ORDERS: `${API_BASE}/orders`,
  GET_ORDER: `${API_BASE}/orders/:id`,
};

// ════════════════════════════════════════════════════════
// AUTH API CALLS
// ════════════════════════════════════════════════════════

async function apiLogin(email, password) {
  try {
    const res = await fetch(API_ENDPOINTS.LOGIN, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    console.error('Login API error:', error);
    return {
      ok: false,
      data: { message: 'Connection failed' }
    };
  }
}

async function apiRegister(email, password, confirmpassword, mobilenumber) {
  try {
    const res = await fetch(API_ENDPOINTS.REGISTER, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password, confirmpassword, mobilenumber })
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    return {
      ok: false,
      data: { message: 'Connection failed' }
    };
  }
}

async function apiVerifyOtp(otp) {
  try {
    const res = await fetch(`${API_ENDPOINTS.VERIFY_OTP}?otp=${encodeURIComponent(otp)}`, {
      method: 'POST'
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    return {
      ok: false,
      data: { message: 'Connection failed' }
    };
  }
}

async function apiForgotPassword(email) {
  try {
    const res = await fetch(`${API_ENDPOINTS.FORGOT_PASSWORD}?email=${encodeURIComponent(email)}`, {
      method: 'POST'
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    return {
      ok: false,
      data: { message: 'Connection failed' }
    };
  }
}

async function apiResetPassword(otp, password, confirmpassword) {
  try {
    const res = await fetch(
      `${API_ENDPOINTS.RESET_PASSWORD}?otp=${encodeURIComponent(otp)}&password=${encodeURIComponent(password)}&confirmpassword=${encodeURIComponent(confirmpassword)}`,
      { method: 'POST' }
    );
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    return {
      ok: false,
      data: { message: 'Connection failed' }
    };
  }
}

async function apiGetUser(token) {
  try {
    const res = await fetch(API_ENDPOINTS.GET_USER, {
      headers: { 'Authorization': `Bearer ${token}` }
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    return {
      ok: false,
      data: null
    };
  }
}

// ════════════════════════════════════════════════════════
// PRODUCTS API CALLS
// ════════════════════════════════════════════════════════

async function apiGetAllProducts() {
  try {
    const res = await fetch(API_ENDPOINTS.GET_PRODUCTS);
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    console.error('Products API error:', error);
    return {
      ok: false,
      data: { data: [] }
    };
  }
}

async function apiGetProduct(productId) {
  try {
    const res = await fetch(API_ENDPOINTS.GET_PRODUCT.replace(':id', productId));
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    return {
      ok: false,
      data: null
    };
  }
}

// ════════════════════════════════════════════════════════
// CART API CALLS
// ════════════════════════════════════════════════════════

async function apiGetCart(token) {
  try {
    const res = await fetch(API_ENDPOINTS.GET_CART, {
      headers: { 'Authorization': `Bearer ${token}` }
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    return {
      ok: false,
      data: null
    };
  }
}

async function apiAddToCart(token, productId, quantity = 1) {
  try {
    const res = await fetch(`${API_ENDPOINTS.ADD_TO_CART}?productid=${productId}&quantity=${quantity}`, {
      method: 'POST',
      headers: { 'Authorization': `Bearer ${token}` }
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    return {
      ok: false,
      data: { message: 'Failed to add item' }
    };
  }
}

async function apiRemoveFromCart(token, productId) {
  try {
    const res = await fetch(`${API_ENDPOINTS.REMOVE_FROM_CART}?productid=${productId}`, {
      method: 'POST',
      headers: { 'Authorization': `Bearer ${token}` }
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    return {
      ok: false,
      data: null
    };
  }
}

async function apiIncreaseQty(token, productId) {
  try {
    const res = await fetch(`${API_ENDPOINTS.INCREASE_QTY}?productid=${productId}`, {
      method: 'POST',
      headers: { 'Authorization': `Bearer ${token}` }
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    return {
      ok: false,
      data: null
    };
  }
}

async function apiDecreaseQty(token, productId) {
  try {
    const res = await fetch(`${API_ENDPOINTS.DECREASE_QTY}?productid=${productId}`, {
      method: 'POST',
      headers: { 'Authorization': `Bearer ${token}` }
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    return {
      ok: false,
      data: null
    };
  }
}

// ════════════════════════════════════════════════════════
// PAYMENT API CALLS
// ════════════════════════════════════════════════════════

async function apiCreateOrder(token) {
  try {
    const res = await fetch(API_ENDPOINTS.CHECKOUT, {
      method: 'GET',
      headers: { 'Authorization': `Bearer ${token}` }
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    console.error('Order creation error:', error);
    return {
      ok: false,
      data: { error: 'Failed to create order' }
    };
  }
}

async function apiVerifyPayment(token, orderId, paymentId, signature) {
  try {
    const res = await fetch(API_ENDPOINTS.VERIFY_PAYMENT, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        razorpay_order_id: orderId,
        razorpay_payment_id: paymentId,
        razorpay_signature: signature
      })
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    console.error('Payment verification error:', error);
    return {
      ok: false,
      data: { error: 'Verification failed' }
    };
  }
}

async function apiTestSignature(orderId, paymentId) {
  try {
    const res = await fetch(API_ENDPOINTS.TEST_SIGNATURE, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ orderId, paymentId })
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    return {
      ok: false,
      data: { error: 'Failed to generate signature' }
    };
  }
}

// ════════════════════════════════════════════════════════
// ORDERS API CALLS
// ════════════════════════════════════════════════════════

async function apiGetOrders(token) {
  try {
    const res = await fetch(API_ENDPOINTS.GET_ORDERS, {
      headers: { 'Authorization': `Bearer ${token}` }
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    return {
      ok: false,
      data: { data: [] }
    };
  }
}

async function apiGetOrder(token, orderId) {
  try {
    const res = await fetch(API_ENDPOINTS.GET_ORDER.replace(':id', orderId), {
      headers: { 'Authorization': `Bearer ${token}` }
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    return {
      ok: false,
      data: null
    };
  }
}

// ════════════════════════════════════════════════════════
// HELPER: Get token from localStorage
// ════════════════════════════════════════════════════════

function getAuthToken() {
  return localStorage.getItem('token') || null;
}

function hasAuthToken() {
  return !!getAuthToken();
}

// ════════════════════════════════════════════════════════
// HELPER: Make authenticated request
// ════════════════════════════════════════════════════════

async function makeAuthRequest(url, options = {}) {
  const token = getAuthToken();
  
  if (!token) {
    return {
      ok: false,
      data: { message: 'Not authenticated' }
    };
  }

  const headers = {
    ...options.headers,
    'Authorization': `Bearer ${token}`
  };

  try {
    const res = await fetch(url, {
      ...options,
      headers
    });
    return {
      ok: res.ok,
      data: await res.json()
    };
  } catch (error) {
    console.error('Auth request error:', error);
    return {
      ok: false,
      data: { message: 'Request failed' }
    };
  }
}

// ════════════════════════════════════════════════════════
// EXPORTS (for modular use if needed)
// ════════════════════════════════════════════════════════

// All API functions are available globally
// Example: apiLogin(email, password)
// Example: apiGetAllProducts()
// Example: apiAddToCart(token, productId, 1)