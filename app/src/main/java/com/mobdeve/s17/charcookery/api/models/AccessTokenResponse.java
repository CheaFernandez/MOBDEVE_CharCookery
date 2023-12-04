package com.mobdeve.s17.charcookery.api.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AccessTokenResponse {

    @SerializedName("uid")
    private String uid;

    @SerializedName("email")
    private String email;

    @SerializedName("emailVerified")
    private boolean emailVerified;

    @SerializedName("isAnonymous")
    private boolean isAnonymous;

    @SerializedName("providerData")
    private List<ProviderData> providerData;

    @SerializedName("stsTokenManager")
    private TokenManager stsTokenManager;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("lastLoginAt")
    private String lastLoginAt;

    @SerializedName("apiKey")
    private String apiKey;

    @SerializedName("appName")
    private String appName;

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public List<ProviderData> getProviderData() {
        return providerData;
    }

    public TokenManager getStsTokenManager() {
        return stsTokenManager;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getLastLoginAt() {
        return lastLoginAt;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getAppName() {
        return appName;
    }

    // Nested classes for inner structures

    public static class ProviderData {

        @SerializedName("providerId")
        private String providerId;

        @SerializedName("uid")
        private String uid;

        @SerializedName("displayName")
        private String displayName;

        @SerializedName("email")
        private String email;

        @SerializedName("phoneNumber")
        private String phoneNumber;

        @SerializedName("photoURL")
        private String photoURL;

        public String getProviderId() {
            return providerId;
        }

        public String getUid() {
            return uid;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getPhotoURL() {
            return photoURL;
        }
    }

    public static class TokenManager {

        @SerializedName("refreshToken")
        private String refreshToken;

        @SerializedName("accessToken")
        private String accessToken;

        @SerializedName("expirationTime")
        private long expirationTime;

        public String getRefreshToken() {
            return refreshToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public long getExpirationTime() {
            return expirationTime;
        }
    }
}
