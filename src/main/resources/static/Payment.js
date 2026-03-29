/**
 * RAZORPAY PAYMENT INTEGRATION
 * Handles all payment processing and verification
 */

// ════════════════════════════════════════════════════════
// PAYMENT INITIALIZATION & CHECKOUT FLOW
// ════════════════════════════════════════════════════════

let orderSummary = {
  items: [],
  subtotal: 0,
  delivery: 0,
  discount: 0,
  total: 0
};

async function loadCheckoutCart() {
  const token = localStorage.getItem('token');
  if (!token) return;

  try {
    const res = await fetch(`${CART_API}/mycart`, {
      headers: { 'Authorization': `Bearer ${token}` }
    });

    if (!res.ok) {
      showCheckoutError();
      return;
    }

    const data = await res.json();
    
    if (!data.cart || !data.cart.items || data.cart.items.length === 0) {
      showEmptyCart();
      return;
    }

    // Store order data
    orderSummary.items = data.cart.items;
    orderSummary.subtotal = data.cart.carttotal || 0;
    orderSummary.delivery = 0; // Free by default
    orderSummary.discount = 0;
    orderSummary.total = orderSummary.subtotal + orderSummary.delivery;

    renderCheckoutItems();
    updatePaymentSummary();

  } catch (error) {
    console.error('Failed to load cart:', error);
    showCheckoutError();
  }
}

function renderCheckoutItems() {
  const container = document.getElementById('checkoutItems');
  container.innerHTML = '';

  if (!orderSummary.items.length) {
    showEmptyCart();
    return;
  }

  orderSummary.items.forEach(item => {
    const product = item.product || {};
    const quantity = item.quantity || 1;
    const price = product.price || 0;
    const subtotal = price * quantity;
    const imgUrl = product.imageurl || '';
    const name = product.name || 'Product';

    const itemEl = document.createElement('div');
    itemEl.className = 'checkout-item';
    itemEl.innerHTML = `
      <div class="checkout-item-img">
        ${imgUrl ? `<img src="${imgUrl}" alt="${name}" onerror="this.style.display='none'">` : '<span style="font-size: 2rem">🥛</span>'}
      </div>
      <div class="checkout-item-details">
        <div class="checkout-item-name">${name}</div>
        <div class="checkout-item-qty">Quantity: <strong>${quantity}</strong></div>
      </div>
      <div class="checkout-item-price">
        <div class="checkout-item-unit">₹${price.toFixed(2)}/unit</div>
        <div class="checkout-item-subtotal">₹${subtotal.toFixed(2)}</div>
      </div>
    `;
    container.appendChild(itemEl);
  });
}

function updatePaymentSummary() {
  const subtotal = orderSummary.subtotal;
  const deliveryRadio = document.querySelector('input[name="delivery"]:checked');
  const isFreed = deliveryRadio?.value === 'free';
  const delivery = isFreed ? 0 : 50;
  const discount = orderSummary.discount;
  const total = subtotal + delivery - discount;

  orderSummary.delivery = delivery;
  orderSummary.total = total;

  // Update sidebar
  document.getElementById('summarySubtotal').textContent = subtotal.toFixed(2);
  
  // Delivery row
  const deliveryRow = document.getElementById('summaryDeliveryRow');
  if (isFreed) {
    deliveryRow.innerHTML = '<span>Delivery</span><span class="delivery-tag">FREE</span>';
  } else {
    deliveryRow.innerHTML = '<span>Delivery</span><span>₹50</span>';
  }

  // Discount row
  const discountRow = document.getElementById('summaryDiscountRow');
  if (discount > 0) {
    discountRow.style.display = 'flex';
    document.getElementById('summaryDiscount').textContent = discount.toFixed(2);
  } else {
    discountRow.style.display = 'none';
  }

  document.getElementById('summaryTotal').textContent = total.toFixed(2);

  // Breakdown
  document.getElementById('breakdownSubtotal').textContent = subtotal.toFixed(2);
  document.getElementById('breakdownDelivery').textContent = `₹${delivery}`;
  document.getElementById('breakdownTotal').textContent = total.toFixed(2);

  const breakdownDiscountRow = document.getElementById('breakdownDiscountRow');
  if (discount > 0) {
    breakdownDiscountRow.style.display = 'flex';
    document.getElementById('breakdownDiscount').textContent = discount.toFixed(2);
  } else {
    breakdownDiscountRow.style.display = 'none';
  }
}

// ════════════════════════════════════════════════════════
// DELIVERY & COUPON HANDLING
// ════════════════════════════════════════════════════════

