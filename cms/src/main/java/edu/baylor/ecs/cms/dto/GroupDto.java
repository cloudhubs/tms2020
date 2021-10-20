package edu.baylor.ecs.cms.dto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GroupDto {
    private static final Logger logger = LogManager.getLogger(GroupDto.class.getName());
    private String category;
    private String level;
    private String language;
    private String count;

    public GroupDto() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
