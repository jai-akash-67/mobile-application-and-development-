package com.gcollector.utils

import android.content.Context
import android.content.SharedPreferences
import com.gcollector.models.HistoryItem
import com.gcollector.models.Post
import com.gcollector.models.UserProfile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PrefHelper {

    private const val PREF_NAME = "GCollectorPrefs"
    private val gson = Gson()

    private fun prefs(ctx: Context): SharedPreferences =
        ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // ── Users ────────────────────────────────────────────────────────────
    fun getUsers(ctx: Context): MutableList<Pair<String, String>> {
        val json = prefs(ctx).getString("users", "[]") ?: "[]"
        val type = object : TypeToken<MutableList<Map<String, String>>>() {}.type
        val list: MutableList<Map<String, String>> = gson.fromJson(json, type)
        return list.map { Pair(it["u"] ?: "", it["p"] ?: "") }.toMutableList()
    }

    fun addUser(ctx: Context, username: String, password: String) {
        val users = getUsers(ctx)
        users.add(Pair(username, password))
        val mapList = users.map { mapOf("u" to it.first, "p" to it.second) }
        prefs(ctx).edit().putString("users", gson.toJson(mapList)).apply()
    }

    fun validateUser(ctx: Context, username: String, password: String): Boolean =
        getUsers(ctx).any { it.first == username && it.second == password }

    // ── Profile ──────────────────────────────────────────────────────────
    fun saveProfile(ctx: Context, profile: UserProfile) {
        prefs(ctx).edit().putString("profile", gson.toJson(profile)).apply()
    }

    fun getProfile(ctx: Context): UserProfile? {
        val json = prefs(ctx).getString("profile", null) ?: return null
        return gson.fromJson(json, UserProfile::class.java)
    }

    // ── Coins ────────────────────────────────────────────────────────────
    fun getCoins(ctx: Context): Int = prefs(ctx).getInt("coins", 0)

    fun addCoins(ctx: Context, amount: Int) {
        val current = getCoins(ctx)
        prefs(ctx).edit().putInt("coins", current + amount).apply()
    }

    // ── Location ─────────────────────────────────────────────────────────
    fun saveLocation(ctx: Context, lat: Double, lng: Double) {
        prefs(ctx).edit()
            .putString("lat", lat.toString())
            .putString("lng", lng.toString())
            .apply()
    }

    fun getLocation(ctx: Context): Pair<String, String> {
        val lat = prefs(ctx).getString("lat", "N/A") ?: "N/A"
        val lng = prefs(ctx).getString("lng", "N/A") ?: "N/A"
        return Pair(lat, lng)
    }

    // ── History ──────────────────────────────────────────────────────────
    fun getHistory(ctx: Context): MutableList<HistoryItem> {
        val json = prefs(ctx).getString("history", "[]") ?: "[]"
        val type = object : TypeToken<MutableList<HistoryItem>>() {}.type
        return gson.fromJson(json, type)
    }

    fun addHistory(ctx: Context, item: HistoryItem) {
        val list = getHistory(ctx)
        list.add(item)
        prefs(ctx).edit().putString("history", gson.toJson(list)).apply()
    }

    // ── Posts ────────────────────────────────────────────────────────────
    fun getPosts(ctx: Context): MutableList<Post> {
        val json = prefs(ctx).getString("posts", null)
        if (json == null) {
            val defaults = mutableListOf(
                Post("Clean India 🌿", 5),
                Post("Recycling drive ♻️", 3)
            )
            prefs(ctx).edit().putString("posts", gson.toJson(defaults)).apply()
            return defaults
        }
        val type = object : TypeToken<MutableList<Post>>() {}.type
        return gson.fromJson(json, type)
    }

    fun savePosts(ctx: Context, posts: MutableList<Post>) {
        prefs(ctx).edit().putString("posts", gson.toJson(posts)).apply()
    }

    // ── Theme ────────────────────────────────────────────────────────────
    fun isLightTheme(ctx: Context): Boolean = prefs(ctx).getBoolean("lightTheme", false)

    fun setLightTheme(ctx: Context, light: Boolean) {
        prefs(ctx).edit().putBoolean("lightTheme", light).apply()
    }
}