document.addEventListener('change', (e) => {
  if (e.target.name === 'delivery') {
    updatePaymentSummary();
  }
});

function applyCoupon() {
  const code = (document.getElementById('couponCode').value || '').trim().toUpperCase();
  const msgEl = document.getElementById('couponMessage');

  if (!code) {
    showToast('Please enter a coupon code', 'error');
    return;
  }

  // Simulate coupon validation
  const validCoupons = {
    'FRESH10': 10,     // 10% off
    'DAIRY5': 5,       // 5% off
    'WELCOME15': 15,   // 15% off new user
    'VD20': 20         // 20% off
  };

  if (validCoupons[code]) {
    const discountPercent = validCoupons[code];
    const discount = (orderSummary.subtotal * discountPercent) / 100;
    orderSummary.discount = discount;

    msgEl.className = 'coupon-message success';
    msgEl.textContent = `✅ Coupon applied! You saved ₹${discount.toFixed(2)} (${discountPercent}% off)`;
    msgEl.style.display = 'block';

    updatePaymentSummary();
    showToast('Coupon applied successfully!');
  } else {
    msgEl.className = 'coupon-message error';
    msgEl.textContent = '❌ Invalid coupon code';
    msgEl.style.display = 'block';
    showToast('Coupon not found', 'error');
  }
}

// ════════════════════════════════════════════════════════
// RAZORPAY PAYMENT PROCESSING
// ════════════════════════════════════════════════════════

async function handlePaymentClick() {
  const token = localStorage.getItem('token');
  if (!token) {
    showToast('Please sign in to checkout', 'error');
    return;
  }

  const address = (document.getElementById('deliveryAddress').value || '').trim();
  if (!address) {
    showToast('Please enter delivery address', 'error');
    document.getElementById('deliveryAddress').focus();
    return;
  }

  if (orderSummary.total <= 0) {
    showToast('Invalid order total', 'error');
    return;
  }

  // Show payment modal
  openPaymentModal();

  // Create Razorpay order
  await createRazorpayOrder(token);
}

async function createRazorpayOrder(token) {
  try {
    const paymentBtn = document.getElementById('paymentBtn');
    paymentBtn.disabled = true;

    console.log('Creating Razorpay order...');
    const res = await fetch('http://localhost:8080/payment/checkout', {
      method: 'GET',
      headers: { 'Authorization': `Bearer ${token}` }
    });

    if (!res.ok) {
      const data = await res.json();
      throw new Error(data.error || 'Failed to create order');
    }

    const data = await res.json();
    console.log('Order created:', data);

    if (!data.data || !data.data.orderId) {
      throw new Error('Invalid response from server');
    }

    const orderData = data.data;

    // Update modal with amount
    document.getElementById('modalAmount').textContent = orderData.amount.toFixed(2);

    // Initialize Razorpay
    setTimeout(() => {
      initializeRazorpay(orderData, token);
    }, 500);

  } catch (error) {
    console.error('Order creation failed:', error);
    showToast('Failed to create order: ' + error.message, 'error');
    closePaymentModal();
    document.getElementById('paymentBtn').disabled = false;
  }
}

function initializeRazorpay(orderData, token) {
  const options = {
    key: orderData.key,
    amount: orderData.amount * 100, // Convert to paise
    currency: orderData.currency,
    order_id: orderData.orderId,
    
    prefill: {
      email: localStorage.getItem('userEmail') || 'customer@example.com',
      contact: localStorage.getItem('userPhone') || '9999999999'
    },
    
    handler: (response) => handlePaymentSuccess(response, token),
    modal: {
      ondismiss: handlePaymentDismissed
    }
  };

  console.log('Initializing Razorpay with options:', options);

  try {
    const rzp = new Razorpay(options);
    
    // Hide modal loading state
    document.getElementById('paymentModalContent').innerHTML = '';
    
    // Open Razorpay checkout
    rzp.open();

    rzp.on('payment.failed', function(response) {
      console.error('Payment failed:', response.error);
      handlePaymentFailed(response.error);
    });

  } catch (error) {
    console.error('Razorpay initialization failed:', error);
    showToast('Failed to initialize payment: ' + error.message, 'error');
    closePaymentModal();
  }
}

// ════════════════════════════════════════════════════════
// PAYMENT VERIFICATION
// ════════════════════════════════════════════════════════

