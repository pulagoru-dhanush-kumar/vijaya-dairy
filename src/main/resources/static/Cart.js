/**
 * CART MANAGEMENT
 * Handles cart operations and UI updates
 */

// ════════════════════════════════════════════════════════
// CART STATE
// ════════════════════════════════════════════════════════

let cartState = {
  items: [],
  total: 0,
  itemCount: 0
};

// ════════════════════════════════════════════════════════
// LOAD & REFRESH CART
// ════════════════════════════════════════════════════════

async function loadCart() {
  const token = getAuthToken();
  if (!token) return;

  const result = await apiGetCart(token);
  
  if (result.ok && result.data.cart) {
    cartState.items = result.data.cart.items || [];
    cartState.total = result.data.cart.carttotal || 0;
    cartState.itemCount = cartState.items.length;
    updateCartBadge();
  }
}

async function refreshCart() {
  await loadCart();
}

// ════════════════════════════════════════════════════════
// UPDATE CART BADGE
// ════════════════════════════════════════════════════════

function updateCartBadge(count = null) {
  const badge = document.getElementById('cart-count');
  if (!badge) return;

  const displayCount = count !== null ? count : cartState.itemCount;
  
  badge.textContent = displayCount > 99 ? '99+' : displayCount;
  
  // Animate badge pop
  badge.classList.remove('pop');
  void badge.offsetWidth;
  badge.classList.add('pop');

  // Update header count if exists
  const headerCount = document.getElementById('cartHeaderCount');
  if (headerCount) {
    headerCount.textContent = `${displayCount} ${displayCount === 1 ? 'item' : 'items'}`;
  }
}

// ════════════════════════════════════════════════════════
// ADD TO CART
// ════════════════════════════════════════════════════════

async function addToCart(productId, quantity = 1) {
  const token = getAuthToken();
  
  if (!token) {
    showToast('Please sign in first', 'error');
    openAuth?.();
    return false;
  }

  try {
    const result = await apiAddToCart(token, productId, quantity);
    
    if (result.ok) {
      await loadCart();
      showToast('Added to cart!');
      return true;
    } else {
      showToast(result.data.message || 'Could not add item', 'error');
      return false;
    }
  } catch (error) {
    showToast('Error adding to cart', 'error');
    return false;
  }
}

// ════════════════════════════════════════════════════════
// REMOVE FROM CART
// ════════════════════════════════════════════════════════

async function removeFromCart(productId) {
  const token = getAuthToken();
  if (!token) return false;

  try {
    const result = await apiRemoveFromCart(token, productId);
    
    if (result.ok) {
      await loadCart();
      showToast('Item removed');
      return true;
    } else {
      showToast('Could not remove item', 'error');
      return false;
    }
  } catch (error) {
    showToast('Error removing item', 'error');
    return false;
  }
}

// ════════════════════════════════════════════════════════
// ADJUST QUANTITY
// ════════════════════════════════════════════════════════

async function increaseQuantity(productId) {
  const token = getAuthToken();
  if (!token) return false;

  try {
    const result = await apiIncreaseQty(token, productId);
    
    if (result.ok) {
      await loadCart();
      return true;
    } else {
      showToast('Could not update quantity', 'error');
      return false;
    }
  } catch (error) {
    showToast('Error updating quantity', 'error');
    return false;
  }
}

async function decreaseQuantity(productId) {
  const token = getAuthToken();
  if (!token) return false;

  try {
    const result = await apiDecreaseQty(token, productId);
    
    if (result.ok) {
      await loadCart();
      return true;
    } else {
      showToast('Could not update quantity', 'error');
      return false;
    }
  } catch (error) {
    showToast('Error updating quantity', 'error');
    return false;
  }
}

// ════════════════════════════════════════════════════════
// CART PANEL TOGGLE
// ════════════════════════════════════════════════════════

async function toggleCart() {
  const panel = document.getElementById('cartPanel');
  const overlay = document.getElementById('cartOverlay');
  
  if (!panel || !overlay) return;

  const isOpen = panel.classList.contains('show');
  
  if (isOpen) {
    panel.classList.remove('show');
    overlay.classList.remove('show');
  } else {
    panel.classList.add('show');
    overlay.classList.add('show');
    await loadCartDetails();
  }
}

// ════════════════════════════════════════════════════════
// LOAD CART DETAILS FOR DISPLAY
// ════════════════════════════════════════════════════════

