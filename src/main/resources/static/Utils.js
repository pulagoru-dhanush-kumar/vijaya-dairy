/**
 * UTILITY FUNCTIONS
 * Common helpers for UI, theme, and notifications
 */

// ════════════════════════════════════════════════════════
// TOAST NOTIFICATIONS
// ════════════════════════════════════════════════════════

function showToast(message, type = 'success') {
  const container = document.getElementById('toastContainer');
  if (!container) return;

  const toast = document.createElement('div');
  toast.className = `toast ${type}`;
  
  const icon = {
    success: '✅',
    error: '❌',
    info: 'ℹ️',
    warning: '⚠️'
  }[type] || '✓';

  toast.textContent = `${icon} ${message}`;
  container.appendChild(toast);

  // Auto remove after 3 seconds
  setTimeout(() => {
    toast.style.animation = 'toastOut 0.3s ease forwards';
    setTimeout(() => toast.remove(), 300);
  }, 3000);
}

// ════════════════════════════════════════════════════════
// THEME MANAGEMENT
// ════════════════════════════════════════════════════════

let isDark = false;

function applyStoredTheme() {
  isDark = localStorage.getItem('theme') === 'dark';
  if (isDark) applyDarkStyles();
}

function applyDarkStyles() {
  const styles = document.getElementById('darkStyles');
  if (!styles) return;

  styles.textContent = `
    :root {
      --cream: #0f1810;
      --cream-dark: #172217;
      --white: #1a2b1e;
      --border: #2d4533;
      --text: #e8f5ed;
      --text-mid: #b0d4bc;
      --text-muted: #7a9e83;
      --green-light: #162a1e;
    }
    body { background: #0f1810; }
    header { background: rgba(15, 24, 16, 0.95); }
    .auth-modal,
    .dropdown-content,
    .payment-modal,
    .confirmation-modal,
    footer { background: #1a2b1e; }
    .auth-form input,
    .coupon-input,
    .address-input { 
      background: #0f1810;
      color: #e8f5ed;
    }
    footer { background: #080e09; }
    #cartPanel { background: #1a2b1e; }
    .cart-footer { background: #0f1810; }
    .cart-item:hover { background: #172217; }
    .cart-item-img-wrap { 
      background: #172217;
      border-color: #2d4533;
    }
    .cart-summary-box { 
      background: #0f1810;
      border-color: #2d4533;
    }
    .skeleton {
      background: linear-gradient(90deg, #172217 25%, #1e2e20 50%, #172217 75%);
      background-size: 200% 100%;
    }
    .checkout-section,
    .payment-summary {
      background: #1a2b1e;
    }
    .checkout-item {
      background: #172217;
    }
    .checkout-item:hover {
      background: #1e2e20;
    }
  `;
}

function toggleTheme() {
  isDark = !isDark;
  if (isDark) {
    applyDarkStyles();
    localStorage.setItem('theme', 'dark');
  } else {
    const styles = document.getElementById('darkStyles');
    if (styles) styles.textContent = '';
    localStorage.setItem('theme', 'light');
  }
}

// ════════════════════════════════════════════════════════
// VALIDATION HELPERS
// ════════════════════════════════════════════════════════

function validateEmail(email) {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return regex.test(email);
}

function validatePhone(phone) {
  const regex = /^[6-9]\d{9}$/; // Indian phone number
  return regex.test(phone.replace(/\D/g, ''));
}

function validatePassword(password) {
  return password && password.length >= 6;
}

// ════════════════════════════════════════════════════════
// FORMAT HELPERS
// ════════════════════════════════════════════════════════

function formatCategory(category) {
  return (category || '')
    .replace(/_/g, ' ')
    .replace(/\b\w/g, char => char.toUpperCase());
}

function formatCurrency(amount) {
  return parseFloat(amount).toFixed(2);
}

function formatPrice(amount) {
  return `₹${formatCurrency(amount)}`;
}

function formatDate(date) {
  return new Date(date).toLocaleDateString('en-IN', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  });
}

