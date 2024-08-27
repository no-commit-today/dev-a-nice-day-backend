package com.nocommittoday.techswipe.admin.support.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubUserResponse(
        @JsonProperty("login") String login,
        @JsonProperty("id") int id,
        @JsonProperty("node_id") String nodeId,
        @JsonProperty("avatar_url") String avatarUrl,
        @JsonProperty("gravatar_id") String gravatarId,
        @JsonProperty("url") String url,
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
        @JsonProperty("type") String type,
        @JsonProperty("site_admin") boolean siteAdmin,
        @JsonProperty("name") String name,
        @JsonProperty("company") String company,
        @JsonProperty("blog") String blog,
        @JsonProperty("location") String location,
        @JsonProperty("email") String email,
        @JsonProperty("hireable") boolean hireable,
        @JsonProperty("bio") String bio,
        @JsonProperty("twitter_username") String twitterUsername,
        @JsonProperty("public_repos") int publicRepos,
        @JsonProperty("public_gists") int publicGists,
        @JsonProperty("followers") int followers,
        @JsonProperty("following") int following,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("updated_at") String updatedAt
) {
}
