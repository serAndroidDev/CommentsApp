package com.test.commentsapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@SuppressWarnings("unused")
public class Comment implements Parcelable {
    @SerializedName("postId")
    @Expose
    private String mPostId;
    @SerializedName("id")
    @Expose
    private String mCommentId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("email")
    @Expose
    private String mEmail;
    @SerializedName("body")
    @Expose
    private String mBody;

    protected Comment(Parcel in) {
        mPostId = in.readString();
        mCommentId = in.readString();
        mName = in.readString();
        mEmail = in.readString();
        mBody = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public String getPostId() {
        return mPostId;
    }

    public void setPostId(String mPostId) {
        this.mPostId = mPostId;
    }

    public String getCommentId() {
        return mCommentId;
    }

    public void setCommentId(String mCommentId) {
        this.mCommentId = mCommentId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String mBody) {
        this.mBody = mBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(mPostId, comment.mPostId) &&
                Objects.equals(mCommentId, comment.mCommentId) &&
                Objects.equals(mName, comment.mName) &&
                Objects.equals(mEmail, comment.mEmail) &&
                Objects.equals(mBody, comment.mBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mPostId, mCommentId, mName, mEmail, mBody);
    }

    @NonNull
    @Override
    public String toString() {
        return "Comment{" +
                "mPostId='" + mPostId + '\'' +
                ", mCommentId='" + mCommentId + '\'' +
                ", mName='" + mName + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mBody='" + mBody + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPostId);
        dest.writeString(mCommentId);
        dest.writeString(mName);
        dest.writeString(mEmail);
        dest.writeString(mBody);
    }
}
