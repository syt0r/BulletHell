package ua.syt0r.logic

interface HealthChangeListener {
    fun onHealthChanged(oldHealth: Int, newHealth: Int)
}