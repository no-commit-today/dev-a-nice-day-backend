package com.nocommittoday.techswipe.client.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubOAuth2UserResponse(
        String login,
        String id,
        @JsonProperty("node_id") String nodeId,
        @JsonProperty("avatar_url") String avatarUrl,
        @JsonProperty("gravatar_id") String gravatarId,
        String url,
        @JsonProperty("html_url") String htmlUrl,
        @JsonProperty("followers_url") String followersUrl,
        @JsonProperty("following_url") String followingUrl,
        @JsonProperty("gists_url") String gistsUrl,
        @JsonProperty("starred_url") String starredUrl,
        @JsonProperty("subscriptions_url") String subscriptionsUrl,
        @JsonProperty("organizations_url") String organizationsUrl,
        @JsonProperty("repos_url") String reposUrl,
        @JsonProperty("events_url") String eventsUrl,
        @JsonProperty("received_events_url") String receivedEventsUrl,
        String type,
        @JsonProperty("site_admin") boolean siteAdmin,
        String name,
        String company,
        String blog,
        String location,
        String email,
        boolean hireable,
        String bio,
        @JsonProperty("twitter_username") String twitterUsername,
        @JsonProperty("public_repos") int publicRepos,
        @JsonProperty("public_gists") int publicGists,
        int followers,
        int following,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("updated_at") String updatedAt,
        @JsonProperty("private_gists") int privateGists,
        @JsonProperty("total_private_repos") int totalPrivateRepos,
        @JsonProperty("owned_private_repos") int ownedPrivateRepos,
        @JsonProperty("disk_usage") int diskUsage,
        int collaborators,
        @JsonProperty("two_factor_authentication") boolean twoFactorAuthentication,
        Plan plan
) {
    public record Plan(
            String name,
            int space,
            @JsonProperty("private_repos") int privateRepos,
            int collaborators
    ) {}
}