async function loadCartDetails() {
  const token = getAuthToken();
  const itemsContainer = document.getElementById('cartItems');
  const footer = document.getElementById('cartFooter');

  if (!itemsContainer || !footer) return;

  // Show loading state
  itemsContainer.innerHTML = `
    <div class="loading-state" style="padding: 40px 20px; text-align: center;">
      <div class="spinner" style="margin: 0 auto 16px;"></div>
      <p style="color: var(--text-muted);">Loading cart...</p>
    </div>
  `;

  if (!token) {
    itemsContainer.innerHTML = `
      <div class="cart-empty">
        <div class="cart-empty-icon">🔒</div>
        <p>Sign in to view your cart</p>
        <small>Items save automatically</small>
      </div>
    `;
    footer.style.display = 'none';
    return;
  }

  try {
    const result = await apiGetCart(token);

    if (!result.ok || !result.data.cart?.items?.length) {
      itemsContainer.innerHTML = `
        <div class="cart-empty">
          <div class="cart-empty-icon">🛒</div>
          <p>Your cart is empty</p>
          <small>Add some fresh dairy products!</small>
        </div>
      `;
      footer.style.display = 'none';
      return;
    }

    itemsContainer.innerHTML = '';
    const cart = result.data.cart;

    // Render items
    cart.items.forEach(item => {
      const product = item.product || {};
      const quantity = item.quantity || 1;
      const price = product.price || 0;
      const subtotal = price * quantity;
      const imgUrl = product.imageurl || '';
      const name = product.name || 'Product';
      const pid = product.pid;

      const itemEl = document.createElement('div');
      itemEl.className = 'cart-item';

      const imgHTML = imgUrl
        ? `<div class="cart-item-img-wrap">
             <img src="${imgUrl}" alt="${name}" 
                  onerror="this.parentElement.innerHTML='<span class=\\'img-fallback\\'>🥛</span>'">
           </div>`
        : `<div class="cart-item-img-wrap"><span class="img-fallback">🥛</span></div>`;

      itemEl.innerHTML = `
        ${imgHTML}
        <div class="cart-item-body">
          <div class="cart-item-name" title="${name}">${name}</div>
          <div class="cart-item-meta">₹${price.toFixed(2)} per unit</div>
          <div class="cart-item-subtotal">₹${subtotal.toFixed(2)}</div>
        </div>
        <div class="qty-controls">
          <button class="qty-btn del" onclick="removeFromCart(${pid}); loadCartDetails();" title="Remove item">
            <svg width="11" height="11" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
              <polyline points="3 6 5 6 21 6"/>
              <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v2"/>
            </svg>
          </button>
          <button class="qty-btn" onclick="decreaseQuantity(${pid}); loadCartDetails();">−</button>
          <span class="qty-num">${quantity}</span>
          <button class="qty-btn" onclick="increaseQuantity(${pid}); loadCartDetails();">+</button>
        </div>
      `;
      itemsContainer.appendChild(itemEl);
    });

    // Update totals
    const total = cart.carttotal || 0;
    document.getElementById('cartSubtotal').textContent = total.toFixed(2);
    document.getElementById('cartTotal').textContent = total.toFixed(2);

    footer.style.display = 'block';

    // Update badge
    updateCartBadge(cart.items.length);

  } catch (error) {
    console.error('Load cart details error:', error);
    itemsContainer.innerHTML = `
      <div class="cart-empty">
        <div class="cart-empty-icon">⚠️</div>
        <p>Could not load cart</p>
        <small>Check your connection</small>
      </div>
    `;
    footer.style.display = 'none';
  }
}

// ════════════════════════════════════════════════════════
// CART OPERATIONS (Older names for compatibility)
// ════════════════════════════════════════════════════════

async function removeItem(productId) {
  await removeFromCart(productId);
}

async function increaseItem(productId) {
  await increaseQuantity(productId);
}

async function decreaseItem(productId) {
  await decreaseQuantity(productId);
}

// ════════════════════════════════════════════════════════
// CHECKOUT FLOW
// ════════════════════════════════════════════════════════

function handleCheckout() {
  const token = getAuthToken();
  
  if (!token) {
    showToast('Please sign in to checkout', 'error');
    openAuth?.();
    return;
  }

  // Redirect to checkout page
  window.location.href = 'checkout.html';
}

function buyNow(name, price) {
  const token = getAuthToken();
  
  if (!token) {
    showToast('Please sign in to purchase', 'error');
    openAuth?.();
    return;
  }

  showToast(`Redirecting to payment for ${name}...`);
  setTimeout(() => {
    window.location.href = 'checkout.html';
  }, 1000);
}

// ════════════════════════════════════════════════════════
// INITIALIZE CART ON PAGE LOAD
// ════════════════════════════════════════════════════════

if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', loadCart);
} else {
  loadCart();
}

// Handle cart overlay click
document.addEventListener('click', (e) => {
  const cartOverlay = document.getElementById('cartOverlay');
  if (e.target === cartOverlay) {
    toggleCart();
  }
});

// Close cart on Escape key
document.addEventListener('keydown', (e) => {
  if (e.key === 'Escape') {
    const cartPanel = document.getElementById('cartPanel');
    const cartOverlay = document.getElementById('cartOverlay');
    if (cartPanel?.classList.contains('show')) {
      cartPanel.classList.remove('show');
      cartOverlay?.classList.remove('show');
    }
  }
});