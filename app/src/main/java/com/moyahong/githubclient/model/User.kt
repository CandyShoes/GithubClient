package com.moyahong.githubclient.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.*

@Keep
class User : Parcelable {
    enum class UserType {
        User, Organization
    }

    var login: String? = null
    var id: String? = null
    var name: String? = null

    @SerializedName("avatar_url")
    var avatarUrl: String? = null

    @SerializedName("html_url")
    var htmlUrl: String? = null
    var type: UserType? = null
    var company: String? = null
    var blog: String? = null
    var location: String? = null
    var bio: String? = null

    @SerializedName("public_repos")
    var publicRepos = 0

    @SerializedName("public_gists")
    var publicGists = 0
    var followers = 0
    var following = 0

    @SerializedName("created_at")
    var createdAt: Date? = null

    @SerializedName("updated_at")
    var updatedAt: Date? = null


    val isUser: Boolean
        get() = UserType.User == type

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(login)
        dest.writeString(id)
        dest.writeString(name)
        dest.writeString(avatarUrl)
        dest.writeString(htmlUrl)
        dest.writeInt(if (type == null) -1 else type!!.ordinal)
        dest.writeString(company)
        dest.writeString(blog)
        dest.writeString(location)
        dest.writeString(bio)
        dest.writeInt(publicRepos)
        dest.writeInt(publicGists)
        dest.writeInt(followers)
        dest.writeInt(following)
        dest.writeLong(if (createdAt != null) createdAt!!.time else -1)
        dest.writeLong(if (updatedAt != null) updatedAt!!.time else -1)
    }

    protected constructor(`in`: Parcel) {
        login = `in`.readString()
        id = `in`.readString()
        name = `in`.readString()
        avatarUrl = `in`.readString()
        htmlUrl = `in`.readString()
        val tmpType: Int = `in`.readInt()
        type = if (tmpType == -1) null else UserType.values()[tmpType]
        company = `in`.readString()
        blog = `in`.readString()
        location = `in`.readString()
        bio = `in`.readString()
        publicRepos = `in`.readInt()
        publicGists = `in`.readInt()
        followers = `in`.readInt()
        following = `in`.readInt()
        val tmpCreatedAt: Long = `in`.readLong()
        createdAt = if (tmpCreatedAt == -1L) null else Date(tmpCreatedAt)
        val tmpUpdatedAt: Long = `in`.readLong()
        updatedAt = if (tmpUpdatedAt == -1L) null else Date(tmpUpdatedAt)
    }

    override fun equals(obj: Any?): Boolean {
        return if (obj != null && obj is User) {
            obj.login == login
        } else super.equals(obj)
    }

    override fun toString(): String {
        return "User(login=$login, id=$id, name=$name, avatarUrl=$avatarUrl, htmlUrl=$htmlUrl, type=$type, company=$company, blog=$blog, location=$location, bio=$bio, publicRepos=$publicRepos, publicGists=$publicGists, followers=$followers, following=$following, createdAt=$createdAt, updatedAt=$updatedAt)"
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}