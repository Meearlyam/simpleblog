package com.innowisegroup.simpleblog.model;

import lombok.Getter;

import java.util.Locale;

public enum UserRole {

    /**
     * Super-administrator. A user who is relevant for multiblogs. It can do everything, including managing the
     * activities of the administrators of individual blogs in a multi-blog structure.
     */
    SUPER_ADMIN("super_admin"),

    /**
     * Administrator. Can do anything.
     */
    ADMIN("admin"),

    /**
     * Editor. It can manage authors and contributors, edit all posts and comments accordingly, and create pages.
     */
    EDITOR("editor"),

    /**
     * Author. It can publish posts, create tags and categories, add images to the blog, edit its posts and
     * comments in the future. It can also approve comments to its posts, send comments to spam.
     */
    AUTHOR("author"),

    /**
     * Contributor. It can change his profile and publish simple posts.
     */
    CONTRIBUTOR("contributor"),

    /**
     * Subscriber. The simplest registered user. It has minimal rights — it can only view information.
     * The only thing that he can change is some data in his profile.
     */
    SUBSCRIBER("subscriber");

    @Getter
    private final String label;

    UserRole(String label) {
        this.label = label;
    }

    public static UserRole valueOfLabel(String label) {
        label = label.toLowerCase(Locale.ROOT);
        for(UserRole userRole : values()) {
            if(userRole.label.equalsIgnoreCase(label)) {
                return userRole;
            }
        }
        return null;
    }
}
