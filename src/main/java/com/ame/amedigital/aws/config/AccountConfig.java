package com.ame.amedigital.aws.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountConfig {
	
	@Value("${cloud.aws.region.static}")
    private String regionName;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

}
