package com.example.nearby_resto.utils

import android.content.Context
import com.example.nearby_resto.data.model.CartItem
import io.paperdb.Paper

class CartUtils {

    companion object {

        fun addItem(cartItem: CartItem) {
            val cart =
                getCart()

            val targetItem = cart.singleOrNull { it.product.id_menu == cartItem.product.id_menu }
            if (targetItem == null) {
                cartItem.quantity++
                cart.add(cartItem)
            } else {
                targetItem.quantity++
            }
            saveCart(cart)
        }

        fun removeItem(cartItem: CartItem, context: Context) {
            val cart =
                getCart()

            val targetItem = cart.singleOrNull { it.product.id_menu == cartItem.product.id_menu }
            if (targetItem != null) {
                if (targetItem.quantity > 0) {
                    targetItem.quantity--
                } else {
                    cart.remove(targetItem)
                }
            }

            saveCart(cart)
        }

        fun saveCart(cart: MutableList<CartItem>) {
            Paper.book().write("cart", cart)
        }

        fun getCart(): MutableList<CartItem> {
            return Paper.book().read("cart", mutableListOf())
        }

        fun clearCart(cart: MutableList<CartItem>) {
            Paper.book().destroy()
        }

        fun getShoppingCartSize(): Int {
            var cartSize = 0
            getCart().forEach {
                cartSize += it.quantity;
            }

            return cartSize
        }
    }

}