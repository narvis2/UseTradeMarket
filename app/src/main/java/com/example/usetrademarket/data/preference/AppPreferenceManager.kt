package com.example.usetrademarket.data.preference

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.delay

class AppPreferenceManager(
    private val context: Context
) {
    companion object {
        const val PREFERENCES_NAME = "aop-part6-chapter-01-pref"
        private const val DEFAULT_VALUE_STRING = ""
        private const val DEFAULT_VALUE_BOOLEAN = false
        private const val DEFAULT_VALUE_INT = -1
        private const val DEFAULT_VALUE_LONG = -1L
        private const val DEFAULT_VALUE_FLOAT = -1f

        const val TOKEN = "token"
        const val REFRESH_TOKEN = "refresh_token"
        const val USER_NAME = "user_name"
        const val USER_ID = "user_id"
        const val USER_EMAIL = "user_email"
        const val FCM_TOKEN = "fcm_token"
        const val NOTIFICATION_ID = "notification_id"
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private val prefs by lazy { getPreferences(context) }

    private val editor by lazy { prefs.edit() }

    /**
     * token 조회
     */
    fun getToken() : String? {
        return prefs.getString(TOKEN,null)
    }

    /**
     * refreshToken 조회
     */
    fun getRefreshToken() : String? {
        return prefs.getString(REFRESH_TOKEN, null)
    }

    /**
     * useName 조회
     */
    fun getUserName() : String? {
        return prefs.getString(USER_NAME, null)
    }

    /**
     * userId 조회
     */
    fun getUserId() : Long {
        return prefs.getLong(USER_ID, 0)
    }

    /**
     * userEmail 조회
     */
    fun getUserEmail() : String? {
        return prefs.getString(USER_EMAIL, null)
    }
    /**
     * fcmToken 조회
     */
    fun getFcmToken() : String? {
        return prefs.getString(FCM_TOKEN, null)
    }
    /**
     * notification id 조회
     */
    fun getNotificationId() : Int {
        return prefs.getInt(NOTIFICATION_ID, 0)
    }

    /**
     * token 저장
     */
    fun setToken(token: String?) {
        editor.putString(TOKEN, token)
        editor.apply()
    }

    /**
     * refreshToken 저장
     */
    fun setRefreshToken(refreshToken: String) {
        editor.putString(REFRESH_TOKEN, refreshToken)
        editor.apply()
    }

    /**
     * userName 저장
     */
    fun setUserName(userName: String) {
        editor.putString(USER_NAME, userName)
        editor.apply()
    }

    /**
     * userId 저장
     */
    fun setUserId(userId: Long) {
        editor.putLong(USER_ID, userId)
        editor.apply()
    }
    /**
     * userEmail 저장
     */
    fun setUserEmail(email: String) {
        editor.putString(USER_EMAIL, email)
        editor.apply()
    }
    /**
     * fcm 토큰 저장
     */
    fun setFcmToken(fcmToken: String) {
        editor.putString(FCM_TOKEN, fcmToken)
        editor.apply()
    }
    /**
     * notificationId 저장
     */
    fun setNotificationId(notificationId: Int) {
        editor.putInt(NOTIFICATION_ID, notificationId)
        editor.apply()
    }

    /**
     * token 제거
     */
    fun removeToken() {
        editor.putString(TOKEN, null)
        editor.apply()
    }
    /**
     * refreshToken 제거
     */
    fun removeRefreshToken() {
        editor.putString(REFRESH_TOKEN, null)
        editor.apply()
    }
    /**
     * userEmail 제거
     */
    fun removeUserEmail() {
        editor.putString(USER_EMAIL, null)
        editor.apply()
    }
    /**
     * fcm 토큰 제거
     */
    fun removeFcmToken() {
        editor.putString(FCM_TOKEN, null)
        editor.apply()
    }

    fun removeUserName() {
        editor.putString(USER_NAME, null)
        editor.apply()
    }

    /**
     * String 값 저장
     * @param context
     * @param key
     * @param value
     */
    fun setString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * boolean 값 저장
     * @param context
     * @param key
     * @param value
     */
    fun setBoolean(key: String?, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    /**
     * int 값 저장
     * @param context
     * @param key
     * @param value
     */
    fun setInt(key: String?, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    /**
     * long 값 저장
     * @param context
     * @param key
     * @param value
     */
    fun setLong(key: String?, value: Long) {
        editor.putLong(key, value)
        editor.apply()
    }

    /**
     * float 값 저장
     * @param context
     * @param key
     * @param value
     */
    fun setFloat(key: String?, value: Float) {
        editor.putFloat(key, value)
        editor.apply()
    }

    /**
     * String 값 로드
     * @param context
     * @param key
     * @return
     */
    fun getString(key: String?): String? {
        return prefs.getString(key, DEFAULT_VALUE_STRING)
    }

    /**
     * boolean 값 로드
     * @param context
     * @param key
     * @return
     */
    fun getBoolean(key: String?): Boolean {
        return prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN)
    }

    /**
     * int 값 로드
     * @param context
     * @param key
     * @return
     */
    fun getInt(key: String?): Int {
        return prefs.getInt(key, DEFAULT_VALUE_INT)
    }

    /**
     * long 값 로드
     * @param context
     * @param key
     * @return
     */
    fun getLong(key: String?): Long {
        return prefs.getLong(key, DEFAULT_VALUE_LONG)
    }

    /**
     * float 값 로드
     * @param context
     * @param key
     * @return
     */
    fun getFloat(key: String?): Float {
        return prefs.getFloat(key, DEFAULT_VALUE_FLOAT)
    }

    /**
     * 키 값 삭제
     * @param context
     * @param key
     */
    fun removeKey(key: String?) {
        editor.remove(key)
        editor.apply()
    }

    /**
     * 모든 저장 데이터 삭제
     * @param context
     */
    fun clear() {
        editor.clear()
        editor.apply()
    }

}