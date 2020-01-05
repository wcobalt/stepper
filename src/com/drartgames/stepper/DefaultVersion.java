package com.drartgames.stepper;

import java.util.regex.*;

public class DefaultVersion implements Version {
    private int major, minor, index, buildNumber;

    public DefaultVersion(String version) {
        Pattern regexp = Pattern.compile("^\\s*([0-9]+)\\.([0-9]+)\\.([0-9]+):([0-9]+)\\s*$");
        Matcher matcher = regexp.matcher(version);

        if (matcher.find() && matcher.groupCount() == 4) {
            major = Integer.valueOf(matcher.group(1));
            minor = Integer.valueOf(matcher.group(2));
            index = Integer.valueOf(matcher.group(3));
            buildNumber = Integer.valueOf(matcher.group(4));
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
        return "v" + getStringVersion();
    }

    @Override
    public boolean isHigherOrEqualThan(Version version) {
        return (major >= version.getMajor() &&
                minor >= version.getMinor() &&
                index >= version.getIndex() &&
                buildNumber >= version.getBuildNumber());
    }
}
