package com.ame.amedigital.aws.config;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

@Component
public class RegionDetector {
	
	private static final Log LOG = LogFactory.getLog(RegionDetector.class);
	
	@Autowired
	private AccountConfig accountConfig;
	
	private Region region;
	
	@PostConstruct
    private void detect() {
        String regionName = accountConfig.getRegionName();
        if (StringUtils.isBlank(regionName)) {
            LOG.info("No AWS Region name is configured. Detecting region we´re currently running on... " + regionName);
            Region currentRegion = Regions.getCurrentRegion();
            if (currentRegion == null) {
                LOG.warn("Could not detect AWS region. Using " + Regions.US_EAST_1);
                this.region = Region.getRegion(Regions.US_EAST_1);
            } else {
                LOG.info("Detected region is " + currentRegion);
                this.region = currentRegion;
            }
        } else {
            LOG.info("Configured AWS Region name is " + regionName);
            Regions regions = Regions.fromName(regionName);
            this.region = Region.getRegion(regions);
        }
    }

    public String getRegion() {
        return region.getName();
    }

}
