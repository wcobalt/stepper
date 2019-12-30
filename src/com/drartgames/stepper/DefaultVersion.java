package com.drartgames.stepper;

import java.util.regex.*;

public class DefaultVersion implements Version {
    private int major, minor, index, buildNumber;

    public DefaultVersion(String version) {
        Pattern regexp = Pattern.compile("([0-9]+)\\.([0-9]+)\\.([0-9]+):([0-9]+)");
        Matcher matcher = regexp.matcher(version);

        if (matcher.groupCount() == 4) {
            major = Integer.valueOf(matcher.group(0));
            minor = Integer.valueOf(matcher.group(1));
            index = Integer.valueOf(matcher.group(2));
            buildNumber = Integer.valueOf(matcher.group(3));
        } else
            throw new IllegalArgumentException("Version has unsupported syntax");
    }

    public DefaultVersion(int major, int minor, int index, int buildNumber) {
        this.major = major;
        this.minor = minor;
        this.index = index;
        this.buildNumber = buildNumber;
    }

    @Override
    public int getMajor() {
        return major;
    }

    @Override
    public int getMinor() {
        return minor;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getBuildNumber() {
        return buildNumber;
    }

    @Override
    public String getStringVersion() {
        return major + "." + minor + "." + index + ":" + buildNumber;
    }

    @Override
    public String getFullStringVersion() {
        return getStringVersion();
    }
}
