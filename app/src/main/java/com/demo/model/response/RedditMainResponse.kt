package com.demo.model.response

data class RedditMainResponse(
    var `data`: Data?,
    var kind: String?
)

data class Data(
    var after: String?,
    var before: Any?,
    var children: List<Children>?,
    var dist: Int?,
    var modhash: String?
)

data class Children(
    var `data`: DataX?,
    var kind: String?
)

data class DataX(
    var all_awardings: List<AllAwarding?>?,
    var allow_live_comments: Boolean?,
    var approved_at_utc: Any?,
    var approved_by: Any?,
    var archived: Boolean?,
    var author: String?,
    var author_flair_background_color: Any?,
    var author_flair_css_class: String?,
    var author_flair_richtext: List<Any?>?,
    var author_flair_template_id: String?,
    var author_flair_text: String?,
    var author_flair_text_color: String?,
    var author_flair_type: String?,
    var author_fullname: String?,
    var author_patreon_flair: Boolean?,
    var author_premium: Boolean?,
    var awarders: List<Any?>?,
    var banned_at_utc: Any?,
    var banned_by: Any?,
    var can_gild: Boolean?,
    var can_mod_post: Boolean?,
    var category: Any?,
    var clicked: Boolean?,
    var content_categories: Any?,
    var contest_mode: Boolean?,
    var created: Int?,
    var created_utc: Int?,
    var discussion_type: Any?,
    var distinguished: Any?,
    var domain: String?,
    var downs: Int?,
    var gilded: Int?,
    var gildings: Gildings?,
    var hidden: Boolean?,
    var hide_score: Boolean?,
    var id: String?,
    var is_crosspostable: Boolean?,
    var is_meta: Boolean?,
    var is_original_content: Boolean?,
    var is_reddit_media_domain: Boolean?,
    var is_robot_indexable: Boolean?,
    var is_self: Boolean?,
    var is_video: Boolean?,
    var likes: Any?,
    var link_flair_background_color: String?,
    var link_flair_css_class: String?,
    var link_flair_richtext: List<Any?>?,
    var link_flair_template_id: String?,
    var link_flair_text: String?,
    var link_flair_text_color: String?,
    var link_flair_type: String?,
    var locked: Boolean?,
    var media: Any?,
    var media_embed: MediaEmbed?,
    var media_only: Boolean?,
    var mod_note: Any?,
    var mod_reason_by: Any?,
    var mod_reason_title: Any?,
    var mod_reports: List<Any?>?,
    var name: String?,
    var no_follow: Boolean?,
    var num_comments: Int?,
    var num_crossposts: Int?,
    var num_reports: Any?,
    var over_18: Boolean?,
    var parent_whitelist_status: String?,
    var permalink: String?,
    var pinned: Boolean?,
    var post_hint: String?,
    var preview: Preview?,
    var pwls: Int?,
    var quarantine: Boolean?,
    var removal_reason: Any?,
    var removed_by: Any?,
    var removed_by_category: Any?,
    var report_reasons: Any?,
    var saved: Boolean?,
    var score: Int?,
    var secure_media: Any?,
    var secure_media_embed: SecureMediaEmbed?,
    var selftext: String?,
    var selftext_html: Any?,
    var send_replies: Boolean?,
    var spoiler: Boolean?,
    var stickied: Boolean?,
    var subreddit: String?,
    var subreddit_id: String?,
    var subreddit_name_prefixed: String?,
    var subreddit_subscribers: Int?,
    var subreddit_type: String?,
    var suggested_sort: Any?,
    var thumbnail: String?,
    var thumbnail_height: Int?,
    var thumbnail_width: Int?,
    var title: String?,
    var total_awards_received: Int?,
    var ups: Int?,
    var url: String?,
    var user_reports: List<Any?>?,
    var view_count: Any?,
    var visited: Boolean?,
    var whitelist_status: String?,
    var wls: Int?
)

data class AllAwarding(
    var award_sub_type: String?,
    var award_type: String?,
    var coin_price: Int?,
    var coin_reward: Int?,
    var count: Int?,
    var days_of_drip_extension: Int?,
    var days_of_premium: Int?,
    var description: String?,
    var end_date: Any?,
    var icon_format: Any?,
    var icon_height: Int?,
    var icon_url: String?,
    var icon_width: Int?,
    var id: String?,
    var is_enabled: Boolean?,
    var is_new: Boolean?,
    var name: String?,
    var resized_icons: List<ResizedIcon?>?,
    var start_date: Any?,
    var subreddit_coin_reward: Int?,
    var subreddit_id: Any?
)

data class ResizedIcon(
    var height: Int?,
    var url: String?,
    var width: Int?
)

data class Gildings(
    var gid_2: Int?
)

class MediaEmbed(
)

data class Preview(
    var enabled: Boolean?,
    var images: List<Image?>?
)

data class Image(
    var id: String?,
    var resolutions: List<Resolution?>?,
    var source: Source?,
    var variants: Variants?
)

data class Resolution(
    var height: Int?,
    var url: String?,
    var width: Int?
)

data class Source(
    var height: Int?,
    var url: String?,
    var width: Int?
)

class Variants(
)

class SecureMediaEmbed(
)