function formatDateTime(dateTime) {
  return new Date(dateTime).toLocaleDateString('en-IN', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  });
}

// ════════════════════════════════════════════════════════
// STRING HELPERS
// ════════════════════════════════════════════════════════

function truncate(str, length = 50) {
  return (str || '').length > length 
    ? (str || '').substring(0, length) + '...' 
    : str;
}

function capitalize(str) {
  return (str || '')
    .toLowerCase()
    .charAt(0)
    .toUpperCase() + (str || '').slice(1).toLowerCase();
}

// ════════════════════════════════════════════════════════
// STORAGE HELPERS
// ════════════════════════════════════════════════════════

function getStorage(key, defaultValue = null) {
  try {
    const value = localStorage.getItem(key);
    return value ? JSON.parse(value) : defaultValue;
  } catch {
    return defaultValue;
  }
}

function setStorage(key, value) {
  try {
    localStorage.setItem(key, JSON.stringify(value));
    return true;
  } catch {
    console.error('Storage error:', key);
    return false;
  }
}

function removeStorage(key) {
  try {
    localStorage.removeItem(key);
    return true;
  } catch {
    return false;
  }
}

// ════════════════════════════════════════════════════════
// DOM HELPERS
// ════════════════════════════════════════════════════════

function $(selector) {
  return document.querySelector(selector);
}

function $$(selector) {
  return document.querySelectorAll(selector);
}

function hide(element) {
  if (element) element.style.display = 'none';
}

function show(element, displayType = 'block') {
  if (element) element.style.display = displayType;
}

function toggle(element) {
  if (element) {
    element.style.display = element.style.display === 'none' ? 'block' : 'none';
  }
}

function addClass(element, className) {
  if (element) element.classList.add(className);
}

function removeClass(element, className) {
  if (element) element.classList.remove(className);
}

function hasClass(element, className) {
  return element ? element.classList.contains(className) : false;
}

// ════════════════════════════════════════════════════════
// DEBOUNCE & THROTTLE
// ════════════════════════════════════════════════════════

function debounce(func, wait = 300) {
  let timeout;
  return function executedFunction(...args) {
    const later = () => {
      clearTimeout(timeout);
      func(...args);
    };
    clearTimeout(timeout);
    timeout = setTimeout(later, wait);
  };
}

function throttle(func, limit = 300) {
  let inThrottle;
  return function(...args) {
    if (!inThrottle) {
      func.apply(this, args);
      inThrottle = true;
      setTimeout(() => (inThrottle = false), limit);
    }
  };
}

// ════════════════════════════════════════════════════════
// COOKIE HELPERS
// ════════════════════════════════════════════════════════

function getCookie(name) {
  const nameEQ = name + '=';
  const cookies = document.cookie.split(';');
  for (let cookie of cookies) {
    cookie = cookie.trim();
    if (cookie.indexOf(nameEQ) === 0) {
      return decodeURIComponent(cookie.substring(nameEQ.length));
    }
  }
  return null;
}

function setCookie(name, value, days = 7) {
  const expires = new Date();
  expires.setTime(expires.getTime() + days * 24 * 60 * 60 * 1000);
  document.cookie = `${name}=${encodeURIComponent(value)};expires=${expires.toUTCString()};path=/`;
}

// ════════════════════════════════════════════════════════
// ASYNC HELPERS
// ════════════════════════════════════════════════════════

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

async function retryAsync(fn, retries = 3, delay = 1000) {
  for (let i = 0; i < retries; i++) {
    try {
      return await fn();
    } catch (error) {
      if (i === retries - 1) throw error;
      await sleep(delay);
    }
  }
}

// ════════════════════════════════════════════════════════
// LOG HELPERS (Development)
// ════════════════════════════════════════════════════════

function log(message, data = null) {
  console.log(`🔵 ${message}`, data || '');
}

function warn(message, data = null) {
  console.warn(`⚠️ ${message}`, data || '');
}

function error(message, err = null) {
  console.error(`❌ ${message}`, err || '');
}