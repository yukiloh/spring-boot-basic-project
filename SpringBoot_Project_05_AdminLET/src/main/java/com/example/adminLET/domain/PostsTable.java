package com.example.adminLET.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "posts_table")
public class PostsTable {
    /**
     * 文章编码
     */
    @Id
    @Column(name = "post_guid")
    private String postGuid;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章发布时间
     */
    @Column(name = "time_published")
    private Date timePublished;

    /**
     * 文章状态（0草稿 1已发布的文章 2待审核的文章 3被拒绝文章 4定时发布的文章）
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 文章别名
     */
    private String alias;

    /**
     * 文章得分
     */
    private Short score;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 文章正文
     */
    private String main;

    /**
     * 文章作者对象
     */
    @Column(name = "AUTHORS")
    private String authors;

    /**
     * 获取文章编码
     *
     * @return post_guid - 文章编码
     */
    public String getPostGuid() {
        return postGuid;
    }

    /**
     * 设置文章编码
     *
     * @param postGuid 文章编码
     */
    public void setPostGuid(String postGuid) {
        this.postGuid = postGuid;
    }

    /**
     * 获取文章标题
     *
     * @return title - 文章标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置文章标题
     *
     * @param title 文章标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取文章发布时间
     *
     * @return time_published - 文章发布时间
     */
    public Date getTimePublished() {
        return timePublished;
    }

    /**
     * 设置文章发布时间
     *
     * @param timePublished 文章发布时间
     */
    public void setTimePublished(Date timePublished) {
        this.timePublished = timePublished;
    }

    /**
     * 获取文章状态（0草稿 1已发布的文章 2待审核的文章 3被拒绝文章 4定时发布的文章）
     *
     * @return STATUS - 文章状态（0草稿 1已发布的文章 2待审核的文章 3被拒绝文章 4定时发布的文章）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置文章状态（0草稿 1已发布的文章 2待审核的文章 3被拒绝文章 4定时发布的文章）
     *
     * @param status 文章状态（0草稿 1已发布的文章 2待审核的文章 3被拒绝文章 4定时发布的文章）
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取文章别名
     *
     * @return alias - 文章别名
     */
    public String getAlias() {
        return alias;
    }

    /**
     * 设置文章别名
     *
     * @param alias 文章别名
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 获取文章得分
     *
     * @return score - 文章得分
     */
    public Short getScore() {
        return score;
    }

    /**
     * 设置文章得分
     *
     * @param score 文章得分
     */
    public void setScore(Short score) {
        this.score = score;
    }

    /**
     * 获取文章摘要
     *
     * @return summary - 文章摘要
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置文章摘要
     *
     * @param summary 文章摘要
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 获取文章正文
     *
     * @return main - 文章正文
     */
    public String getMain() {
        return main;
    }

    /**
     * 设置文章正文
     *
     * @param main 文章正文
     */
    public void setMain(String main) {
        this.main = main;
    }

    /**
     * 获取文章作者对象
     *
     * @return AUTHORS - 文章作者对象
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * 设置文章作者对象
     *
     * @param authors 文章作者对象
     */
    public void setAuthors(String authors) {
        this.authors = authors;
    }
}