async function handlePaymentSuccess(response, token) {
  console.log('Payment successful! Response:', response);

  try {
    // Show verification loading
    openPaymentModal();
    updateModalContent('Verifying payment...');

    const paymentData = {
      razorpay_order_id: response.razorpay_order_id,
      razorpay_payment_id: response.razorpay_payment_id,
      razorpay_signature: response.razorpay_signature
    };

    console.log('Verifying payment with data:', paymentData);

    // Verify on backend
    const verifyRes = await fetch('http://localhost:8080/payment/verify', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(paymentData)
    });

    const verifyData = await verifyRes.json();
    console.log('Verification response:', verifyData);

    if (verifyData.success) {
      showOrderConfirmation(response, paymentData);
    } else {
      handlePaymentVerificationFailed(verifyData.error);
    }

  } catch (error) {
    console.error('Payment verification error:', error);
    handlePaymentVerificationFailed(error.message);
  }
}

function handlePaymentDismissed() {
  console.log('Payment modal dismissed');
  closePaymentModal();
  showToast('Payment cancelled', 'error');
}

function handlePaymentFailed(error) {
  console.error('Payment failed:', error);
  closePaymentModal();
  
  const errorMsg = error.description || error.message || 'Payment failed. Please try again.';
  showToast(errorMsg, 'error');
  
  document.getElementById('paymentBtn').disabled = false;
}

function handlePaymentVerificationFailed(error) {
  console.error('Verification failed:', error);
  closePaymentModal();
  
  showToast('Payment verification failed: ' + error, 'error');
  
  document.getElementById('paymentBtn').disabled = false;
}

// ════════════════════════════════════════════════════════
// UI MODALS & STATES
// ════════════════════════════════════════════════════════

function openPaymentModal() {
  const modal = document.getElementById('paymentModal');
  modal.classList.add('show');
  
  // Default loading content
  if (!document.getElementById('paymentModalContent').textContent) {
    updateModalContent('Initializing secure payment...');
  }
}

function closePaymentModal() {
  const modal = document.getElementById('paymentModal');
  modal.classList.remove('show');
  document.getElementById('paymentModalContent').innerHTML = `
    <div class="payment-loading">
      <div class="spinner"></div>
      <p>Initializing secure payment...</p>
    </div>
  `;
}

function updateModalContent(message) {
  document.getElementById('paymentModalContent').innerHTML = `
    <div class="payment-loading">
      <div class="spinner"></div>
      <p>${message}</p>
    </div>
  `;
}

function showOrderConfirmation(paymentResponse, paymentData) {
  closePaymentModal();
  
  const modal = document.getElementById('confirmationModal');
  
  // Populate confirmation details
  const detailsEl = document.getElementById('confirmationDetails');
  detailsEl.innerHTML = `
    <div class="confirmation-detail-row">
      <span>Order ID</span>
      <strong>${paymentData.razorpay_order_id.substring(0, 20)}...</strong>
    </div>
    <div class="confirmation-detail-row">
      <span>Payment ID</span>
      <strong>${paymentData.razorpay_payment_id.substring(0, 20)}...</strong>
    </div>
    <div class="confirmation-detail-row">
      <span>Amount Paid</span>
      <strong>₹${orderSummary.total.toFixed(2)}</strong>
    </div>
    <div class="confirmation-detail-row">
      <span>Status</span>
      <strong style="color: var(--green)">✅ Paid</strong>
    </div>
  `;

  modal.classList.add('show');
  
  showToast('Payment successful! 🎉');
  
  // Disable payment button
  document.getElementById('paymentBtn').disabled = true;
}

function redirectToOrders() {
  showToast('Redirecting to orders...');
  setTimeout(() => {
    window.location.href = 'orders.html';
  }, 1000);
}

function backToProducts() {
  window.location.href = 'products.html';
}

function showCheckoutError() {
  const container = document.getElementById('checkoutItems');
  container.innerHTML = `
    <div class="loading-state">
      <p>⚠️ Failed to load cart</p>
      <p style="font-size: 0.85rem; margin-top: 8px;">Please refresh or go back to products</p>
    </div>
  `;
}

function showEmptyCart() {
  const container = document.getElementById('checkoutItems');
  container.innerHTML = `
    <div class="loading-state">
      <p>🛒 Your cart is empty</p>
      <a href="products.html" style="color: var(--green); text-decoration: none; font-weight: 600; margin-top: 12px; display: inline-block;">Continue Shopping →</a>
    </div>
  `;
}

// ════════════════════════════════════════════════════════
// WINDOW CLOSE HANDLER
// ════════════════════════════════════════════════════════

window.addEventListener('beforeunload', (e) => {
  // Warn if payment is in progress
  if (document.getElementById('paymentModal').classList.contains('show')) {
    e.preventDefault();
    e.returnValue = 'Payment is in progress. Are you sure you want to leave?';
  }
});