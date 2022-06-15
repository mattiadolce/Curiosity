package com.example.curiosity

class AreeInteresseModel {

        private var isSelected = false
        private var player: String? = null
        fun getPlayer(): String? {
            return player
        }
        fun setPlayer(player: String?) {
            this.player = player
        }
        fun getSelected(): Boolean {
            return isSelected
        }
        fun setSelected(selected: Boolean) {
            isSelected = selected
        }

}